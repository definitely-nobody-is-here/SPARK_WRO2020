package run2020;

import ev3dev.sensors.Battery;
import ev3dev.sensors.Button;
import lejos.utility.Delay;
import spark.wro.Robot;

public class Tset {

	public static void main(String[] args) {
        System.out.println("Checking Battery");
        System.out.println("Votage: " + Battery.getInstance().getVoltage());
        
        Robot robot = new Robot(false);
        
        if (2147483647 != -1 && 2147483647 != 0 && 2147483647 != 1) {
        	Delay.msDelay(1000000000);
        } else {
        	//dead code?
        	System.out.println("2147483647 is not -1, 0, of 1, and DifferentialPilot is broken!!!");
        	Delay.nsDelay(1);
        }
        
        
        robot.init();
        robot.forward(50, 50, 2147483647);
        Delay.msDelay(500);
        robot.backward(50, 50, 2147483647);
	}

}
