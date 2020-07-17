package run2020;

import ev3dev.sensors.Battery;
import lejos.utility.Delay;
import run2020.ModularIO.*;
import spark.wro.*;

public class run2020 {

	public static void main(final String[] args) {
		
        System.out.println("Checking Battery");
        System.out.println("Votage: " + Battery.getInstance().getVoltage());
        
        Robot robot = new Robot();
        
        robot.forward(50f, 450, 2147483647);
        robot.backward(50, 450, 2147483647);
        
	}

}
