package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import frc.robot.Robot;

public class Climber {

    TalonSRX climber, roller;

    public Climber(int climberID, int rollerID) {
        climber = new TalonSRX(climberID);
        roller = new TalonSRX(rollerID);
    }

    public void initialize() {
        climber.setNeutralMode(NeutralMode.Brake);
        roller.setNeutralMode(NeutralMode.Brake);
    }

    public void run() {
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
}