/*
 * Copyright (c) 2013, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */ 

/*
 * This program just acts like the RHS simulator. It always receive a command
 * and answer "success"
 * It was derived from Oracle examples about sockets
 * Helio Azevedo   march/2018
 */

import java.net.*;
import java.io.*;
import java.util.Scanner;


public class UnityServer {
    public static void main(String[] args) throws IOException {
        String DELIMITER = "<|>";	// defines a separator between command fields
        int portNumber = 12345;
	Scanner sc = new Scanner(System.in);

        if (args.length == 0) {
            System.err.println("Using default port number = 12345 ");
        }   else     
            if (args.length != 1) {
                System.err.println("Usage: java UnityServer [ <port number> ] ");
                System.exit(1);
             }
             else {
                portNumber = Integer.parseInt(args[0]);
            }

        
        try (
            ServerSocket serverSocket = new ServerSocket(portNumber);
            Socket clientSocket = serverSocket.accept();     
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);                   
            BufferedReader in = new BufferedReader( new InputStreamReader(clientSocket.getInputStream()));
        ) {
            String inputLine, response, cmdId;
            while ((inputLine = in.readLine()) != null) {



  		cmdId = inputLine.substring(inputLine.indexOf(DELIMITER) + DELIMITER.length());
		cmdId = cmdId.substring(0, cmdId.indexOf(DELIMITER));	// get the identifier of the command received
		if (cmdId.equals("21"))
			System.out.println("\n\n*** Receive a Command 21 (GetSenses):" + inputLine);
		else
			System.out.println("\n\n*** Receive a Command:" + inputLine);


  		response = inputLine.substring(0, inputLine.indexOf(DELIMITER) + DELIMITER.length());
		response += "1" + DELIMITER;                // finally one success !!

	        System.out.println("To send the answer please type a char ...");
	        sc.nextLine();		// read a line
                out.println(response);
		System.out.println("*** Send an answer:" + response );



            }
        } 
	catch (IOException e ) {
            System.out.println("Exception caught when trying to listen on port "
                + Integer.toString(portNumber) + " or listening for a connection");
            System.out.println(e.getMessage());
        }



    }
}






