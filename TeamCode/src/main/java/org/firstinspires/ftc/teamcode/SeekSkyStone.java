/* Copyright (c) 2019 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.List;

import static android.icu.util.MeasureUnit.DEGREE;

/**
 * This 2019-2020 OpMode illustrates the basics of using the TensorFlow Object Detection API to
 * determine the position of the Skystone game elements.
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list.
 *
 * IMPORTANT: In order to use this OpMode, you need to obtain your own Vuforia license key as
 * is explained below.
 */







@Autonomous(name = "Tensorflow Skystone Detection Autonomous")
//@Disabled
public class SeekSkyStone extends LinearOpMode {
    private static final String TFOD_MODEL_ASSET = "Skystone.tflite";
    private static final String LABEL_FIRST_ELEMENT = "Stone";
    private static final String LABEL_SECOND_ELEMENT = "Skystone";
    
    private DcMotor rightFront;
    private DcMotor leftFront;
    private DcMotor rightRear;
    private DcMotor leftRear;
    private Servo pinger;

    // Distance from the center of the screen that the skystone can be to pick it up
    private final double skystoneAngleThreshold = 5;

    // How much of the screen the skystone needs to take up for the robot to deploy the pinger
    private final double objectHeightRatioForPinger = 0.8;


    /*
     * IMPORTANT: You need to obtain your own license key to use Vuforia. The string below with which
     * 'parameters.vuforiaLicenseKey' is initialized is for illustration only, and will not function.
     * A Vuforia 'Development' license key, can be obtained free of charge from the Vuforia developer
     * web site at https://developer.vuforia.com/license-manager.
     *
     * Vuforia license keys are always 380 characters long, and look as if they contain mostly
     * random data. As an example, here is a example of a fragment of a valid key:
     *      ... yIgIzTqZ4mWjk9wd3cZO9T1axEqzuhxoGlfOOI2dRzKS4T0hQ8kT ...
     * Once you've obtained a license key, copy the string from the Vuforia web site
     * and paste it in to your code on the next line, between the double quotes.
     */
    private static final String VUFORIA_KEY =
            "AQjY1NP/////AAABmUvaVtQ0nUQ9tejvctez83szc6mfruVEZTBCKtHg2fP0Mj/JZi9/l7fdKbXD9311fPDo7mIzkBaV6RcWT5LY5ksEfoUJXc/ewDYGpkB08zWSHn0C6cP8A2Dxak5l+WsHht7b12+aitu5fDbmIZ8zwtwJ6Lxu3OynVEt95+MfVjfQF2qpSfS0FtgBQMkkBBlTxZPaCkX1/4HJqcZokwgrUZMH5UBvNtSxveBKyHEMznVJiHg3gw6drdIOgfw/+mgdS3Il7MXwMHd13Fm7Un7wyrfcMxOXSqfOOaAymMOCLRQNDUUBJFZF2/QPWZnHHZzEE/nZo7uARlDXDM8aL+JB+chJa9ipx5hhBrvBg7z839Wz";

    /**
     * {@link #vuforia} is the variable we will use to store our instance of the Vuforia
     * localization engine.
     */
    private VuforiaLocalizer vuforia;

    /**
     * {@link #tfod} is the variable we will use to store our instance of the TensorFlow Object
     * Detection engine.
     */
    private TFObjectDetector tfod;

    @Override
    public void runOpMode() {
        // The TFObjectDetector uses the camera frames from the VuforiaLocalizer, so we create that
        // first.
        initVuforia();

        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            initTfod();
        } else {
            telemetry.addData("Sorry!", "This device is not compatible with TFOD");
        }

        /**
         * Activate TensorFlow Object Detection before we wait for the start command.
         * Do it here so that the Camera Stream window will have the TensorFlow annotations visible.
         **/
        if (tfod != null) {
            tfod.activate();
        }

        /** Wait for the game to begin */
        telemetry.addData(">", "Press Play to start op mode");
        telemetry.update();
        waitForStart();



