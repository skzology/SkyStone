package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;


/**
 *
 * Created by Joshua Herer on 8/28/19
 *
 * Task:
 * - Go straight for 3 seconds
 * - turn right 3 times
 * - go straight 4 seconds
 * - turn left
 * - go straight 1.5 seconds
 * - turn 360
 *
 * Drive: Mechanum Drive
 *
*/

@TeleOp
public class ChallengeOpMode1 extends LinearOpMode {

    private DcMotor frontRightMotor;
    private DcMotor frontLeftMotor;
    private DcMotor rearRightMotor;
    private DcMotor rearLeftMotor;


    @Override
    public void runOpMode() {

        frontRightMotor = hardwareMap.get(DcMotor.class, "frontRightMotor");
        frontLeftMotor = hardwareMap.get(DcMotor.class, "frontLeftMotor");
        rearRightMotor = hardwareMap.get(DcMotor.class, "rearRightMotor");
        rearLeftMotor = hardwareMap.get(DcMotor.class, "rearLeftMotor");

        telemetry.addData("Status", "Initialized");
        telemetry.addData("Front Right Motor", frontRightMotor.getPower());
        telemetry.addData("Front Left Motor", frontLeftMotor.getPower());
        telemetry.addData("Rear Right Motor", rearRightMotor.getPower());
        telemetry.addData("Rear Left Motor", rearLeftMotor.getPower());

        telemetry.update();

        waitForStart();

        while(opModeIsActive()) {

            this.forward(0.8, 3000);
            this.turnRight(0.5, 6000);
            this.brake();
            this.forward(0.8, 4000);
            this.turnLeft(0.5, 500);
            this.brake();
            this.forward(0.8, 1500);
            this.turnRight(0.5, 2000)
            this.brake();

        }


    }

    // No motors reversed
    private void forward(double speed, int time) {
        frontRightMotor.setPower(speed);
        frontLeftMotor.setPower(speed);
        rearRightMotor.setPower(speed);
        rearLeftMotor.setPower(speed);
        sleep(time);

    }
    // All motors reversed
    private void back(double speed, int time) {
        frontRightMotor.setPower(-speed);
        frontLeftMotor.setPower(-speed);
        rearRightMotor.setPower(-speed);
        rearLeftMotor.setPower(-speed);
        sleep(time);

    }
    // Front right and rear left motors reversed (strafes right)
    private void right(double speed, int time) {
        frontRightMotor.setPower(-speed);
        frontLeftMotor.setPower(speed);
        rearRightMotor.setPower(speed);,
        rearLeftMotor.setPower(-speed);
        sleep(time);

    }
    // Front left and rear right motors reversed (strafes left)
    private void left(double speed, int time) {
        frontRightMotor.setPower(speed);
        frontLeftMotor.setPower(-speed);
        rearRightMotor.setPower(-speed);
        rearLeftMotor.setPower(speed);
        sleep(time);

    }

    // Right motors reversed
    private void turnRight(double speed, int time) {
        frontRightMotor.setPower(-speed);
        frontLeftMotor.setPower(speed);
        rearRightMotor.setPower(-speed);,
        rearLeftMotor.setPower(speed);
        sleep(time);

    }

    // Left motors reverse
    private void turnLeft(double speed, int time) {
        frontRightMotor.setPower(speed);
        frontLeftMotor.setPower(-speed);
        rearRightMotor.setPower(speed);
        rearLeftMotor.setPower(-speed);
        sleep(time);

    }

    private void brake() {
        frontRightMotor.setPower(0);
        frontLeftMotor.setPower(0);
        rearRightMotor.setPower(0);
        rearLeftMotor.setPower(0);

    }



}
