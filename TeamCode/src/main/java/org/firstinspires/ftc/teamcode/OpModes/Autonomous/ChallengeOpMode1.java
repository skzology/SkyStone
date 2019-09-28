package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
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

@Autonomous
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

        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {

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


    public void setMotors(double frontLeftSpeed, double rearLeftSpeed,double frontRightSpeed, double rearRightSpeed, int time) {
        frontLeftMotor.setPower(frontLeftSpeed);
        rearLeftMotor.setPower(rearLeftSpeed);
        frontRightMotor.setPower(frontRightSpeed);
        rearRightMotor.setPower(rearRightSpeed);
        sleep(time);
    }

    // No motors reversed
    public void forward(double speed, int time) {
        setMotors(speed, speed, speed, speed, time);

    }
    // All motors reversed
    public void backward(double speed, int time) {
        setMotors(-speed, -speed, -speed, -speed, time);

    }
    // Front right and rear left motors reversed (strafes right)
    public void strafeRight(double speed, int time) {
        setMotors(speed, -speed, -speed, speed, time);

    }
    // Front left and rear right motors reversed (strafes left)
    public void strafeLeft(double speed, int time) {
        setMotors(-speed, speed, speed, -speed, time);
    }

    // Right motors reversed
    public void turnRight(double speed, int time) {
        setMotors(speed, speed, -speed, -speed, time);
    }

    // Left motors reverse
    public void turnLeft(double speed, int time) {
        setMotors(-speed, -speed, speed, speed, time);
    }

    public void brake() {
        setMotors(0, 0, 0, 0, 0);

    }

}