/**
 * Module: PrintListener
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

    /*
     * When Soar issues a print, it makes a call to this interface which must be
     * registered beforehand.
     */
public interface PrintListener
{
        public void printEvent(String message);
}
