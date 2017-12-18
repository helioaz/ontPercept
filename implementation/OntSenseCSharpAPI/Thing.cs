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

using ontsenseAPI;


namespace ontsenseAPI
{
	/// Corresponds roughly to the class of ordinary objects (SUMO definition).
	public class Thing
	{
		/// 
		/// 
		private long objectId;

		private string tagInfo;

		private string uriId;

		private CartesianPos pos;

		private RGBValue color;

		private Material material;

		private PhisicalState state;

        public Thing() { }

		/// Constructor for the Thing class. The objective is to create a instance of an inanimate object. 
		/// All  parameters together defines this object.
		/// 
///  
		public Thing(long objId, string tag, RGBValue color, CartesianPos pos, PhisicalState state, Material mat, string uri)
		{

		}

	}

}

