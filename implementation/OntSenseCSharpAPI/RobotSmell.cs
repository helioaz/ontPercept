using System;
using ontsenseAPI;


namespace ontsenseAPI
{
	/// The subclass of RobotPerception in which the sensing is done by an olfactory sensor. 
	/// The unique ID of known object or the position of an unknown object is supplied with the odor information. Note that, the unique ID is only known if the object is in one of the hands of the robot. In this case,  the robot knows what is the object because in order for the robot to pick it up and take it up to the electronic nose, it must have seen the object first .
	public class RobotSmell : RobotPerceptionEvent
	{
		private OlfatoryAttribute smellType;

		private CartesianPos pos;

		/// Constructor of the RobotSmell class. The objective is to create a instance for a odor perception. 
		/// The instant parameter represens the exact moment of the odor capture. 
		/// The idObject parameter represents an unique identifier associated the object responsible to produce the the smell. 
		/// The odor parameter identifies the odor.
		/// 
///  
		///  
		public RobotSmell(DateTime instant, long idObject, OlfatoryAttribute odor)
		{

		}

		/// Constructor of the RobotSmell class. The objective is to create a instance for a odor perception. 
		/// The instant parameter represens the exact moment of the odor capture.
		///  The pos parameter represents the position of an unknow object responsible to produce the the smell. 
		/// The odor parameter identifies the odor.
		public RobotSmell(DateTime instant, CartesianPos pos, OlfatoryAttribute odor)
		{

		}

		/// insert the alfatory attribute captured by the Smell sensor. Returns true if the insertion in the triple store base was a success, false otherwise.
		public override Boolean insert()
		{
			return false;
		}

	}

}

