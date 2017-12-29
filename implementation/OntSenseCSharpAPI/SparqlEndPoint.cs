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

namespace ontsenseAPI
{
    /// This class implements the acess for Spaqrl endPoint. It try a conection with the endPoint located in http://localhost:3030/ontsense
    /// In this version whe are using the Fuseki triple store, but using Sparql endpint any other triple store could be used.
    /// This class is implemented using the Singleton pattern.
    public class SparqlEndPoint
    {
        private static SparqlEndPoint instance;
        private SparqlRemoteUpdateEndpoint endpoint;
        private static readonly string ONT_SENSE_URI = "http://localhost:3030/ontsense";

        /// private constructor of class
        private SparqlEndPoint()
        {
            
            //Then create our Endpoint instance
            endpoint = new SparqlRemoteUpdateEndpoint(ONT_SENSE_URI);

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

    }

}

