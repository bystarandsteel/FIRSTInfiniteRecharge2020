package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.Robot;

public class Drivebase {

    CANSparkMax leftOne, leftTwo, rightOne, rightTwo;

    private static final double driveP = 0.08;
    private static final double driveI = 0.0;
    private static final double driveD = 0.0;

    private static final double turnP = 0.01;
    private static final double turnI = 0.0;
    private static final double turnD = 0.0;

    private PIDController driveBall = new PIDController(driveP, driveI, driveD);

    private PIDController turnBall = new PIDController(turnP, turnI, turnD);

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

        leftOne.setOpenLoopRampRate(0.9);
        leftTwo.setOpenLoopRampRate(0.9);
        rightOne.setOpenLoopRampRate(0.9);
        rightTwo.setOpenLoopRampRate(0.9);

        driveBall.setTolerance(0.1);
        turnBall.setTolerance(0.5);

        driveBall.setSetpoint(1);
        turnBall.setSetpoint(0);
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
            turn = Robot.driver.getX(Robot.right) * 0.8s;
        }

        if (Math.abs(Robot.driver.getY(Robot.left)) > 0.05) {
            throttle = Robot.driver.getY(Robot.left);
        }

        if (Robot.driver.getTriggerAxis(Robot.left) > 0.5) {
            turn *= 0.2;
            throttle *= 0.2;
        }

        leftOne.set(turn - throttle);
        leftTwo.set(turn - throttle);
        rightOne.set(turn + throttle);
        rightTwo.set(turn + throttle);
    }

    public void ballSeek() {
        double throttle = -driveBall.calculate(Robot.camera.getValues()[2]);
        double turn = -turnBall.calculate(Robot.camera.getValues()[1]);

        leftOne.set(turn - throttle);
        leftTwo.set(turn - throttle);
        rightOne.set(turn + throttle);
        rightTwo.set(turn + throttle);
    }

    public void dashboard() {
        SmartDashboard.putNumber("Left Position", leftPosition());
        SmartDashboard.putNumber("Right Position", rightPosition());

        SmartDashboard.putData("Drive Ball PID", driveBall);
        SmartDashboard.putData("Turn Ball PID", turnBall);
    }
}