package run2020.ModularIO;

import lejos.robotics.SampleProvider;

public final class ColorSensor extends ModularIO{
	
	public static int ReadIntensity() {
		
		SampleProvider sp = color2.getRedMode();
		
		int sampleSize = sp.sampleSize();
		float[] sample = new float[sampleSize];
		int ReadValue;
		
		sp.fetchSample(sample, 0);
		ReadValue = (int) sample[0];
		
		return ReadValue;
		
	}

//	public static float WaitUntilReflect(int intensity, String greaterORless) {
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
