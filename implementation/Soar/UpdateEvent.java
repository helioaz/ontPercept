/**
 * Module: Soar
 *
 * A simple, asynchronous, interactive environment for exercise OntCog ontology and RHS simulator.
 * See: https://github.com/helioaz/ontSense
 *
 * Basically , A loop is started that accepts input from standard in. The input is simply parsed and, unless
 * it is a special command, it is asynchronously passed on to the Soar input link. 
 * 
 * If it is a special command, then the environment acts on the command.The special commands include start 
 * (or run) to start running Soar, stop to stop running Soar, and quit to exit the program.
 * 
 * The Soar interface reports any non-special commands received on the command line to a messages WME on the input-link.
 * Each message has a unique id. The agent is expected to issue commands on the output-link.
 * 
 * One of these Soar commands is print, and it simply prints the contents of the "content" parameter. The other 
 * command is clear which tells the Soar interface to remove any existing messages from the input-link.
 * 
 * The agent included with this registers a few canned responses with itself (responding to "hello" and "jump") and
 * then uses these canned responses if their triggers come in. If it doesn't have a canned response then it simply
 * echos the message as a command on the output link.
 * 
 * 
 * Since the agent doesn't have a response for clear, issuing this means it gets passed as a command on the output-link
 * which then, in turn, causes the environment to clear the input link.
 * 
 * 
 * Illustrative run:
 * 
 * > start 
 * > Soar interface: Starting Soar. 
 * > hello 
 * > Soar interface: Agent said: Greetings Professor Falken. 
 * > jump 
 * > Soar interface: Agent said: How high? 
 * > xyzzy 
 * > Soar interface: Unknown command received: xyzzy 
 * > clear 
 * > Soar interface: Messages cleared. 
 * > stop 
 * > Soar interface: Stopping Soar. 
 * > quit
 * 
 * 
 * Note that the Soar Java Debugger can connect to this environment as soon as the environment is started.
 * Issue the -remote switch (inside Java Debugger window) when launching the debugger. Avoid starting and stopping Soar 
 * from within the debugger when doing this, however, since this environment is not written to handle that correctly
 * in all cases.
 * 
 * <p>
 * This program  is based on the example created by @author voigtjr:
 * https://soar.eecs.umich.edu/downloads/files/SimpleAsyncEnv.java
 * 
 */


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.UUID;

import java.net.*;
import java.io.*;

import sml.Agent;
import sml.Identifier;
import sml.Kernel;
import sml.smlUpdateEventId;
import sml.Kernel.UpdateEventInterface;

import br.usp.ontSenseJavaAPI.*;

/**
 * A simple, asynchronous, interactive environment example. A loop is started
 * that accepts input from standard in. The input is simply parsed and, unless
 * it is a special command, it is asynchronously passed on to the Soar input
 * link. If it is a special command, then the environment acts on the command.
 * 
*/

public class UpdateEvent implements UpdateEventInterface  
{

	// statistics for handler
	public static int countHandlerActivations = 0;			// count the number of calls to handler
	public static long totalSliceTime;				// the amount of total handler slaces time 
	public static long beginTime;					// the instant of handler's last activation					



        // parameter type definition
    	private static final int WithId = 1;
    	private static final int WithPos = 2;
    	private static final int WithAngle = 3;
    	private static final int WithString = 4;
    	private static final int WithoutParameter = 5;
	   

        // command type definition
	private enum CommandType
	{
    		ActivateLeft (1, WithId),
    		ActivateRight (2, WithId),
    		DeactivateLeft (3, WithId),
    		DeactivateRight (4, WithId),
    		HeadReset (5,  WithoutParameter),
    		LeaveLeft (6, WithPos),
    		LeaveLeftID (6,  WithId),
    		LeaveRight (7,  WithPos),
    		LeaveRightID (7,  WithId),
    		LookAt (8,  WithPos),
    		LookAtID (8,  WithId),
    		LookFor (9,  WithString),
    		Move (10,  WithPos),
    		MoveID (10,  WithId),
    		Rotate (11,  WithAngle),
    		SmellLeft (12,  WithoutParameter),
    		SmellRight (13,  WithoutParameter),
    		Speech (14,  WithString),
    		TakeLeft (15,  WithId),
    		TakeRight (16,  WithId),
    		TasteLeft (17,  WithoutParameter),
    		TasteRight (18,  WithoutParameter),
    		Turn (19,  WithPos),
    		TurnID (19,  WithId),
    		CancelCommands (20, WithoutParameter),
		GetSenses (21, WithoutParameter);

