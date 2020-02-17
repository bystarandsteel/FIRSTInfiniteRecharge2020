package frc.robot.sensors;

import com.revrobotics.Rev2mDistanceSensor;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DistanceSensor {

    private Rev2mDistanceSensor sensor;

    public DistanceSensor(Rev2mDistanceSensor.Port port) {
        sensor = new Rev2mDistanceSensor(port);
    }

    public void initialize() {
        sensor.setEnabled(true);
        sensor.setAutomaticMode(true);
    }

    public double getRange(Rev2mDistanceSensor.Unit unit) {
        if (sensor.isRangeValid()) {
            return sensor.getRange(unit);
        } else {
            return -1;
        }
    }

    public double getTimestamp() {
        return sensor.getTimestamp();
    }

    public void dashboard() {
        SmartDashboard.putNumber("Distance (in)", getRange(Rev2mDistanceSensor.Unit.kInches));
        SmartDashboard.putNumber("Distance (mm)", getRange(Rev2mDistanceSensor.Unit.kMillimeters));
    }
}