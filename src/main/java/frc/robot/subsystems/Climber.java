package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.Robot;

public class Climber {

    TalonSRX climber, roller;

    Servo rachet;

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
    }

    public void run() {
        if (Robot.operator.getXButtonReleased()) {
            rachetEngaged = !rachetEngaged;
        }

        if (rachetEngaged) {
            rachet.setPosition(0.5);
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
        } else {
            roller.set(ControlMode.PercentOutput, 0);
        }
    }

    public void dashboard() {
        SmartDashboard.putBoolean("Rachet Engaged", rachetEngaged);
    }
}