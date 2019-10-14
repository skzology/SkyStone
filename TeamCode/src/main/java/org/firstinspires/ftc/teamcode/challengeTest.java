package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
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

@Autonomous
public class challengeTest extends LinearOpMode {

    private DcMotor rightFront;
    private DcMotor leftFront;
    private DcMotor rightRear;
    private DcMotor leftRear;


    @Override
    public void runOpMode() {


        rightFront = hardwareMap.get(DcMotor.class, "rightFront");
        leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        rightRear = hardwareMap.get(DcMotor.class, "rightRear");
        leftRear = hardwareMap.get(DcMotor.class, "leftRear");

        waitForStart();

        while(opModeIsActive()) {


            telemetry.addData("Status", "Initialized");
            telemetry.addData("Front Right Motor", rightFront.getPower());
            telemetry.addData("Front Left Motor", leftFront.getPower());
            telemetry.addData("Rear Right Motor", rightRear.getPower());
            telemetry.addData("Rear Left Motor", leftRear.getPower());

            telemetry.update();


            /*this.forward(0.8, 3000);
            this.turnRight(0.5, 6000);
            //this.brake();
            this.forward(0.8, 4000);
            this.turnLeft(0.5, 500);
            //this.brake();
            this.forward(0.8, 1500);
            this.turnRight(0.5, 2000);*/

            this.newFunction();
            this.brake();

        }


    }
    private void originalFunction(){
        this.forward(0.8, 3000);
        this.turnRight(0.5, 300);
        //this.brake();
        this.forward(0.8, 4000);
        this.turnLeft(0.5, 500);
        //this.brake();
        this.forward(0.8, 1500);
        this.turnRight(0.5, 500);
    }

    private void newFunction(){
        this.turnLeft(0.5, 300);
        //this.brake();
        this.forward(0.8, 3000);
        this.turnRight(0.5, 600);
        //this.brake();
        this.forward(0.8, 4000);

        this.forward(0.8, 1500);
        this.turnRight(0.5, 200);
    }
    // No motors reversed
    private void forward(double speed, int time) {
        rightFront.setPower(speed);
        leftFront.setPower(speed);
        rightRear.setPower(speed);
        leftRear.setPower(speed);
        sleep(time);

    }
    // All motors reversed
    private void back(double speed, int time) {
        rightFront.setPower(-speed);
        leftFront.setPower(-speed);
        rightRear.setPower(-speed);
        leftRear.setPower(-speed);
        sleep(time);
        this.brake();

    }
    // Front right and rear left motors reversed (strafes right)
    private void right(double speed, int time) {
        rightFront.setPower(-speed);
        leftFront.setPower(speed);
        rightRear.setPower(speed);
        leftRear.setPower(-speed);
        sleep(time);
        this.brake();

    }
    // Front left and rear right motors reversed (strafes left)
    private void left(double speed, int time) {
        rightFront.setPower(speed);
        leftFront.setPower(-speed);
        rightRear.setPower(-speed);
        leftRear.setPower(speed);
        sleep(time);
        this.brake();

    }

    // Right motors reversed
    private void turnRight(double speed, int time) {
        rightFront.setPower(-speed);
        leftFront.setPower(speed);
        rightRear.setPower(-speed);
        leftRear.setPower(speed);
        sleep(time);
        this.brake();

    }

    // Left motors reverse
    private void turnLeft(double speed, int time) {
        rightFront.setPower(speed);
        leftFront.setPower(-speed);
        rightRear.setPower(speed);
        leftRear.setPower(-speed);
        sleep(time);
        this.brake();

    }

    private void brake() {
        rightFront.setPower(0);
        leftFront.setPower(0);
        rightRear.setPower(0);
        leftRear.setPower(0);

    }


}
