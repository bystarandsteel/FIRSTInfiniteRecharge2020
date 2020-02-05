package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.Robot;

public class BallHandler {

    TalonSRX spinner, hood, intakeRaiser;

    private boolean intakeUp = true;
    private boolean hoodIn = true;

    DigitalInput hoodSwitch = new DigitalInput(9);

    public BallHandler(int spinnerID, int hoodID, int intakeRaiserID) {
        spinner = new TalonSRX(spinnerID);
        hood = new TalonSRX(hoodID);
        intakeRaiser = new TalonSRX(intakeRaiserID);
    }

    public void initialize() {
        spinner.setNeutralMode(NeutralMode.Brake);
        hood.setNeutralMode(NeutralMode.Brake);
        intakeRaiser.setNeutralMode(NeutralMode.Coast);
    }

    public void reset() {
        intakeRaiser.setSelectedSensorPosition(0);
    }

    public double spinnerEncoder() {
        return spinner.getSelectedSensorPosition();
    }

    public double intakeRaiserEncoder() {
        return intakeRaiser.getSelectedSensorPosition();
    }

    public void dashboard() {
        SmartDashboard.putNumber("Spinner Encoder", spinnerEncoder());
        SmartDashboard.putNumber("intakeRaiser Encoder", intakeRaiserEncoder());
        SmartDashboard.putBoolean("Intake Up?", intakeUp);
        SmartDashboard.putBoolean("Hood Switch", hoodSwitch.get());
    }

    public void run() {
        if (Robot.operator.getTriggerAxis(Robot.left) > 0.5) {
            spinner.set(ControlMode.PercentOutput, 0.5);
        } else if (Robot.operator.getTriggerAxis(Robot.right) > 0.5) {
            spinner.set(ControlMode.PercentOutput, -0.5);
        } else {
            spinner.set(ControlMode.PercentOutput, 0);
        }

        /*if (Robot.operator.getAButtonReleased()) {
            intakeUp = !intakeUp;
        }

        if (intakeUp && intakeRaiserEncoder() < 1100) {
            intakeRaiser.set(ControlMode.PercentOutput, 1);
        } else if (!intakeUp && intakeRaiserEncoder() > 20){
            intakeRaiser.set(ControlMode.Position, -1);
        }*/

        if (Robot.operator.getBumper(Robot.left)) {
            intakeRaiser.set(ControlMode.PercentOutput, 1);
        } else if (Robot.operator.getBumper(Robot.right)) {
            intakeRaiser.set(ControlMode.PercentOutput, -1);
        } else {
            intakeRaiser.set(ControlMode.PercentOutput, 0);
        }

        if (Robot.operator.getBButtonReleased()) {
            hoodIn = !hoodIn;
        }

        if (hoodIn && hoodSwitch.get()) {
            hood.set(ControlMode.PercentOutput, -0.25);
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
