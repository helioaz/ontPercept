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

public class SparqlAccess {


	public static String QUERY_COLOR=									// USADO
		"PREFIX ontsense: <http://example.org/sense#> " +
		"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> " +
		"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
		"PREFIX sumo: <http://www.inf.ufrgs.br/phi-group/ontologies/sumo.owl#> " +		
		"SELECT DISTINCT ?subject ?red ?green ?blue " +
		"WHERE { " +
		"  ?subject 	rdf:type 		ontsense:RGBColor ; " +
		"   		ontsense:red		?red ; " +			// Literal double
		"   		ontsense:green		?green ; " +			// Literal double
		"   		ontsense:blue		?blue . " +			// Literal double
		"    }";


	public static String QUERY_POSITION=									// USADO
		"PREFIX ontsense: <http://example.org/sense#> " +
		"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> " +
		"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
		"PREFIX sumo: <http://www.inf.ufrgs.br/phi-group/ontologies/sumo.owl#> " +
		"PREFIX basicPos: <http://www.inf.ufrgs.br/phi-group/ontologies/basicPos.owl#>" +		
		"SELECT DISTINCT ?subject ?cartesianX ?cartesianY ?cartesianZ " +
		"WHERE { " +
		"  ?subject 	rdf:type 			basicPos:CartesianPositionPoint ; " +
		"  		basicPos:cartesianX		?cartesianX ; " +	// Literal double
		"  		basicPos:cartesianY		?cartesianY ; " +	// Literal double
		"  		basicPos:cartesianZ		?cartesianZ . " +	// Literal double
		"    }";



    	public static final String QUERY_OBJECT= 								// USADO
		"PREFIX ontsense: <http://example.org/sense#> " +
		"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> " +
		"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
		"PREFIX sumo: <http://www.inf.ufrgs.br/phi-group/ontologies/sumo.owl#> " +		
		"SELECT DISTINCT * " +
		"WHERE {" + 
		"  ?subject 	rdf:type 		sumo:Object ;" +
		"  		ontsense:objectId	?objectId ;" +						// Literal long
		"		ontsense:isPositionedAt	?isPositionedAt ." +
		"  OPTIONAL {   	?subject ontsense:hasColor		?hasColor . }" +
		"  OPTIONAL {   	?subject ontsense:isMadeOf		?isMadeOf . }" +
		"  OPTIONAL {   	?subject ontsense:name			?name . }" +			// Literal String
		"  OPTIONAL {   	?subject ontsense:tag			?tag . }" +			// Literal String
		"  OPTIONAL {   	?subject ontsense:associateURI		?associateURI . }" +		// Literal String
		"  OPTIONAL {   	?subject ontsense:hasInternalState	?hasInternalState . }  " +
		"}" +
		"ORDERBY ?subject ";



    	public static final String QUERY_HUMAN= 								// USADO
		"PREFIX ontsense: <http://example.org/sense#> " +
		"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> " +
		"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
		"PREFIX sumo: <http://www.inf.ufrgs.br/phi-group/ontologies/sumo.owl#> " +		
		"SELECT DISTINCT * " +
		"WHERE {" + 
		"  ?subject 	rdf:type 		ontsense:Human ;" +
		"  		ontsense:objectId	?objectId ;" +						// Literal long
		"		ontsense:isPositionedAt	?isPositionedAt ." +
		"  OPTIONAL {   	?subject ontsense:hasColor		?hasColor . }" +
		"  OPTIONAL {   	?subject ontsense:isMadeOf		?isMadeOf . }" +
		"  OPTIONAL {   	?subject ontsense:name			?name . }" +			// Literal String
		"  OPTIONAL {   	?subject ontsense:tag			?tag . }" +			// Literal String
		"  OPTIONAL {   	?subject ontsense:associateURI		?associateURI . }" +		// Literal String
		"  OPTIONAL {   	?subject ontsense:hasInternalState	?hasInternalState . }  " +
		"  OPTIONAL {   	?subject ontsense:hasEmotionalState	?hasEmotionalState . }  " +
		"}" +
		"ORDERBY ?subject ";


