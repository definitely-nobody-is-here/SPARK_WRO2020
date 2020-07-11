package run2020.ModularIO;

public final class Drive extends ModularIO {
	
	private static void CheckPowerLimits(int power) {
		
		if (Math.abs(power) > 700) {
			System.exit(1);
			
		} 
	}

	public static void CM(int powerL, int powerR, int centimeters) {
		
		CheckPowerLimits(powerL);
		CheckPowerLimits(powerR);
		
		driveB.stop();
		driveC.stop();
		driveB.setSpeed(Math.abs(powerL));
		driveC.setSpeed(Math.abs(powerR));
		
		if (powerL < 0) {
			driveB.rotate((int) -(DegreesPerCM * centimeters));
		} else {
			driveB.rotate((int) (DegreesPerCM * centimeters));
		}
		if (powerR < 0) {
			driveC.rotate((int) -(DegreesPerCM * centimeters));
		} else {
			driveC.rotate((int) (DegreesPerCM * centimeters));
		}
		
	}
	
	public static void Continuous(int powerL, int powerR) {
		
		CheckPowerLimits(powerL);
		CheckPowerLimits(powerR);
		
		driveB.stop();
		driveC.stop();
		driveB.setSpeed(Math.abs(powerL));
		driveC.setSpeed(Math.abs(powerR));
		
		if (powerL < 0) {
			driveB.forward();
		} else {
			driveB.backward();
		}
		if (powerR < 0) {
			driveC.forward();
		} else {
			driveC.backward();
		}
		
	}
	
	public static void Stop() {
		
		driveB.stop();
		driveC.stop();
		
	}
	
	public static void Turn_angle(int power, int degrees) {
		
		CheckPowerLimits(power);
		
		driveB.stop();
		driveC.stop();
		driveB.setSpeed(Math.abs(power));
		driveC.setSpeed(Math.abs(power));
		
		if (degrees < 0) {
			driveB.rotate((int) (DegreesPerCM * (CMPerDegree * degrees)));
			
		}
	}
	
	public static void Turn_ToLine(String direction) {
		
		driveB.stop();
		driveC.stop();
		
		if (direction == "RIGHT") {
			driveB.setSpeed(70);
			driveC.setSpeed(-70);
			
		}
	}
}