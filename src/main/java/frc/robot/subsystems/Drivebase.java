package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.Robot;

public class Drivebase {

    CANSparkMax leftOne, leftTwo, rightOne, rightTwo;

    public Drivebase(int leftOneID, int leftTwoID, int rightOneID, int rightTwoID) {
        leftOne = new CANSparkMax(leftOneID, CANSparkMaxLowLevel.MotorType.kBrushless);
        leftTwo = new CANSparkMax(leftTwoID, CANSparkMaxLowLevel.MotorType.kBrushless);
        rightOne = new CANSparkMax(rightOneID, CANSparkMaxLowLevel.MotorType.kBrushless);
        rightTwo = new CANSparkMax(rightTwoID, CANSparkMaxLowLevel.MotorType.kBrushless);
    }

    public void initialize() {
        leftOne.setIdleMode(CANSparkMax.IdleMode.kBrake);
        leftTwo.setIdleMode(CANSparkMax.IdleMode.kBrake);
        rightOne.setIdleMode(CANSparkMax.IdleMode.kBrake);
        rightTwo.setIdleMode(CANSparkMax.IdleMode.kBrake);

        leftOne.setOpenLoopRampRate(1);
        leftTwo.setOpenLoopRampRate(1);
        rightOne.setOpenLoopRampRate(1);
        rightTwo.setOpenLoopRampRate(1);
    }

    public void reset() {
        leftOne.getEncoder().setPosition(0);
        leftTwo.getEncoder().setPosition(0);
        rightOne.getEncoder().setPosition(0);
        rightTwo.getEncoder().setPosition(0);
    }

    public double leftPosition() {
        return leftOne.getEncoder().getPosition();
    }

    public double rightPosition() {
        return rightOne.getEncoder().getPosition();
    }

    public double leftVelocity() {
        return leftOne.getEncoder().getVelocity();
    }

    public double rightVelocity() {
        return rightOne.getEncoder().getVelocity();
    }

    public void arcadeDrive() {
        double throttle = 0;
        double turn = 0;

        if (Math.abs(Robot.driver.getX(Robot.right)) > 0.05) {
            turn = Robot.driver.getX(Robot.right);
        }

        if (Math.abs(Robot.driver.getY(Robot.left)) > 0.05) {
            throttle = Robot.driver.getY(Robot.left);
        }

        leftOne.set(turn - throttle);
        leftTwo.set(turn - throttle);
        rightOne.set(turn + throttle);
        rightTwo.set(turn + throttle);
    }

    public void dashboard() {
        SmartDashboard.putNumber("Left Position", leftPosition());
        SmartDashboard.putNumber("Right Position", rightPosition());

        SmartDashboard.putNumber("Left Velocity", leftVelocity());
        SmartDashboard.putNumber("Right Velocity", rightVelocity());
    }
}