    	public static final String QUERY_ROBOT= 								// USADO
		"PREFIX ontsense: <http://example.org/sense#> " +
		"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> " +
		"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
		"PREFIX sumo: <http://www.inf.ufrgs.br/phi-group/ontologies/sumo.owl#> " +
		"PREFIX cora: <http://www.inf.ufrgs.br/phi-group/ontologies/cora.owl#> " +
		"SELECT DISTINCT * " +
		"WHERE {" + 
		"  ?subject 	rdf:type 		cora:Robot ;" +
		"  		ontsense:objectId	?objectId ;" +						// Literal long
		"		ontsense:isPositionedAt	?isPositionedAt ." +
		"  OPTIONAL {   	?subject ontsense:hasColor		?hasColor . }" +
		"  OPTIONAL {   	?subject ontsense:isMadeOf		?isMadeOf . }" +
		"  OPTIONAL {   	?subject ontsense:name			?name . }" +			// Literal String
		"  OPTIONAL {   	?subject ontsense:tag			?tag . }" +			// Literal String
		"  OPTIONAL {   	?subject ontsense:associateURI		?associateURI . }" +		// Literal String
		"  OPTIONAL {   	?subject ontsense:hasInternalState	?hasInternalState . }  " +
		"}" +
		"ORDERBY ?subject ";	



	public static final String DELETE_SUBJECT = 								// USADO
 		"PREFIX ontsense: <http://example.org/sense#> " +
            	" DELETE WHERE {" +
		"  ontsense:%s 	?property      ?value .  " +
	    	" }   "; 



    	public static final String QUERY_HEAR =									// USADO
		"PREFIX ontsense: <http://example.org/sense#> " + 
		"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> " + 
		"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " + 
		"SELECT DISTINCT * " + 
		"WHERE { " + 
		"  ?subject 	rdf:type 			ontsense:RobotHear ; " + 
		"    		ontsense:occursAt 		?time ;  " +						// Literal Date  
		"    		ontsense:hasSoundType		?soundType ; " + 
		"    		ontsense:volume 		?volume . " + 						// Literal long 
		"  OPTIONAL {   	?subject ontsense:generateBy 		?object . }" +
		"  OPTIONAL {   	?subject ontsense:isPositionedAt 	?valuePos . }" + 
		"  OPTIONAL {   	?subject ontsense:detail 		?detail . }" + 				// Literal String
		"}" +
		"    ORDERBY ?time ";



    	public static final String QUERY_TASTE =									// USADO
		"PREFIX ontsense: <http://example.org/sense#> " + 
		"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> " + 
		"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " + 
		"SELECT DISTINCT * " + 
		"WHERE { " + 
		"  ?subject 	rdf:type 			ontsense:RobotTaste ; " + 
		"    		ontsense:occursAt 		?time ;  " +						// Literal Date
		"  		ontsense:generateBy 		?object ;  " + 
		"    		ontsense:sweetness 		?sweetness ;  " +					// Literal double  
		"    		ontsense:umani			?umani ; " +						// Literal double 
		"    		ontsense:saltiness 		?saltiness ; " +					// Literal double 
		"    		ontsense:bitterness 		?bitterness ; " +					// Literal double 
		"    		ontsense:sourness 		?sourness . " +						// Literal double 
		"}" +
		"    ORDERBY ?time ";




    	public static final String QUERY_TOUCH =									// USADO
		"PREFIX ontsense: <http://example.org/sense#> " + 
		"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> " + 
		"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " + 
		"SELECT DISTINCT * " + 
		"WHERE { " + 
		"  ?subject 	rdf:type 			ontsense:RobotTouch ; " + 
		"    		ontsense:occursAt 		?time ;  " +						// Literal Date
		"    		ontsense:temperature 		?temperature ;  " +					// Literal double  
		"    		ontsense:hardness		?hardness ; " +						// Literal double 
		"    		ontsense:moisture 		?moisture ; " +						// Literal double 
		"    		ontsense:roughness 		?roughness ; " +					// Literal double 
		"    		ontsense:pressure 		?pressure . " +						// Literal double
		"  OPTIONAL {   	?subject ontsense:generateBy 		?object . }" +
		"  OPTIONAL {   	?subject ontsense:isPositionedAt 	?valuePos . }" + 
		"}" +
		"    ORDERBY ?time ";



    	public static final String QUERY_SMELL =									// USADO

		"PREFIX ontsense: <http://example.org/sense#> " + 
		"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> " + 
		"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " + 
		"SELECT DISTINCT * " + 
		"WHERE { " + 
		"  ?subject 	rdf:type 			ontsense:RobotSmell ; " + 
		"    		ontsense:occursAt 		?time ;  " +						// Literal Date
		"  		ontsense:generateBy 		?object ;  " + 
		"    		ontsense:hasSmellType  		?smellType .  " +					// Literal double  
		"}" +
		"    ORDERBY ?time ";





