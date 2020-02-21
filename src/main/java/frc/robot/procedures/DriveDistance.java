package frc.robot.procedures;

import frc.robot.Robot;

public class DriveDistance implements Procedure {

    private int status = 0;

    private boolean complete = false;

    public void run(double target) {
        if (status != 0) {
            return;
        }

        Robot.base.setSetpoint(target);

        status = 1;

        while (!Robot.endAuto && !Robot.base.atSetpoint()) {
            Robot.base.driveToTarget();
        }

        Robot.base.stop();

        if (!Robot.base.atSetpoint()) {
            status = -1;
        } else {
            status = 2;
            complete = true;
        }
    }

    @Override
    public int status() {
        return status;
    }

    @Override
    public boolean complete() {
        return complete;
    }
}
