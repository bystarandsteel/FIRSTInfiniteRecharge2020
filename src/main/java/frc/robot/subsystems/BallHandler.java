package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.Robot;

public class BallHandler {

    private TalonSRX spinner, hood, shelf;

    private boolean hoodIn = false;

    private DigitalInput hoodSwitch = new DigitalInput(9);

    private int shelfCounter = 0;

    private boolean shelfMovingUp = false;
    private boolean shelfMovingDown = false;

    public BallHandler(int spinnerID, int hoodID, int shelfID) {
        spinner = new TalonSRX(spinnerID);
        hood = new TalonSRX(hoodID);
        shelf = new TalonSRX(shelfID);
    }

    public void initialize() {
        spinner.setNeutralMode(NeutralMode.Brake);
        hood.setNeutralMode(NeutralMode.Brake);
        shelf.setNeutralMode(NeutralMode.Brake);
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
        shelf.set(ControlMode.PercentOutput, 0);
    }

    public void dashboard() {
        SmartDashboard.putBoolean("Hood Switch", !hoodSwitch.get());
        SmartDashboard.putBoolean("Hood In", hoodIn);
    }

    public void cycleShelf() {
        if (!shelfMovingDown && !shelfMovingUp) {
            shelf.set(ControlMode.PercentOutput, 0);
        } else if (shelfMovingDown && shelfCounter < 30) {
            shelf.set(ControlMode.PercentOutput, 0.2);
            shelfCounter++;
        } else if (shelfMovingDown && shelfCounter == 30) {
            shelf.set(ControlMode.PercentOutput, 0);
            shelfCounter = -1;
        } else if (shelfMovingUp && shelfCounter < 30) {
            shelf.set(ControlMode.PercentOutput, -0.2);
            shelfCounter++;
        } else if (shelfMovingUp && shelfCounter == 30) {
            shelf.set(ControlMode.PercentOutput, 0);
            shelfCounter = -1;
        } else {
            shelf.set(ControlMode.PercentOutput, 0);
        }
    }

    public void run() {
        if (Robot.operator.getTriggerAxis(Robot.left) > 0.5) {
            spinner.set(ControlMode.PercentOutput, 0.8);
        } else if (Robot.operator.getBumper(Robot.left)) {
            spinner.set(ControlMode.PercentOutput, 0.6);
        } else if (Robot.operator.getTriggerAxis(Robot.right) > 0.5) {
            spinner.set(ControlMode.PercentOutput, -0.6);
        } else {
            spinner.set(ControlMode.PercentOutput, 0);
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

        /*if (Robot.operator.getAButtonReleased()) {
            if (!shelfMovingUp && !shelfMovingDown) {
                shelfMovingDown = true;
            } else if (shelfMovingDown && shelfCounter == -1) {
                shelfMovingDown = false;
                shelfMovingUp = true;
            } else if (shelfMovingUp && shelfCounter == -1) {
                shelfMovingUp = false;
                shelfMovingDown = true;
            }
        }*/

        if (Robot.operator.getAButton()) {
            shelf.set(ControlMode.PercentOutput, 0.25);
        } else if (Robot.operator.getBackButton()) {
            shelf.set(ControlMode.PercentOutput, -0.25);
        } else {
            shelf.set(ControlMode.PercentOutput, 0);
        }

        //cycleShelf();
    }
}