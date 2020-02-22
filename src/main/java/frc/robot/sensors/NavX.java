package frc.robot.sensors;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class NavX {

    private AHRS gyro = new AHRS();

    public NavX() {

    }

    public double getAngle() {
        return gyro.getAngle();
    }

    public void dashboard() {
        SmartDashboard.putNumber("Angle", getAngle());
    }
}