/*
 * Copyright (c) 1995, 2013, Oracle and/or its affiliates. All rights reserved.
 *
 */

/**
 * Module: CmdSocket
 *
 * Implements a communication link with the robotic agent.
 * This communication allows the  send of commands and reception of responses
 * of these commands. 
 * The communication link is implemented using Sockets.  
 * See: https://github.com/helioaz/ontSense
 *
 * Helio Azevedo   march/2018
 */


import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

import java.net.Socket;
import java.net.UnknownHostException;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.LinkedBlockingQueue;

import java.util.HashMap;



public class CmdSocket implements Runnable {





        private Socket robotSocket;

	private PrintWriter out;

	private BufferedReader in ;

	private BlockingQueue<String> cmdResponse = new LinkedBlockingQueue<String>();

        private AtomicBoolean keepRunning = new AtomicBoolean(true);	

	/*
	 * CmdSocket()
	 * Class constructor. Basically, instantiate sockets and queues.
         */
	public  CmdSocket(String hostName, int portNumber) {
		try {

	            robotSocket = new Socket(hostName, portNumber);
		    out = new PrintWriter(robotSocket.getOutputStream(), true);
                    in = new BufferedReader( new InputStreamReader(robotSocket.getInputStream()));	        
	        } catch (UnknownHostException e) {
	            System.err.println("Don't know about host " + hostName);
	            System.exit(1);
	        } catch (IOException e) {
	            System.err.println("Couldn't get I/O for the connection to " + hostName);
	            System.err.println("Exception Detail: " + e);
	            System.exit(1);
	        } 

	}

	/*
	 * run()
	 * We use a runnable method just because we do not know when the response arrives.
         * So, in.readLine() is a blockable operation, i.e., it can block the thread.
         */
	public void run()  {

		try {
            		while (keepRunning.get()) {
				cmdResponse.put(in.readLine());
            		}
 	        
	        } catch (InterruptedException e) {
	            System.err.println("CmdSocket:run() => Interrupted Exception");
	            System.exit(1);
	        } catch (IOException e) {
	            System.err.println("CmdSocket:run() => IO Exception");
	            System.exit(1);
	        } 



  
	}


	/*
	 * thereIsResponse()
	 * Does the response already come?
         */

	public boolean isThereResponse() {

		return ! cmdResponse.isEmpty();


 
	}


	/*
	 * getResponse()
	 * If there is an answer it will be delivered, otherwise the calling 
         * thread will be blocked until the response arrives.
         */

	public String getResponse() throws InterruptedException{
		return cmdResponse.take();
	}


	/*
	 * sendCommand(String cmd)
	 * Send a new command via the socket communication line.
         */
	public void sendCommand(String cmd) {		
		out.println(cmd);
	}

	/*
	 * stopCommunication()
	 * 
         */
        public void stopCommunication() throws IOException{
	    keepRunning.set(false);
            try
            {
                Thread.sleep(500); 	// give a break to the run() stop
            }
            catch (InterruptedException ignored)
            {

            }

	}
}
