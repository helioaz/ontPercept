using System;
using ontsenseAPI;

namespace ontsenseAPI
{
	/// The subclass of RobotPerception in which the sensing is done by of a sensor which can discriminate various tastes. The unique ID of known object is supplied with the taste information. Note that, it is necessary that the object have been in contact with the robot tactil sensor. In this sense, the robot knows what is the object because in order for the robot to take it and bring it to the mouth, it must have seen this object first .
	/// 
	public class RobotTaste : RobotPerceptionEvent
	{
		public double bitterness;

		public double saltness;

		public double sourness;

		public double sweetness;

		public double umani;

		/// insert the taste captured by the taste sensor. Returns true if the insertion in the triple store base was a success, false otherwise.
		public override Boolean insert()
		{
			return false;
		}

		/// Constructor of the RobotTaste class. The objective is to create a instance for a taste perception.
		/// The instant parameter represens the exact moment of the taste capture. 
		/// The idObject parameter represents an unique identifier associated with the object responsible to produce  the taste perception. 
		/// The bitterness, saltness, sourness, sweetness and  umani represent specific  caractheristics of the taste perception.
		public  RobotTaste(DateTime instant, long idObject, double bitter, double salt, double sour, double sweet, double umani)
		{

		}

	}

}

