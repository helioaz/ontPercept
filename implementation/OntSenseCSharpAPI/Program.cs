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
using ontsenseAPI;
using System.Threading;
using System.Globalization;

namespace OntSenseCSharpAPI
{
    class Program {



        public static void Main(String[] args)
        {
            Thread.CurrentThread.CurrentCulture = CultureInfo.CreateSpecificCulture("");   // to ensure point as separator in decimal numbers
            CartesianPos cPos = new CartesianPos(0.90, 0.91, 0.92);     // creates a CartesianPos object with (x,y,z) = (0.90, 0.91, 0.92)


            RobotTouch rTouch1 = new RobotTouch(
                DateTime.Now,                           // the event occurs now
                9876543211,                             //  object definition
                0.67,                                   // hardness level 
                0.68,                                   // moisture level 
                0.69,                                   // pressure level 
                0.70,                                   // roughness level 
                25.5);                                  // temperature level 


            try                                         // Try to access a resource.
            {
                rTouch1.insert();                       // using dotNetRDF library inserts the information in the triple store
            }

            catch (Exception e)
            {
                Console.WriteLine(e);                   // change for your: LogError(e);     // Call a custom error logging procedure.
                throw;                                  // Re-throw the error. Probaly will borken the simulator
            }


            /*                DateTime dat = new DateTime(2009, 6, 15, 13, 45, 30,
                                                    DateTimeKind.Unspecified);
                        Console.WriteLine("{0} ({1}) --> {0:O}", dat, dat.Kind);

                        DateTime uDat = new DateTime(2009, 6, 15, 13, 45, 30,
                                                     DateTimeKind.Utc);
                        Console.WriteLine("{0} ({1}) --> {0:O}", uDat, uDat.Kind);

                        DateTime lDat = new DateTime(2009, 6, 15, 13, 45, 30,
                                                     DateTimeKind.Local);
                        Console.WriteLine("{0} ({1}) --> {0:O}\n", lDat, lDat.Kind);

                        DateTimeOffset dto = new DateTimeOffset(lDat);
                        Console.WriteLine("{0} --> {0:O}", dto);

                        Console.WriteLine(SparqlAccess.INSERT_THING);
            */
            //First build the update we want to send
            //In this example we are making a copy of a Graph then deleting the rdf:type triples
            //from our copy
            //SparqlParameterizedString update = new SparqlParameterizedString();
            //update.CommandText = INSERT_OBJECT;

            //Then create our Endpoint instance
            //SparqlRemoteUpdateEndpoint endpoint = new SparqlRemoteUpdateEndpoint("http://localhost:3030/ontsense");

            //And finally send the update request
            // endpoint.Update(update.ToString());

            Console.WriteLine("insira um caracter");  Console.ReadLine();
        }

        /*

                public static void Main(String[] args)
                {
                    //First connect to a store, in this example we use Fuseki
                    FusekiConnector fuseki = new FusekiConnector("http://localhost:3030/dataset/data");


                    if (fuseki.IsReadOnly) {
                        Console.WriteLine("Indicates whether the Store is read");
                    }

                    if (fuseki.IsReady) {
                        Console.WriteLine("Indicates whether the Store is ready for use");
                    }

                    //Apply a SPARQL Update to the Store
                    fuseki.Update("SELECT * WHERE { { ?s ?p ?o } UNION { GRAPH ?g { ?s ?p ?o } } }");

                    //Create a Graph and then load it with data from the store
                    Graph g = new Graph();
                    Uri nula = null;
                    fuseki.LoadGraph(g, nula);

                    foreach (Triple t in g.Triples)
                    {
                        Console.WriteLine(t.ToString());
                    }

                    //Assuming we have some Graph g find all the URI Nodes
                    foreach (IUriNode u in g.Nodes.UriNodes())
                    {
                        //Write the URI to the Console
                        Console.WriteLine(u.Uri.ToString());
                    }
                    Console.ReadLine();
                }
        */
    }
}
