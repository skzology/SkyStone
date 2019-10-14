package org.firstinspires.ftc.teamcode.BasicModules;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;


@TeleOp(name = "BasicTeleopDrive")
public class BasicTeleopDrive extends LinearOpMode {

    private DcMotor leftFront;
    private DcMotor rightFront;
    private DcMotor leftRear;
    private DcMotor rightRear;


    @Override
    public void runOpMode() {

        leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        leftRear = hardwareMap.get(DcMotor.class, "leftRear");
        rightRear = hardwareMap.get(DcMotor.class, "rightRear");
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");

        rightFront.setDirection(DcMotor.Direction.REVERSE);
        rightRear.setDirection(DcMotor.Direction.REVERSE);

        telemetry.addData("Status", "Initialized");
        telemetry.update();
        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive() ) {
            //Drive motor control

            double threshold = 0.157;

            if(Math.abs(gamepad1.left_stick_y) > threshold || Math.abs(gamepad1.left_stick_x) > threshold)
            {
                rightFront.setPower(((-gamepad1.left_stick_y) + (-gamepad1.left_stick_x)));
                leftRear.setPower(((-gamepad1.left_stick_y) + (-gamepad1.left_stick_x)));
                leftFront.setPower(((-gamepad1.left_stick_y) - (-gamepad1.left_stick_x)));
                rightRear.setPower(((-gamepad1.left_stick_y) - (-gamepad1.left_stick_x)));
            }

            else
            {
                leftFront.setPower(0);
                rightFront.setPower(0);
                leftRear.setPower(0);
                rightRear.setPower(0);
            }

            if(Math.abs(gamepad1.right_stick_x) > threshold)
            {
                rightFront.setPower((-gamepad1.right_stick_x));
                leftFront.setPower((gamepad1.right_stick_x));
                leftRear.setPower((gamepad1.right_stick_x));
                rightRear.setPower((-gamepad1.right_stick_x));
            }
            telemetry.addData("Status", "Running");
            telemetry.addData("front left power", leftFront.getPower());
            telemetry.addData("front right power", rightFront.getPower());
            telemetry.addData("back left power", leftRear.getPower());
            telemetry.addData("back right power", rightRear.getPower());
            telemetry.update();

        }


    }
}

