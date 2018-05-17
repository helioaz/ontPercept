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



package br.usp.ontSenseJavaAPI;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.Date;
import java.util.Calendar;

import java.lang.*;
import java.lang.Object; 			// for the getClass method

import javax.xml.crypto.URIReferenceException;
import javax.xml.crypto.KeySelectorException;

import org.apache.jena.query.*;
import org.apache.jena.query.DatasetAccessor;
import org.apache.jena.query.DatasetAccessorFactory;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;

import org.apache.jena.update.UpdateAction;
import org.apache.jena.update.UpdateProcessor;
import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.ontology.Individual;


import org.apache.jena.vocabulary.RDFS;
import org.apache.jena.vocabulary.OWL;

import org.apache.jena.datatypes.xsd.XSDDateTime;

/**
 * This class implements the acess for Spaqrl endPoint. It try a conection with the endPoint located in http://localhost:3030/ontsense
 * In this version whe are using the Fuseki triple store, but using Sparql endpint any other triple store could be used.
 * This class is implemented using the Singleton pattern.
 */
public class SparqlEndPoint {

	private static SparqlEndPoint instance;
	private static final int SUPPORT_INITIAL_CAPACITY = 500;		// defines the map capacity for support objects
	private static final int VISION_INITIAL_CAPACITY  = 500;		// defines the map capacity for vision objects
	private static final int SENSE_INITIAL_CAPACITY   = 100;		// defines the map capacity for other senses objects
	private static final int AGENT_INITIAL_CAPACITY   = 10;			// defines the map capacity for the agents in the environment

	/**
	 * Maps position identifier (key = long) to position object (values = CartesianPos). 
	 * The identifier is identical the individual identifier   without the first char .  Ex. P1961080821 is associated with 1961080821 number.
	 */
	public static Map<Long, CartesianPos> posMap = new LinkedHashMap<Long, CartesianPos>(SUPPORT_INITIAL_CAPACITY);	

	/**
	 * Maps color identifier (key = long) to color object (values = RGBValue). 
	 * The identifier is identical the individual identifier   without the first char .  Ex. C1961080831 is associated with 1961080831 number.
	 */
	public static Map<Long, RGBValue> colorMap = new LinkedHashMap<Long, RGBValue>(SUPPORT_INITIAL_CAPACITY);

	/**
	 * Maps object identifier (key = long) to object  (values = Thing). 
	 * The identifier is identical to objectId data supplied by the Unity framework.    
	 */
	public static Map<Long, Thing> objectMap = new LinkedHashMap<Long, Thing>(SUPPORT_INITIAL_CAPACITY);

	/**
	 * Maps Human identifier (key = long) to Human  (values = Human). 
	 * The identifier is identical to objectId data supplied by the Unity framework.    
	 */
	public static Map<Long, Human> humanMap = new LinkedHashMap<Long, Human>(AGENT_INITIAL_CAPACITY);

	/**
	 * Maps Robot identifier (key = long) to Robot  (values = Robot). 
	 * The identifier is identical to objectId data supplied by the Unity framework.    
	 */
	public static Map<Long, Robot> robotMap = new LinkedHashMap<Long, Robot>(AGENT_INITIAL_CAPACITY);

	/**
	 * Maps hear identifier (key = long) to hear object (values = RobotHear). 
	 * The identifier is identical the individual identifier   without the first char .  Ex. H1961080841 is associated with 1961080841 number.
	 */
	public static Map<Long, RobotHear> hearMap = new LinkedHashMap<Long, RobotHear>(SENSE_INITIAL_CAPACITY);

	/**
	 * Maps taste identifier (key = long) to taste object (values = RobotTaste). 
	 * The identifier is identical the individual identifier   without the first char .  Ex. T1961080861 is associated with 1961080861 number.
	 */
	public static Map<Long, RobotTaste> tasteMap = new LinkedHashMap<Long, RobotTaste>(SENSE_INITIAL_CAPACITY);

	/**
	 * Maps touch identifier (key = long) to touch object (values = RobotTouch). 
	 * The identifier is identical the individual identifier   without the first char .  Ex. T1961080881 is associated with 1961080881 number.
	 */
	public static Map<Long, RobotTouch> touchMap = new LinkedHashMap<Long, RobotTouch>(SENSE_INITIAL_CAPACITY);

	/**
	 * Maps vision identifier (key = long) to vision object (values = RobotVision). 
	 * The identifier is identical the individual identifier   without the first char .  Ex. V1961080891 is associated with 1961080891 number.
	 */
	public static Map<Long, RobotVision> visionMap = new LinkedHashMap<Long, RobotVision>(SENSE_INITIAL_CAPACITY);

	/**
	 * Maps smell identifier (key = long) to smell object (values = RobotSmell). 
	 * The identifier is identical the individual identifier   without the first char .  Ex. S1961080871 is associated with 1961080871 number.
	 */
	public static Map<Long, RobotSmell> smellMap = new LinkedHashMap<Long, RobotSmell>(SENSE_INITIAL_CAPACITY);

	/**
	 * This Set maintain the "fragment identifier" to information associated with the sense perception: position, color, object and human.
	 * It also mantain info about all senses: Hear, Taste, ...
	 * All these registers must be removed from the data store at end of executeSparqlQuery() operation. 
	 */
	private HashSet<String> cleanUpSet = new HashSet<String>(500);

	private String serviceURI;			// maintain the sparql endpoint URL address 





        /**
         *    O P E R A T I O N S
         */


        /// private constructor of class. only to create the local attributes
        private SparqlEndPoint()
        {           

        }


	/**
	 * returns a unique instance of SparqlEndPoint. If the instance is not created it must be instatiated now.
	 */
	public static SparqlEndPoint getInstance() {

            if (instance == null)
                instance = new SparqlEndPoint();

            return instance;
	}



