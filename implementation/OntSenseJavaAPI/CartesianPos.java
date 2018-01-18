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
 * A CartesianPos denotes the quantitative position of an object in a cartesian coordinate system.
 */
public class CartesianPos {

	private double cartesianX;

	private double cartesianY;

	private double cartesianZ;

	/**
	 * fragment identifier: one syntax component of  Uniform Resource Identifier (URI) associated with the concept 
	 */
	private String fragIdent;



	public CartesianPos(double pX, double pY, double pZ, String value) {
		cartesianX = pX;
		cartesianY = pY;
		cartesianZ = pZ;		
		fragIdent = value;  // fragment identification it is one syntax component of  Uniform Resource Identifier (URI) associated with the concept.
	}


	/**
	 *		Get protect information
	 */
	public String getFragIdent() {return fragIdent; }

	/**
	 * Convert class to a string. Just for debug
	 */
	public String toString() {
		return 	"pX= " + Double.toString(cartesianX) +
			" pY= " + Double.toString(cartesianY) +
			" pZ= " + Double.toString(cartesianZ);
	}






}
