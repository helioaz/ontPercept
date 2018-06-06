

/**
 * Module: CogArch
 * Responsible: Helio Azevedo   / USP São Carlos
 *
 * A simple environment for exercise OntSense ontology and RHS simulator.
 * See: https://github.com/helioaz/ontSense
 *
 * Basically , A loop is started that accepts input from standard input. The input  include start 
 * (or run) to start running Soar, stop to stop running Soar, and quit to exit the program.
 * 
 * There are four threads of execution:
 * 1. Main Thread: implemented by the module SimpleConsole.java. 
 *    Interface with the standard input for processing user commands.
 *
 * 2. Soar Thread: implemented by the module UpdateEvent.java and Soar.java. 
 *    Read inputs using the UpdateEvent Handler, process rules and eventually generates 
 *    commands for the robotic agent.
 * 
 * 3. Socket Thread: implemented by the module CmdSocket.java. Receives response of the commands sent
 *    to the robotic agent.
 * 
 * 4. Triple store communication Thread: implemented by the package ontSenseJavaAPI.
 *    Receives perception information from a Fuseki triple store that implements the ontoSense ontology.
 * 
 * How  Run this Tutorial:
 *   1. Start a Fuseki dataStore: 
 *      ~/localbin/apache-jena-fuseki-2.5.0$  ./fuseki-server -update
 *       Note that in the directory ~/localbin/apache-jena-fuseki-2.5.0 we have a file to configure
 *       the logging for java package log4j: log4j.properties
 *       Only errors are presented. If desire a more verbose information edit or remove 
 *       this configuration file.
 * 
 *   2. Execute this program:
 *      $ ./execSoarUSP.sh CogArc
 *      Note 1: we are using a shell script to turn easy the access to ontSenseJavaAPI and other stuff.
 *      You can build your script or use a IDE like Eclipse.
 *      Note 2: Below a simple execution sequence:
 * 		> run 
 * 		> Soar interface: Starting Soar. 
 * 		> stop 
 *		> Soar interface: Stopping Soar. 
 * 		> quit
 *      Ctl. C to finish the execution.
 *      
 * Running without a robotic agent: 
 * To do that we need generate two source of data inputs, one for perception and another for commands.
 *  +++ Inserte additional individuals or query the dataset using your browser 
 *       - use the URL : localhost:3030
 *       - access the dataset \ontsense
 *       - to update localhost:3030/ontsense use =>     use: http://localhost:3030/ontsense/update
 *       - to query  localhost:3030/ontsense use =>     use: http://localhost:3030/ontsense/sparql
 *      You could use the file GetCracker_SPARQL.txt that contain the SPARQL scripts for the Get Cracker mission.
 * 
 *  +++ We need a Socket server to receive commands an generate the responses.
 *      Execute the UnityServer (Do this first of all starting):
 *      $ ./execSoarUSP.sh UnityServer
 *       
 *  
 *
 * 
 * Note that the Soar Java Debugger can connect to this environment as soon as the environment is started.
 * Issue the -remote switch (inside Java Debugger window) when launching the debugger. Avoid starting and stopping Soar 
 * from within the debugger when doing this, however, since this environment is not written to handle that correctly
 * in all cases.
 * 
 * This program  is based on the example created by @author voigtjr:
 * https://soar.eecs.umich.edu/downloads/files/SimpleAsyncEnv.java 
 */

import java.util.Locale;
import java.io.IOException;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import br.usp.ontSenseJavaAPI.*;

public class CogArc
{
    public static void main(String[] args)
    {

	// default  host=127.0.0.1  , port = 12345
        String hostName = "127.0.0.1";
        int portNumber = 12345;

	// to ensure point as separator in decimal numbers
	Locale.setDefault(new Locale("en", "CA"));

 

        /*
         * Create environment to  to run Socket
         */

        //   Define the host and port number of the Socket 

        System.out.println("Number of args = " + String.valueOf(args.length ) );    
	if  (args.length == 2) {
            	  hostName = args[0];
            	  portNumber = Integer.parseInt(args[1]);
        }
	else if ( args.length != 0) {
                 System.err.println("Usage: java CogArc <host name> <port number>");
                 System.exit(1);
        }

        System.out.println("Using host= " + hostName + " port = " + String.valueOf(portNumber) );
        System.out.println("Starting Socket. Warning: do not forget to unable the windows firewall");



	// the socket runs in a separeted thread, since it could blocks
	CmdSocket cmdInterface = new CmdSocket(hostName, portNumber);
        ExecutorService exec = Executors.newSingleThreadExecutor();
	exec.execute(cmdInterface);


        /*
         * Create environment to create a connection with the triple store Fuseki
         */

        String ONT_SENSE_URL = "http://localhost:3030/ontsense";	// URL address of the triple store
	SparqlEndPoint instanceSparql;					// mantain the instance of Sparql end point access

	try {		
	    // define the sparql endpoint  used to access the triple store and load OWL file with ontSense ontology
            instanceSparql = SparqlEndPoint.getInstance();   		// gets the instance for the  singleton object  
									// just one time at main method is enough
	    instanceSparql.init ("../../Projeto/Protege/ontsense.owl", ONT_SENSE_URL );
	    }
	    catch (Exception e) {
		System.out.println("CogArc:main:" + e);
		System.exit(1); 		// Hello Houston. We have a serius problem!
	    }


        /*
         * Create the Soar interface and hand it to the simple console.
         */

	// tripleStoreInterface = new TripleStore(tripleHost);	// connects with Triple Store with OntSense ontology
        Soar soar = new Soar(cmdInterface);			// create a object to process Soar productions
        SimpleConsole console = new SimpleConsole(soar); 	// create an object for terminal interface
	console.startConsole();					// blocks until quit


	/*
	 *   get statistic information
	 */
	long meanPeriod = UpdateEvent.totalSliceTime / UpdateEvent.countHandlerActivations;					
  	System.out.println("CogArc:main: Number of Handler Activations = " + String.valueOf(UpdateEvent.countHandlerActivations) +
			   "    Mean Period =  " + String.valueOf(meanPeriod));


        /*
         * Shutdown the Socket interface and the executor service.
         */
        exec.shutdown();
        
        try { cmdInterface.stopCommunication(); }
        catch (IOException e) {
			System.err.println("CogArc:main() => IO Exception");
	}
    }
}
