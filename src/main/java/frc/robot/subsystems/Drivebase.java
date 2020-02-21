package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.Robot;

public class Drivebase {

    CANSparkMax leftOne, leftTwo, rightOne, rightTwo;

    private static final double driveP = 0.01;
    private static final double driveI = 0.0;
    private static final double driveD = 0.0;

    private static final double turnP = 0.01;
    private static final double turnI = 0.0;
    private static final double turnD = 0.0;

    private static PIDController driveController = new PIDController(driveP, driveI, driveD);

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

        leftOne.setOpenLoopRampRate(0.7);
        leftTwo.setOpenLoopRampRate(0.7);
        rightOne.setOpenLoopRampRate(0.7);
        rightTwo.setOpenLoopRampRate(0.7);

        driveController.setTolerance(0.05);
        driveController.setSetpoint(0);
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

    public void arcadeDrive() {
        double throttle = 0;
        double turn = 0;

        if (Math.abs(Robot.driver.getX(Robot.right)) > 0.05) {
            turn = Robot.driver.getX(Robot.right) * 0.45;
        }

        if (Math.abs(Robot.driver.getY(Robot.left)) > 0.05) {
            throttle = Robot.driver.getY(Robot.left);
        }

        if (Robot.driver.getTriggerAxis(Robot.left) > 0.5) {
            turn *= 0.2;
            throttle *= 0.2;
        }

        if (Robot.flipDrive) {
            leftOne.set(turn + throttle);
            leftTwo.set(turn + throttle);
            rightOne.set(turn - throttle);
            rightTwo.set(turn - throttle);
        } else {
            leftOne.set(turn - throttle);
            leftTwo.set(turn - throttle);
            rightOne.set(turn + throttle);
            rightTwo.set(turn + throttle);
        }
    }

    public void tankDrive() {
        if (Robot.flipDrive) {
            leftOne.set(Robot.driver.getY(Robot.left));
            leftTwo.set(Robot.driver.getY(Robot.left));
            rightOne.set(-Robot.driver.getY(Robot.right));
            rightTwo.set(-Robot.driver.getY(Robot.right));
        } else {
            leftOne.set(-Robot.driver.getY(Robot.left));
            leftTwo.set(-Robot.driver.getY(Robot.left));
            rightOne.set(Robot.driver.getY(Robot.right));
            rightTwo.set(Robot.driver.getY(Robot.right));
        }
    }

    public void setSetpoint(double setpoint) {
        driveController.setSetpoint(setpoint);
    }

    public boolean atSetpoint() {
        return driveController.atSetpoint();
    }

    public double getSetpoint() {
        return driveController.getSetpoint();
    }

    public void driveToTarget() {
        double speed = driveController.calculate(leftPosition());

        leftOne.set(speed);
        leftTwo.set(speed);
        rightOne.set(-speed);
        rightTwo.set(-speed);
    }

    public void stop() {
        leftOne.set(0);
        leftTwo.set(0);
        rightOne.set(0);
        rightTwo.set(0);
    }

    public void dashboard() {
        SmartDashboard.putNumber("Left Position", leftPosition());
        SmartDashboard.putNumber("Right Position", rightPosition());
        SmartDashboard.putData("PID", driveController);
    }
}