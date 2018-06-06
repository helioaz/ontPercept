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
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import sml.Agent;
import sml.Identifier;
import sml.Kernel;
import sml.smlUpdateEventId;
import sml.Kernel.UpdateEventInterface;



/**
 * A simple, asynchronous, interactive environment example. A loop is started
 * that accepts input from standard in. The input is simply parsed and, unless
 * it is a special command, it is asynchronously passed on to the Soar input
 * link. If it is a special command, then the environment acts on the command.
 * 
*/

public class Soar implements Runnable
    {
      /*
       * When Soar issues a print, it makes a call to PrintListener interface which must be
       * registered beforehand.
       *
       * Cleans up the related code.
       */
       public static final PrintListener consoleListener = new PrintListener()
       {
	    public void printEvent(String message)
                {
                    /*
                     * Clearly distinguish output from the agent. In a GUI this
                     * would go to its own text box. Reprint the prompt since it
                     * is likely clobbered.
                     */
                    System.out.print(String.format("%nSoar interface: %s%n%s", message, "> "));
                }
       };



        /*
         * Create objects for Soar kernel and agent
         */

        private  final Kernel kernel;

        private  final Agent agent;



        /*
         * We hold on to each message's WME as we add it so that we can easily
         * remove them when a clear command is issued, without having to remove
         * and re-add the root messages WME.
         */
        private final List<Identifier> messages = new ArrayList<Identifier>();

        /*
         * Here is the shared state that we must synchronize between the
         * environment and the Soar interface. These are messages that have been
         * entered in to standard in but not yet added to the input-link.
         */
        private final BlockingQueue<String> lines = new LinkedBlockingQueue<String>();

        /*
         * This variable is used to gracefully ask Soar to stop executing.
         */
        private final AtomicBoolean stopSoar = new AtomicBoolean(true);

        /*
         * Output from the Soar interface gets sent to this print listener so
         * that it may be clearly distinguished from other print calls.
         */
        private PrintListener pl = consoleListener;



	

     public Soar(CmdSocket cmdInterface)
        {

	    String response;

            kernel = Kernel.CreateKernelInNewThread();
            //kernel = Kernel.CreateKernelInNewThread(kernelPort);
            /*
             * As long as the execution environment is set up correctly and Java
             * can find the Java_smlClientInterface library this will return a
             * kernel object even if there is an error. In the event of an
             * error, the kernel object will have diagnostic information in it
             * for retrieval. If the execution environment isn't set up
             * correctly, however, a runtime exception will be thrown here when
             * it cannot load the shared library.
             */
            if (kernel.HadError())
            {
                System.err.println("Error creating kernel: " + kernel.GetLastErrorDescription());
                System.exit(1);
            }

            agent = kernel.CreateAgent("soar");
            /*
             * The agent, however, does return null on error. Use kernel to get
             * diagnostic information.
             */
            if (agent == null) {
                System.err.println("Error creating agent: " + kernel.GetLastErrorDescription());
                System.exit(1);
            }


            /*
             * Load the productions.
             */
            if (!agent.LoadProductions( "butler.soar")) {
                System.err.println("Can't load butler.soar");
                System.exit(1);
            }



            // Start a debugger                ***  Inserted by Helio   It does not work !!!   
	    //agent.SpawnDebugger(kernelPort, "/home/hazevedo/localbin/SoarSuite_9.6.0/SoarJavaDebugger.jar");


	    // Generate a wait state rather than a state-no-change impasse.     ***Inserted by Helio
	    //
	    // Note that: If you are using Soar version 9.4.0, the command syntax is other: waitsnc -e
	    //            If you are using Soar version 9.6.0, the command syntax is other: soar wait-snc on
            // You must manually give this command in the Soar Debugger or change next line appropiately 
            response = agent.ExecuteCommandLine("soar wait-snc on") ;			
	    System.out.print("\nCHECK THE RESPONSE OF wait-snc: " + response);
	    System.out.println("\nIF THE ANSWER IS STRANGE SEE FILE Soar.java:68");


            UpdateEvent upEvent = new UpdateEvent(agent, lines,  pl, stopSoar, cmdInterface);   // defines a handler to update event  


            /*
             * Register for update event that fires after our agent passes its output phase. Our update 
             * handler will post new messages on the input-link and read any commands off of the output link.
             */
            kernel.RegisterForUpdateEvent( smlUpdateEventId.smlEVENT_AFTER_ALL_OUTPUT_PHASES, upEvent, null);
            /*
             * That final null parameter above is for user data. Anything passed
             * there will appear in the updateEventHandler's Object data parameter.
             */
        }

 
        public void run()
        {
            pl.printEvent("Starting Soar with OntSense.");

            /*
             * Reset the request to stop just before we start up.
             */
            stopSoar.set(false);

            /*
             * This run call blocks, hopefully you're in a separate thread or
             * things will hang here.
             */
            kernel.RunAllAgentsForever();
            pl.printEvent("Stopping Soar.");
        }

        public void stop()
        {
            /*
             * Politely ask the agent to stop itself during its next update event.
             * 
             */

            stopSoar.set(true);

        }

        public void put(String line)
        {
            /*
             * Queue the line for addition to the messages input-link. The queue
             * handles synchronization issues.
             */
            try
            {
                lines.put(line);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }

        public void shutdown()
        {
            /*
             * In case things are running, make a half-hearted attempt to stop
             * them first. This is a hack. Instead, you should be registering
             * for kernel events that tell you when Soar starts and stops so
             * that you know when you need to stop Soar and when it actually
             * stops. See kernel.RegisterForSystemEvent()
             */
            stop();
            try
            {
                Thread.sleep(500);
            }
            catch (InterruptedException ignored)
            {

            }
            /*
             * This will remove any agents and close the listener thread that
             * listens for things like remote debugger connections.
             */
            kernel.Shutdown();
        }
    }
