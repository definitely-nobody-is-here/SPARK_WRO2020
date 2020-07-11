package run2020.ModularIO;

import ev3dev.actuators.lego.motors.EV3LargeRegulatedMotor;
import ev3dev.actuators.lego.motors.EV3MediumRegulatedMotor;
import ev3dev.sensors.ev3.EV3ColorSensor;
import ev3dev.sensors.ev3.EV3UltrasonicSensor;
import ev3dev.actuators.LCDStretch;
import ev3dev.sensors.Button;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.robotics.SampleProvider;
import java.lang.Math;


public class ModularIO {
	
	static final EV3LargeRegulatedMotor driveB = new EV3LargeRegulatedMotor(MotorPort.B);
    static final EV3LargeRegulatedMotor driveC = new EV3LargeRegulatedMotor(MotorPort.C);
    static final EV3MediumRegulatedMotor motorA = new EV3MediumRegulatedMotor(MotorPort.A);
    static final EV3MediumRegulatedMotor motorD = new EV3MediumRegulatedMotor(MotorPort.D);
    static final EV3ColorSensor color2 = new EV3ColorSensor(SensorPort.S2);
    static final EV3ColorSensor color3 = new EV3ColorSensor(SensorPort.S3);
    
    static double DegreesPerCM;
    static double CMPerDegree;
    
    public static final void Config(double wheelDiameter, double wheelDistance) {
   
    	driveB.brake();
    	driveC.brake();
    	
    	DegreesPerCM = (1 / (Math.PI * wheelDiameter)) * 360;
    	
    	SampleProvider ColorSensorRead2 = color2.getRedMode();
    	SampleProvider ColorSensorRead2 = color3.getRedMode();
    	
    }
}