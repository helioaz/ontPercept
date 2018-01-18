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
 * The subclass of RobotPerception in which the sensing is done by an olfactory sensor. 
 * The unique ID of known object or the position of an unknown object is supplied with the odor information. Note that, the unique ID is only known if the object is in one of the hands of the robot. In this case,  the robot knows what is the object because in order for the robot to pick it up and take it up to the electronic nose, it must have seen the object first .
 */
public class RobotSmell extends RobotPerceptionEvent {

	private OlfatoryAttribute smellType;

	private CartesianPos pos;

	/**
	 * Constructor of the RobotSmell class. The objective is to create a instance for a odor perception. 
	 * The instant parameter represens the exact moment of the odor capture. 
	 * The idObject parameter represents  the Thing object responsible to produce the the smell. 
	 * The odor parameter identifies the odor.
	 * 
	 *  
	 *  
	 */
	public RobotSmell(Date instant, Thing thingObj, OlfatoryAttribute odor) {
		occurs = instant;
		generateBy = thingObj;				// Great. There is a Thing object
		this.pos = null;				// there is no position object: set a value; just in case!
		smellType = odor;

	}

	/**
	 * Constructor of the RobotSmell class. The objective is to create a instance for a odor perception. 
	 * The instant parameter represens the exact moment of the odor capture.
	 *  The pos parameter represents the position of an unknow object responsible to produce the the smell. 
	 * The odor parameter identifies the odor.
	 */
	public RobotSmell(Date instant, CartesianPos pos, OlfatoryAttribute odor) {
		this.occurs = instant;
		this.generateBy = null;			// there is no Thing object: set a value; just in case!
		this.pos = pos;				// Great. There is a position object
		smellType = odor;
	}


	/**
	 *		Get protect information
	 */
	public CartesianPos getPos() { return pos; }
	public OlfatoryAttribute getSmellType() { return smellType; }



	/**
	 * Convert class to a string. Just for debug
	 */
	public String toString() {
		String result = super.toString();

		if (pos == null) 
			result += "\n \t pos= null";
		else 
			result += "\n \t pos= " + pos;

		result += "\n \t smellType= " + smellType ;
  
		return result;
	}  // toString

}