        /// initialize the data store with ontSense ontology defined in rdf file.
	/// It must be called just after the first singleton instance generation
	public  void init(String rdfFile, String service)   throws IOException {

		serviceURI = service;		// mantain the sparql endpoint URL address 
			
		// parse the file
		Model sparqlModel = ModelFactory.createDefaultModel();
		try (FileInputStream in = new FileInputStream(rdfFile)) {
			sparqlModel.read(in, null, "RDF/XML");		// read the ontology in RDF/XML syntax
		}

		// upload the resulting model
		DatasetAccessor accessor = DatasetAccessorFactory.createHTTP(serviceURI);
		accessor.putModel(sparqlModel);				// update the data store with the ontology
		
		
	}

	/**
	 * executes a Sparql update command .
	 */
	public void executeSparqlUpdate(String updateCmd) {

	}

	/**
	 * This operation recovers all perception information present in the triple store. This include: CartesianPos, RGBValue, Thing, Human, Robot, 
   	 * RobotHear, RobotSmell, RobotTaste, RobotTouch, RobotVision. 
	 * Each class of recovered information is stored in the apropriated map object. 
	 * Before fill up the new information the associate map object is cleaned. After query execution, all registers recovered   are also deleted from the data store.
	 *
	 * Note: Removing the records from the triple store requires special care. 
	 *       Because update and query are asynchronous, it is possible for a record to be retrieved before it has been used in a given sense of perception. 
	 *       For example: a set of records was inserted in the triple store and before the begining of the recovery, a new set of  insertion is started. 
         *       In the middle of this new insert a recovery is started. In this scenario it is possible that records associated with the new set being inserted could be retrieved. 
	 *       These records can not be removed from the triple store, otherwise it will generate invalid links. The strategy used to overcome this problem is to retrieve all the 
         *       available records, and then to perform a hierarchical removal, that is, first the senses and then the objects, positions and colors associated with each perceived sense. 
	 */
	public int executeSparqlQuery() throws IOException, URIReferenceException, KeySelectorException {

		
		//System.out.println("antes positionSparqlQuery");	
		positionSparqlQuery();  	// query all CartesianPos register presents on the data store
		//System.out.println("antes colorSparqlQuery");
		colorSparqlQuery();  		// query all RGBValue register presents on the data store
		//System.out.println("antes objectSparqlQuery");
		objectSparqlQuery();  		// query all Thing register presents on the data store
		//System.out.println("antes humanSparqlQuery");
		humanSparqlQuery();  		// query all Human register presents on the data store
		//System.out.println("antes robotSparqlQuery");
		robotSparqlQuery();  		// query and clean up all Robot register presents on the data store
		//System.out.println("antes hearSparqlProcess");
		hearSparqlProcess();  		// query and clean up all RobotHear register presents on the data store
		//System.out.println("antes smellSparqlProcess");
		smellSparqlProcess();  		// query and clean up all RobotSmell register presents on the data store
		//System.out.println("antes tasteSparqlProcess");
		tasteSparqlProcess();  		// query and clean up all RobotTaste register presents on the data store
		//System.out.println("antes touchSparqlProcess");
		touchSparqlProcess();  		// query and clean up all RobotTouch register presents on the data store
		//System.out.println("antes visionSparqlProcess");
		visionSparqlProcess();  	// query and clean up all RobotVision register presents on the data store

		//System.out.println("antes cleanUpSupportInfo");
		cleanUpSupportInfo();		// remove from the triple store information used to build the senses

		//System.out.println("antes cleanUpSet");
		cleanUpSet.clear();		// remove all of the elements from this set collection


		int perceptionCount = hearMap.size() + tasteMap.size() + touchMap.size() + visionMap.size() + smellMap.size();
		//System.out.println("Number of perception senses= " + Integer.toString(perceptionCount));
		return perceptionCount;		// return a statistic of all perception senses captured from the environment 
		


	}



	/**
	 * query  all CartesianPos register presents on the data store
	 */
	private  void positionSparqlQuery() {

		String	objectName;			// name of subject in the URI identifier
		long 	mapId;				// key to insert in the map structure

	        double  pX, pY, pZ;			// Cartesian position
		CartesianPos position;			// object with the position information
	
		posMap.clear();				// first of all => clear up the associate Map

		Query query = QueryFactory.create(SparqlAccess.QUERY_POSITION) ;

	  	try (QueryExecution q = QueryExecutionFactory.sparqlService(serviceURI,query)) {  // ensure that QueryExecution object will be closed at end of operation

			ResultSet results = q.execSelect();

			while (results.hasNext()) {
				QuerySolution soln = results.nextSolution();			

				objectName = soln.getResource("subject").toString();
				objectName = objectName.substring(objectName.lastIndexOf('#') + 1);     // extract fragment identifier present in URI identifier			
				mapId = Long.parseLong(objectName.substring(1));			// remove char 'P' OR 'L' to get the map key element 

				pX = soln.getLiteral("cartesianX").getDouble();
				pY = soln.getLiteral("cartesianY").getDouble();
				pZ = soln.getLiteral("cartesianZ").getDouble();
				
				position =  new CartesianPos (pX, pY, pZ, objectName);

				posMap.put(mapId, position);						// insert pair<key, value> in the map

			}    // close while

	  	}  // close try


	}	// close positionSparqlQuery




	/**
	 * query all RGBValue register presents on the data store
	 */
	private void colorSparqlQuery() {
		String	objectName;			// name of subject in the URI identifier
		long 	mapId;				// key to insert in the map structure

	        double  red, green, blue;		// RGB components
		RGBValue color;				// object with the color information
	
		colorMap.clear();			// first of all => clear up the associate Map

		Query query = QueryFactory.create(SparqlAccess.QUERY_COLOR) ;

	  	try (QueryExecution q = QueryExecutionFactory.sparqlService(serviceURI,query)) {  // ensure that QueryExecution object will be closed at end of operation

			ResultSet results = q.execSelect();


			while (results.hasNext()) {
				QuerySolution soln = results.nextSolution();			

				objectName = soln.getResource("subject").toString();
				objectName = objectName.substring(objectName.lastIndexOf('#') + 1);     // extract fragment identifier present in URI identifier			
				mapId = Long.parseLong(objectName.substring(1));			// remove char 'C' to get the map key element 

				red = soln.getLiteral("red").getDouble();
				green = soln.getLiteral("green").getDouble();
				blue = soln.getLiteral("blue").getDouble();
				
				color =  new RGBValue (red, green, blue, objectName);

				colorMap.put(mapId, color);						// insert pair<key, value> in the map

			}    // close while

	  	}  // close try
	}



	 

