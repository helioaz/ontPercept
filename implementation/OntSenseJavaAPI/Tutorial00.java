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

import java.lang.String;
import java.lang.Object; 			// for the getClass method
import java.util.Map;
import java.util.Scanner;

import br.usp.ontSenseJavaAPI.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import java.lang.String;

import java.util.UUID;

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


public class Tutorial00 {


        private static final String ONT_SENSE_URL = "http://localhost:3030/ontsense";	// URL address of the triple store

	private static  SparqlEndPoint instanceSparql;					// mantain the instance of Sparql end point access



	public static void uploadModel(File rdf, String serviceURI)
			throws IOException {

		// parse the file
		Model m = ModelFactory.createDefaultModel();
		try (FileInputStream in = new FileInputStream(rdf)) {
			m.read(in, null, "RDF/XML");
		}

		// upload the resulting model
		DatasetAccessor accessor = DatasetAccessorFactory
				.createHTTP(serviceURI);
		accessor.putModel(m);
		}







    	public static void execDelete(String serviceURI, String delete) {
        	QueryExecution qe;
        	ResultSet results;
        	//Add a new concept to the collection
        	String id = UUID.randomUUID().toString();
		String command = String.format(delete, id, id);
       		//System.out.println(command);

		System.out.println("Before DELETE...");
		// Create a QueryExecution that will access a SPARQL service over HTTP
		qe = QueryExecutionFactory.sparqlService(
                	serviceURI, "PREFIX ontsense: <http://example.org/sense#> " +
				    "SELECT ?subject ?time WHERE { ?subject ontsense:occursAt ?time . }");
        	results = qe.execSelect();
        	ResultSetFormatter.out(System.out, results);
        	qe.close();

		// Delete the triples identified by  WHERE clause
		System.out.println("DELETING...");
        	UpdateProcessor upp = UpdateExecutionFactory.createRemote(
                	UpdateFactory.create(delete), 
                	serviceURI);
        	upp.execute();

		System.out.println("After DELETE...");
		// Create a QueryExecution that will access a SPARQL service over HTTP
        	qe = QueryExecutionFactory.sparqlService(
                	serviceURI, "PREFIX ontsense: <http://example.org/sense#> " +
				    "SELECT ?subject ?time WHERE { ?subject ontsense:occursAt ?time . }");
        	results = qe.execSelect();
        	ResultSetFormatter.out(System.out, results);
        	qe.close();

    	}







    	public static void execInsert(String serviceURI, String insert) {
        	//Add a new concept to the collection
        	String id = UUID.randomUUID().toString();
		String command = String.format(insert, id, id);
       		//System.out.println(command);


        	UpdateProcessor upp = UpdateExecutionFactory.createRemote(
                	UpdateFactory.create(insert), 
                	serviceURI);
        	upp.execute();
       	//Query the collection, dump output
        //	QueryExecution qe = QueryExecutionFactory.sparqlService(
        //        	serviceURI, "SELECT * WHERE {?x ?r ?y}");
        //	ResultSet results = qe.execSelect();
        //	ResultSetFormatter.out(System.out, results);
        //	qe.close();

    	}




	public static void execSelectAndPrint(String serviceURI, String query) {
		QueryExecution q = QueryExecutionFactory.sparqlService(serviceURI,
				query);
		ResultSet results = q.execSelect();

		ResultSetFormatter.out(System.out, results);

		q.close();
	}

	public static void execSelectAndProcess(String serviceURI, String queryString) {
		String	objectName, hasColor, iMadeOf, isPositionedAt, tag;
		long 	objectId, mapId;
		int index;


		Query query = QueryFactory.create(queryString) ;

	  try (QueryExecution q = QueryExecutionFactory.sparqlService(serviceURI,query)) {  // assure that QueryExecution object will be closed at end of operation

		ResultSet results = q.execSelect();

		//Note que voce não pode ativar o ResultSetFormatter.out(System.out, results);
		// aparentemente esse metodo usa hasNext, exaurindo o ponteiro na primeira execução
 
		while (results.hasNext()) {
			QuerySolution soln = results.nextSolution();

			

			objectName = soln.getResource("subject").toString();
			objectName = objectName.substring(objectName.lastIndexOf('#') + 1);   // extract fragment identifier present in URL reference
			
			mapId = Long.parseLong(objectName.substring(1));


			hasColor = soln.getResource("hasColor").toString();
			hasColor = hasColor.substring(hasColor.lastIndexOf('#') + 1);   // extract fragment identifier present in URL reference

			iMadeOf = soln.getResource("isMadeOf").toString();
			iMadeOf = iMadeOf.substring(iMadeOf.lastIndexOf('#') + 1);   //extract fragment identifier present in URL reference

			isPositionedAt = soln.getResource("isPositionedAt").toString();
			isPositionedAt = isPositionedAt.substring(isPositionedAt.lastIndexOf('#') + 1);   // gets only the position

			tag = soln.getLiteral("tag").getString();

			objectId = soln.getLiteral("objectId").getLong();

			String saida  = objectName + "  -  " + 
					hasColor + "  -  " + 
					iMadeOf + " - " + 
					isPositionedAt + " - " + 
					tag  + " - " + 
					mapId  + " - " + 
					objectId;

			// traverse the to the date - must be careful you know what you want
			// as if these aren't types as dateTime, you'll wind up with nulls to check
			// result = resultNode.asLiteral().getValue().asCalendar().getTime()
			// https://gist.github.com/AlBaker/1008232/4802365c38e9599db2051a1dee7defd350bdff38


			System.out.println(saida);

		}    // close while

	  }  // close try
	

		
		 






	}






