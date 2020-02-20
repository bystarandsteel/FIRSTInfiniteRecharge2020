package frc.robot.routines;

import frc.robot.procedures.DriveDistance;
import frc.robot.procedures.Procedure;

public class Back implements Routine {

    private int status = 0;

    private boolean complete = false;

    private double[] params = {0};

    private Procedure[] procedures = {new DriveDistance()};

    @Override
    public void run() {
        if (status == 1) {
            return;
        }

        status = 1;

        for (int i = 0; i < procedures.length; i++) {
            procedures[i].run(params[i]);

            while (procedures[i].status() == 1) {
                System.out.println("Running procedure " + i + ".");
            }

            if (procedures[i].status() == 2) {
                continue;
            } else {
                status = -1;
                return;
            }
        }

        status = 2;
        complete = true;
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
