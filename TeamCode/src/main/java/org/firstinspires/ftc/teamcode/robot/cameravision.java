package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.robot.FreightFrenzyDeterminationPipeline.CapstonePosition;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

public class cameravision {
	private OpenCvCamera webcam;
	private FreightFrenzyDeterminationPipeline pipeline;

	public cameravision(HardwareMap hardwareMap) {
		int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
		webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "TensorFlowCamera"), cameraMonitorViewId);
		pipeline = new FreightFrenzyDeterminationPipeline();
		webcam.setPipeline(pipeline);
	}

	public void start() {
		webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
		{
			@Override
			public void onOpened()
			{
				// 1280x720, 960x720
				// webcam.startStreaming(1280,720, OpenCvCameraRotation.SIDEWAYS_LEFT);
				webcam.startStreaming(960, 720, OpenCvCameraRotation.SIDEWAYS_RIGHT);
			}

			@Override
			public void onError(int errorCode) {

			}
		});
	}

	public void end() {
		webcam.stopStreaming();
	}

	public CapstonePosition getPosition() {
		return pipeline.getCapstonePosition();
	}

	public int getAnalysis() {
		return pipeline.getAnalysis();
	}

	public void save() {
		pipeline.saveLastFrame();
	}
}