	/**
	 * query all Thing common attribute present on the data store associated with soln
	*/ 
	private long getCommonAttrib(Thing thingObj, QuerySolution soln)
		throws IOException, URIReferenceException, KeySelectorException {

		long objectId; 			// represents a unique identification present in the simulator or robotic agent
						// this value is used as a key to insert the object in the Map
		String tag;
		String name;
		RGBValue colorObj; 		// defines the object color
		CartesianPos posObj; 		// defines the object position 
		PhysicalState hasStateEnum;	// defines the object physical state. If not defined assume PhysicalState.noneState 
		Material isMadeOfEnum; 		// defines the object material. If not defined assume Material.unknownMaterial
		String associateURI; 
		String objectName;		// name of subject in the URI identifier

								

		String	hasColor, isMadeOf, isPositionedAt;		// String nodes associated with the object 
		String	hasState, uri;					// more String nodes associated with the object 
		long index;						// generic index, just to calculate the Map access key

		Literal		literal;				// An RDF Literal.
		Resource	resource;				// An RDF Resource.


		resource = soln.getResource("subject");					// The subject is always present, it is in the Sparql Where clause
		objectName = resource.toString();
		objectName = objectName.substring(objectName.lastIndexOf('#') + 1);     // extract fragment identifier present in URI identifier			

		resource = soln.getResource("hasColor");
		if (resource == null) colorObj = null;					// Ok. object without color. It is not  a serious problem		
		else {
 			hasColor = resource.toString();
			hasColor = hasColor.substring(hasColor.lastIndexOf('#') + 1);   // extract fragment identifier present in URL reference
			index = Long.parseLong(hasColor.substring(1));			// remove char 'C' to get the map key element
			colorObj = colorMap.get(index);

			if (colorObj == null)						// The object does not exist in the Map => Problem					
				throw new KeySelectorException(objectName + ": Object color does not exist in the colorMap");
		}

		resource = soln.getResource("isMadeOf");
		if (resource == null) isMadeOfEnum = Material.unknownMaterial;		// not present => assume unknownMaterial
		else {
			isMadeOf = resource.toString();
			isMadeOf = isMadeOf.substring(isMadeOf.lastIndexOf('#') + 1);   //extract fragment identifier present in URL reference
			isMadeOfEnum = Material.valueOf(isMadeOf);
		}


		resource = soln.getResource("hasInternalState");
		if (resource == null) hasStateEnum = PhysicalState.noneState;		// not present => assume noneState
		else {
			hasState = resource.toString();
			hasState = hasState.substring(hasState.lastIndexOf('#') + 1);   //extract fragment identifier present in URL reference
			hasStateEnum = PhysicalState.valueOf(hasState);
		}


		resource = soln.getResource("isPositionedAt");			// The isPositionedAt is always present, it is in the Sparql Where clause	
		isPositionedAt = resource.toString();			 
		isPositionedAt = isPositionedAt.substring(isPositionedAt.lastIndexOf('#') + 1); // extract fragment identifier present in URL reference
		index = Long.parseLong(isPositionedAt.substring(1));		// remove char 'P' to get the map key element
		posObj = posMap.get(index);

		if (posObj == null) {						// The object does not exist in the Map => Problem					
			throw new KeySelectorException(objectName + ": Object position does not exist in the posMap");
			//System.out.println("object name= " + objectName);
			//System.exit(0);
		}

		literal = soln.getLiteral("name");
		if (literal == null) name = null;				// not present => assume empty String
		else name = literal.getString();				// get the name
			
		literal = soln.getLiteral("tag");
		if (literal == null) tag = null;				// not present => assume empty String
		else tag = literal.getString();					// get the tag

		literal = soln.getLiteral("associateURI");
		if (literal == null) associateURI = null;			// not present => assume empty String
		else associateURI = literal.getString();			// get the associateURI


		literal = soln.getLiteral("objectId");				// The objectId is always present, it is in the Sparql Where clause	
		objectId = literal.getLong();					// get a unique identification present in the simulator or robotic agent.


		thingObj.updateThing(objectId, name, tag, colorObj, posObj, hasStateEnum, isMadeOfEnum, associateURI, objectName);

		return objectId;						// returns the 	unique identification present in the simulator or robotic agent.			

	}		// close getCommonAttrib



	/**
	 * query all Thing register presents on the data store
	*/ 
	private void objectSparqlQuery() throws IOException, URIReferenceException, KeySelectorException {

		
		long index;						// generic index, just to calculate the Map access key
		Thing thingObj;

		objectMap.clear();					// first of all => clear up the associate Map

		Query query = QueryFactory.create(SparqlAccess.QUERY_OBJECT) ;

	  	try (QueryExecution q = QueryExecutionFactory.sparqlService(serviceURI,query)) {  // ensure that QueryExecution object will be closed at end of operation

			ResultSet results = q.execSelect();

			while (results.hasNext()) {
				QuerySolution soln = results.nextSolution();

				thingObj = new Thing();
				index = getCommonAttrib (thingObj, soln); 			// get all Thing attributes 		

				objectMap.put(index, thingObj);					// insert pair<key, value> in the map

			}    	// close while

	  	}  		// close try
	}			// close objectSparqlQuery






