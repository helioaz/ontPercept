///
/// This file is part of OntSense project ( https://github.com/helioaz/ontSense )
/// 
/// OntSense is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
/// the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
/// 
/// OntSense is distributed in the hope that it will be useful,  but WITHOUT ANY WARRANTY; without even the implied warranty of
/// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.See the GNU General Public License for more details.
/// 
/// 
/// You should have received a copy of the GNU General Public License  along with Foobar. 
/// If not, see<http://www.gnu.org/licenses/>.
/// 
///

package br.usp.ontSenseJavaAPI;

/**
 * Using a composition of ten basic odorants this class completely identifies a specific odor. See DOI: 10.1371 / journal.pone.0073289
 * 
 */
public class OdorComposition {
	
		/**
	 * Represents the level of chemical odorant present in a olfatory information.
	 */
	public double chemicalLevel;
	

	/**
	 * Represents the level of decayed odorant present in a olfatory information.
	 */
	public double decayedLevel;

	/**
	 * Represents the level of fragrant odorant present in a olfatory information.
	 */
	public double fragrantLevel;

	/**
	 * Represents the level of fruity odorant present in a olfatory information.
	 */
	public double fruityLevel;

	/**
	 * Represents the level of lemon odorant present in a olfatory information.
	 */
	public double lemonLevel;

	/**
	 * Represents the level of minty odorant present in a olfatory information.
	 */
	public double mintyLevel;

	/**
	 * Represents the level of popcorn odorant present in a olfatory information.
	 */
	public double popcornLevel;

	/**
	 * Represents the level of pungent odorant present in a olfatory information.
	 */
	public double pungentLevel;

	/**
	 * Represents the level of sweet odorant present in a olfatory information.
	 */
	public double sweetLevel;

	/**
	 * Represents the level of woody odorant present in a olfatory information.
	 */
	public double woodyLevel;
	
	
	/**
	 * Constructor of odor composition
	 */	
    public OdorComposition(double chemical, double decayed, double fragrant, double fruity, double lemon, double minty, double popcorn, double pungent, double sweet, double woody)
    {
            chemicalLevel = chemical;
            decayedLevel = decayed;
            fragrantLevel = fragrant;
            fruityLevel = fruity;
            lemonLevel = lemon;
            mintyLevel = minty;
            popcornLevel = popcorn;
            pungentLevel = pungent;
            sweetLevel = sweet;
            woodyLevel = woody;
     }	


	public String toString() {
		String result;

		result = 	"[chemical= " + Double.toString(chemicalLevel) + ", decayed= " + Double.toString(decayedLevel) + ", fragrant= " + Double.toString(fragrantLevel) + 
					", fruity= " + Double.toString(fruityLevel) + ", lemon= " + Double.toString(lemonLevel) + ",\n\t\t     minty= " + Double.toString(mintyLevel) + 
					", popcorn= " + Double.toString(popcornLevel) + ", pungent= " + Double.toString(pungentLevel) + 
					", sweet= " + Double.toString(sweetLevel) + ", woody= " + Double.toString(woodyLevel) + "]";	
		
		return result;
	}  // toString





}
