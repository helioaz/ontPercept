///
/// This file is part of OntCog project ( https://github.com/helioaz/ontSense )
/// 
/// OntCog is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
/// the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
/// 
/// OntCog is distributed in the hope that it will be useful,  but WITHOUT ANY WARRANTY; without even the implied warranty of
/// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.See the GNU General Public License for more details.
/// 
/// 
/// You should have received a copy of the GNU General Public License  along with Foobar. 
/// If not, see<http://www.gnu.org/licenses/>.
/// 
///

/// How  Run this Tutorial:
///   1. Start a Fuseki dataStore: 
///      ~/localbin/apache-jena-fuseki-2.5.0$  ./fuseki-server -update
///      Note that in the directory ~/localbin/apache-jena-fuseki-2.5.0 we have a file to configure
///      of the logging for java package log4j: log4j.properties
///      Only errors are presented. If desire a more verbose information remove the configuration file.
///
///   2. Execute this totorial and see the individuals present in the triple store
///      remember: after read the data, all RDF triple wil be erased by the API.
///
///   3. If you desire: inserte additional individuals or query the dataset using your browser 
///      - use the URL : localhost:3030
///      - access the dataset \ontsense
///      - to update localhost:3030/ontsense use =>     use: http://localhost:3030/ontsense/update
///      - to query  localhost:3030/ontsense use =>     use: http://localhost:3030/ontsense/update
///
///   4. Ctl. C to finish the execution.
/// 


import java.lang.String;
import java.lang.Object; 			// for the getClass method
import java.util.Map;
import java.util.Scanner;

import br.usp.ontSenseJavaAPI.*;

public class Tutorial02 {


        private static final String ONT_SENSE_URL = "http://localhost:3030/ontsense";	// URL address of the triple store
	private static  SparqlEndPoint instanceSparql;					// mantain the instance of Sparql end point access	


	/// ####
	///   main(String[] argv) 
	///   Main method associated wit this tutorial.  
	///      - It get an instance  to access the triple store
	///      - initialize the triple store with ontSense ontology and some intial data
        ///      - continuously read and prints all individuals present in the store
        ///   Note that the user could insert new individuals manually and start the cicle again
        ///   To finish the execution : Ctl C 
	/// ####
	public static void main(String[] argv) {

	    Scanner sc = new Scanner(System.in);

	    try {		
	    	//
	    	// define the sparql endpoint  used to access the triple store and load OWL file with ontSense ontology
	    	//
            	instanceSparql = SparqlEndPoint.getInstance();   		// gets the instance for the  singleton object  
										// just one time at main method is enough
	    	instanceSparql.init ("/home/hazevedo/Dropbox/02_Tese_Doutorado/DesenvolvimentoTese/Projeto/Protege/ontsensePlusData.owl", ONT_SENSE_URL );
	    	//
	    	// gets all perception information
	    	//


	    	while (!Thread.interrupted()) {
			instanceSparql.executeSparqlQuery();
			printAllObjects();
	        	System.out.println("\n\nTo read more data enter any string ...");
	        	sc.nextLine();		// read a line
		}

	    }
	    catch (Exception e) {
		System.out.println(e);
		System.out.println("to stop this Tutorial enter any string ...");
		sc.nextLine();	// read a line
		System.exit(1); 		// Hello Houston. We have a problem!
	    }




	   //
	   // Finally, Just playing to recognize hierarchy between classes in Java
	   //
	   System.out.println("\n\nJust playing to recognize hierarchy between classes in Java ...");
	   Human objHuman = new Human (1961080812, "Mariana", "Human", null, null, PhysicalState.noneState, 
				       Material.organicMaterial, "http://localhost", EmotionalState.happinessEmotion);

	   Thing objThing = objHuman;					// saves the reference in a Thing object

	   if (Human.class == objThing.getClass()) {			// is the runtime class of objThing equal to Human?
	   		Human mariana = (Human) objThing;
			System.out.println("Mariana = " + mariana);
	   }
	   else 
			System.out.println("It was not able to recognize the obvius relationship");



	    System.out.println("\n\nTo stop this Tutorial enter any string ...");
	    sc.nextLine();		// read a line
	    System.exit(0); 		// All right. That's one small step for a man, one giant leap for mankind...


	}



	/// #
	///   printAllObjects()
	///   Print all objects read from the triple store
	/// #
	public static void printAllObjects() {
 
	    // go through Map structure recovering the position information
	    System.out.println("\n******  position information  ****** ");
	    for (Map.Entry<Long, CartesianPos> e : SparqlEndPoint.posMap.entrySet())
    		System.out.println(e.getKey() + ": " + e.getValue());


	    // go through Map structure recovering the color information
	    System.out.println("\n******  color information  ****** ");
	    for (Map.Entry<Long, RGBValue> e : SparqlEndPoint.colorMap.entrySet())
    		System.out.println(e.getKey() + ": " + e.getValue());


	    // go through Map structure recovering the object information
	    System.out.println("\n******  object information  ****** ");
	    for (Map.Entry<Long, Thing> e : SparqlEndPoint.objectMap.entrySet())
    		System.out.println(e.getKey() + ": " + e.getValue());


	    // go through Map structure recovering the human information
	    System.out.println("\n******  human information  ****** ");
	    for (Map.Entry<Long, Human> e : SparqlEndPoint.humanMap.entrySet())
    		System.out.println(e.getKey() + ": " + e.getValue());


	    // go through Map structure recovering the robot information
	    System.out.println("\n******  robot information  ****** ");
	    for (Map.Entry<Long, Robot> e : SparqlEndPoint.robotMap.entrySet())
    		System.out.println(e.getKey() + ": " + e.getValue());

	    // go through Map structure recovering the hear information
	    System.out.println("\n******  hear information  ****** ");
	    for (Map.Entry<Long, RobotHear> e : SparqlEndPoint.hearMap.entrySet())
    		System.out.println(e.getKey() + ": " + e.getValue());


	    // go through Map structure recovering the taste information
	    System.out.println("\n******  taste information  ****** ");
	    for (Map.Entry<Long, RobotTaste> e : SparqlEndPoint.tasteMap.entrySet())
    		System.out.println(e.getKey() + ": " + e.getValue());

	    // go through Map structure recovering the touch information
	    System.out.println("\n******  touch information  ****** ");
	    for (Map.Entry<Long, RobotTouch> e : SparqlEndPoint.touchMap.entrySet())
    		System.out.println(e.getKey() + ": " + e.getValue());

	    // go through Map structure recovering the vision information
	    System.out.println("\n******  vision information  ****** ");
	    for (Map.Entry<Long, RobotVision> e : SparqlEndPoint.visionMap.entrySet())
    		System.out.println(e.getKey() + ": " + e.getValue());

	    // go through Map structure recovering the smell information
	    System.out.println("\n******  smell information  ****** ");
	    for (Map.Entry<Long, RobotSmell> e : SparqlEndPoint.smellMap.entrySet())
    		System.out.println(e.getKey() + ": " + e.getValue());

	}    // end public static void printAllObjects()



















}   // end of class