	/**
	 * query all Human register presents on the data store
	 */
	private void  humanSparqlQuery() throws IOException, URIReferenceException, KeySelectorException{

		
		long index;						// generic index, just to calculate the Map access key
		Human humanObj;

		String hasEmotion;					// defines the human emotion
		EmotionalState hasEmotionEnum;				// represents the emotion in an Enum
		Resource	resource;				// An RDF Resource.		

		//System.out.println(" HumanMap size before clear= " + Integer.toString(humanMap.size()));
		humanMap.clear();					// first of all => clear up the associate Map
		//System.out.println(" HumanMap size after clear= " +  Integer.toString(humanMap.size()));

		Query query = QueryFactory.create(SparqlAccess.QUERY_HUMAN) ;

	  	try (QueryExecution q = QueryExecutionFactory.sparqlService(serviceURI,query)) {  // TRY: ensure that QueryExecution object will be closed at end of operation

			ResultSet results = q.execSelect();

			while (results.hasNext()) {
				QuerySolution soln = results.nextSolution();

				humanObj = new Human();
				index = getCommonAttrib (humanObj, soln); 					// get all generic Thing attributes directly

				// process specific human attributes 
				resource = soln.getResource("hasEmotionalState");
				if (resource == null) hasEmotionEnum = EmotionalState.neutralEmotion;		// not present => assume neutralState
				else {
					hasEmotion = resource.toString();
					hasEmotion = hasEmotion.substring(hasEmotion.lastIndexOf('#') + 1);   	//extract fragment identifier present in URL reference
					hasEmotionEnum = EmotionalState.valueOf(hasEmotion);
				}		

				humanObj.setEmotion(hasEmotionEnum);
				humanMap.put(index, humanObj);					// insert pair<key, value> in the map

			}    	// close while

	  	}  		// close try
		
	}


	/**
	 * query and schedule a clean up all Robot register presents on the data store
	 * Note that the Robot is the only support information 
	 */
	private void robotSparqlQuery() throws IOException, URIReferenceException, KeySelectorException{

		
		long index;						// generic index, just to calculate the Map access key
		Robot robotObj;

		

		robotMap.clear();					// first of all => clear up the associate Map

		Query query = QueryFactory.create(SparqlAccess.QUERY_ROBOT) ;

	  	try (QueryExecution q = QueryExecutionFactory.sparqlService(serviceURI,query)) {  // TRY: ensure that QueryExecution object will be closed at end of operation

			ResultSet results = q.execSelect();
			while (results.hasNext()) {
				QuerySolution soln = results.nextSolution();
				robotObj = new Robot();
				index = getCommonAttrib (robotObj, soln); 		// get all generic Thing attributes directly
				robotMap.put(index, robotObj);				// insert pair<key, value> in the map


			}    	// close while

	  	}  		// close try

		
	}	// end robotSparqlProcess




