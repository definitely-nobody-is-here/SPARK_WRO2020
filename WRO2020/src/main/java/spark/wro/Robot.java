
package spark.wro;

import org.slf4j.Logger;

import ev3dev.actuators.lego.motors.EV3LargeRegulatedMotor;
import ev3dev.actuators.lego.motors.EV3MediumRegulatedMotor;
import ev3dev.sensors.Button;
import ev3dev.sensors.ev3.EV3ColorSensor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.robotics.SampleProvider;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.hardware.Keys;
import lejos.hardware.lcd.CommonLCD;

public class Robot {
	private final static Logger LOG = null;

	EV3MediumRegulatedMotor motorA = new EV3MediumRegulatedMotor(MotorPort.A);
	EV3LargeRegulatedMotor motorB = new EV3LargeRegulatedMotor(MotorPort.B);
	EV3LargeRegulatedMotor motorC = new EV3LargeRegulatedMotor(MotorPort.C);
	EV3MediumRegulatedMotor motorD = new EV3MediumRegulatedMotor(MotorPort.D);

	EV3ColorSensor sensor1 = new EV3ColorSensor(SensorPort.S1);
	EV3ColorSensor sensor2 = new EV3ColorSensor(SensorPort.S2);
	EV3ColorSensor sensor3 = new EV3ColorSensor(SensorPort.S3);
	EV3ColorSensor sensor4 = new EV3ColorSensor(SensorPort.S4);

	Button ev3Buttons = new Button();

	float maxWhite = 0;
	float maxBlack = 100;

	SampleProvider ReadIntensity2 = sensor2.getRedMode();
	SampleProvider ReadIntensity3 = sensor3.getRedMode();
	SampleProvider ReadIntensity4 = sensor4.getRedMode();
	SampleProvider ReadColor2 = sensor2.getColorIDMode();
	SampleProvider ReadColor3 = sensor3.getColorIDMode();
	SampleProvider ReadColor4 = sensor4.getColorIDMode();

	float wheelSize = 8.16f;
	float trackWidth = 9.5f;
	double DegreesPerCM = (1 / (Math.PI * wheelSize)) * 360;
	double CMPerDegree = ((Math.PI * trackWidth) / 360);
	boolean reversed = true;

	DifferentialPilot pilot = new DifferentialPilot(wheelSize, trackWidth, motorB, motorC);

	public Robot() {
	}

	/**
	 * moves robot forwards
	 * @param cm | must be a positive measurement in centimeters, how much you want to travel
	 * @param speed | between one and 700
	 * @param stopLine | -1 means search with left sensor, 0 means search with both, 1 means search with right sensor. Input any other value to not search for line.
	 */
	public void forward(float cm, int speed, int stopLine) {

		pilot.setLinearSpeed(speed);
		motorB.resetTachoCount();
		motorC.resetTachoCount();
		
		//determine stopping at line
		if (stopLine != -1 && stopLine != 0 && stopLine != 1){
			pilot.forward();
			while ((motorB.getTachoCount() + motorC.getTachoCount()) / 2 < (cm * DegreesPerCM)) {
				// do nothing
			}
			switch (stopLine) {
			case -1: {
				while (readReflect(2) > 20) {

				}
			}
			case 0: {
				while (readReflect(2) > 20 && readReflect(3) > 20) {

				}
			}
			case 1: {
				while (readReflect(3) > 20) {

				}
			}
			pilot.stop();
			}
		} else {
			pilot.travel(cm);
		}

	}

	/**
	 * moves the robot backwards
	 * @param cm | must be a positive measurement in centimeters, how much you want to travel
	 * @param speed | between one and 700
	 * @param stopLine | -1 means search with left sensor, 0 means search with both, 1 means search with right sensor. Input any other value to not search for line.
	 */
	public void backward(float cm, int speed, int stopLine) {

		pilot.setLinearSpeed(speed);
		motorB.resetTachoCount();
		motorC.resetTachoCount();
		
		//determine stopping at line
		if (stopLine != -1 && stopLine != 0 && stopLine != 1){
			pilot.backward();
			while ((Math.abs(motorB.getTachoCount()) + Math.abs(motorC.getTachoCount())) / 2 < (cm * DegreesPerCM)) {
				//do nothing
			}
			switch (stopLine) {
			case -1: {
				while (readReflect(2) > 20) {

				}
			}
			case 0: {
				while (readReflect(2) > 20 && readReflect(3) > 20) {

				}
			}
			case 1: {
				while (readReflect(3) > 20) {

				}
			}
				pilot.stop();
			}
		} else {
			pilot.travel(cm);
		}

	}

