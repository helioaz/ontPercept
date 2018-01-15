///
/// This file is part of OntCog project.
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
/// 


using System;


using VDS.RDF;

using VDS.RDF.Query;
using VDS.RDF.Update;
using OntSenseCSharpAPI;


namespace OntSenseCSharpAPITest
{
    // The class Totorial01 is just an example to use the OntSenseCSharpAPI. 
    // it simply inserts a record in the triple store.
    // This class is NOT part of OntSenseCSharpAPI.
    class Tutorial01
    {
        private static readonly string ONT_SENSE_URL = "http://localhost:3030/ontsense";	// URL address of the triple store


        public static void Main(String[] args)
        {
<<<<<<< HEAD
=======

	    // Start access to Sparql End Point
            SparqlEndPoint instanceSparql = SparqlEndPoint.getInstance();       // gets the instance for the  singleton object : just one time at main method is enough
	    instanceSparql.init (ONT_SENSE_URL);				// creates the endpoint access to triple store


            CartesianPos cPos = new CartesianPos(0.90, 0.91, 0.92);     // creates a CartesianPos object with (x,y,z) = (0.90, 0.91, 0.92)
>>>>>>> ae8be9760e845c5c9c8eaab7df7e78f072cce725

            // Start access to Sparql End Point : just one time at main method is enough
            SparqlEndPoint instanceSparql = SparqlEndPoint.getInstance();       // gets the instance for the  singleton object
            instanceSparql.init(ONT_SENSE_URL);

            CartesianPos cPos = new CartesianPos(0.25, 0.326, 0.27);        // creates a CartesianPos object with (x,y,z) = (0.25, 0.326, 0.27))
            RGBValue rgb = new RGBValue(0.25, 0.326, 0.27);             // creates a  RGBValue value with (red, green, blue) = (0.25, 0.326, 0.27))
            Robot robot = new Robot(
                987654322,                             // object  identifier
                "Kyle",                                // object name 
                "Robot",                               // internal object tag, 
                rgb,                                   // color in RGBValue 
                cPos,                                  // CartesianPos
                PhysicalState.noneState,               // object Phisical State
                Material.organicMaterial,              // object Material type
                "http://dbpedia.org/page/Robot");      // URI associated with the robot 


            RobotVision rVision = new RobotVision(DateTime.Now, robot);         // creates a RobotVision object  seen at this very moment

            try                                         // Try to access a resource.
            {
                rVision.insert();                       // using dotNetRDF library inserts the information in the triple store
            }

            catch (Exception e)
            {
                Console.WriteLine(e);                   // change for your: LogError(e);     // Call a custom error logging procedure.
                throw;                                  // Re-throw the error. It is likely to interrupt the simulator
            }

            Console.WriteLine("- You can see the results of this execution in Fuseki start terminal or");
            Console.WriteLine("  doing a query to the triple store with your browser at : localhost:3030");
            Console.WriteLine("- Insert any char to close this terminal....");  Console.ReadLine();
        }

    }
}
