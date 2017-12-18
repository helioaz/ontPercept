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
/// 








using ontsenseAPI;
using System;

namespace ontsenseAPI
{
	/// The subclass of RobotPerception in which the sensing is done by an haptic technology. 
	/// The unique ID of known object or the position of an unknown object is supplied with the tactil information. Note that, the unique ID is only known if the object is in one of the hands of the robot.  In this case, the knowledge is obtained because, for the robot to catch the object, it must have seen this object first .
	public class RobotTouch : RobotPerceptionEvent
	{
		public double hardness;

		public double moisture;

		public double pressure;

		public double roughness;

		public double temperature;

		private CartesianPos pos;

		/// Constructor of the RobotTouch class. The objective is to create a instance for a touch perception. 
		/// The instant parameter represens the exact moment of the touch capture. 
		/// The idObject parameter represents an unique identifier associated with the object responsible to produce  the touch perception. 
		/// The hardness, moisture, pressure, roughness and temperature represent specific  caractheristics of the touch perception.
		/// 
///  
		public RobotTouch(DateTime instant, long idObject, double hard, double mois, double press, double rough, double temp)
		{

		}

		/// Constructor of the RobotTouch class. The objective is to create a instance for a touch perception.
		/// The instant parameter represens the exact moment of the touch capture. 
		/// The pos parameter represents the position of an unknow object responsible to produce the touch perception.
		/// The hardness, moisture, pressure, roughness and temperature represent specific  caractheristics of the touch perception.  
		public RobotTouch(DateTime instant, CartesianPos pos, double hard, double mois, double press, double rough, double temp)
		{

		}

		/// insert the touch information captured by the touch sensor. Returns true if the insertion in the triple store basewas a success, false otherwise.
		public override Boolean insert()
		{
			return false;
		}

	}

}

