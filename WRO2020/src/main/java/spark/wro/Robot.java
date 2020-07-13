package spark.wro;

import ev3dev.actuators.lego.motors.EV3LargeRegulatedMotor;
import ev3dev.actuators.lego.motors.EV3MediumRegulatedMotor;
import ev3dev.sensors.ev3.EV3ColorSensor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.robotics.navigation.DifferentialPilot;

public class Robot {

	EV3MediumRegulatedMotor motorA = new EV3MediumRegulatedMotor(MotorPort.A);
	EV3LargeRegulatedMotor motorB = new EV3LargeRegulatedMotor(MotorPort.B);
	EV3LargeRegulatedMotor motorC = new EV3LargeRegulatedMotor(MotorPort.C);
	EV3MediumRegulatedMotor motorD = new EV3MediumRegulatedMotor(MotorPort.D);

	EV3ColorSensor sensor1 = new EV3ColorSensor(SensorPort.S1);
	EV3ColorSensor sensor2 = new EV3ColorSensor(SensorPort.S2);
	EV3ColorSensor sensor3 = new EV3ColorSensor(SensorPort.S3);
	EV3ColorSensor sensor4 = new EV3ColorSensor(SensorPort.S4);
	
	float wheelSize = 8.16f;
	float trackWidth = 9.5f;
	boolean reverse = true;
	
	DifferentialPilot pilot = new DifferentialPilot(wheelSize, trackWidth, motorB, motorC);
	
	public Robot() {
		
	}
	
	public void forward(float cm, int speed, int stopLine) {
		//TODO:
	}
	public void backward(float cm, int speed, int stopLine) {
		//TODO:
	}
	public void turn(int type, int degree, int stopLine) {
		//TODO:
	}
	public void followLine(float cm, int speed, int stopLine) {
		//TODO:M
	}
	public void turnMotor(int motor, float degree, int angularSpeed) {
		//TODO:M
	}
	public void readReflect(int port) {
		//TODO:
	}
	public void readColor(int port) {
		//TODO:
	}
	public void init() {
		//TODO:
	}
	public void getEV3() {
		//TODO:M
	}
	public void arc(float cm, int speed, int stopLine) {
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