   	    private final int type;
   	    private final int parameter;   
    	    CommandType(int value, int parameterValue){
                type = value;
		parameter = parameterValue;
            }
            public int getCommandType(){
                return type;
            }

            public int getCommandParameter(){
                return parameter;
            }
         }	   



        /*
         * Here is the shared state that we must synchronize between the
         * environment and the Soar interface. These are messages that have been
         * entered in to standard in but not yet added to the input-link.
         */
        private BlockingQueue<String> lines;

        /*
         * We hold on to each message's WME as we add it so that we can easily
         * remove them when a clear command is issued, without having to remove
         * and re-add the root messages WME.
         */
        private  final List<Identifier> messages  = new ArrayList<Identifier>();


       /*
         * We hold on to each operacional command sent to robotic agent. It was save
	 * to allow a merge with the response of the command. 
         */
        private  final List<String> operationalCommands  = new ArrayList<String>();


        /*
         * We hold on to each command sent to robotic agent so that we can easily
         * locate the command when the response arrives.
	 * NOte that we do not have concerns about race conditions due to the fact
         * that the operations over the Map occurs int the same thread. 
         */
        private  final  Map<String, Identifier> soarCommands = new HashMap<String, Identifier>();

        /*
         * Output from the Soar interface gets sent to this print listener so
         * that it may be clearly distinguished from other print calls.
         */
        private PrintListener pl;

        /*
         * This variable is used to gracefully ask Soar to stop executing.
         */
        private AtomicBoolean  stopSoar ;

        /*
         * We cache the root of the messages WME on the input-link so that we
         * can quickly add additional messages as they come in.
         */
        private final Identifier messagesAction, messagesHear, messagesSmell, messagesTaste,
                                 messagesTouch, messagesVision ;


        private  Agent agent;

	private  CmdSocket cmdInterface;

        private final String DELIMITER = "<|>";			// defines a separator between command fields

	private boolean firstHandlerExecution; 			// mark first Handler Execution. Just to clean up the triple store
								// We do not know how long the robotic agent have been running...

	private boolean tripleStoreUpdated;			// indication that the triple store has been updated



     /*
      * Constructor fot the class Handler class  
      */
     public UpdateEvent(Agent agent, BlockingQueue<String> lines, PrintListener pl, AtomicBoolean stopSoar,  
                        CmdSocket cmdInterf) {
	this.agent = agent;
	this.lines = lines;
	this.pl = pl;
	this.stopSoar = stopSoar;
        cmdInterface = cmdInterf;
	firstHandlerExecution = true;			// Yes. It is the first handler execution


        /*
         * Create and cache the root messages WME on the input-link so that
         * we can quickly add messages later.
        */
        messagesAction = agent.GetInputLink().CreateIdWME("action");
        messagesHear = agent.GetInputLink().CreateIdWME("hear");
        messagesSmell = agent.GetInputLink().CreateIdWME("smell");
        messagesTaste = agent.GetInputLink().CreateIdWME("taste");
        messagesTouch = agent.GetInputLink().CreateIdWME("touch");
        messagesVision = agent.GetInputLink().CreateIdWME("vision");

     }   // constructor


