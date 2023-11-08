package org.firstinspires.ftc.teamcode.drive.opmode;

import android.nfc.Tag;
import android.util.Log;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;



public class SleeveDetectionBetter extends OpenCvPipeline {

    public enum ParkPosition {
        LEFT,
        CENTER,
        RIGHT
    }

    private Mat coneCrop = new Mat(), red = new Mat(), green = new Mat(), blue = new Mat(), output = new Mat();
    double redavgfin;
    double greenavgfin;
    double blueavgfin;
    double winner;

    double MidWin;
    double LeftWin;
    double RightWin;

    private final Scalar
            yellow      = new Scalar(255, 255, 0),
            cyan        = new Scalar(0, 255, 255),
            magenta     = new Scalar(255, 0, 255);

    private volatile ParkPosition position = ParkPosition.CENTER;

//    @Override

    public Mat processFrameold(Mat input) {

        Rect ConeRectangle = new Rect(260, 180, 40, 60);
        coneCrop = input.submat(ConeRectangle);

        Core.extractChannel(coneCrop, red, 0);
        Core.extractChannel(coneCrop, green, 1);
        Core.extractChannel(coneCrop, blue, 2);

        Scalar redavg   = Core.mean(red);
        Scalar greenavg = Core.mean(green);
        Scalar blueavg  = Core.mean(blue);

        redavgfin = redavg.val[0];
        greenavgfin = greenavg.val[0];
        blueavgfin = blueavg.val[0];

        winner = Math.min(redavgfin, Math.min(greenavgfin, blueavgfin));

        if (redavgfin == winner) {
            position = ParkPosition.CENTER;
            input.copyTo(output);
            Imgproc.rectangle(output, ConeRectangle, cyan, 2);
        } else if (greenavgfin == winner) {
            position = ParkPosition.RIGHT;
            input.copyTo(output);
            Imgproc.rectangle(output, ConeRectangle, magenta, 2);
        } else if (blueavgfin == winner) {
            position = ParkPosition.LEFT;
            input.copyTo(output);
            Imgproc.rectangle(output, ConeRectangle, yellow, 2);
        }


        return(output);
    }
    public Mat processFrame(Mat input) {

        Rect LeftScreen = new Rect(0, 0, 213, 640);
        Rect MiddleScreen = new Rect(214, 0, 213, 640);
        Rect RightScreen = new Rect(428, 0, 213, 640);



        //
        Mat TeampropMid = input.submat(MiddleScreen);

        Core.extractChannel(TeampropMid, red, 0);

        Scalar redAvgMid   = Core.mean(red);
        //


        Mat TeampropLeft = input.submat(LeftScreen);

        Core.extractChannel(TeampropLeft, red, 0);

        Scalar redAvgLeft   = Core.mean(red);

        //


        Mat TeampropRight = input.submat(RightScreen);

        Core.extractChannel(TeampropRight, red, 0);

        Scalar redAvgRight   = Core.mean(red);

        //

        MidWin = redAvgMid.val[0];
        LeftWin = redAvgLeft.val[0];
        RightWin = redAvgRight.val[0];

        winner = Math.min(RightWin, Math.min(LeftWin, MidWin));


        Log.d("this is red", String.valueOf(redavgfin));
        System.out.println("HELLO HELLO HELLO");


        if (MidWin == winner) {
            position = ParkPosition.CENTER;
            input.copyTo(output);
//            Imgproc.rectangle(output, ConeRectangle, cyan, 2);
        } else if (RightWin == winner) {
            position = ParkPosition.RIGHT;
            input.copyTo(output);
//            Imgproc.rectangle(output, ConeRectangle, magenta, 2);
        } else if (LeftWin == winner) {
            position = ParkPosition.LEFT;
            input.copyTo(output);
//            Imgproc.rectangle(output, ConeRectangle, yellow, 2);
        }



        return(output);
    }

    public ParkPosition getPosition() {
        return position;
    }

}
