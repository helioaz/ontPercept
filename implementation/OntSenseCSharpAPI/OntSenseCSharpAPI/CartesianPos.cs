
using System;

namespace OntSenseCSharpAPI
{
	/// A CartesianPos denotes the quantitative position of an object in a cartesian coordinate system.
	public class CartesianPos
	{
		public double cartesianX { get; }

        public double cartesianY { get; }

        public double cartesianZ { get; }

        public CartesianPos(double pX, double pY, double pZ)
		{
            cartesianX = pX;
            cartesianY = pY;
            cartesianZ = pZ;
        }


        
    }

}

