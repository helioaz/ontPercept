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
 * Represents the main color associated with a given object.
 */
public class RGBValue {

	private double red;

	private double green;

	private double blue;

	/**
	 * fragment identifier: one syntax component of  Uniform Resource Identifier (URI) associated with the concept 
	 */
	private String fragIdent;

	public RGBValue(double redLevel, double greenLevel, double blueLevel, String value) {
		red = redLevel;
		green = greenLevel;
		blue = blueLevel;
		fragIdent = value;  // fragment identification it is one syntax component of  Uniform Resource Identifier (URI) associated with the concept.
	}


	/**
	 *		Get protect information
	 */
	public String getFragIdent() {return fragIdent; }

	/**
	 * Convert class to a string. Just for debug...
	 */
	public String toString() {
		return 	"red= " + Double.toString(red) +
			" green= " + Double.toString(green) +
			" blue= " + Double.toString(blue);
	}


}
