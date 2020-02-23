package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.Robot;

public class BallHandler {

    private TalonSRX spinner, hood, intakeRaiser;

    private boolean hoodIn = false;

    private boolean intakeUp = true;

    private DigitalInput hoodSwitch = new DigitalInput(9);

    public BallHandler(int spinnerID, int hoodID, int intakeRaiserID) {
        spinner = new TalonSRX(spinnerID);
        hood = new TalonSRX(hoodID);
        intakeRaiser = new TalonSRX(intakeRaiserID);
    }

    public void initialize() {
        reset();

        spinner.setNeutralMode(NeutralMode.Brake);
        hood.setNeutralMode(NeutralMode.Brake);
        intakeRaiser.setNeutralMode(NeutralMode.Coast);
    }

    public void reset() {
        intakeRaiser.setSelectedSensorPosition(0);
    }

    public double intakeRaiserEncoder() {
        return intakeRaiser.getSelectedSensorPosition();
    }

    public void releaseHood() {
        hood.set(ControlMode.PercentOutput, 0.25);
    }

    public void spitOut() {
        spinner.set(ControlMode.PercentOutput, 0.75);
    }

    public void hoodIn() {
        hoodIn = true;

        if (hoodSwitch.get()) {
            hood.set(ControlMode.PercentOutput, -0.25);
        } else {
            hood.set(ControlMode.PercentOutput, 0);
        }

        if (hoodIn && !hoodSwitch.get()) {
            hood.setNeutralMode(NeutralMode.Brake);
        }
    }

    public void stop() {
        spinner.set(ControlMode.PercentOutput, 0);
        hood.set(ControlMode.PercentOutput, 0);
        intakeRaiser.set(ControlMode.PercentOutput, 0);
    }

    public void dashboard() {
        SmartDashboard.putBoolean("Hood Switch", !hoodSwitch.get());
        SmartDashboard.putBoolean("Hood In", hoodIn);
    }

    public void run() {
        if (Robot.operator.getTriggerAxis(Robot.left) > 0.5) {
            spinner.set(ControlMode.PercentOutput, 0.8);
        } else if (Robot.operator.getBumper(Robot.left)) {
            spinner.set(ControlMode.PercentOutput, 0.3);
        } else if (Robot.operator.getTriggerAxis(Robot.right) > 0.5) {
            spinner.set(ControlMode.PercentOutput, -0.8);
        } else {
            spinner.set(ControlMode.PercentOutput, 0);
        }

        if (Robot.operator.getAButtonReleased()) {
            intakeUp = !intakeUp;
        }

        if (intakeUp && intakeRaiserEncoder() > 200) {
            intakeRaiser.set(ControlMode.PercentOutput, 0.25);
        } else if (!intakeUp && intakeRaiserEncoder() < 1000) {
            intakeRaiser.set(ControlMode.PercentOutput, -0.25);
        } else {
            intakeRaiser.set(ControlMode.PercentOutput, 0);
        }

        if (Robot.operator.getBButtonReleased()) {
            hoodIn = !hoodIn;
        }

        if (hoodIn && hoodSwitch.get()) {
            hood.set(ControlMode.PercentOutput, -0.25);
        } else if (Robot.operator.getYButton()) {
            hood.set(ControlMode.PercentOutput, 0.25);
        } else {
            hood.set(ControlMode.PercentOutput, 0);
        }

        if (hoodIn && !hoodSwitch.get()) {
            hood.setNeutralMode(NeutralMode.Brake);
        } else if (!hoodIn) {
            hood.setNeutralMode(NeutralMode.Coast);
        }
    }
}