    	public static final String QUERY_VISION =									// USADO
		"PREFIX ontsense: <http://example.org/sense#> " + 
		"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> " + 
		"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " + 
		"SELECT DISTINCT * " + 
		"WHERE { " + 
		"  ?subject 	rdf:type 			ontsense:RobotVision ; " +
 		"    		ontsense:occursAt 		?time ;  " +						// Literal Date
		"  		ontsense:generateBy 		?object .  " + 
		"}" +
		"    ORDERBY ?time ";




















    	public static final String INSERT_VISION =
		"PREFIX os: <http://example.org/sense/> " +
		"PREFIX ontsense: <http://example.org/sense#> " +
		"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> " +
		"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
		"PREFIX owl: <http://www.w3.org/2002/07/owl#> " +
		"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +  
            	" INSERT DATA {" +
		"  ontsense:vision010 	rdf:type 		ontsense:RobotVision ; " +
		"  			rdf:type 		owl:NamedIndividual ; " +
		"   			ontsense:occursAt 	\"2018-04-12T13:24:00.0\"^^xsd:dateTime ; " + 
		"   			ontsense:generateBy 	ontsense:object002 ; " + 
	    	" }   "; 


	/**
	 * script for a sparql insert operation with touch sense  information
	 */
    	public static final String INSERT_TOUCH =
		"PREFIX os: <http://example.org/sense/> " +
		"PREFIX ontsense: <http://example.org/sense#> " +
		"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> " +
		"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
		"PREFIX owl: <http://www.w3.org/2002/07/owl#> " +
		"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +  
            	" INSERT DATA {" +
		"  ontsense:touch010 	rdf:type 		ontsense:RobotTouch ; " +
		"  			rdf:type 		owl:NamedIndividual ; " +
		"   			ontsense:occursAt 	\"2018-04-12T13:20:15.6\"^^xsd:dateTime ; " + 
		"   			ontsense:generateBy 	ontsense:object004 ; " +
		"   			ontsense:hardness 	\"0.8\"^^xsd:double ; " +
		"   			ontsense:temperature	\"23.0\"^^xsd:double ; " +
		"   			ontsense:moisture	\"0.1\"^^xsd:double ; " +
		"   			ontsense:roughness	\"0.1\"^^xsd:double ; " +
		"   			ontsense:pressure	\"0.0\"^^xsd:double ; " +
 
	    	" }   "; 

	/**
	 * script for a sparql query operation with taste sense  information after a given time
	 */
    	public static final String INSERT_TASTE =
		"PREFIX os: <http://example.org/sense/> " +
		"PREFIX ontsense: <http://example.org/sense#> " +
		"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> " +
		"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
		"PREFIX owl: <http://www.w3.org/2002/07/owl#> " +
		"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +  
            	" INSERT DATA {" +
		"  ontsense:taste010 	rdf:type 		ontsense:RobotTaste ; " +
		"  			rdf:type 		owl:NamedIndividual ; " +
		"   			ontsense:occursAt 	\"2018-04-12T13:20:15.6\"^^xsd:dateTime ; " + 
		"   			ontsense:generateBy 	ontsense:object005 ; " +
		"   			ontsense:sweetness 	\"0.05\"^^xsd:double ; " +
		"   			ontsense:umani		\"0.05\"^^xsd:double ; " +
		"   			ontsense:saltiness	\"0.3\"^^xsd:double ; " +
		"   			ontsense:bitterness	\"0.2\"^^xsd:double ; " +
		"   			ontsense:sourness	\"0.9\"^^xsd:double ; " +
 
	    	" }   "; 

	/**
	 * script for a sparql query operation with smell sense  information after a given time		"PREFIX ontsense: <http://example.org/sense#> " +
		"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> " +
		"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
		"PREFIX sumo: <http://www.inf.ufrgs.br/phi-group/ontologies/sumo.owl#> " +		
		"SELECT DISTINCT ?subject ?hasColor ?isMadeOf ?objectId ?isPositionedAt ?tag " +
		"WHERE { " +
		"  ?subject rdf:type 			sumo:Object . " +
		"  ?subject ontsense:hasColor		?hasColor . " +
		"  ?subject ontsense:isMadeOf		?isMadeOf . " +
		"  ?subject ontsense:objectId		?objectId . " +			// Literal long
		"  ?subject ontsense:isPositionedAt	?isPositionedAt . " +
		"  ?subject ontsense:tag		?tag . " +			// Literal String
		"    }";
	 */
    	public static final String INSERT_SMELL =
		"PREFIX os: <http://example.org/sense/> " +
		"PREFIX ontsense: <http://example.org/sense#> " +
		"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> " +
		"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
		"PREFIX owl: <http://www.w3.org/2002/07/owl#> " +
		"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +  
            	" INSERT DATA {" +

