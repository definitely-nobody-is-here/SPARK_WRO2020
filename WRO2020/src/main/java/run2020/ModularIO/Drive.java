package run2020.ModularIO;

public final class Drive extends ModularIO {
	
	private void CheckPowerLimits(int power) {
		
		if (Math.abs(power) > 700) {
			System.exit(1);
			
		} 
	}

	public void CM(int powerL, int powerR, int centimeters, boolean StopAtLine) {
		
		CheckPowerLimits(powerL);
		CheckPowerLimits(powerR);
		
		driveB.setSpeed(Math.abs(powerL));
		driveC.setSpeed(Math.abs(powerR));
		
		float Error = 0;
		float LastError = 0;
		float Integral = 0;
		float Derivative = 0;
		float Correction = 0;
		float LPower = powerL;
		float RPower = powerR;
		
		float p = 0.5f;
		float i = 0.001f;
		float d = 2f;
		
		driveB.resetTachoCount();
		driveC.resetTachoCount();
		
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
		
		while ((driveB.getTachoCount() + driveC.getTachoCount()) / 2 < (centimeters * DegreesPerCM)) {
			Error = driveB.getTachoCount()-driveC.getTachoCount();
			Integral = Integral - Error;
			Derivative = Error - LastError;
			
			Correction = (Error * p) + (Integral * i) + (Derivative * d);
			
			if (Correction < 0) {
				
			}
		}		
	}
	
	public void Continuous(int powerL, int powerR) {
		
		CheckPowerLimits(powerL);
		CheckPowerLimits(powerR);
		
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
	
	public void Stop() {
		
		driveB.stop();
		driveC.stop();
		
	}
	
	public void Turn_angle(int degrees, boolean StopAtLine) {
				
		driveB.stop();
		driveC.stop();

		driveB.resetTachoCount();
		driveC.resetTachoCount();
		
		int tachocount = 0;
		
		if (degrees < 0) {
			driveB.setSpeed(Math.abs(100));
			driveC.setSpeed(Math.abs(100));
			driveB.backward();
			driveC.forward();
			while (tachocount < ((DegreesPerCM * (CMPerDegree * degrees)))) {
				tachocount = (driveB.getTachoCount() + driveC.getTachoCount()) / 2;
			Stop();
			}
		} else {
			driveB.setSpeed(Math.abs(100));
			driveC.setSpeed(Math.abs(100));
			driveB.forward();
			driveC.backward();
			while (tachocount < ((DegreesPerCM * (CMPerDegree * degrees)))) {
				tachocount = ((Math.abs(driveB.getTachoCount()) + Math.abs(driveC.getTachoCount()))) / 2;
			Stop();
			}
		}
	}
	
	public void Turn_ToLine(String direction) {
		
		driveB.stop();
		driveC.stop();
		
		if (direction == "RIGHT") {
			driveB.setSpeed(70);
			driveC.setSpeed(-70);
			
		}
	}
}