	/**
	 * turns the robot
	 * @param type -1 means left wheel, 0 means both, 1 means right wheel
	 * @param degree | positive is right turn, negative is left turn
	 * @param stopLine | -1 means left sensor, 0 means don't search for line, 1 means right sensor
	 */
	public void turn(int type, int degrees, int stopLine) {

		pilot.setLinearSpeed(100);
		motorB.resetTachoCount();
		motorC.resetTachoCount();
		
		//determine turn type
		switch (type) {
		case -1: {
			while (Math.abs(motorB.getTachoCount()) < (CMPerDegree / 2) * degrees) {
				
			}
		}
		case 0: {
			
		}
		case 1: {
			
		}

	}
	}

	/**
	 * follows a line in front of the robot with sensors
	 * @param cm | must be a positive measurement in centimeters, how much you want to travel
	 * @param speed | between one and 700
	 * @param stopLine | -1 means search with left sensor, 0 means search with both, 1 means search with right sensor. Input any other value to not search for line.
	 * @param port | N/A
	 */
	public void followLine(float cm, int speed, int stopLine, int port) {
		//          why is this a one-sensor thing?
		//          both sensors would be better, since the breakages in the line can be ignored by a two-sensor config where the p is just the values of the two sensors subracting from each otehr
		//PID Settings
		float kP = 1f;
		float kI = 0.01f;
		float kD = 1f;
		float integralDecay = 1 / 2;

		// Tacho Count
		int wheelValue = 0;

		// Create Variables
		float error;
		float pastError = 0;
		float integralError = 0;

		// Loop
		while (wheelValue < cm) {
			// Update Tacho Count
			wheelValue = (motorB.getTachoCount() + motorC.getTachoCount()) / 2;

			// Color Sensor Values
			float colorValue = readReflect(port);

			// Calculate errors
			float errorP = colorValue - (maxWhite + maxBlack) / 2;
			float errorI = integralError;
			float errorD = colorValue - (maxWhite + maxBlack) / 2;

			// Calculate Total Error
			error = kP * errorP + kI * errorI + kD * errorD;

			// Make pastError error
			pastError = colorValue - (maxWhite + maxBlack) / 2;

			// Change integralError
			integralError = integralError * integralDecay + colorValue - (maxWhite + maxBlack) / 2;

			// Drive Robot
			arc(error, 0, 100, stopLine);
		}
	}

	/**
	 * @param motor
	 * @param degree
	 * @param angularSpeed
	 */
	public void turnMotor(int motor, float degree, int angularSpeed) {
		// TODO:M
	}

	/**
	 * @param mode
	 */
	public void setMode(int mode) {

	}

	/**
	 * @param port
	 * @return
	 */
	public int readReflect(int port) {
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
			int samples = ReadIntensity4.sampleSize();
			float[] sample = new float[samples];
			ReadIntensity4.fetchSample(sample, 0);
			return (int) sample[0];
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + port);
		}
	}

	/**
	 * @param port
	 * @return
	 */
	public float readColor(int port) {
		switch (port) {
		case 1: {
			throw new IllegalArgumentException("Unexpected value: " + port);
		}
		case 2: {
			int samples = ReadColor2.sampleSize();
			float[] sample = new float[samples];
			ReadColor2.fetchSample(sample, 0);
			return sample[0];
		}
		case 3: {
			int samples = ReadColor3.sampleSize();
			float[] sample = new float[samples];
			ReadColor3.fetchSample(sample, 0);
			return sample[0];
		}
		case 4: {
			int samples = ReadColor4.sampleSize();
			float[] sample = new float[samples];
			ReadColor4.fetchSample(sample, 0);
			return sample[0];
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + port);
		}
	}

	/**
	 * 
	 */
	public void init() {
		// TODO:
		// Calibrate Color Sensors
		calibrateColorSensor();
	}

	/**
	 * 
	 */
	public void calibrateColorSensor() {
		// Max White and Black Values
		float maxWhiteValue = 0;
		float maxBlackValue = 100;

		// Calibrate

		// Set Values
		maxWhite = maxWhiteValue;
		maxBlack = maxBlackValue;
	}

	/**
	 * 
	 */
	public void getEV3() {
		// TODO:M
	}

	/**
	 * @param steering
	 * @param cm
	 * @param speed
	 * @param stopLine
	 */
	public void arc(float steering, float cm, int speed, int stopLine) {
		// TODO:M
	}

	/**
	 * 
	 */
	public void reset() {
		// TODO:none
	}

	/**
	 * 
	 */
	public void align() {
		// TODO:M
	}

	/**
	 * 
	 */
	public void square() {
		// TODO:M
	}
}
