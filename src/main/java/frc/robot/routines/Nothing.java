package frc.robot.routines;

public class Nothing implements Routine {

    int status = 0;

    boolean complete = false;

    @Override
    public void run() {
        if (status == 1) {
            return;
        }

        status = 1;
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
