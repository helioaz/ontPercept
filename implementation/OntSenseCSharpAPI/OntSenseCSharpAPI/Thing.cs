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



namespace OntSenseCSharpAPI
{
	/// Corresponds roughly to the class of ordinary objects (SUMO definition).
	public class Thing
	{
		/// 
		/// 
		public long objectId { get; }

        public string tagInfo { get; }

        public string uriId { get; }

        public CartesianPos pos { get; }

        public RGBValue color { get; }

        public Material material { get; }

        public PhysicalState state { get; }


		/// Constructor for the Thing class. The objective is to create a instance of an inanimate object. 
		/// All  parameters together defines this object.
		/// 
///  
		public Thing(long objId, string tag, RGBValue color, CartesianPos pos, PhysicalState state, Material mat, string uri)
		{
            objectId = objId;
            tagInfo = tag;
            this.color = color;
            this.pos = pos;
            this.state = state;
            material = mat;
            uriId = uri;
        }

	}

}