		"  ontsense:smell010 	rdf:type 		ontsense:RobotSmell ; " +
		"  			rdf:type 		owl:NamedIndividual ; " +
		"   			ontsense:occursAt 	\"2018-04-12T13:24:15.6\"^^xsd:dateTime ; " + 
		"   			ontsense:generateBy 	ontsense:object001 ; " +
		"   			ontsense:hasSmellType 	ontsense:fruitiSmell ; " +
	    	" }   "; 


	/**
	 * script for a sparql query operation with hear sense  information after a given time
	 */
    	public static final String INSERT_HEAR =
		"PREFIX os: <http://example.org/sense/> " +
		"PREFIX ontsense: <http://example.org/sense#> " +
		"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> " +
		"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
		"PREFIX owl: <http://www.w3.org/2002/07/owl#> " +
		"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +  
            	" INSERT DATA {" +
		"  ontsense:sound010 	rdf:type 		ontsense:RobotHear ; " +
		"  			rdf:type 		owl:NamedIndividual ; " +
		"   			ontsense:occursAt 	\"2018-04-12T13:20:15.7\"^^xsd:dateTime ; " + 
		"   			ontsense:generateBy 	ontsense:object007 ; " +
		"   			ontsense:volume 	\"0.3\"^^xsd:double ; " +
		"   			ontsense:hasSoundType 	ontsense:liquidFlowingSound ; " +
	    	" }   "; 


	// Delete all statements associated with a given subject
    	public static final String DELETE_SPECIFIC_1 =
		"PREFIX ontsense: <http://example.org/sense#> " +
		"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +  
            	" DELETE WHERE {" +
		"  ontsense:vision003 	?property      ?value .  " +
	    	" }   "; 





	// Delete all statements associated property occursAt
    	public static final String DELETE_SPECIFIC_2 =
		"PREFIX ontsense: <http://example.org/sense#> " +
		"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> " +
		"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
		"PREFIX owl: <http://www.w3.org/2002/07/owl#> " +
		"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +  
            	" DELETE WHERE {" +
		"  ?subject ontsense:occursAt      ?value .  " +
	    	" }   "; 







	// Delete all statements associated with a sense that occurs before a given time
    	public static final String DELETE_BEFORE_TIME =
		"PREFIX ontsense: <http://example.org/sense#> " +
		"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> " +
		"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
		"PREFIX owl: <http://www.w3.org/2002/07/owl#> " +
		"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +  
            	" DELETE { ?subject ?property      ?value } " +
		"	WHERE {" +
		"  		?subject ontsense:occursAt      ?time .  " +
		" 		FILTER ( ?time < \"2018-04-12T13:20:16.7\"^^xsd:dateTime) " + 
		"		?subject ?property      ?value " +
	    	" 	      }   "; 
 




	// # This block in SPARQL recovers all triples that are subClassOf  RobotPerceptionEvent  
    	public static final String QUERY_SUBCLASS =  
		"PREFIX os: <http://example.org/sense/> " +
		"PREFIX ontsense: <http://example.org/sense#> " +

		"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> " +
		"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
		"PREFIX owl: <http://www.w3.org/2002/07/owl#> " +
		"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
		"SELECT ?subject WHERE {   ?subject rdfs:subClassOf ontsense:RobotPerceptionEvent  }";



	// # This block in SPARQL recovers all triples generated BEFORE a given time.
    	public static final String QUERY_BEFORE_TIME =  
		"PREFIX ontsense: <http://example.org/sense#> " +
		"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> " +
		"SELECT ?subject  ?time " +
		"WHERE { " +
		"  ?subject ontsense:occursAt ?time . FILTER ( ?time < \"2018-04-12T13:20:16.7\"^^xsd:dateTime)   }";


	// # This block in SPARQL recovers all triples generated AFTER a given time.
    	public static final String QUERY_ALL_AFTER_TIME =  
		"PREFIX ontsense: <http://example.org/sense#> " +
		"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> " +
		"SELECT ?subject  ?time " +
		"WHERE { " +
		"  ?subject ontsense:occursAt ?time . FILTER ( ?time > \"2018-04-12T13:23:19.9\"^^xsd:dateTime)   }";

	// # This block in SPARQL recovers all triples associated with Vision and that ocurrs  after a given time.
    	public static final String QUERY_VISION_AFTER_TIME = 
		"PREFIX ontsense: <http://example.org/sense#> " +
		"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> " +
		"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
		"SELECT ?subject  ?time ?object  ?predicate ?value " +
		"WHERE { " +
		"  ?subject rdf:type ontsense:RobotVision . " +
		"  ?subject ontsense:occursAt ?time . " + 
		"  ?subject ontsense:generateBy ?object . " +
		"  ?object ?predicate ?value . " +
		"FILTER ( ?time > \"2018-04-12T13:23:19.9\"^^xsd:dateTime)    }";





