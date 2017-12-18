using ontsenseAPI;
using System;


namespace ontsenseAPI
{
	/// The subclass of RobotPerception in which the sensing is done by an auditory sensor. 
	/// The unique ID of known object or the position of an unknown object is supplied with the sound information. Note that, the unique ID is only known if the object is in the robot view.
	public class RobotHear : RobotPerceptionEvent
	{
		public double volume;

		public String detail;

		private HearingAttribute soundType;

		private CartesianPos pos;

		/// insert the sound captured by the hear sensor. Returns true if the insertion in the triple store base was a success, false otherwise.
		public override Boolean insert()
		{
			return false;
		}

		/// Constructor of the RobotHear class. The objective is to create a instance for a sound perception. 
		/// he instant parameter represens the exact moment of the sound capture.
		/// The idObject parameter represents an unique identifier associated the object responsible to produce the sound. 
		/// The kind defines the kind of the sound. 
		/// The volume parameter defines the sound volume.
		/// The detail parameter defines additional information associated with the sound, for example, if kind is MARIANA_VOICE then the detail represents the sentence said.
		/// 
///  
		///  
		public RobotHear(DateTime instant, long idObject, HearingAttribute kind, double volume, string detail)
		{

		}

		/// Constructor of the RobotHear class. The objective is to create a instance for a sound perception.
		/// The instant parameter represens the exact moment of the sound capture. 
		/// The pos parameter represents the position of an unknow object responsible to produce the sound. 
		/// The kind defines the kind of the sound. 
		/// The volume parameter defines the sound volume. 
		/// The detail parameter defines additional information associated with the sound, for example, if kind is MARIANA_VOICE then the detail represents the sentence said.
		public RobotHear(DateTime instant, CartesianPos pos, HearingAttribute kind, double volume, string detail)
		{

		}

	}

}