     /*
      * Handler for update event that fires after our agent passes its output phase.  
      * It will post new messages on the input-link and read any commands off of the output link.
      */
     public void updateEventHandler(int eventID, Object data, Kernel kernel, int runFlags)  
     {

	Identifier command; 			// defines the WME associated with a command 
	String commandID;			// UUID fo a given command sent to robotic agent
	int perceptionCount = 0;		// save a statistic of all perception senses captured from the environment 
	String commandToBeSent;			// keeps command to be sent using the interface with the agent robotic

		
	

	/*
	 *    Prepare statistics counts
	 *    I know that this statistic could be better, as example: only the effective execution time should be considered
	 */
	if (countHandlerActivations == 0) {				// first activation of counting
		beginTime = System.currentTimeMillis();			// gets current time
		totalSliceTime = 0;
		countHandlerActivations ++;
	}
	else {
		totalSliceTime += System.currentTimeMillis() - beginTime;
		beginTime = System.currentTimeMillis();			// saves current time	
		countHandlerActivations ++;
	}	
		


        /*
         * Pull each sense entry in the triple store  and post it on the input-link.
	 */
	try {		
	    	//
	    	// define the sparql endpoint  used to access the triple store and load OWL file with ontSense ontology
	    	//
            	SparqlEndPoint instanceSparql = SparqlEndPoint.getInstance();   // gets the instance for the  singleton object  

	    	//
	    	// gets all perception information
	    	//
		if (firstHandlerExecution) {
			instanceSparql.cleanUpAllInfo();					// first, clean up the house
			perceptionCount = 0;
			firstHandlerExecution = false;		
			tripleStoreUpdated = true;						// free our first read of perception events
		}
		else {
			if (tripleStoreUpdated)	{						// robotic agent update the triple store with new date 
				tripleStoreUpdated = false;
				perceptionCount = instanceSparql.executeSparqlQuery();		//get perception info
        			commandToBeSent  = UUID.randomUUID().toString();
				operationalCommands.add(commandToBeSent); 			// insert the command using the ID as a key
				commandToBeSent += DELIMITER + String.valueOf(CommandType.GetSenses.getCommandType()); 
				commandToBeSent += DELIMITER + String.valueOf(CommandType.GetSenses.getCommandParameter()) + DELIMITER;
                		cmdInterface.sendCommand (commandToBeSent);    			// send command to start a new cicle of read
				// pl.printEvent("     Command sent: " + commandToBeSent);	
			}    // end of  if (tripleStoreUpdated) ...
		}    // end of  if (firstHandlerExecution) ...
			
	} // end of  try { ...
	catch (Exception e) {
		pl.printEvent("UpdateEvent:updateEventHandler() => fail accessing triple stores" + e);
	}




        /*
         * If you are inserting new senses then remove the old one.
	 * Iterate through stored message WMEs and delete them.
	 * Deleting a WME deletes all of its children.  In this case the id and content WMEs.
	 */
        if (perceptionCount > 0) {		// We have news, so forget the past and live the present
		for (Identifier message : messages) {
			message.DestroyWME();
		}
 		messages.clear();		//  clear our cache now that it has been invalidated (it is old...)
		analyzeNewPerceptionData();	//  update the io.input-link
	}


        /*
         * Check if we have some command answer from the robotic agent
         */

	String response;		// saves the response of robotic agent (true or false)
	String commData;		// saves all stream received from robotic agent
	boolean findIt;			// find an operational command associated with the response	
	while (cmdInterface.isThereResponse()) {
        
         	// get response of a command execution
         	try { commData = cmdInterface.getResponse(); }
         	catch (InterruptedException e) {
			pl.printEvent("UpdateEvent:updateEventHandler() => InterruptedException");
			break;  // we have a problem!!! give up this interaction
		}
 		pl.printEvent("     Response received: " + commData);

		// we could have several responses in the same communication data

		while (	commData.length() > DELIMITER.length()) {					// at least the length of one delimiter must exist	
  			commandID = commData.substring(0, commData.indexOf(DELIMITER));
			commData = commData.substring(commData.indexOf(DELIMITER) + DELIMITER.length());	// move to next field == response
			response = commData.substring(0, commData.indexOf(DELIMITER));				// get response (true or false)
			commData = commData.substring(commData.indexOf(DELIMITER) + DELIMITER.length());	// move to next  message		

			//
			// First: verify it the command answer is associated with  Operational class kind (Cancel and GetSenses)
			//        Now a days, we are considering only the GetSenses command. If you need more commands then add more lines of code.
			findIt =  operationalCommands.remove(commandID); // recover and remove the command using the ID
			if (findIt) {
				tripleStoreUpdated =  true; 	 	//  we schedule a new  cicle of read, independently of the response
	 			//pl.printEvent("     Command complete: GetSenses"  + " result= " + response);
			}
			
			else {
				//
				// Second: It must be an answer of a Soar command
				//        
				command =  soarCommands.remove(commandID); // recover and remove the command using the ID
				if (command == null) {  
					pl.printEvent("UpdateEvent:updateEventHandler() => lost Command");
					break;  // we have a problem!!! give up this interaction
				}   // if (command == null) ...
		
	
			
		        	switch (response) {
		            		case "0":  		// command fail
	                    			command.AddStatusError();
		                     		break;
		            		case "1":  		// command success
		                    		command.AddStatusComplete();
		                     		break;
				}  //         	switch (response)...
	        
		 		pl.printEvent("     Command complete: " + command.GetCommandName() + " result= " + response);
			}   // end else
		}       //         while (commData.length() > DELIMITER.length())...


	}    // while (cmdInterface.isThereResponse()))..


	
        /*
         * Iterate through the new commands on the output link.
         */
        for (int index = 0; index < agent.GetNumberCommands(); ++index)
        {
               /*
                * Get the command by index. Note: avoid storing  this identifier because the agent created it
                * and may delete it at any time. If you need to store it across decision cycles, you'll need
                * to do a lot more work to make sure it is valid before attempting to use it in future
                * updates.
                */
               command = agent.GetCommand(index);



               /*
                * Get the complete command string to be sent for the robotic agent
                */
		commandToBeSent = convertCmd2String(command); 		
                cmdInterface.sendCommand (commandToBeSent);    // send command by socket
		pl.printEvent("     Command sent: " + commandToBeSent);	
		


		/*
                 * Store the identifier for easy locating later. 
		 * I know that this is wrong, but I was unable to find another way...
                 */
		commandID = commandToBeSent.substring(0, commandToBeSent.indexOf(DELIMITER));
                soarCommands.put( commandID, command);   


        }   // end for (int index = 0 ...




        /*
         * This marks any commands on the output-link as seen so they will not be encountered via
         * GetCommand on future updates if they are still on the output-link then.
         */
        agent.ClearOutputLinkChanges();

        /*
         * Finally, check to see if we have been asked to
         * stop and issue a stop if so.
         */
        if (stopSoar.get())
        {
            kernel.StopAllAgents();
        }   // end  if (stopSoar ...

     }	// end updateEventHandler method









