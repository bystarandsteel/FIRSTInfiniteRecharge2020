package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.Robot;

public class Climber {

    private TalonSRX climber, roller;

    private Servo rachet;

    private static final double levelerP = 0.1;
    private static final double levelerI = 0;
    private static final double levelerD = 0;

    PIDController leveler = new PIDController(levelerP, levelerI, levelerD);

    boolean rachetEngaged = false;

    public Climber(int climberID, int rollerID, int servoID) {
        climber = new TalonSRX(climberID);
        roller = new TalonSRX(rollerID);

        rachet = new Servo(servoID);
    }

    public void initialize() {
        climber.setNeutralMode(NeutralMode.Brake);
        roller.setNeutralMode(NeutralMode.Brake);

        rachet.setPosition(0.1);

        leveler.setSetpoint(3.5);
        leveler.setTolerance(4);
    }

    public void run() {
        if (Robot.operator.getXButtonReleased()) {
            rachetEngaged = !rachetEngaged;
        }

        if (rachetEngaged) {
            rachet.setPosition(0.6);
        } else {
            rachet.setPosition(0.1);
        }

        if (Robot.operator.getPOV() == 0) {
            climber.set(ControlMode.PercentOutput, -0.4);
        } else if (Robot.operator.getPOV() == 180) {
            climber.set(ControlMode.PercentOutput, 0.4);
        } else {
            climber.set(ControlMode.PercentOutput, 0);
        }

        if (Robot.operator.getX(Robot.right) > 0.5) {
            roller.set(ControlMode.PercentOutput, -0.4);
        } else if (Robot.operator.getX(Robot.right) < -0.5) {
            roller.set(ControlMode.PercentOutput, 0.4);
        } else if (Robot.operator.getBumper(Robot.right)) {
            roller.set(ControlMode.PercentOutput, leveler.calculate(Robot.imu.getAngle()));
        } else {
            roller.set(ControlMode.PercentOutput, 0);
        }
    }

    public void dashboard() {
        SmartDashboard.putBoolean("Rachet Engaged", rachetEngaged);
    }
}