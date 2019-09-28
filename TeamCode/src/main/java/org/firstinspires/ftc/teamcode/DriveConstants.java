package org.firstinspires.ftc.teamcode;

public class DriveConstants {

    /*
    *
    * USE INCHES
    *
    */

    private static final double WHEEL_RADIUS = 2;
    private static final double WHEEL_CIRCUMFERENCE = WHEEL_RADIUS*2*Math.PI;

    private static final double WHEEL_BASE = 10; // length between front & back wheels
    private static final double TRACK_WIDTH = 16; // width between left and right wheels
    private static final double CIRCUMSCRIBED_DIAMETER = Math.sqrt(Math.pow(WHEEL_BASE, 2) + (Math.pow(TRACK_WIDTH, 2))); // Distance between centers of 2 diagonal wheels
    private static final double CIRCUMSCRIBED_CIRCUMFERENCE = CIRCUMSCRIBED_DIAMETER*Math.PI;

    public static double inchesToTicks(int inches) {
        return (inches / WHEEL_CIRCUMFERENCE) * 360;

    }




}
