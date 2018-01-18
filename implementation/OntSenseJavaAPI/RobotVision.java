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
 * The subclass of RobotPerception in which the sensing is done by a vision  sensor. 
 * A Thing object are only inserted in the triple store by this class.
 */
public class RobotVision extends RobotPerceptionEvent {


	/**
	 * Constructor of the RobotVision class. The objective is to create a instance of an  object, like Thing, Human or Robot.
	 * 
	 * 
	 */
	public RobotVision(Date instant, Thing obj) {
		occurs = instant;
		generateBy = obj;				// Great. There is a Thing object
	}






	/**
	 * public String toString(): Convert class to a string. Just for debug
	 *
	 *  OBS: For the time being, the toString () method does not need to be declared. 
	 *       With this, the call of this method automatically triggers the superclass tostring () method, 
         *       that is, RobotPerceptionEvent.toString().
	 */




}	// close RobotVision class

