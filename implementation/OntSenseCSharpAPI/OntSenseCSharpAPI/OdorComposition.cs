///
/// This file is part of OntCog project.
/// 
/// OntCog is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
/// the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
/// 
/// OntCog is distributed in the hope that it will be useful,  but WITHOUT ANY WARRANTY; without even the implied warranty of
/// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.See the GNU General Public License for more details.
/// 
/// 
/// You should have received a copy of the GNU General Public License  along along  this directory. 
/// If not, see<http://www.gnu.org/licenses/>.
/// 
///
namespace OntSenseCSharpAPI
{
    /// The Class of properties that are detectable by smell.
    public class OdorComposition
    {
        /// Represents the level of chemical odorant present in a olfatory information.
        public double chemicalLevel { get; }

        /// Represents the level of decayed odorant present in a olfatory information.
        public double decayedLevel { get; }

        /// Represents the level of fragrant odorant present in a olfatory information.
        public double fragrantLevel { get; }

        /// Represents the level of fruity odorant present in a olfatory information.
        public double fruityLevel { get; }

        /// Represents the level of lemon odorant present in a olfatory information.
        public double lemonLevel { get; }

        /// Represents the level of minty odorant present in a olfatory information.
        public double mintyLevel { get; }

        /// Represents the level of popcorn odorant present in a olfatory information.
        public double popcornLevel { get; }

        /// Represents the level of pungent odorant present in a olfatory information.
        public double pungentLevel { get; }

        /// Represents the level of sweet odorant present in a olfatory information.
        public double sweetLevel { get; }

        /// Represents the level of woody odorant present in a olfatory information.
        public double woodyLevel { get; }



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
    }

}