	public static void main(String[] argv) throws IOException {
	    Scanner sc = new Scanner(System.in);
		
	    try {		
	    	//
	    	// define the sparql endpoint  used to access the triple store and load OWL file with ontSense ontology
	    	//
            	instanceSparql = SparqlEndPoint.getInstance();   		// gets the instance for the  singleton object  
										// just one time at main method is enough
	    	instanceSparql.init ("../../Projeto/Protege/ontsensePlusData.owl", ONT_SENSE_URL );

	    	//
	    	// gets all perception information
	    	//
	    	instanceSparql.executeSparqlQuery();
	    }
	    catch (Exception e) {
		System.out.println(e);
		System.out.println("to stop this Tutorial enter any string ...");
		sc.nextLine();	// read a line
		System.exit(1); 		// Hello Houston. We have a problem!
	    }


	    //
	    // go through Map structure recovering the position information
	    //
	    System.out.println("\n******  position information  ****** ");
	    for (Map.Entry<Long, CartesianPos> e : SparqlEndPoint.posMap.entrySet())
    		System.out.println(e.getKey() + ": " + e.getValue());


		// uploadModel("../../Projeto/Protege/ontsense.owl", "http://localhost:3030/ontsense" );

		//execDelete("http://localhost:3030/ontsense", DELETE_BEFORE_TIME  );


		//execInsert("http://localhost:3030/ontsense", INSERT_SMELL  );

		 //execSelectAndPrint("http://localhost:3030/ontsense", QUERY_OBJECT);

		 //execSelectAndProcess("http://localhost:3030/ontsense", SparqlAccess.QUERY_OBJECT);

		//execInsertModel ("http://localhost:3030/ontsense");





	}







// * 
// *
// *       OUTRA FORMA UTILIZANDO ModelFactory
// *
// *


    	public static void execInsertModel (String serviceURI) {
        	String ns = "http://somewhere/";
        	OntModel model = ModelFactory.createOntologyModel( OntModelSpec.OWL_DL_MEM );
        	model.setNsPrefix( "", ns );

        	Individual i = model.createIndividual( ns+"JDoe", OWL.Thing );
        	i.addLabel( "John Doe", "en" );

        	model.write( System.out, "TTL" );

        	String rename = "" +
                "prefix : <"+ns+">\n" +
                "prefix rdfs: <"+RDFS.getURI()+">\n" +
                "delete { :JDoe rdfs:label ?label }\n" +
                "insert { :JDoe rdfs:label \"Jack Doe\"@en }\n" +
                "where { :JDoe rdfs:label ?label }";

        	UpdateAction.parseExecute( rename, model );

        	model.write( System.out, "TTL" );
    	}

/*
    	public static void execRemove {
        	String ns = "http://stackoverflow.com/q/23102507/1281433/";
        	OntModel model = ModelFactory.createOntologyModel( OntModelSpec.OWL_DL_MEM );
        	model.setNsPrefix( "", ns );"http://localhost:3030/test"

        	Individual i = model.createIndividual( ns+"JDoe", OWL.Thing );
        	i.addLabel( "John Doe", "en" );

        	model.write( System.out, "TTL" );

        	String rename = "" +
                "prefix : <"+ns+">\n" +
                "prefix rdfs: <"+RDFS.getURI()+">\n" +
                "delete { :JDoe rdfs:label ?label }\n" +
                "insert { :JDoe rdfs:label \"Jack Doe\"@en }\n" +
                "where { :JDoe rdfs:label ?label }";

        	UpdateAction.parseExecute( rename, model );

        	model.write( System.out, "TTL" );
    	}
*/


















}   // end of class