        if (opModeIsActive()) {

            rightFront = hardwareMap.get(DcMotor.class, "rightFront");
            leftFront = hardwareMap.get(DcMotor.class, "leftFront");
            rightRear = hardwareMap.get(DcMotor.class, "rightRear");
            leftRear = hardwareMap.get(DcMotor.class, "leftRear");
            pinger = hardwareMap.get (Servo.class, "pinger");

            int stonesCaptured = 0;

            waitForStart();

            while (opModeIsActive()) {
                // Strafe right until Skystone found within threshold
                strafe(-0.5);
                while (stonesCaptured < 2) {
                    if (tfod != null) {
                        // getUpdatedRecognitions() will return null if no new information is available since
                        // the last time that call was made.
                        List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                        if (updatedRecognitions != null) {
                            telemetry.addData("# Object Detected", updatedRecognitions.size());
                            // step through the list of recognitions and display boundary info.
                            int i = 0;
                            for (Recognition recognition : updatedRecognitions) {
                                telemetry.addData(String.format("label (%d)", i), recognition.getLabel());
                                telemetry.addData(String.format("  left,top (%d)", i), "%.03f , %.03f",
                                        recognition.getLeft(), recognition.getTop());
                                telemetry.addData(String.format("  right,bottom (%d)", i), "%.03f , %.03f",
                                        recognition.getRight(), recognition.getBottom());

                                double objectAngle = recognition.estimateAngleToObject(AngleUnit.DEGREES);
                                telemetry.addData(String.format("  estimated angle (%d)", i), "%.03f",
                                        objectAngle);

                                // If skystone angle is within threshold, brake and prepare to make fine adjustments
                                if (Math.abs(objectAngle) < skystoneAngleThreshold) {
                                    brake();
                                    
                                    double objectHeightRatio;
                                    double speedMultiplier;
                                    
                                    do {
                                        
                                        // How much of the screen the skystone takes up vertically out of 1.
                                        objectHeightRatio = recognition.getHeight()/recognition.getImageHeight();
                                        telemetry.addData(String.format("  object height ratio (%d)", i), "%.03f",
                                                objectHeightRatio);
                                        
                                        // Speed is based on how close the robot is to the skystone compared to how far it needs to go (slower if closer to increase accuracy)
                                        speedMultiplier = 0.25*(1-(objectHeightRatio/objectHeightRatioForPinger));
                                        
                                        // Turns the robot based on how far from the center and which side of the camera the skystone is.
                                        objectAngle = recognition.estimateAngleToObject(AngleUnit.DEGREES);
                                        steer(speedMultiplier*(objectAngle/45), speedMultiplier*(objectAngle/45));
                                        
                                        // Moves towards the skystone until the object takes up enough of the screen. This is when the robot is at the optimal distance to use the pinger.
                                    } while (objectHeightRatio <= objectHeightRatioForPinger);
                                    
                                    telemetry.addData("Done!", objectHeightRatio/objectHeightRatioForPinger);
                                    
                                    // Pinger extends outward to turn the skystone 90 degrees, which angles the skystone for The Succ.
                                    pingerOut();
                                    sleep(1000);
                                    pingerIn();
                                    steer(0.5, 0.5);
                                    sleep(2000);
                                    brake();

                                }


                                telemetry.update();
                            }
                        }
                    }
                }
            }
        }

        if (tfod != null) {
            tfod.shutdown();
        }
    }

    /**
     * Initialize the Vuforia localization engine.
     */
    private void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraName = hardwareMap.get(WebcamName.class, "Webcam 1");

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Loading trackables is not necessary for the TensorFlow Object Detection engine.
    }

    /**
     * Initialize the TensorFlow Object Detection engine.
     */
    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minimumConfidence = 0.8;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_FIRST_ELEMENT, LABEL_SECOND_ELEMENT);
    }



    // Stations the robot in current position
    public void brake() {
        leftFront.setPower(0);
        rightFront.setPower(0);
        leftRear.setPower(0);
        rightRear.setPower(0);
    }

    // Moves the left and right side motors
    public void steer(double leftSpeed, double rightSpeed) {
        leftFront.setPower(leftSpeed);
        rightFront.setPower(rightSpeed);
        leftRear.setPower(leftSpeed);
        rightRear.setPower(rightSpeed);

    }

    // Moves the robot sideways without turning
    public void strafe(double speed){
        // Positive speed strafes right, negative speed strafes left.
        leftFront.setPower(speed);
        rightFront.setPower(-speed);
        leftRear.setPower(-speed);
        rightRear.setPower(speed);

    }

    // Extends pinger to its maximum length
    public void pingerOut() {
        pinger.setPosition(0.25);

    }

    // Retracts pinger into the robot
    public void pingerIn() {
        pinger.setPosition(0);

    }

}