	// # This block in SPARQL recovers all triples associated with Touch and that ocurrs  after a given time.
    	public static final String QUERY_TOUCH_AFTER_TIME = 
		"PREFIX ontsense: <http://example.org/sense#> " +
		"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> " +
		"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
		"SELECT DISTINCT ?subject   ?object  ?predicate ?value ?time ?temp ?moist  ?rough ?press " +
		"WHERE { " +
		"  ?subject rdf:type 			ontsense:RobotTouch . " +
		"  ?subject ontsense:occursAt 		?time . " + 
		"  ?subject ontsense:generateBy 	?object . " +
		"  ?subject ontsense:temperature	?temp . " +
		"  ?subject ontsense:moisture		?moist . " +
		"  ?subject ontsense:roughness		?rough . " +
		"  ?subject ontsense:pressure		?press . " +
		"  ?object ?predicate 			?value . " +  
		"FILTER ( ?time > \"2018-04-12T13:00:19.9\"^^xsd:dateTime)    }";

	// # This block in SPARQL recovers all triples associated with Taste and that ocurrs  after a given time.
    	public static final String QUERY_TASTE_AFTER_TIME = 
		"PREFIX ontsense: <http://example.org/sense#> " +
	"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> " +
		"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
		"SELECT DISTINCT ?subject    ?predicate  ?value ?sour ?salt ?sweet ?bitter ?uma " +
		"WHERE { " +
		"    ?subject rdf:type 				ontsense:RobotTaste . " +
		"    ?subject ontsense:occursAt 		?time . " + 
		" 	?subject ontsense:generateBy ?object . " +
		"    ?subject ontsense:sourness		?sour . " +
		"    ?subject ontsense:saltiness		?salt . " +
		"    ?subject ontsense:sweetness		?sweet . " +
		"    ?subject ontsense:bitterness	?bitter . " +
		"    ?subject ontsense:umani			?uma . " +
		"    ?object ?predicate 				?value . " + 
		"    FILTER ( ?time > \"2018-04-12T13:00:19.9\"^^xsd:dateTime)    }";

	// # This block in SPARQL recovers all triples associated with Smell and that ocurrs  after a given time.
    	public static final String QUERY_SMELL_AFTER_TIME =
		"PREFIX ontsense: <http://example.org/sense#> " +
		"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> " +
		"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
		"SELECT DISTINCT ?subject ?object ?time  ?smellAtt ?valueMat ?valueColor ?valueId ?valuePos ?valueDes " +
		"WHERE { " +
		"    ?subject rdf:type 				ontsense:RobotSmell . " +
		"    ?subject ontsense:occursAt 		?time . " + 
		"    ?subject ontsense:generateBy 	?object . " +
		"    ?subject ontsense:hasSmellType	?smellAtt . " +
		"    ?object ontsense:hasMaterial 		?valueMat . " +
		"    ?object ontsense:hasColor 		?valueColor . " +
		"    ?object ontsense:objectId 		?valueId . " + 
		"    ?object ontsense:isPositionedAt 		?valuePos . " +
		"    ?object ontsense:description 		?valueDes . " +
		"    FILTER ( ?time > \"2018-04-12T13:00:19.9\"^^xsd:dateTime)    }";


	// # This block in SPARQL recovers all triples associated with Hear and that ocurrs  after a given time.
    	public static final String QUERY_HEAR_AFTER_TIME =
		"PREFIX ontsense: <http://example.org/sense#> " + 
		"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> " + 
		"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " + 
		"SELECT DISTINCT ?subject ?object ?time  ?soundAtt  ?valueMat  ?valueColor ?valueId ?valuePos ?valueDes " + 
		"WHERE { " + 
		"    ?subject rdf:type 				ontsense:RobotHear . " + 
		"    ?subject ontsense:occursAt 		?time .  " + 
		"    ?subject ontsense:generateBy 	?object . " + 
		"    ?subject ontsense:hasSoundType	?soundAtt . " + 
		"    ?object ontsense:hasMaterial 		?valueMat . " + 
		"    ?object ontsense:hasColor 		?valueColor . " + 
		"    ?object ontsense:objectId 		?valueId . " +  
		"    ?object ontsense:isPositionedAt 		?valuePos . " + 
		"    ?object ontsense:description 		?valueDes . " + 
		"    FILTER ( ?time > \"2018-04-12T13:00:19.9\"^^xsd:dateTime)    }";
 

}
