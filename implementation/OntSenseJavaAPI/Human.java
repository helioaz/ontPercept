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

/**
 * A CognitiveAgent is an Agent that has the ability to reason, deliberate, make plans, and experience emotions. Although Human is a subclass of CognitiveAgent, there may be instances of CognitiveAgent which are not also instances of Human. For example, Primates, dolphins, whales, and some extraterrestrials (if they exist) might be considered CognitiveAgents (SUMO definition).
 * 
 */
public class Human extends Thing {

	private EmotionalState emotion;



	/**
	 * Default constructor for the Human class. 
	 */

	public Human()
	{
	}

	/**
	 * Constructor for the Human class. The objective is to create a instance of an Human agent. 
	 * All the parameter defines this agent in a similar way of the super class Thing. 
	 * 
	 */
	public Human(long objId, String name, String tag, RGBValue colorObj, CartesianPos posObj, PhysicalState phyState, Material mat, String uri, EmotionalState emotionSt) {
        	super(objId, name, tag, colorObj, posObj, phyState, mat, uri);
		emotion = emotionSt;						// update human emotion
	}




	/**
	 * update the emotion of a human being
	 */
	public void setEmotion (EmotionalState emotionSta ) {
		emotion = emotionSta;
	}




	/**
	 * Convert class to a string. Just for debug
	 */
	public String toString() {
		String result = super.toString();
		return 	result + 
			"\n \t emotion= " + emotion;
	}  // toString

}
