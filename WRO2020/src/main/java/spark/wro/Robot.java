package spark.wro;


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
	
	SampleProvider ReadIntensity2 = color2.getRedMode();
	SampleProvider ReadIntensity3 = color3.getRedMode();
	SampleProvider ReadColor2 = color2.getColorIDMode();
	SampleProvider ReadColor3 = color3.getColorIDMode();
	
	float wheelSize = 8.16f;
	float trackWidth = 9.5f;
	boolean reversed = true;
	
	DifferentialPilot pilot = new DifferentialPilot(wheelSize, trackWidth, motorB, motorC);
	
	
	public Robot() {
		
	}
	
	public void forward(float cm, int speed, int stopLine) {
		
		pilot.setLinearSpeed(speed);
		
		//determine stopping at line
		if (stopLine) {
			pilot.forward();
			while ((motorB.getTachoCount() + motorC.getTachoCount()) / 2 < (cm * )) {
			
		} else {
			pilot.travel(cm);
		}

		}
	}
	public void backward(float cm, int speed, int stopLine) {
		//TODO:
	}
	public void turn(int type, int degree, int stopLine) {
		//TODO:
	}
	public void followLine(float cm, int speed, int stopLine, int port) {
		//PID Settings
		float kP = 1f;
		float kI = 0.01f;
		float kD = 1f;
		float integralDecay = 1 / 2;
		
		//Tacho Count
		int wheelValue = 0;

		//Create Variables
		float error;
		float pastError = 0;
		float integralError = 0;
		
		//Loop
		while(wheelValue < cm) {
			//Update Tacho Count
			wheelValue = (motorB.getTachoCount() + motorC.getTachoCount()) / 2;
			
			//Color Sensor Values
			float colorValue = readReflect(port);
			
			//Calculate errors
			float errorP = colorValue - (maxWhite + maxBlack) / 2;
			float errorI = integralError;
			float errorD = colorValue - (maxWhite + maxBlack) / 2;
			
			//Calculate Total Error
			error = kP * errorP + kI * errorI + kD * errorD;
			
			//Make pastError error
			pastError = colorValue - (maxWhite + maxBlack) / 2;
			
			//Change integralError
			integralError = integralError * integralDecay + colorValue - (maxWhite + maxBlack) / 2;
			
			//Drive Robot
			arc(error,0,100,stopLine);
		}
	}
	public void turnMotor(int motor, float degree, int angularSpeed) {
		//TODO:M
	}
	public void setMode(int mode) {
		
	}
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
			throw new IllegalArgumentException("Unexpected value: " + port);
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + port);
		}
	}
	public String fhgfadklgvnrgkhagkhahLSGQRAGHFDHGHADGreadColor(int port) {
		switch (port) {
		case 1: {
			throw new IllegalArgumentException("Unexpected value: " + port);
		}
		case 2: {
			int samples = ReadColor2.sampleSize();
			float[] sample = new float[samples];
			ReadColor2.fetchSample(sample, 0);
			return (String) sample[0];
		}
		case 3: {
			int samples = ReadColor3.sampleSize();
			float[] sample = new float[samples];
			ReadColor3.fetchSample(sample, 0);
			return sample[0];
		}
		case 4: {
			throw new IllegalArgumentException("Unexpected value: " + port);
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + port);
		}
	}
	public void init() {
		//TODO:
		//Calibrate Color Sensors
		calibrateColorSensor();
	}
	public void calibrateColorSensor() {
		//Max White and Black Values
		float maxWhiteValue = 0;
		float maxBlackValue = 100;
		
		//Calibrate
		//TODO:M stuff
		
		//Set Values
		maxWhite = maxWhiteValue;
		maxBlack = maxBlackValue;
	}
	public void getEV3() {
		//TODO:M
	}
	public void arc(float steering, float cm, int speed, int stopLine) {
		//TODO:M
	}
	public void reset() {
		//TODO:none
	}
	public void align() {
		//TODO:M
	}
	public void square() {
		//TODO:M
	}
}