	/**
	 * query and schedule a clean up of all RobotHear register presents on the data store
	*/
	private void hearSparqlProcess() throws IOException, URIReferenceException, KeySelectorException {

		long 		index,indAux;					// index to calculate the Map access key
		Literal		literal;				// An RDF Literal.
		Resource	resource;				// An RDF Resource.
		Calendar	cal;					// defines the instant of event occurence


		String 		isPositionedAt, objectName;		// String nodes associated with the object 
		Date 		instant;				// to maintain the instant of occurrence of the event
		long 		generateBy;				// defines the id of the object responsable for the event generation
		Thing		thingObj;				// defines the object responsable for the event generation
		CartesianPos 	posObj; 				// defines the object position


		String 		soundType;				// property detail
		String 		detail;					// information associated with the sound
		HearingAttribute  soundTypeEnum;			// defines the kind of sound
		double		volume; 				// defines the sound volume
		RobotHear	hearObj;				// defines the hear event being processed



		hearMap.clear();					// first of all => clear up the associate Map

		Query query = QueryFactory.create(SparqlAccess.QUERY_HEAR) ;

	  	try (QueryExecution q = QueryExecutionFactory.sparqlService(serviceURI,query)) {  // TRY: ensure that QueryExecution object will be closed at end of operation

			ResultSet results = q.execSelect();

			while (results.hasNext()) {
				QuerySolution soln = results.nextSolution();

				resource = soln.getResource("subject");				// The subject is always present, it is not in the Sparql OPTIONAL clause
				objectName = resource.toString();
				objectName = objectName.substring(objectName.lastIndexOf('#') + 1);     // extract fragment identifier present in URI identifier
				index = Long.parseLong(objectName.substring(1));		// remove char 'H' to get the map key element

				literal = soln.getLiteral("time");				// The occursAt is always present, it is not in the Sparql OPTIONAL clause
				//Object object = literal.getValue();
				XSDDateTime xsd = (XSDDateTime)literal.getValue();
				cal = xsd.asCalendar();				
				instant = cal.getTime();

				//System.out.println("hearSparqlProcess(): antes de generateBy");
				//if 	(soln.contains("time")) System.out.println("time exists int RDF");
				//if 	(soln.contains("generateBy")) System.out.println("generateBy exists int RDF");
				//if 	(soln.contains("generateBy")) System.out.println("generateBy exists int RDF");
				//literal = soln.getLiteral("generateBy");
				//if 	(literal == null)  System.out.println("Literal de generateBy equal null");
				if (soln.contains("generateBy"))
					generateBy = soln.getLiteral("generateBy").getLong();	// gets	 the identification of the object responsable for the event generation
				else 
					generateBy = 0;	
				
				if (generateBy == 0) thingObj = null;				// if generateBy does not exist then isPositionedAt must exist
				else {
					//System.out.println("generateBy = " + Long.toString(generateBy));
					thingObj = objectMap.get(generateBy);
					if (thingObj == null) {					// The object does not exist in the Object Map => there is still hope
						thingObj = humanMap.get(generateBy);
						if (thingObj == null) {				// The object does not exist in the Human Map => there is still hope
							thingObj = robotMap.get(generateBy);
							if (thingObj == null)			// The object also does not exist in the Robot Map => that is a problem		
								throw new KeySelectorException(Long.toString(generateBy) + ": Object generator of the Hear does not exist in the maps");
						}
					}
 					//System.out.println("hearSparqlProcess(): antes de cleanUpSet");
					cleanUpSet.add (thingObj.getFragIdent());		// prepare the tripe store cleanUp saving the the object reference
					cleanUpSet.add (thingObj.getPos().getFragIdent());	// prepare the tripe store cleanUp saving the the position reference
					cleanUpSet.add (thingObj.getColor().getFragIdent());	// prepare the tripe store cleanUp saving the the color reference	 
				} // end else

				//System.out.println("hearSparqlProcess(): antes de valuePos");
				resource = soln.getResource("valuePos");			// get the position of the event  
				if (resource == null) posObj = null;
				else {
					isPositionedAt = resource.toString();			 
					isPositionedAt = isPositionedAt.substring(isPositionedAt.lastIndexOf('#') + 1); // extract fragment identifier present in URL reference
					indAux = Long.parseLong(isPositionedAt.substring(1));	// remove char 'L' to get the map key element
					posObj = posMap.get(indAux);
					if (posObj == null) {					// The object does not exist in the Map => Problem					
						throw new KeySelectorException(objectName + ": Object position does not exist in the posMap");
						//System.out.println("object name= " + objectName);
						//System.exit(0);
					}
					cleanUpSet.add (posObj.getFragIdent());			// prepare the tripe store cleanUp saving the the position reference
				}

				if (posObj == null && thingObj == null) 
						throw new KeySelectorException(objectName + ": No event generator was located");

				if (posObj != null && thingObj != null) 
						throw new KeySelectorException(objectName + ": Two event generators were located. It must exist only one");


				//System.out.println("hearSparqlProcess(): antes de soundType");
				resource = soln.getResource("soundType");			// The hasSoundType is always present, it is not in the Sparql OPTIONAL clause
				soundType = resource.toString();
				soundType = soundType.substring(soundType.lastIndexOf('#') + 1);   	// extract fragment identifier present in URL reference
				soundTypeEnum = HearingAttribute.valueOf(soundType);

				literal = soln.getLiteral("volume");				// The volume is always present, it is not in the Sparql OPTIONAL clause	
				volume = literal.getLong();

				literal = soln.getLiteral("detail");				// search for detail property
				if (literal == null) detail = null;				// not present => assume empty String
				else detail = literal.getString();				// get the information

				if (thingObj != null) 
					hearObj = new RobotHear(instant, generateBy, soundTypeEnum, volume, detail);
				else
					hearObj = new RobotHear(instant, posObj,   soundTypeEnum, volume, detail);

				hearMap.put(index, hearObj);					// insert pair<key, value> in the map


				//
				//	Now is the time to prepare the cleanup of the perception event
				//
				cleanUpSet.add(objectName);					// prepare the tripe store cleanUp saving the perception event reference 	

			}    	// close while

	  	}  		// close try

	}			// end of hearSparqlProcess
	


	/**
	 * Query and schedule a clean up of all RobotTaste register presents on the data store
	 * Attention: Note that when there is an object generating the event only its identifier is saved in the class.
	 * This behavior is different from the sense vision, where a reference to the object is saved.
	 * This distinction is associated with our premise that only the sense of sight has conditions to generate 
	 * new objects, so other senses must use only the reference to the object that must have been previously seen.
	 */
	private void tasteSparqlProcess() throws IOException, URIReferenceException, KeySelectorException {

		long 		index;					// index to calculate the Map access key
		long		generateBy;				// identify the object
		Literal		literal;				// An RDF Literal.
		Resource	resource;				// An RDF Resource.
		Calendar	cal;					// defines the instant of event occurence


		String 		objectName;				// String nodes associated with the object 
		Date 		instant;				// to maintain the instant of occurrence of the event
		Thing 		thingObj;				// defines the object responsable for the event generation


		double 		sourness, bitterness, saltiness, umani, sweetness;		// taste attributes
		RobotTaste	tasteObj;				// defines the taste event being processed



		tasteMap.clear();					// first of all => clear up the associate Map

		Query query = QueryFactory.create(SparqlAccess.QUERY_TASTE) ;

	  	try (QueryExecution q = QueryExecutionFactory.sparqlService(serviceURI,query)) {  // TRY: ensure that QueryExecution object will be closed at end of operation

			ResultSet results = q.execSelect();

			while (results.hasNext()) {
				QuerySolution soln = results.nextSolution();

				resource = soln.getResource("subject");				// The subject is always present, it is not in the Sparql OPTIONAL clause
				objectName = resource.toString();
				objectName = objectName.substring(objectName.lastIndexOf('#') + 1);     // extract fragment identifier present in URI identifier
				index = Long.parseLong(objectName.substring(1));		// remove char 'A' to get the map key element

				literal = soln.getLiteral("time");				// The occursAt is always present, it is not in the Sparql OPTIONAL clause
				//Object object = literal.getValue();
				XSDDateTime xsd = (XSDDateTime)literal.getValue();
				cal = xsd.asCalendar();				
				instant = cal.getTime();

				if (soln.contains("generateBy"))
					generateBy = soln.getLiteral("generateBy").getLong();	// gets	 the identification of the object responsable for the event generation
				else 
					generateBy = 0;	


				if (generateBy == 0) {						// The object does not exist !!!
					throw new KeySelectorException(Long.toString(generateBy) + ": Object generator of Taste does not exist in triple Store");
				} 

	 			sweetness = soln.getLiteral("sweetness").getDouble();		// The sweetness is always present, it is not in the Sparql OPTIONAL clause
				umani = soln.getLiteral("umani").getDouble();			// The umani is always present, it is not in the Sparql OPTIONAL clause
				saltiness = soln.getLiteral("saltiness").getDouble();		// The saltiness is always present, it is not in the Sparql OPTIONAL clause
				bitterness = soln.getLiteral("bitterness").getDouble();		// The bitterness is always present, it is not in the Sparql OPTIONAL clause
				sourness = soln.getLiteral("sourness").getDouble();		// The sourness is always present, it is not in the Sparql OPTIONAL clause


				tasteObj = new RobotTaste(instant, generateBy, bitterness, saltiness, sourness, sweetness, umani);
				tasteMap.put(index, tasteObj);					// insert pair<key, value> in the map


				//
				//	Now is the time to prepare the cleanup of the perception event
				//
				cleanUpSet.add(objectName);					// prepare the tripe store cleanUp saving the perception event reference

			}    	// close while

	  	}  		// close try
		

		
	}  			// close tasteSparqlProcess

