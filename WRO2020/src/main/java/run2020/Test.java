package run2020;

import ev3dev.sensors.Button;
import spark.wro.Robot;

public class Test {

	public static void main(String[] args) {
		Robot robot = new Robot();
		robot.forward(100, 50, 99);
		robot.pilot.stop();
	}

}