     /*
      * convertCmd2String(Identifier cmd)
      * Receives a Identifier associated with a command present at output-link.
      * Returns null it was unable to mount the command string.
      */
     private String convertCmd2String(Identifier command) {

	int cmdId = 0;			// command Id
	int parId = 0;	        	// parameter Id
	String parameters = null;	// maintains command parameter data
	String completeCmd = null;	// the final commnand, ready to be sent 

	
	String CmdName = command.GetCommandName();			// get the commmand name
        pl.printEvent("Command name: " + CmdName);

        // switch case to process the command and get the appropriate parameter type
        switch (CmdName) {
            case "ActivateLeft":  
			cmdId = CommandType.ActivateLeft.getCommandType();
			parId = CommandType.ActivateLeft.getCommandParameter();
                     	break;
            case "ActivateRight":  
			cmdId = CommandType.ActivateRight.getCommandType();
			parId = CommandType.ActivateRight.getCommandParameter();
                     	break;
            case "DeactivateLeft":  
			cmdId = CommandType.DeactivateLeft.getCommandType();
			parId = CommandType.DeactivateLeft.getCommandParameter();
                     	break;
            case "DeactivateRight":  
			cmdId = CommandType.DeactivateRight.getCommandType();
			parId = CommandType.DeactivateRight.getCommandParameter();
                     	break;
            case "HeadReset":  
			cmdId = CommandType.HeadReset.getCommandType();
			parId = CommandType.HeadReset.getCommandParameter();
                     	break;
            case "LeaveLeft":  
			cmdId = CommandType.LeaveLeft.getCommandType();
			parId = CommandType.LeaveLeft.getCommandParameter();
                     	break;
            case "LeaveLeftID":  
			cmdId = CommandType.LeaveLeftID.getCommandType();
			parId = CommandType.LeaveLeftID.getCommandParameter();
                     	break;
            case "LeaveRight":  
			cmdId = CommandType.LeaveRight.getCommandType();
			parId = CommandType.LeaveRight.getCommandParameter();
                     	break;
            case "LeaveRightID":  
			cmdId = CommandType.LeaveRightID.getCommandType();
			parId = CommandType.LeaveRightID.getCommandParameter();
                     	break;
            case "LookAt":  
			cmdId = CommandType.LookAt.getCommandType();
			parId = CommandType.LookAt.getCommandParameter();
                     	break;
            case "LookAtID":  
			cmdId = CommandType.LookAtID.getCommandType();
			parId = CommandType.LookAtID.getCommandParameter();
                     	break;
            case "LookFor":  
			cmdId = CommandType.LookFor.getCommandType();
			parId = CommandType.LookFor.getCommandParameter();
                     	break;
            case "Move":  
			cmdId = CommandType.Move.getCommandType();
			parId = CommandType.Move.getCommandParameter();
                     	break;
            case "MoveID":  
			cmdId = CommandType.MoveID.getCommandType();
			parId = CommandType.MoveID.getCommandParameter();
                     	break;
            case "Rotate":  
			cmdId = CommandType.Rotate.getCommandType();
			parId = CommandType.Rotate.getCommandParameter();
                     	break;
            case "SmellLeft":  
			cmdId = CommandType.SmellLeft.getCommandType();
			parId = CommandType.SmellLeft.getCommandParameter();
                     	break;
            case "SmellRight":  
			cmdId = CommandType.SmellRight.getCommandType();
			parId = CommandType.SmellRight.getCommandParameter();
                     	break;
            case "Speech":  
			cmdId = CommandType.Speech.getCommandType();
			parId = CommandType.Speech.getCommandParameter();
                     	break;
            case "TakeLeft":  
			cmdId = CommandType.TakeLeft.getCommandType();
			parId = CommandType.TakeLeft.getCommandParameter();
                     	break;
            case "TakeRight":  
			cmdId = CommandType.TakeRight.getCommandType();
			parId = CommandType.TakeRight.getCommandParameter();
                     	break;
            case "TasteLeft":
			cmdId = CommandType.TasteLeft.getCommandType();
			parId = CommandType.TasteLeft.getCommandParameter();
                     	break;
            case "TasteRight":   
			cmdId = CommandType.TasteRight.getCommandType();
			parId = CommandType.TasteRight.getCommandParameter();
                     	break;
            case "Turn":  
			cmdId = CommandType.Turn.getCommandType();
			parId = CommandType.Turn.getCommandParameter();
                     	break;
            case "TurnID":  
			cmdId = CommandType.TurnID.getCommandType();
			parId = CommandType.TurnID.getCommandParameter();
                     	break;
            case "CancelCommands":  
			cmdId = CommandType.CancelCommands.getCommandType();
			parId = CommandType.CancelCommands.getCommandParameter();
                     	break;

        }    // end switch



        // compose string with parameter type information
        switch (parId) {  
            case WithId: 
                parameters = DELIMITER + command.GetParameterValue("objectId") + DELIMITER;
               break;
            case WithPos:
                parameters  = DELIMITER + command.GetParameterValue("x") + DELIMITER;
		parameters += command.GetParameterValue("y") + DELIMITER;
		parameters += command.GetParameterValue("z") + DELIMITER;
                break;
            case WithAngle:
                parameters = DELIMITER + command.GetParameterValue("angle") + DELIMITER;
                break;
            case WithString:
                parameters = DELIMITER + command.GetParameterValue("text")+ DELIMITER;		// String need a DELIMITER at the end...
                break;
            case WithoutParameter:
		parameters = DELIMITER;
                break;

        }

        // compose the command string:
	// <UUID>DELIMITER<command type>DELIMITER<parameter type>[DELIMITER<command type> ...]DELIMITER
        completeCmd  = UUID.randomUUID().toString();
	completeCmd += DELIMITER + String.valueOf(cmdId);	
	completeCmd += DELIMITER + String.valueOf(parId);
	completeCmd += parameters;
	return completeCmd;


     }   // end  convertCmd2String(Identifier cmd)






