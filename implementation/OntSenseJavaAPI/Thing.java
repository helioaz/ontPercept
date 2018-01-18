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

import java.lang.*;

/**
 * Corresponds roughly to the class of ordinary objects (SUMO definition).
 * Note that the Object concept has two identifiers.
 * The first named objectID represents a unique identification present in the simulator or robotic agent.
 * The second, called fragIdent represents one syntax component of the Uniform Resource Identifier (URI) associated with the 
 * concept Thing. This URI is used to access the concept in the triple store.
 */
public class Thing {

	/**
	 * 
	 * 
	 */
	protected long objectId;		// this number is used to compose the fragment identifier of the subject
						// it is used to compose the fragIdent. Ex: O1234567890, the number 1234567890 is the objectId
	protected String tagInfo;		// the tag represents a taxonomy classification inside the robot system. Ex. Salmon_Pack
	protected String name;			// the name represents a string used to identify the object in the world. ex Mariana
	protected String uriId;
	protected long mapId;			// a key used to save objects in the Map structure
	protected CartesianPos pos;
	protected RGBValue color;
	protected Material material;
	protected PhysicalState state;
	protected String fragIdent;		// one syntax component of  Uniform Resource Identifier (URI) associated with the concept 

	/**
	 * updateThing. The objective is to update a instance of an inanimate object. 
	 * All  parameters together defines this object.
	 * One additional parameter deserves attention: fragment identification: It is one syntax component 
	 * of  Uniform Resource Identifier (URI) associated with the concept, so it only exists after the concept have been created. 
	 *  
	 */
	public void updateThing(Long objId, String name, String tag, RGBValue colorObj, CartesianPos posObj, PhysicalState phyState, Material mat, String uri, String value) {

		objectId = objId;
		this.name = name;
		tagInfo = tag;
		uriId = uri;
		pos = posObj;
		color = colorObj;
		material = mat;
		state = phyState;
		fragIdent = value;	// fragment identification it is one syntax component of  Uniform Resource Identifier (URI) associated with the concept.
	}



	/**
	 * Constructor for the Thing class. The objective is to create a instance of an inanimate object. 
	 * All  parameters together defines this object.
	 * 
	 *  
	 */
	public Thing(Long objId, String name, String tag, RGBValue colorObj, CartesianPos posObj, PhysicalState phyState, Material mat, String uri) {

		objectId = objId;
		this.name = name;
		tagInfo = tag;
		uriId = uri;
		pos = posObj;
		color = colorObj;
		material = mat;
		state = phyState;
	}


	/**
	 * Default Constructor : justo to allocate space for the object
	 */
	public Thing() {

	}


	/**
	 *		Get protect information
	 */
	public long getObjectId() { return objectId; }
	public String getName() { return name; }
	public String getTagInfo() { return tagInfo; }
	public String getUriId() { return uriId; }
	public long getMapId() { return objectId; }
	public CartesianPos getPos() { return pos; }
	public RGBValue getColor() { return color; }
	public Material getMaterial() { return material; }
	public PhysicalState getState() { return state; }
	public String getFragIdent() {return fragIdent; }


	/**
	 * Convert class to a string. Just for debug
	 */
	public String toString() {
		return 	fragIdent + 
			"\n \t objectId= " + Long.toString(objectId) +
			"\n \t name= " + name +
			"\n \t tag= " + tagInfo +
			"\n \t uri= " + uriId +
			"\n \t position= [" + pos + "]" +
			"\n \t color= [" + color + "]" +
			"\n \t material= " + material +
			"\n \t state= " + state;
	}  // toString



}
