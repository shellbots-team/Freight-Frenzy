package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

abstract class robotcomponent {

	// Setting up constants used for run to position
	static final double COUNTS_PER_MOTOR_REV = 400;    // eg: 1440 if TETRIX Motor Encoder
	static final double DRIVE_GEAR_REDUCTION = 1.0;     // This is < 1.0 if geared UP
	static final double WHEEL_DIAMETER_INCHES = 3.75;     // For figuring circumference
	static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
			(WHEEL_DIAMETER_INCHES * 3.1415);

	static final double DEFAULT_DRIVE_SPEED = 1.0;
	static final double DEFAULT_TURN_SPEED = 0.5;

	private OpMode opmode;

	robotcomponent(OpMode opmode) {
		this.opmode = opmode;
	}

	/**
	 * Force all components to have a quick stop option
	 */
	public abstract void stopEverything();

	/**
	 * Force all componts to be able to log their data while in teleOp mode
	 */
	abstract void logTeleOpData();

	/**
	 * Sets the power for all passed motors to a given power
	 *
	 * @param power  The power to set the motors to (-1.0 to 1.0)
	 * @param motors The motors to set to the given power
	 */
	void setSpecificPowers(double power, DcMotor... motors) {
		for (DcMotor motor : motors) {
			motor.setPower(power);
		}
	}

	/**
	 * Sets the run mode for all passed motors to a given mode
	 *
	 * @param runMode The DcMotor.RunMode to set the motors to
	 * @param motors One or more motors to be set on that mode
	 */
	void setRunMode(DcMotor.RunMode runMode, DcMotor... motors) {
		for (DcMotor motor : motors) {
			motor.setMode(runMode);
		}
	}

	/**
	 * Tells if the opmode is active
	 *
	 * @return A true or false value of if opmode is active
	 */
	boolean opModeIsActive() {
		if (opmode instanceof LinearOpMode) {
			return ((LinearOpMode) opmode).opModeIsActive();
		}
		return true;
	}

	/**
	 * Allow pausing of user-code execution for a set amount of time without breaking things
	 *
	 * @param milliseconds The time (in milliseconds) to pause for
	 */
	void sleep(long milliseconds) {
		if (opmode instanceof LinearOpMode) {
			((LinearOpMode) opmode).sleep(milliseconds);
		}
	}

}
