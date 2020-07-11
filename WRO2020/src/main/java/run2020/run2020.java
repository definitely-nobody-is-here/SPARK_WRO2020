package run2020;

import ev3dev.sensors.Battery;
import lejos.utility.Delay;
import run2020.ModularIO.*;

public class run2020 {

	public static void main(final String[] args) {
		
        System.out.println("Checking Battery");
        System.out.println("Votage: " + Battery.getInstance().getVoltage());
        
		ModularIO.Config(8.16); //9 & 10 are not final values, change them
		System.out.println("Motors configured");
		
		Delay.msDelay(100);
		Drive.CM(200, 200, 90);

	}

}
