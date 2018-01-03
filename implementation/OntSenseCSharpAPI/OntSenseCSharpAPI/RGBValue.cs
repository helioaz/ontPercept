namespace OntSenseCSharpAPI
{
	/// Represents the main color associated with a given object.
	public class RGBValue
	{
		public double blue { get; }

        public double green { get; }

        public double red { get; }

		public RGBValue(double redLevel, double greenLevel, double blueLevel)
		{
            red = redLevel;
            green = greenLevel;
            blue = blueLevel;
        }

        

    }

}

