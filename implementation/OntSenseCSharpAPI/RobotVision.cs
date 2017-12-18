using ontsenseAPI;
using System;

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
/// You should have received a copy of the GNU General Public License  along with Foobar. 
/// If not, see<http://www.gnu.org/licenses/>.
/// 
///
/// namespace ontsenseAPI
{
	/// The subclass of RobotPerception in which the sensing is done by a vision  sensor. 
	/// A Thing or CognitiveThing object are only inserted in the triple store by this class.
	public class RobotVision : RobotPerceptionEvent
	{
		/// Constructor of the RobotVision class. The objective is to create a instance of an inanimate object view.
		/// 
		/// 
		public RobotVision(DateTime instant, Thing obj)
		{

		}

		/// Constructor of the RobotVision class. The objective is to create a instance of an animate agents with emotion.
		public RobotVision(DateTime instant, Human agent)
		{

		}

        /// Constructor of the RobotVision class. The information about the robot agent is supplied as a vision perception. 
        /// This is a trick used to upgrade the triple store with robot status information.  In a philosophical view,
        /// the robot is expressing the knowledge of itself.
        public RobotVision(DateTime instant, Robot rob)
        {

        }

        /// insert the objects captured by the vision sensor. Returns true if the insertion in the triple store base was a success, false otherwise.
        public override Boolean insert()
		{
			return false;
		}

	}

}

