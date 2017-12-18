using java.lang;

namespace ontsenseAPI
{
	public sealed class SparqlAccess
	{
		/// script for a sparql insert operation with Hear sense  information
		public readonly const String INSERT_HEAR;

		/// script for a sparql insert operation with smell sense  information
		public readonly const String INSERT_SMELL;

		/// script for a sparql insert operation with taste sense  information
		public readonly const String INSERT_TASTE;

		/// script for a sparql insert operation with touch sense  information
		public readonly const String INSERT_TOUCH;

		/// script for a sparql insert operation with vision sense  information
		public readonly const String INSERT_VISION;

		/// script for a sparql query operation with all sense information after a given time
		/// 
		/// 
		/// 
		public readonly const String QUERY_ALL_AFTER_TIME;

		/// script for a sparql query operation with hear sense  information after a given time
		public const String QUERY_HEAR_AFTER_TIME;

		/// script for a sparql query operation with smell sense  information after a given time
		public readonly const String QUERY_SMELL_AFTER_TIME;

		/// script for a sparql query operation with taste sense  information after a given time
		public readonly const String QUERY_TASTE_AFTER_TIME;

		/// script for a sparql query operation with touch sense  information after a given time
		public readonly const String QUERY_TOUCH_AFTER_TIME;

		/// script for a sparql query operation with vision sense  information after a given time
		public readonly const String QUERY_VISION_AFTER_TIME;

		/// script for a sparql delete operation  after a given time
		public readonly const String DELETE_BEFORE_TIME;

		public readonly const String INSERT_THING;

		public readonly const String INSERT_COGNITIVE;

	}

}

