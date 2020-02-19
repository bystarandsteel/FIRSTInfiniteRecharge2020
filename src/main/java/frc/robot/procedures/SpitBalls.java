package frc.robot.procedures;

import frc.robot.Robot;

public class SpitBalls implements Procedure {

    private int status = 0;

    private boolean complete = false;

    @Override
    public void run(double value) {
        if (status != 0) {
            return;
        }

        status = 1;

        if (!Robot.endAuto) {
            for (int i = 0; i < 100; i++) {
                Robot.handler.releaseHood();
                if (Robot.endAuto) {
                    status = -1;
                    Robot.handler.stop();
                    return;
                }
            }
            Robot.handler.stop();
        }

        if (!Robot.endAuto) {
            for (int i = 0; i < 5000; i++) {
                Robot.handler.spitOut();
                if (Robot.endAuto) {
                    status = -1;
                    Robot.handler.stop();
                    return;
                }
            }
            Robot.handler.stop();
        }

        if (Robot.endAuto) {
            status = -1;
            return;
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
