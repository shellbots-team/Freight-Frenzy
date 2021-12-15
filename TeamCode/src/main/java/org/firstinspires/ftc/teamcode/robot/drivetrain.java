package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Logger;

public class drivetrain extends  robotcomponent {

	private DcMotor frontLeft;
	private DcMotor frontRight;
	private DcMotor backLeft;
	private DcMotor backRight;

	private Logger logger = null;
	public double defaultSpeed = 0.2;

	drivetrain(OpMode opmode) {
		super(opmode);
	}

	void init(Telemetry telemetry, DcMotor frontLeft, DcMotor frontRight, DcMotor backLeft, DcMotor backRight) {

		logger = new Logger(telemetry);

		// Save all passed motors
		this.frontLeft = frontLeft;
		this.frontRight = frontRight;
		this.backLeft = backLeft;
		this.backRight = backRight;

		// Set them to have a target position of 0 (nothing)
		frontLeft.setTargetPosition(0);
		frontRight.setTargetPosition(0);
		backLeft.setTargetPosition(0);
		backRight.setTargetPosition(0);

		// Set forward/reverse to account for position on the robot
		frontLeft.setDirection(DcMotor.Direction.FORWARD); // Set to REVERSE if using AndyMark motors
		backLeft.setDirection(DcMotor.Direction.FORWARD);
		frontRight.setDirection(DcMotor.Direction.REVERSE);
		backRight.setDirection(DcMotor.Direction.REVERSE);// Set to FORWARD if using AndyMark motors

		// Set motors to actively break (resist movement) is they are given 0 power
		frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);// Set to FORWARD if using AndyMark motors
		frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);// Set to FORWARD if using AndyMark motors
		backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);// Set to FORWARD if using AndyMark motors
		backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);// Set to FORWARD if using AndyMark motors

		setAllPowers(0);

		// Set all motors to run using encoders
		setRunMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER, frontLeft, frontRight, backLeft, backRight);
	}

	@Override
	public void logTeleOpData() {
		logger.numberLog("Frontleft", frontLeft.getPower());
		logger.numberLog("Frontleft", frontLeft.getCurrentPosition());
		logger.numberLog("Frontright", frontRight.getPower());
		logger.numberLog("Frontright", frontRight.getCurrentPosition());
		logger.numberLog("Backleft", backLeft.getPower());
		logger.numberLog("Backleft", backLeft.getCurrentPosition());
		logger.numberLog("Backright", backRight.getPower());
		logger.numberLog("Backright", backRight.getCurrentPosition());
	}

	public void runWithoutEncoders() {
		setRunMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER, frontLeft, frontRight, backLeft, backRight);
	}

	private void normalizePower(double speed, double totalDistance, DcMotor motor) {
		double x = Math.abs( motor.getTargetPosition() - motor.getCurrentPosition() ) / totalDistance;
		//if( x < 0.15 || x > 0.85 ) { motor.setPower(speed * 0.75); }
		//else { motor.setPower(speed); }
		//motor.setPower( speed * Math.abs( Math.sin( Math.PI / 3 * x + Math.PI/3) ) );
		//motor.setPower( speed * Math.abs( Math.sin( ( 0.5 * Math.PI * x ) + ( Math.PI / 3.75 ) ) ) );
	}

	/**
	 * Sets all 4 motors on the drivetrain to a given power
	 *
	 * @param power The power to set the motors to (-1.0 to 1.0)
	 */

	public void setAllPowers(double power) {
		setSpecificPowers(power, frontLeft, frontRight, backLeft, backRight);
	}

	public void setIndividualPowers(double[] motorPowers) {
		if (motorPowers.length != 4) {
			return;
		}
		frontLeft.setPower(motorPowers[0]);
		frontRight.setPower(motorPowers[1]);
		backLeft.setPower(motorPowers[2]);
		backRight.setPower(motorPowers[3]);
	}
}