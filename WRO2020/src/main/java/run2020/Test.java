package run2020;

import ev3dev.sensors.ev3.EV3ColorSensor;
import lejos.hardware.port.SensorPort;
import lejos.robotics.SampleProvider;

public class Test {

	public static void main(String[] args) {
		final EV3ColorSensor ColorSensor = new EV3ColorSensor(SensorPort.S2);
		
		SampleProvider sp = ColorSensor.getColorIDMode();
		float[] sample = new float[sampleSize];
		int value = 0;
		
		
	}

}
