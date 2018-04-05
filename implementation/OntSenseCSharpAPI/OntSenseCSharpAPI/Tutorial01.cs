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
            CartesianPos cPos;
            RGBValue rgb;
            Thing thing;


            // Start access to Sparql End Point : just one time at main method is enough
            SparqlEndPoint instanceSparql = SparqlEndPoint.getInstance();       // gets the instance for the  singleton object
            instanceSparql.init(ONT_SENSE_URL);



            //
            // creates a RobotVision object	with a 	Salmon_Pack
            //		
            cPos = new CartesianPos(0.35, 0.36, 0.37);     // creates a CartesianPos object with (x,y,z) = (0.35, 0.36, 0.37)
            rgb = new RGBValue(0.45, 0.46, 0.47);              // creates a  RGBValue value with (red, green, blue) = (0.45, 0.46, 0.47)
            thing = new Thing(
                1000000010,                                              // object  identifier
                "",                                                     // object name 
                "Salmon_Pack",                                          // internal object tag, 
                rgb,                                                    // color in RGBValue 
                cPos,                                                   // CartesianPos
                PhysicalState.noneState,                                // object Phisical State
                Material.organicMaterial,                               // object Material type
                "https://www.wikidata.org/wiki/Q7405484");             // URI associated with the object

            RobotVision rVision = new RobotVision(DateTime.Now, thing);         // creates a RobotVision object  seen at this very moment

            try
            {                                           // Try to access a resource.
                rVision.insert();                       // using dotNetRDF library inserts the information in the triple store
            }


            catch (Exception e)
            {

                Console.WriteLine(e);                   // change for your: LogError(e);     // Call a custom error logging procedure.
                throw;                                  // Re-throw the error. It is likely to interrupt the simulator
            }


            //
            // creates a RobotVision object	with Human	
            //
            cPos = new CartesianPos(0.25, 0.326, 0.27);        // creates a CartesianPos object with (x,y,z) = (0.25, 0.326, 0.27))
            rgb = new RGBValue(0.25, 0.326, 0.27);             // creates a  RGBValue value with (red, green, blue) = (0.25, 0.326, 0.27))
            Human human = new Human(
                1000000100,                                  // object  identifier
                "mariana",                                  // object name 
                "Human",                                    // internal object tag, 
                rgb,                                        // color in RGBValue 
                cPos,                                       // CartesianPos
                PhysicalState.noneState,                    // object Phisical State
                Material.organicMaterial,                   // object Material type
                "https://www.wikidata.org/wiki/Q3238275",   // URI associated with the human 
                EmotionalState.happinessEmotion);           // human emotional state

            RobotVision rVisionHuman = new RobotVision(DateTime.Now, human);         // creates a Vision object  seen at this very moment

            try                                         // Try to access a resource.
            {
                rVisionHuman.insert();                       // using dotNetRDF library inserts the information in the triple store
            }

            catch (Exception e)
            {
                Console.WriteLine(e);                   // change for your: LogError(e);     // Call a custom error logging procedure.
                throw;                                  // Re-throw the error. It is likely to interrupt the simulator
            }






            //
            // creates a RobotVision object	with a Robot information	
            //		
            cPos = new CartesianPos(0.25, 0.326, 0.27);        // creates a CartesianPos object with (x,y,z) = (0.25, 0.326, 0.27))
            rgb = new RGBValue(0.25, 0.326, 0.27);             // creates a  RGBValue value with (red, green, blue) = (0.25, 0.326, 0.27))
            Robot robot = new Robot(
                1000001000,                                // object  identifier
                "Kyle",                                   // object name 
                "Robot",                                  // internal object tag, 
                rgb,                                      // color in RGBValue 
                cPos,                                     // CartesianPos
                PhysicalState.noneState,                  // object Phisical State
                Material.organicMaterial,                 // object Material type
                "https://www.wikidata.org/wiki/Q11012");  // URI associated with the robot 


            RobotVision rVisionRobot = new RobotVision(DateTime.Now, robot);         // creates a RobotVision object  seen at this very moment

            try                                         // Try to access a resource.
            {
                rVisionRobot.insert();                       // using dotNetRDF library inserts the information in the triple store
            }

            catch (Exception e)
            {
                Console.WriteLine(e);                   // change for your: LogError(e);     // Call a custom error logging procedure.
                throw;                                  // Re-throw the error. It is likely to interrupt the simulator
            }




            //
            // creates a RobotHear object with Hear information		
            //		      
            RobotHear rh1 = new RobotHear(
                    DateTime.Now,                           // the event occurs now
                    1000010000,                             // object  identifier
                    HearingAttribute.musicSound,           // I heard a beautiful music
                    0.5,                                    // the volume is in the middle
                    "What A Wonderful World");              // sound detail

            try                                         // Try to access a resource.
            {
                rh1.insert();                       // using dotNetRDF library inserts the information in the triple store
            }

            catch (Exception e)
            {
                Console.WriteLine(e);                   // change for your: LogError(e);     // Call a custom error logging procedure.
                throw;                                  // Re-throw the error. It is likely to interrupt the simulator
            }




            //
            // creates a RobotHear object	with Hear and Position information		
            //		
            cPos = new CartesianPos(0.50, 0.51, 0.52);     // creates a CartesianPos object with (x,y,z) = (0.50, 0.51, 0.52)
            RobotHear rh2 = new RobotHear(
                DateTime.Now,                           // the event occurs now
                cPos,                                   // source position
                HearingAttribute.musicSound,           // I heard a beautiful music
                0.8,                                    // the volume is a little high
                "Don't worry be Happy");                // sound detail

            try                                         // Try to access a resource.
            {
                rh2.insert();                       // using dotNetRDF library inserts the information in the triple store
            }

            catch (Exception e)
            {
                Console.WriteLine(e);                   // change for your: LogError(e);     // Call a custom error logging procedure.
                throw;                                  // Re-throw the error. It is likely to interrupt the simulator
            }


            //
            // creates a RobotSmell object with Smell	
            //		
            RobotSmell rs1 = new RobotSmell(
                DateTime.Now,                           // the event occurs now
                1000100000,                             // object  identifier
                OlfactoryAttribute.woodySmell);           // it is a forest odor


            try                                         // Try to access a resource.
            {
                rs1.insert();                       // using dotNetRDF library inserts the information in the triple store
            }

            catch (Exception e)
            {
                Console.WriteLine(e);                   // change for your: LogError(e);     // Call a custom error logging procedure.
                throw;                                  // Re-throw the error. Probaly will borken the simulator
            }

            //
            // creates a RobotSmell with with Smell and Position information		
            //		

            cPos = new CartesianPos(0.75, 0.76, 0.77);     // creates a CartesianPos object with (x,y,z) = (0.75, 0.76, 0.77))

            RobotSmell rs2 = new RobotSmell(
                DateTime.Now,                           // the event occurs now
                cPos,                                   // source position
                OlfactoryAttribute.decayedSmell);           // it is a putrid odor


            try                                         // Try to access a resource.
            {
                rs2.insert();                       // using dotNetRDF library inserts the information in the triple store
            }

            catch (Exception e)
            {
                Console.WriteLine(e);                   // change for your: LogError(e);     // Call a custom error logging procedure.
                throw;                                  // Re-throw the error. Probaly will borken the simulator
            }





            //
            // creates a RobotTaste object	 with Taste and Position information		
            //		
            RobotTaste rt = new RobotTaste(
                DateTime.Now,                           // the event occurs now
                1001000000,                             // object  identifier
                0.12,                                   // bitter level 
                0.13,                                   // salt level 
                0.14,                                   // sour level 
                0.89,                                   // sweet level 
                0.70);                                  // umani level 

            try                                         // Try to access a resource.
            {
                rt.insert();                       // using dotNetRDF library inserts the information in the triple store
            }

            catch (Exception e)
            {
                Console.WriteLine(e);                   // change for your: LogError(e);     // Call a custom error logging procedure.
                throw;                                  // Re-throw the error. Probaly will borken the simulator
            }



            //
            // creates a RobotTouch object		with Touch information	
            //		
            RobotTouch rTouch1 = new RobotTouch(
                DateTime.Now,                           // the event occurs now
                1010000000,                             //  object definition
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



            //
            // creates a RobotTouch object	with Touch and Position information		
            //		
            cPos = new CartesianPos(0.90, 0.91, 0.92);     // creates a CartesianPos object with (x,y,z) = (0.90, 0.91, 0.92)

            RobotTouch rTouch2 = new RobotTouch(
                DateTime.Now,                           // the event occurs now
                cPos,                                   // position of the object
                0.22,                                   // hardness level 
                0.23,                                   // moisture level 
                0.24,                                   // pressure level 
                0.79,                                   // roughness level 
                23.5);                                  // temperature level 


            try                                         // Try to access a resource.
            {
                rTouch2.insert();                       // using dotNetRDF library inserts the information in the triple store
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
