package frc.robot.sensors;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.Robot;

public class Limelight {

    public Limelight(int pipeline) {
        setPipeline(pipeline);
    }

    public void setPipeline(int pipeline) {
        if (pipeline < 0 || pipeline > 2) {
            return;
        } else {
            NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setNumber(pipeline);
        }
    }

    public void nextPipeline() {
        if (getPipeline() != 2) {
            setPipeline(getPipeline() + 1);
        } else {
            setPipeline(0);
        }
    }

    public int getPipeline() {
        return NetworkTableInstance.getDefault().getTable("limelight").getEntry("getpipe").getNumber(-1).intValue();
    }

    public double[] getValues() {
        double[] values = new double[3];

        values[0] = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0);
        values[1] = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
        values[2] = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ta").getDouble(0);

        return values;
    }

    public void run() {
        if (Robot.operator.getStartButtonReleased()) {
            nextPipeline();
        }
    }

    public void dashboard() {
        SmartDashboard.putNumber("tv", getValues()[0]);
        SmartDashboard.putNumber("tx", getValues()[1]);
        SmartDashboard.putNumber("ta", getValues()[2]);
        SmartDashboard.putNumber("Pipeline", getPipeline());
    }
}