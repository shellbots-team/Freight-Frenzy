package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Robot.Robot;


/**
 * Created by shell on 11/04/2021.
 */

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(group = "Manual", name = "Manual Mode")
public class TeleOp extends OpMode {

	private Robot robot = new Robot();
	private Logger logger = null;

	private double speed = 0.5;
	private double armSpeed = 1.0;

	private boolean last_x = false;
	private boolean last_a = false;
	private boolean last_y = false;
	private boolean last_b = false;

	/**
	 * Run once after INIT is pushed
	 */
	@Override
	public void init() {
		robot.init(hardwareMap, telemetry, this);
		logger = new Logger(telemetry);
		this.msStuckDetectStop = 60000;

		// Step 0 - Initialized
		logger.statusLog(0, "Initialized");

	}

	/**
	 * Runs constantly after INIT is pushed but before PLAY is pushed
	 */
	@Override
	public void init_loop() {

	}

	int X = 0;
	int Y = 0;

	/**
	 * Runs once after PLAY is pushed
	 */
	@Override
	public void start() {
		logger.statusLog(0, "Playing");
	}

	@Override
	public void loop() {
		/* Controller Layouts
		 *
		 * Controller 1 - "Body Controller"
		 *      Left Trigger     - Set full speed
		 *      Right Trigger    - Set half speed
		 *
		 *      Right Joystick X - Turn the robot
		 *
		 * 		Y Button 		 - Turn on the duck spinner
		 * 		X Button		 - Turn on the duck spinner
		 *
		 * Controller 2 - "Arm Controller"
		 *      Right Trigger    - Extends arm
		 *      Left Trigger     - Detracts arm
		 *
		 * 		Right Bumper	 - Full arm speed
		 * 		Left Bumper 	 - Half arm speed
		 *
		 *      Dpad Up          - Raise the arm
		 *      Dpad Down        - Lower the arm
		 *
		 *      Y Button         - Grab blocks with the hand
		 *      A Button         - Release blocks with the hand
		 *
		 */

		/*
		 * Controller 1 settings
		 */

		singleJoystickDrive();

		if (this.gamepad1.right_trigger > 0.5) {
			speed = 1.0;
		} else if (this.gamepad1.left_trigger > 0.5) {
			speed = 0.5;
		} else if (this.gamepad1.right_bumper) {
			speed = 0.25;
		}

		if(this.gamepad1.y) {
			robot.arm.turnOnSpinner(1);
		} else if (this.gamepad1.a) {
			robot.arm.turnOnSpinner(-1);
		} else {
			robot.arm.turnOffSpinner();
		}

		last_x = this.gamepad1.x;
		last_a = this.gamepad1.a;
		last_y = this.gamepad1.y;
		last_b = this.gamepad1.b;

		/*
		 * Controller 2 settings
		 */

		if (this.gamepad2.right_trigger > 0.5) {
			robot.arm.extendWithPower(0.55);
		} else if (this.gamepad2.left_trigger > 0.5) {
			robot.arm.extendWithPower(-0.55);
		} else {
			robot.arm.extendWithPower(0);
		}

		if (this.gamepad2.right_bumper) {
			armSpeed = 1.0;
		} else if (this.gamepad2.left_bumper) {
			armSpeed = 0.5;
		}

		if (this.gamepad2.dpad_up) {
			robot.arm.raiseWithPower(1 * armSpeed);
		} else if (this.gamepad2.dpad_down) {
			robot.arm.lowerWithPower(0.20);
		} else {
			robot.arm.raiseWithPower(0);
		}

		if(this.gamepad2.y) {
			robot.arm.grabHand();
		} else if (this.gamepad2.a) {
			robot.arm.releaseHand();
		}

		logger.numberLog("Drive Speed", speed);
		logger.numberLog("Arm Speed", armSpeed);
		logger.update();

	}

	/**
	 * Runs once after STOP is pushed
	 */
	@Override
	public void stop() {
		robot.stopAllMotors();
		logger.completeLog("Status", "Stopped");
		logger.update();
	}

	private void singleJoystickDrive() {
		float leftX = this.gamepad1.left_stick_x;
		float leftY = this.gamepad1.left_stick_y;
		float rightX = this.gamepad1.right_stick_x;

		float[] motorPowers = new float[4];
		motorPowers[0] = (leftY-leftX-rightX);// -+
		motorPowers[1] = (leftY+leftX+rightX);// +-
		motorPowers[2] = (leftY+leftX-rightX);// ++
		motorPowers[3] = (leftY-leftX+rightX);// --

		float max = getLargestAbsVal(motorPowers);
		if(max < 1) { max = 1; }

		for(int i = 0; i < motorPowers.length; i++) {
			motorPowers[i] *= (speed / max);

			float abs = Math.abs(motorPowers[i]);
			if(abs < 0.05) { motorPowers[i] = 0.0f; }
			if(abs > 1.0) { motorPowers[i] /= abs; }
			logger.numberLog("Motor" + i, motorPowers[i]);
		}

		robot.drivetrain.setIndividualPowers(motorPowers);
	}

	private float getLargestAbsVal(float[] values) {
		float max = 0;
		for(float val : values) {
			if(Math.abs(val) > max) { max = Math.abs(val); }
		}
		return max;
	}

}