     /*
      * analyzeNewPerceptionData()
      * Go through all perception information and update the io.input-link
      */
     private void analyzeNewPerceptionData() { 

	Identifier identWME;		// pointer to identifier of new Working Memory Element (WME)

	// go through Map structure recovering the hear information
	// There is a trick here. The only processed sound is the voice. Moreover, only one 
	// command is recognized: "get cracker". This block needs to be better worked on. 
	RobotHear hearPercept;
	String sentence;
	for (Map.Entry<Long, RobotHear> e : SparqlEndPoint.hearMap.entrySet()) {
		hearPercept = e.getValue();
		if (hearPercept.getSoundType() == HearingAttribute.humanVoiceSound) {
			pl.printEvent("Input Hear: " + hearPercept.getDetail());
			sentence = hearPercept.getDetail().toLowerCase();	// everything in lower case
			if  (sentence.contains("get") && sentence.contains("cracker")) {
				// the deletion of this WME occurs in the rule Apply*cmdReception 
               			messagesAction.CreateStringWME("task", "get");
              			messagesAction.CreateStringWME("object", "Cracker");
			}
		}	
	}


	// go through Map structure recovering the vision information
	RobotVision visionPercept;
	Thing someThing; 
	long objectId;				// object identifier as used by the agent robotic 
	for (Map.Entry<Long, RobotVision> e : SparqlEndPoint.visionMap.entrySet()) {
		visionPercept = e.getValue();
		objectId = visionPercept.getGenerateBy();         		// I saw something else with my little eyes...
		someThing = SparqlEndPoint.objectMap.get(objectId);
		if (someThing == null) {						// The object does not exist in the Object Map => there is still hope
			someThing = SparqlEndPoint.humanMap.get(objectId);
			if (someThing == null) {					// The object does not exist in the Human Map => there is still hope
				someThing = SparqlEndPoint.robotMap.get(objectId);
				if (someThing == null) {				// The object also does not exist in the Robot Map => that is a problem		
					pl.printEvent("UpdateEvent:analyzeNewPerceptionData() => Vision lost an object:" + Long.toString(objectId));
					break;
				}
			}
		} 


		pl.printEvent("Input Vision: " + someThing.getTagInfo() + " " + someThing.getFragIdent());
		identWME = messagesVision.CreateIdWME(someThing.getFragIdent());
		messages.add(identWME);
		identWME.CreateStringWME("tagInfo", someThing.getTagInfo()); // Make easy to remove later
		identWME.CreateIntWME("objectId", someThing.getObjectId());
	        if (Human.class == someThing.getClass()) {			// is the runtime class of someThing equal to Human?
	   		Human mariana = (Human) someThing;
			identWME.CreateStringWME("emotion", ((Human)someThing).getEmotion());	
		}

	}


	// go through Map structure recovering the taste information
	// not necessary yet...		To be done later!!!
	// 	for (Map.Entry<Long, RobotTaste> e : SparqlEndPoint.tasteMap.entrySet())
 	//    		System.out.println(e.getKey() + ": " + e.getValue());

	// go through Map structure recovering the touch information
	// not necessary yet...		To be done later!!!
	// 	for (Map.Entry<Long, RobotTouch> e : SparqlEndPoint.touchMap.entrySet())
    	//	System.out.println(e.getKey() + ": " + e.getValue());

	// go through Map structure recovering the smell information
	// not necessary yet...		To be done later!!!
	// 	for (Map.Entry<Long, RobotSmell> e : SparqlEndPoint.smellMap.entrySet())
    	//	System.out.println(e.getKey() + ": " + e.getValue());





     }   // end analyzeNewPerceptionData()





} // end UpdateEvent class

 
