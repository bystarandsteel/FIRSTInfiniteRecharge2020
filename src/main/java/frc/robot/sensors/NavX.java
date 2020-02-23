package frc.robot.sensors;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class NavX {

    private AHRS gyro;

    public NavX() {
        gyro = new AHRS();
    }

    public void reset() {
        gyro.reset();
    }

    public double getAngle() {
        return gyro.getAngle();
    }

    public void dashboard() {
        SmartDashboard.putNumber("Angle", getAngle());
    }
}