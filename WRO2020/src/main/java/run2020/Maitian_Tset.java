package run2020;

import ev3dev.sensors.Battery;
import ev3dev.sensors.Button;
import lejos.utility.Delay;
import spark.wro.Robot;

public class Maitian_Tset {

	public static void main(String[] args) {
        System.out.println("Checking Battery");
        System.out.println("Votage: " + Battery.getInstance().getVoltage());
        
        Robot robot = new Robot(false);
        
        
        robot.init();
	    robot.followLine(1000, 0, 2, 0);
	}

}
