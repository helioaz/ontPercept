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
import java.util.Calendar;

/**
 * The subclass of RobotPerception in which the sensing is done by an auditory sensor. 
 * The unique ID of known object or the position of an unknown object is supplied with the sound information. Note that, the unique ID is only known if the object is in the robot view.
 */
public class RobotHear extends RobotPerceptionEvent {

	private double volume;

	private String detail;

	private HearingAttribute soundType;

	private CartesianPos pos;


	/**
	 * insert the sound captured by the hear sensor. 
	 */
	public void insert() {

	}

	/**
	 * Constructor of the RobotHear class. The objective is to create a instance for a sound perception. 
	 * he instant parameter represens the exact moment of the sound capture.
	 * The idObject parameter represents  the object responsible to produce the sound. 
	 * The kind defines the kind of the sound. 
	 * The volume parameter defines the sound volume.
	 * The detail parameter defines additional information associated with the sound, for example, if kind is MARIANA_VOICE then the detail represents the sentence said.
	 * 
	 */
	public RobotHear(Date instant, long idObject, HearingAttribute kind, double volume, String detail) {
		occurs = instant;
		generateBy = idObject;				// Great. There is a Thing object
		this.pos = null;				// there is no position object: set a value; just in case!
		this.volume = volume;
		this.detail = detail;
		this.soundType =  kind;
	

	}

	/**
	 * Constructor of the RobotHear class. The objective is to create a instance for a sound perception.
	 * The instant parameter represens the exact moment of the sound capture. 
	 * The pos parameter represents the position of an unknow object responsible to produce the sound. 
	 * The kind defines the kind of the sound. 
	 * The volume parameter defines the sound volume. 
	 * The detail parameter defines additional information associated with the sound, for example, if kind is MARIANA_VOICE then the detail represents the sentence said.
	 *
	 */
	public RobotHear(Date instant, CartesianPos pos, HearingAttribute kind, double volume, String detail) {
		this.occurs = instant;
		this.generateBy = 0;			// there is no Thing object: set a value; just in case!
		this.pos = pos;				// Great. There is a position object
		this.volume = volume;
		this.detail = detail;
		this.soundType =  kind;


	}



	/**
	 *		Get protect information
	 */
	public CartesianPos getPos() { return pos; }
	public double getVolume() { return volume; }
	public String getDetail() { return detail; }
	public HearingAttribute getSoundType() { return soundType; }



	/**
	 * Convert class to a string. Just for debug
	 */
	public String toString() {
		String result = super.toString();

		if (pos == null) 
			result += "\n \t pos= null";
		else 
			result += "\n \t pos= " + pos;

		result += "\n \t volume= " + Double.toString(volume) +
			  "\n \t detail= " + detail +
			  "\n \t soundType= " + soundType ;
  
		return result;
	}  // toString

	



}