	/**
	 * Query and schedule a clean up of all RobotTouch register presents on the data store
	 * Attention: Note that when there is an object generating the event only its identifier is saved in the class.
	 * This behavior is different from the sense vision, where a reference to the object is saved.
	 * This distinction is associated with our premise that only the sense of sight has conditions to generate 
	 * new objects, so other senses must use only the reference to the object that must have been previously seen.
	 */
	private void touchSparqlProcess() throws IOException, URIReferenceException, KeySelectorException {
		long 		index, indAux;				//  index to calculate the Map access key
		Literal		literal;				// An RDF Literal.
		Resource	resource;				// An RDF Resource.
		Calendar	cal;					// defines the instant of event occurence


		long		generateBy;				// identify the object
		String 		isPositionedAt, objectName;		// String nodes associated with the object 
		Date 		instant;				// to maintain the instant of occurrence of the event
		Thing 		thingObj;				// defines the object responsable for the event generation
		CartesianPos 	posObj; 				// defines the object position


		double		temperature; 				// defines the temperature attribute of the touch perception
		double		hardness; 				// defines the hardness attribute of the touch perception
		double		moisture; 				// defines the moisture attribute of the touch perception
		double		roughness; 				// defines the roughness attribute of the touch perception
		double		pressure; 				// defines the pressure attribute of the touch perception
		RobotTouch	touchObj;				// defines the taste event being processed


		touchMap.clear();					// first of all => clear up the associate Map

		Query query = QueryFactory.create(SparqlAccess.QUERY_TOUCH) ;

	  	try (QueryExecution q = QueryExecutionFactory.sparqlService(serviceURI,query)) {  // TRY: ensure that QueryExecution object will be closed at end of operation

			ResultSet results = q.execSelect();

			while (results.hasNext()) {
				QuerySolution soln = results.nextSolution();

				resource = soln.getResource("subject");				// The subject is always present, it is not in the Sparql OPTIONAL clause
				objectName = resource.toString();
				objectName = objectName.substring(objectName.lastIndexOf('#') + 1);     // extract fragment identifier present in URI identifier
				index = Long.parseLong(objectName.substring(1));		// remove char 'T' to get the map key element

				literal = soln.getLiteral("time");				// The occursAt is always present, it is not in the Sparql OPTIONAL clause
				//Object object = literal.getValue();
				XSDDateTime xsd = (XSDDateTime)literal.getValue();
				cal = xsd.asCalendar();				
				instant = cal.getTime();

				if (soln.contains("generateBy"))
					generateBy = soln.getLiteral("generateBy").getLong();	// gets	 the identification of the object responsable for the event generation
				else 
					generateBy = 0;	
	

				resource = soln.getResource("valuePos");			// get the position of the event  
				if (resource == null) posObj = null;
				else {
					isPositionedAt = resource.toString();			 
					isPositionedAt = isPositionedAt.substring(isPositionedAt.lastIndexOf('#') + 1); // extract fragment identifier present in URL reference
					indAux = Long.parseLong(isPositionedAt.substring(1));	// remove char 'L' to get the map key element
					posObj = posMap.get(indAux);
					if (posObj == null)					// The object does not exist in the Map => Problem					
						throw new KeySelectorException(objectName + ": Object position does not exist in the posMap");
					cleanUpSet.add (posObj.getFragIdent());			// prepare the tripe store cleanUp saving the the position reference
				}

				if (posObj == null && generateBy == 0) 
						throw new KeySelectorException(objectName + ": No event generator was located");

				if (posObj != null && generateBy != 0) 
						throw new KeySelectorException(objectName + ": Two event generators were located. It must exist only one");



	 			temperature = soln.getLiteral("temperature").getDouble();	// The temperature is always present, it is not in the Sparql OPTIONAL clause
				hardness = soln.getLiteral("hardness").getDouble();		// The hardness is always present, it is not in the Sparql OPTIONAL clause
				moisture = soln.getLiteral("moisture").getDouble();		// The moisture is always present, it is not in the Sparql OPTIONAL clause
				roughness = soln.getLiteral("roughness").getDouble();		// The roughness is always present, it is not in the Sparql OPTIONAL clause
				pressure = soln.getLiteral("pressure").getDouble();		// The pressure is always present, it is not in the Sparql OPTIONAL clause




				if (generateBy != 0)  
					touchObj = new RobotTouch(instant, generateBy, hardness, moisture, pressure, roughness, temperature);
                                                                 
				else
					touchObj = new RobotTouch(instant, posObj, hardness, moisture, pressure, roughness, temperature);

				touchMap.put(index, touchObj);					// insert pair<key, value> in the map



				//
				//	Now is the time to prepare the cleanup of the perception event
				//
				cleanUpSet.add(objectName);					// prepare the tripe store cleanUp saving the perception event reference 	

			}    	// close while

	  	}  		// close try		
	}












