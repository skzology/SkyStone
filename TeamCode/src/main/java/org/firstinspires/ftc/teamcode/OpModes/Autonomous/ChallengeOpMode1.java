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

            steer(0.8, 0.8, 3000);
            steer(0.5, -0.5, 6000);
            brake();
            steer(0.8, 0.8, 4000);
            steer(-0.5, 0.5, 500);
            brake();
            steer(0.8, 0.8, 1500);
            steer(0.5, -0.5, 2000);
            brake();

        }

    }


    public void steer(double leftSpeed, double rightSpeed, int time) {
        frontLeftMotor.setPower(leftSpeed;
        frontRightMotor.setPower(rightSpeed);
        rearLeftMotor.setPower(leftSpeed);
        rearRightMotor.setPower(rightSpeed);
        sleep(time);
        brake();
    }

    public void strafe(double frontLeftSpeed, double frontRightSpeed, double rearLeftSpeed, double rearRightSpeed, int time){
        frontLeftMotor.setPower(frontLeftSpeed);
        frontRightMotor.setPower(frontLeftSpeed);
        rearLeftMotor.setPower(rearLeftSpeed);
        rearRightMotor.setPower(rearRightSpeed);
        sleep(time);
        brake();

    }

    public void brake() {
        frontLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        rearLeftMotor.setPower(0);
        rearRightMotor.setPower(0);

    }

}
