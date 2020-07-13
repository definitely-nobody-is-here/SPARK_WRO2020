package run2020;

import ev3dev.sensors.Battery;
import lejos.utility.Delay;
import run2020.ModularIO.*;

public class run2020 {

	public static void main(final String[] args) {
		
        System.out.println("Checking Battery");
        System.out.println("Votage: " + Battery.getInstance().getVoltage());
        
        ModularIO Robot = new ModularIO();
        
        
        new Drive().CM(-200, -200, 50, false);
        new Drive().CM(100, -100, 20, false);
	}

}
