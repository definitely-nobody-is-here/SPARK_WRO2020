package run2020;

import org.slf4j.Logger;

import ev3dev.sensors.Battery;
import lejos.utility.Delay;
import spark.wro.Robot;

public class Maitian_Tset {

	private final static Logger LOG = org.slf4j.LoggerFactory.getLogger(Maitian_Tset.class);
	public static void main(String[] args) {
        System.out.println("Checking Battery");
        System.out.println("Votage: " + Battery.getInstance().getVoltage());
        
        Robot robot = new Robot(false);
        
        
        robot.init();
        LOG.info("Running PID.");
        Delay.msDelay(3000);
	    robot.followLine(1000, 0, 2, 0);
	}

}
