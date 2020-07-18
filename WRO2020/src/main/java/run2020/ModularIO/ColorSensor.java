package run2020.ModularIO;

//import lejos.robotics.SampleProvider;

public final class ColorSensor extends ModularIO {

	public ColorSensor(double wheelDiameter, double wheelDistance, boolean reversed) {
		//super(wheelDiameter, wheelDistance, reversed);
		// TODO Auto-generated constructor stub
	}

	public int ReadIntensity(int port) {

		switch (port) {
		case 1: {
			throw new IllegalArgumentException("Unexpected value: " + port);
		}
		case 2: {
			int samples = ReadIntensity2.sampleSize();
			float[] sample = new float[samples];
			ReadIntensity2.fetchSample(sample, 0);
			return (int) sample[0];
		}
		case 3: {
			int samples = ReadIntensity3.sampleSize();
			float[] sample = new float[samples];
			ReadIntensity3.fetchSample(sample, 0);
			return (int) sample[0];
		}
		case 4: {
			throw new IllegalArgumentException("Unexpected value: " + port);
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + port);
		}

	}

	public int ReadColor(int port) {
		switch (port) {
		case 1: {
			throw new IllegalArgumentException("Unexpected value: " + port);
		}
		case 2: {
			int samples = ReadColor2.sampleSize();
			float[] sample = new float[samples];
			ReadColor2.fetchSample(sample, 0);
			return (int) sample[0];
		}
		case 3: {
			int samples = ReadColor3.sampleSize();
			float[] sample = new float[samples];
			ReadColor3.fetchSample(sample, 0);
			return (int) sample[0];
		}
		case 4: {
			throw new IllegalArgumentException("Unexpected value: " + port);
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + port);
		}
	}

//	public float WaitUntilReflect(int intensity, String greaterORless) {
//		
//		if (greaterORless == ">") {
//
//			}
//			
//			return
//		}
//		
//	}
//
}
