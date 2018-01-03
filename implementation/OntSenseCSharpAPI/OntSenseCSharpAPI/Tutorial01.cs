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
using System.Threading;
using System.Globalization;

namespace OntSenseCSharpAPITest
{
    // The class Totorial01 is just an example to use the OntSenseCSharpAPI. 
    // it simply inserts a record in the triple store.
    // This class is NOT part of OntSenseCSharpAPI.
    class Tutorial01
    {



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



            Console.WriteLine("- You can see the results of this execution in Fuseki start terminal or");
            Console.WriteLine("  doing a query to the triple store with your browser at : localhost:3030");
            Console.WriteLine("- Insert any char to close this terminal....");  Console.ReadLine();
        }

    }
}
