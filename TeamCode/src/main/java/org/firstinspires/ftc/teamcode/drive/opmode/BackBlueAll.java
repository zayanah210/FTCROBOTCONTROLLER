package org.firstinspires.ftc.teamcode.drive.opmode;


import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.drive.opmode.BackBlueC;

import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

@Autonomous
public class BackBlueAll extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {


        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        BackBlueC BackBlueCenter = new BackBlueC();



        int choice = 3;


        Pose2d startPose = new Pose2d(0, 0, Math.toRadians(90));

        drive.setPoseEstimate(startPose);

        waitForStart();

        if (choice == 3) {
            //BackBlueC
            TrajectorySequence trajSeq = drive.trajectorySequenceBuilder(startPose)

                    .forward(47)
                    .back(14)
                    .turn(Math.toRadians(155))
                    .forward(165)
                    .build();

        } else if (choice == 2) {
            //BackBlueR
            TrajectorySequence trajSeq = drive.trajectorySequenceBuilder(startPose)
//                .splineTo(new Vector2d(100, 0), 0


                    .forward(38)
                    .turn(Math.toRadians(-155))
                    .forward(10)
                    .back(150)
                    .build();

        } else {
            //BackBlueL
            TrajectorySequence trajSeq = drive.trajectorySequenceBuilder(startPose)


                    .forward(40)
                    .turn(Math.toRadians(140))
                    .forward(10)
                    .back(15)
                    .turn(Math.toRadians(140))
                    .forward(30)
                    .turn(Math.toRadians(-140))
                    .forward(70)
                    .build();

        }
    }
}