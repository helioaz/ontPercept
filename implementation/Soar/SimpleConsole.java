/**
 * Module: SimpleConsole
 *
 * A simple, asynchronous, interactive environment for exercise OntSense ontology and RHS simulator.
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
 * Illustrative run:PrintListener
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





    /*
     * This class is what the user will be interacting with when running this
     * simple environment. It listens for console input, parses it very naively
     * for some basic commands, and dumps everything else to the Soar interface
     * so that it can hand them off to the agent.
     * 
     * The constructor blocks as it executes until quit is entered at the
     * prompt.
     */
public class SimpleConsole
    {
        /*
         * We keep a reference to the Soar interface.
         */
        private final Soar soar;

        /*
         * Various string constants.
         */
        

        private final String START = "start";

        private final String RUN = "run";

        private final String STOP = "stop";

        private final String QUIT = "quit";

        /*
         * Wrap standard input so that it is a bit easier to use.
         */
        private final BufferedReader input = new BufferedReader( new InputStreamReader(System.in));

        /*
         * Create an executor service to run Soar in since it blocks.
         */
        private final ExecutorService exec = Executors.newSingleThreadExecutor();



        public SimpleConsole(Soar soar)
        {
            this.soar = soar;
        }


        /*
         * This is the main command loop. The input isn't fully sanitized so don't bang on it too hard.
         * 
         * If it encounters start or run it executes Soar in a new thread. Stop makes it request Soar to stop,
         * and quit breaks out eventually shutting down Soar and the executor service and closing the program.
         * 
         * Anything else just gets put to Soar so that it will show up as a message.
         * 
         */
        public void startConsole()
        {
            try
            {
                System.out.println("\n\nWarning: If you are planning use the debugger then do not activate using  START or RUN commands."); 
                System.out.println("         Instead, start SoarDebugger, in the sequence, press Remote and Step buttons.");



                while (!Thread.interrupted())
                {
                    System.out.print("> ");

                    String line = input.readLine();
                    if (line == null || line.isEmpty())
                    {
                        continue;
                    }

                    if (line.equalsIgnoreCase(START) || line.equalsIgnoreCase(RUN))
                    {
                        /*
                         * Start Soar in its own thread. Soar implements Runnable which means it will 
                         * execute the run() method. Runnable which means it will execute the run() method.
                         * 
                         * Note that multiple successive calls to this will cause the run calls
                         * to queue up in the executor service. Calling start twice then stop
                         * will cause soar to stop (when stop is issued) and then immediately
                         * start up again as the second start gets processed. All of this can be 
                         * prevented by registering for system start and stop events with the
                         * kernel. See kernel.RegisterForSystemEvent()
                         */
                        exec.execute(soar);

                    }
                    else if (line.equalsIgnoreCase(STOP))
                    {
                        /*
                         * Politely ask Soar to stop itself asynchronously. Does
                         * not block.
                         */
                        soar.stop();
                    }
                    else if (line.equalsIgnoreCase(QUIT))
                    {
                        /*
                         * We're done, goto finally.
                         */
                        soar.stop();
                        break;
                    }
                    else
                    {
                        /*
                         * Everything else gets added to the Soar input link.
                         */
                        soar.put(line);
                    }
                }
            }
            catch (IOException e)
            {
                /*
                 * Thrown by readLine.
                 */
                e.printStackTrace();
            }
            finally
            {
                /*
                 * Shutdown the Soar interface and the executor service.
                 */
                System.out.println("\n\nCogArch: Ending Soar execution ..."); 
                soar.shutdown();
                System.out.println("\n\nCogArch: Ending Exec execution ..."); 
                exec.shutdown();
            }
        }
    }

