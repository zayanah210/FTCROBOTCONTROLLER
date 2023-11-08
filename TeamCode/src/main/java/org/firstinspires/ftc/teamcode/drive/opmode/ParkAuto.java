package org.firstinspires.ftc.teamcode.drive.opmode;
import static android.os.SystemClock.sleep;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.drive.opmode.SleeveDetectionBetter;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraRotation;

@Autonomous(name = "ParkAuto", group = "Cyber_Eagles")
public class ParkAuto extends LinearOpMode {
    @Override
    public void runOpMode()  {


        ElapsedTime runtime = new ElapsedTime();

        SampleMecanumDrive robot = new SampleMecanumDrive(hardwareMap);

        Object parkingposition;

        SleeveDetectionBetter sleeveDetectionBetter;

        Pose2d startPose = new Pose2d(0, 0, Math.toRadians(90));

        robot.setPoseEstimate(startPose);



//old posiiton

            telemetry.addData("Status", "Initializing");
            telemetry.update();
    //        robot.init(hardwareMap);
            //add servo init
            sleeveDetectionBetter = new SleeveDetectionBetter();
            robot.frontcamera.setPipeline(sleeveDetectionBetter);

            robot.frontcamera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
            {
                @Override
                public void onOpened()
                {
                    robot.frontcamera.startStreaming(640,480, OpenCvCameraRotation.UPSIDE_DOWN);
                }

                @Override
                public void onError(int errorCode) {}
            });

            waitForStart();
            sleep(1000);
            telemetry.addData("Status", "Initialized");
            telemetry.update();
            telemetry.addData("Park:", sleeveDetectionBetter.getPosition());
            parkingposition = sleeveDetectionBetter.getPosition();
            telemetry.update();

            TrajectorySequence trajSeq = robot.trajectorySequenceBuilder(startPose)

                    .forward(20)
                    .build();


                if (parkingposition == SleeveDetectionBetter.ParkPosition.LEFT) {

                    trajSeq = robot.trajectorySequenceBuilder(startPose)

                            .forward(20)
                            .turn(180)
                            .forward(8)
                            .back(45)
                            .build();

                } else if(parkingposition == SleeveDetectionBetter.ParkPosition.RIGHT) {
                    //yellow
                    trajSeq = robot.trajectorySequenceBuilder(startPose)

                            .forward(20)
                            .turn(-180)
                            .forward(8)
                            .back(13)
                            .turn(-20)
                            .forward(45)
                            .build();

                }else if(parkingposition == SleeveDetectionBetter.ParkPosition.CENTER) {
                    //red
                    trajSeq = robot.trajectorySequenceBuilder(startPose)

                            .forward(30)
                            .back(10)
                            .turn(-180)
                            .forward(45)
                            .build();
                }


    }


}
