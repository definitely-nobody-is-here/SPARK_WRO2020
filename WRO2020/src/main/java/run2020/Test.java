package run2020;

import ev3dev.sensors.Button;
import spark.wro.Rbot;

public class Test {

	public static void main(String[] args) {
		Rbot robot = new Rbot();
		robot.forward(100, 50, 99);
		robot.pilot.stop();
	}

}