	/**
	 * Query and schedule a clean up of all RobotSmell register presents on the data store.
	 * Note that, when there is an object generating the event, there are not a register of the object reference
	 * in the CleanUpSet. This behavior is different from the sense vision, where a reference to the object is saved.
	 * This distinction is associated with our premise that only the sense of sight has conditions to generate 
	 * new objects, so other senses must use only the reference to the object that must have been previously seen.
	 * The position information just offers a likely position of the smell source.
	 */
	private void smellSparqlProcess() throws IOException, URIReferenceException, KeySelectorException {
		long 		index,indAux;				//  index to calculate the Map access key
		Literal		literal;				// An RDF Literal.
		Resource	resource;				// An RDF Resource.
		Calendar	cal;					// defines the instant of event occurence

		long		generateBy;				// identify the object
		String 		isPositionedAt, objectName;		// String nodes associated with the object 
		Date 		instant;				// to maintain the instant of occurrence of the event
		Thing 		thingObj;				// defines the object responsable for the event generation
		CartesianPos 	posObj; 				// defines the object position


		RobotSmell	smellObj;				// defines the taste event being processed
		OdorComposition odor;				// defines ten chemical odorant levels that completely identifies a specific odor in the environment. 
	
	    // declare the ten levels of chemical odorant that completely identifies a specific odor present in the environment. 
	    // See DOI: 10.1371 / journal.pone.0073289
        double chemical, decayed, fragrant, fruity;
        double lemon, minty, popcorn, pungent;
        double sweet, woody;

		smellMap.clear();					// first of all => clear up the associate Map

		Query query = QueryFactory.create(SparqlAccess.QUERY_SMELL) ;

	  	try (QueryExecution q = QueryExecutionFactory.sparqlService(serviceURI,query)) {  // TRY: ensure that QueryExecution object will be closed at end of operation

			ResultSet results = q.execSelect();
			while (results.hasNext()) {
				QuerySolution soln = results.nextSolution();

				resource = soln.getResource("subject");				// The subject is always present, it is not in the Sparql OPTIONAL clause
				objectName = resource.toString();
				objectName = objectName.substring(objectName.lastIndexOf('#') + 1);     // extract fragment identifier present in URI identifier
				index = Long.parseLong(objectName.substring(1));		// remove char 'T' to get the map key element

				literal = soln.getLiteral("time");				// The occursAt is always present, it is not in the Sparql OPTIONAL clause
				//Object object = literal.getValue();
				XSDDateTime xsd = (XSDDateTime)literal.getValue();
				cal = xsd.asCalendar();				
				instant = cal.getTime();


				if (soln.contains("generateBy"))
					generateBy = soln.getLiteral("generateBy").getLong();	// gets	 the identification of the object responsable for the event generation
				else 
					generateBy = 0;	



				resource = soln.getResource("valuePos");			// get the position of the event  
				if (resource == null) posObj = null;
				else {
					isPositionedAt = resource.toString();			 
					isPositionedAt = isPositionedAt.substring(isPositionedAt.lastIndexOf('#') + 1); // extract fragment identifier present in URL reference
					indAux = Long.parseLong(isPositionedAt.substring(1));	// remove char 'L' to get the map key element
					posObj = posMap.get(indAux);
					if (posObj == null) {					// The object does not exist in the Map => Problem					
						throw new KeySelectorException(objectName + ": Object position does not exist in the posMap");
						//System.out.println("object name= " + objectName);
						//System.exit(0);
					}
					cleanUpSet.add (posObj.getFragIdent());			// prepare the tripe store cleanUp saving the the position reference
				}

				if (posObj == null && generateBy == 0) 
						throw new KeySelectorException(objectName + ": No event generator was located");

				if (posObj != null && generateBy != 0) 
						throw new KeySelectorException(objectName + ": Two event generators were located. It must exist only one");



	 			chemical = soln.getLiteral("chemicalLevel").getDouble();	// The chemicalLevel is always present, it is not in the Sparql OPTIONAL clause
				decayed = soln.getLiteral("decayedLevel").getDouble();		// The decayedLevel is always present, it is not in the Sparql OPTIONAL clause
				fragrant = soln.getLiteral("fragrantLevel").getDouble();	// The fragrantLevel is always present, it is not in the Sparql OPTIONAL clause
				fruity = soln.getLiteral("fruityLevel").getDouble();		// The fruityLevel is always present, it is not in the Sparql OPTIONAL clause
				lemon = soln.getLiteral("lemonLevel").getDouble();			// The lemonLevel is always present, it is not in the Sparql OPTIONAL clause
				minty = soln.getLiteral("mintyLevel").getDouble();			// The mintyLevel is always present, it is not in the Sparql OPTIONAL clause
				popcorn = soln.getLiteral("popcornLevel").getDouble();		// The popcornLevel is always present, it is not in the Sparql OPTIONAL clause
				pungent = soln.getLiteral("pungentLevel").getDouble();		// The pungentLevel is always present, it is not in the Sparql OPTIONAL clause
				sweet = soln.getLiteral("sweetLevel").getDouble();			// The sweetLevel is always present, it is not in the Sparql OPTIONAL clause
				woody = soln.getLiteral("woodyLevel").getDouble();			// The woodyLevel is always present, it is not in the Sparql OPTIONAL clause

				odor = new OdorComposition(chemical, decayed, fragrant, fruity, lemon, minty, popcorn, pungent, sweet, woody);

				if (generateBy != 0) 
					smellObj = new RobotSmell(instant, generateBy, odor);
                                                                 
				else
					smellObj = new RobotSmell(instant, posObj, odor);

				smellMap.put(index, smellObj);					// insert pair<key, value> in the map



				//
				//	Now is the time to prepare the cleanup of the perception event
				//
				cleanUpSet.add(objectName);					// prepare the triple store cleanUp saving the perception event reference 	

			}    	// close while

	  	}  		// close try		
	}   // close smellSparqlProcess



 



