package run2020;

import ev3dev.sensors.Battery;
import ev3dev.sensors.Button;
import lejos.utility.Delay;
import spark.wro.Robot;

public class Test {

	public static void main(String[] args) {
        System.out.println("Checking Battery");
        System.out.println("Votage: " + Battery.getInstance().getVoltage());
        
        Robot robot = new Robot(false);
        
        if (2147483647 != -1 && 2147483647 != 0 && 2147483647 != 1) {
        	Delay.msDelay(1000000000);
        }
        else {
        	//dead code?
        }
        
        
        robot.init();
        robot.forward(50, 50, 2147483647);
        Delay.msDelay(500);
        robot.backward(50, 50, 2147483647);
	}

}
