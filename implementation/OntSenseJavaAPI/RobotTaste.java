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

import java.util.Date;

/**
 * The subclass of RobotPerception in which the sensing is done by of a sensor which can discriminate various tastes. The unique ID of known object is supplied with the taste information. Note that, it is necessary that the object have been in contact with the robot tactil sensor. In this sense, the robot knows what is the object because in order for the robot to take it and bring it to the mouth, it must have seen this object first .
 * 
 */
public class RobotTaste extends RobotPerceptionEvent {
	private long objectId;		// this number represents the unique identifier of the object 

	private double bitterness;

	private double saltness;

	private double sourness;

	private double sweetness;

	private double umani;


	/**
	 * Constructor of the RobotTaste class. The objective is to create a instance for a taste perception.
	 * The instant parameter represens the exact moment of the taste capture. 
	 * The idObject parameter represents an unique identifier associated with the object responsible to produce  the taste perception. 
	 * The bitterness, saltness, sourness, sweetness and  umani represent specific  caractheristics of the taste perception.
	 */
	public RobotTaste(Date instant, long objectId, double bitter, double salt, double sour, double sweet, double umani) {
		occurs = instant;
		this.objectId = objectId;			
		bitterness = bitter;
		saltness = salt;
		sourness = sour;
		sweetness = sweet;
		this.umani = umani;
	}




	/**
	 *		Get protect information
	 */
	public double getBitterness() { return bitterness; }
	public double getSaltness() { return saltness; }
	public double getSourness() { return sourness; }
	public double getSweetness() { return sweetness; }
	public double getUmani() { return umani; }


	/**
	 * Convert class to a string. Just for debug
	 */
	public String toString() {
		String result = super.toString();

		result += "\n \t bitterness= " + Double.toString(bitterness) +
			  "\n \t saltness= "   + Double.toString(saltness) +
			  "\n \t sourness= "   + Double.toString(sourness) +
			  "\n \t sweetness= "  + Double.toString(sweetness) +
			  "\n \t umani= "      + Double.toString(umani) ;
  
		return result;
	}  // toString





}
