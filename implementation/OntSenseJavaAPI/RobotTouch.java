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
 * The subclass of RobotPerception in which the sensing is done by an haptic technology. 
 * The unique ID of known object or the position of an unknown object is supplied with the tactil information. Note that, the unique ID is only known if the object is in one of the hands of the robot.  In this case, the knowledge is obtained because, for the robot to catch the object, it must have seen this object first .
 */
public class RobotTouch extends RobotPerceptionEvent {

	private double hardness;

	private double moisture;

	private double pressure;

	private double roughness;

	private double temperature;

	private CartesianPos pos;

	/**
	 * Constructor of the RobotTouch class. The objective is to create a instance for a touch perception. 
	 * The instant parameter represens the exact moment of the touch capture. 
	 * The idObject parameter represents  the object responsible to produce  the touch perception. 
	 * The hardness, moisture, pressure, roughness and temperature represent specific  caractheristics of the touch perception.
	 * 
	 *  
	 */
	public RobotTouch(Date instant, Thing thingObj, double hard, double mois, double press, double rough, double temp) {
		occurs = instant;
		generateBy = thingObj;				// Great. There is a Thing object
		this.pos = null;				// there is no position object: set a value; just in case!

		hardness = hard;
		moisture = mois;
		pressure = press;
		roughness = rough;
		temperature = temp;
	}

	/**
	 * Constructor of the RobotTouch class. The objective is to create a instance for a touch perception.
	 * The instant parameter represens the exact moment of the touch capture. 
	 * The pos parameter represents the position of an unknow object responsible to produce the touch perception.
	 * The hardness, moisture, pressure, roughness and temperature represent specific  caractheristics of the touch perception.  
	 */
	public RobotTouch(Date instant, CartesianPos pos, double hard, double mois, double press, double rough, double temp) {
		this.occurs = instant;
		this.generateBy = null;			// there is no Thing object: set a value; just in case!
		this.pos = pos;				// Great. There is a position object

		hardness = hard;
		moisture = mois;
		pressure = press;
		roughness = rough;
		temperature = temp;
	}

	/**
	 *		Get protect information
	 */
	public CartesianPos getPos() { return pos; }
	public double getHardness() { return hardness; }
	public double getMoisture() { return moisture; }
	public double getPressure() { return pressure; }
	public double getRoughness() { return roughness; }
	public double getTemperature() { return temperature; }


	/**
	 * Convert class to a string. Just for debug
	 */
	public String toString() {
		String result = super.toString();

		if (pos == null) 
			result += "\n \t pos= null";
		else 
			result += "\n \t pos= " + pos;

		result += "\n \t hardness= " + Double.toString(hardness) +
			  "\n \t moisture= " + Double.toString(moisture) +
			  "\n \t pressure= " + Double.toString(pressure) +
			  "\n \t roughness= " + Double.toString(roughness) +
			  "\n \t temperature= " + Double.toString(temperature) ;
  
		return result;
	}  // toString


}
