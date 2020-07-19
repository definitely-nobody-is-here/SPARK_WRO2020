
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
import lejos.utility.Delay;
import lejos.hardware.lcd.CommonLCD;

public class Robot {
	private final static Logger LOG = org.slf4j.LoggerFactory.getLogger(Robot.class);

	EV3MediumRegulatedMotor motorA = null;
	EV3LargeRegulatedMotor motorB = null;
	EV3LargeRegulatedMotor motorC = null;
	EV3MediumRegulatedMotor motorD = null;

	EV3ColorSensor sensor1 = null;
	EV3ColorSensor sensor2 = null;
	EV3ColorSensor sensor3 = null;
	EV3ColorSensor sensor4 = null;

	Button ev3Buttons = null;

	SampleProvider spColor1 = null;
	SampleProvider spRed2 = null;
	SampleProvider spRed3 = null;
	SampleProvider spColor4 = null;

	float maxWhite = 0;
	float maxBlack = 100;

	float wheelSize = 8.16f;
	float trackWidth = 9.5f;
	double DegreesPerCM = (1 / (Math.PI * wheelSize)) * 360;
	double CMPerDegree = ((Math.PI * trackWidth) / 360);
	boolean reversed = false;

	public DifferentialPilot pilot = null;

	/**
	 * Constructor for Robot
	 */
	public Robot(boolean reversed) {
		String msg = "Cannot init port %s.";
		try {
			motorA = new EV3MediumRegulatedMotor(MotorPort.A);
		} catch (RuntimeException e) {
			System.err.println(String.format(msg, MotorPort.A.getName()));
		}
		try {
			motorB = new EV3LargeRegulatedMotor(MotorPort.B);
		} catch (RuntimeException e) {
			System.err.println(String.format(msg, MotorPort.B.getName()));
		}
		try {
			motorC = new EV3LargeRegulatedMotor(MotorPort.C);
		} catch (RuntimeException e) {
			System.err.println(String.format(msg, MotorPort.C.getName()));
		}
		try {
			motorD = new EV3MediumRegulatedMotor(MotorPort.D);
		} catch (RuntimeException e) {
			System.err.println(String.format(msg, MotorPort.D.getName()));
		}

		try {
			sensor1 = new EV3ColorSensor(SensorPort.S1);
		} catch (RuntimeException e) {
			System.err.println(String.format(msg, SensorPort.S1.getName()));
		}
		try {
			sensor2 = new EV3ColorSensor(SensorPort.S2);
		} catch (RuntimeException e) {
			System.err.println(String.format(msg, SensorPort.S2.getName()));
		}
		try {
			sensor3 = new EV3ColorSensor(SensorPort.S3);
		} catch (RuntimeException e) {
			System.err.println(String.format(msg, SensorPort.S3.getName()));
		}
		try {
			sensor4 = new EV3ColorSensor(SensorPort.S4);
		} catch (RuntimeException e) {
			System.err.println(String.format(msg, SensorPort.S4.getName()));
		}
		
		try {
			ev3Buttons = new Button();
		} catch (RuntimeException e) {
			System.err.println("Cannot init button.");
		}

		try {
			if (sensor1 != null) {
				spColor1 = sensor1.getColorIDMode();
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		try {
			if (sensor2 != null) {
				spRed2 = sensor2.getRedMode();
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		try {
			if (sensor3 != null) {
				spRed3 = sensor3.getRedMode();
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		try {
			if (sensor4 != null) {
				spColor4 = sensor4.getColorIDMode();
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
		}

		pilot = new DifferentialPilot(wheelSize, trackWidth, motorB, motorC, reversed);
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
		if (stopLine == -1 || stopLine == 0 || stopLine == 1){
			pilot.forward();
			int techo = 0;
			do {
				techo = (motorB.getTachoCount() + motorC.getTachoCount()) / 2;
				LOG.debug("techo = {}", techo);
			} while (techo < (cm * DegreesPerCM));

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
			default: {
			}
			pilot.stop();
			}
		} else {
			pilot.travel(cm);
			pilot.stop();
		}

	}

	/**
	 * moves robot backwards
	 * @param cm | must be a positive measurement in centimeters, how much you want to travel backwards
	 * @param speed | between one and 700
	 * @param stopLine | -1 means search with left sensor, 0 means search with both, 1 means search with right sensor. Input any other value to not search for line.
	 */
	public void backward(float cm, int speed, int stopLine) {

		pilot.setLinearSpeed(Math.abs(speed));
		motorB.resetTachoCount();
		motorC.resetTachoCount();
		
		//determine stopping at line
		if (stopLine == -1 || stopLine == 0 || stopLine == 1){
			pilot.backward();
			while ((Math.abs(motorB.getTachoCount()) + Math.abs(motorC.getTachoCount())) / 2 < (cm * DegreesPerCM)) {
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
			default: {				
			}
				pilot.stop();
			}
		} else {
			pilot.travel(-cm);
			pilot.stop();
		}

	}

	/**
	 * turns the robot
	 * @param type | -1 means left wheel, 0 means both, 1 means right wheel
	 * @param degree | positive is right turn, negative is left turn
	 * @param stopLine | -1 means left sensor, 0 means don't search for line, 1 means right sensor *doesn't apply to -1 and 1 turn values*
	 */
	public void turn(int type, int degrees, int stopLine) {

		pilot.setLinearSpeed(100);
		motorB.resetTachoCount();
		motorC.resetTachoCount();
		
		//determine turn type
		switch (type) {
		case -1: {
			motorB.forward();
			while (Math.abs(motorB.getTachoCount()) < (CMPerDegree / 2) * degrees) {				
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
			default: {				
			}
			}
		}
		case 0: {
			motorB.forward();
			while (Math.abs(motorB.getTachoCount()) < (CMPerDegree / 2) * degrees) {				
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
			default: {				
			}
			}
		}
		case 1: {
			
		}

	}
	}

	/**
	 * follows a line in front of the robot with sensors
	 * @param cm | must be a positive measurement in centimeters, how much you want to travel
	 * @param stopLine | -1 means search with left sensor, 0 means search with both, 1 means search with right sensor. Input any other value to not search for line.
	 * @param port1 | Left sensor port
	 * @param port2 | Right sensor port, 0 means only one sensor
	 */
	public void followLine(float cm, int stopLine, int port1, int port2) {
		//          why is this a one-sensor thing?
		//          both sensors would be better, since the breakages in the line can be ignored by a two-sensor config where the p is just the values of the two sensors subracting from each other
		// PID Settings
		float kP = 1.5f;
		float kI = 0.001f;
		float kD = 1.5f;
		float integralDecay = 1 / 2;

		// Tacho Count
		int wheelValue = 0;

		// Create Variables
		float error;
		float pastError = 0;
		float integralError = 0;

		LOG.info("DegreesPerCM: " + DegreesPerCM);
		LOG.info("wheelValue: " + wheelValue);
		LOG.info("cm: " + cm);
		if(port2 != 0 && port1 != 0) {
			LOG.info("PORTS ARE NOT ZERO!!!!!");
		}
		LOG.info("port1: " + port1);
		LOG.info("port2: " + port2);
		// Loop
		while (wheelValue < (cm * DegreesPerCM)) {
			// Update Tacho Count
			wheelValue = (motorB.getTachoCount() + motorC.getTachoCount()) / 2;

			// Color Sensor Values
			float currentError = 0;
			if(port1 != 0 && port2 != 0) {
				float colorL = readReflect(port1);
				float colorR = readReflect(port2);
				currentError = colorL - colorR;
			}
			else if(port1 != 0 && port2 == 0) {
				float colorL = readReflect(port1);
				currentError = colorL - (maxWhite + maxBlack) / 2;
			}
			else if(port1 == 0 && port2 != 0) {
				float colorR = readReflect(port2);
				currentError = (maxWhite + maxBlack) / 2 - colorR;
			}
			else {
				return;
			}
			
			LOG.info("currentError: " + currentError);

			// Change integralError
			integralError = integralError * integralDecay + currentError;

			// Calculate errors
			float errorP = currentError;
			float errorI = integralError;
			float errorD = currentError - pastError;

			// Make pastError error
			pastError = currentError;

			// Calculate Total Error
			error = kP * errorP + kI * errorI + kD * errorD;

			// Drive Robot
			if(error < 0) {
				motorB.setSpeed(300 + Math.round(error));
				motorC.setSpeed(300);
			}
			else {
				motorB.setSpeed(300);
				motorC.setSpeed(300 - Math.round(error));
			}
			LOG.info("error: " + error);
			motorB.forward();
			motorC.forward();
		}
		motorB.stop();
		motorC.stop();
	}

	/**
	 * Turns a motor
	 * @param motor | One of the ports
	 * @param degree | How many degrees you want to turn the motor
	 * @param angularSpeed | How fast you want to turn the motor
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
		case 1:
		case 4: {
			throw new IllegalArgumentException("Unexpected value: " + port);
		}
		case 2: {
			int samples = spRed2.sampleSize();
			float[] sample = new float[samples];
			spRed2.fetchSample(sample, 0);
			return (int) sample[0];
		}
		case 3: {
			int samples = spRed3.sampleSize();
			float[] sample = new float[samples];
			spRed3.fetchSample(sample, 0);
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
		case 1:
		case 4: {
			throw new IllegalArgumentException("Unexpected value: " + port);
		}
		case 2: {
			int samples = spColor1.sampleSize();
			float[] sample = new float[samples];
			spColor1.fetchSample(sample, 0);
			return sample[0];
		}
		case 3: {
			int samples = spColor4.sampleSize();
			float[] sample = new float[samples];
			spColor4.fetchSample(sample, 0);
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
		pilot.setLinearSpeed(10);
		pilot.forward();
		
		// Read Values
		int i = 10;
		float colorValue;
		while(i > 0) {
			i -= 1;
			colorValue = readReflect(2);
			if(colorValue > maxWhiteValue) {
				maxWhiteValue = colorValue;
			}
			if(colorValue < maxBlackValue) {
				maxBlackValue = colorValue;
			}
            Delay.msDelay(1);
    		System.out.println(maxWhiteValue);
    		System.out.println(maxBlackValue);
    		LOG.info("White: " + maxWhiteValue);
    		LOG.info("Black: " + maxBlackValue);
		}
		
		// Stop Pilot
		pilot.stop();
		
		// Set Values
		maxWhite = maxWhiteValue;
		maxBlack = maxBlackValue;
		
		// Print Values
		
	}

	/**
	 * 
	 */
	public void getEV3() {
		// TODO:M
	}
	
	/**
	 * @param steering
	 * @param speed
	 * @param stopLine
	 * @param immediateReturn
	 */
	public void steer(float steering, int speed, int stopLine, boolean immediateReturn) {
		// TODO:M
		pilot.arc(steering, 0.1, immediateReturn);
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
