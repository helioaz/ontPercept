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
using VDS.RDF.Query;
using VDS.RDF.Update;
using System.Runtime.CompilerServices;
using System.Threading;
using System.Globalization;

namespace OntSenseCSharpAPI
{
    /// This class implements the acess for Spaqrl endPoint. It try a conection with the endPoint located in http://localhost:3030/ontsense
    /// In this version whe are using the Fuseki triple store, but using Sparql endpint concept any other triple store could be used.
    /// This class is implemented using the Singleton pattern.
    ///  WARNING: In the Unity software this module was changed.
    public class SparqlEndPoint
    {
        private static SparqlEndPoint instance;
        private SparqlRemoteUpdateEndpoint endpoint;


        /// private constructor of class. only to create the local attributes
        private SparqlEndPoint()
        {
            


        }



        /// returns a unique instance of SparqlEndPoint. If the instance is not created it must be instatiated now.
        // [MethodImpl(MethodImplOptions.Synchronized)]     // only remove the initial comment if you are having troubles with thread synchronization
        //                                                     Reason: it is very expensive resource

        public static SparqlEndPoint getInstance()
        {
            if (instance == null)
                instance = new SparqlEndPoint();

            return instance;
        }

        /// executes a Sparql command receive as parameter.

        public void executeSparqlUpdate( string updateCmd)
        {
            //And finally send the update request
            endpoint.Update(updateCmd);
        }

        /// initialize the singleton with data store URL
	/// It must be called just after the first singleton instance generation

        public void init( string ontSenseURL)
        {
	    // to ensure that point is used as separator in decimal numbers
            Thread.CurrentThread.CurrentCulture = CultureInfo.CreateSpecificCulture("");  
 
            //Then create our Endpoint instance
            endpoint = new SparqlRemoteUpdateEndpoint(ontSenseURL);
        }

    }

}

