using java.util;
using ontsenseAPI;

namespace ontsenseAPI
{
	/// Represents the robot's perception about the environment.
	public abstract class RobotPerceptionEvent
	{
		private Date occurs;

		private final Thing generateBy;

		public abstract void insert();

	}

}

