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
 * Represents the robot's perception about the environment.
 */
public abstract class RobotPerceptionEvent {

	/**
	 * Defines the  instant of event ocurrence. This instant is assumed to be the time of object  instantiation.  
	 */
	protected Date occurs;



	/**
	 * Defines the object  responsable by the event generation. Note that, this knowledge is not always present. As an example, when an odor is present but the source is unknown.
	 */
	protected long generateBy;



	/**
	 * return a unique identification for the event. 
	 */
	public long getEventCount() {
		return 0;
	}



	/**
	 *		Get protect information
	 */
	public Date getOccurs() { return occurs; }
	public long getGenerateBy() { return generateBy; }



	/**
	 * Convert class to a string. Just for debug
	 */

	public String toString() {
		String result = "\n \t occurs= " + occurs;
		result += "\n \t generateBy= " + Long.toString(generateBy);
		return 	result;
	}  // toString


}