	/**
	 * Query and schedule a clean up all RobotVision register presents on the data store
	 */
	private void visionSparqlProcess() throws IOException, URIReferenceException, KeySelectorException {

		long 		index;					// index to calculate the Map access key
		Literal		literal;				// An RDF Literal.
		Resource	resource;				// An RDF Resource.
		Calendar	cal;					// defines the instant of event occurence

		long		generateBy;				// identify the object
		String 		objectName;				// String nodes associated with the object 
		Date 		instant;				// to maintain the instant of occurrence of the event
		Thing 		thingObj;				// defines the object responsable for the event generation


		RobotVision	visionObj;				// defines the taste event being processed



		visionMap.clear();					// first of all => clear up the associate Map

		Query query = QueryFactory.create(SparqlAccess.QUERY_VISION) ;

	  	try (QueryExecution q = QueryExecutionFactory.sparqlService(serviceURI,query)) {  // TRY: ensure that QueryExecution object will be closed at end of operation

			ResultSet results = q.execSelect();

			while (results.hasNext()) {
				QuerySolution soln = results.nextSolution();

				resource = soln.getResource("subject");				// The subject is always present, it is not in the Sparql OPTIONAL clause
				objectName = resource.toString();
				objectName = objectName.substring(objectName.lastIndexOf('#') + 1);     // extract fragment identifier present in URI identifier
				index = Long.parseLong(objectName.substring(1));		// remove char 'A' to get the map key element

				literal = soln.getLiteral("time");				// The occursAt is always present, it is not in the Sparql OPTIONAL clause
				//Object object = literal.getValue();
				XSDDateTime xsd = (XSDDateTime)literal.getValue();
				cal = xsd.asCalendar();				
				instant = cal.getTime();



				if (soln.contains("generateBy"))
					generateBy = soln.getLiteral("generateBy").getLong();	// gets	 the identification of the object responsable for the event generation
				else 
					generateBy = 0;	


				thingObj = objectMap.get(generateBy);
				if (thingObj == null) {						// The object does not exist in the Object Map => there is still hope
					thingObj = humanMap.get(generateBy);
					if (thingObj == null) {					// The object does not exist in the Human Map => there is still hope
						thingObj = robotMap.get(generateBy);
						if (thingObj == null)				// The object also does not exist in the Robot Map => that is a problem		
								throw new KeySelectorException(Long.toString(generateBy) + ": Object generator of the Vision does not exist in the maps");
					}
				} 
				cleanUpSet.add (thingObj.getFragIdent());			// prepare the tripe store cleanUp saving the the object reference
				cleanUpSet.add (thingObj.getPos().getFragIdent());		// prepare the tripe store cleanUp saving the the position reference
				cleanUpSet.add (thingObj.getColor().getFragIdent());		// prepare the tripe store cleanUp saving the the color reference



				visionObj = new RobotVision(instant, generateBy);
				visionMap.put(index, visionObj);					// insert pair<key, value> in the map


				//
				//	Now is the time to prepare the cleanup of the perception event
				//
				cleanUpSet.add(objectName);					// prepare the tripe store cleanUp saving the perception event reference

			}    	// close while

	  	}  		// close try
		
	}  			// close tasteSparqlProcess



	/**
	 * remove from the triple store info used to build the senses
	 * This information is available at cleanUpSet structure. 
	 */
	private void cleanUpSupportInfo() {
		String 		deleteSparqlCmd;			// mantain sparql string for delete a register in the data store
		UpdateProcessor upp;

		// Run the cleanUpSet set to remove  information associated with the sense perception: position, color, object, robot and human.

		for(String itr:cleanUpSet)  { 
 
 			deleteSparqlCmd = String.format(SparqlAccess.DELETE_SUBJECT, itr);

			// Create an UpdateProcessor that sends the update to a remote SPARQL Update service.
	      		upp = UpdateExecutionFactory.createRemote(UpdateFactory.create(deleteSparqlCmd),serviceURI);
 	     		upp.execute();

		}
  
	}
		

	/**
	 * remove from the triple store all info
	 * The idea is remove all individuals proving a clean base to start up the operation
         * Remember that th robotic agent is generating data all the time so, it could be interesting clean up the base at beginning. 
	 */
	public void cleanUpAllInfo() {
		UpdateProcessor upp;

	      	upp = UpdateExecutionFactory.createRemote(UpdateFactory.create(SparqlAccess.DELETE_ALL_VISION),serviceURI);    upp.execute();
	      	upp = UpdateExecutionFactory.createRemote(UpdateFactory.create(SparqlAccess.DELETE_ALL_SMELL),serviceURI);    upp.execute();
	      	upp = UpdateExecutionFactory.createRemote(UpdateFactory.create(SparqlAccess.DELETE_ALL_TOUCH),serviceURI);    upp.execute();
	      	upp = UpdateExecutionFactory.createRemote(UpdateFactory.create(SparqlAccess.DELETE_ALL_TASTE),serviceURI);    upp.execute();
	      	upp = UpdateExecutionFactory.createRemote(UpdateFactory.create(SparqlAccess.DELETE_ALL_HEAR),serviceURI);    upp.execute();
	      	upp = UpdateExecutionFactory.createRemote(UpdateFactory.create(SparqlAccess.DELETE_ALL_ROBOT),serviceURI);    upp.execute();
	      	upp = UpdateExecutionFactory.createRemote(UpdateFactory.create(SparqlAccess.DELETE_ALL_HUMAN),serviceURI);    upp.execute();
	      	upp = UpdateExecutionFactory.createRemote(UpdateFactory.create(SparqlAccess.DELETE_ALL_OBJECT),serviceURI);    upp.execute();
	      	upp = UpdateExecutionFactory.createRemote(UpdateFactory.create(SparqlAccess.DELETE_ALL_CARTESIAN),serviceURI);    upp.execute();
	      	upp = UpdateExecutionFactory.createRemote(UpdateFactory.create(SparqlAccess.DELETE_ALL_COLOR),serviceURI);    upp.execute();
  
	}
		














}
