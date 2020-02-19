package frc.robot.procedures;

public interface Procedure {

    void run(double value);
    int status();
    boolean complete();
}