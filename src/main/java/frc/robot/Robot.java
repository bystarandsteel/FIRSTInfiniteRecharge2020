package frc.robot;

import com.revrobotics.Rev2mDistanceSensor;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.sensors.ColorSensor;
import frc.robot.sensors.DistanceSensor;
import frc.robot.sensors.Limelight;
import frc.robot.sensors.NavX;
import frc.robot.subsystems.BallHandler;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Drivebase;

public class Robot extends TimedRobot {

    public static XboxController driver = new XboxController(0);
    public static XboxController operator = new XboxController(1);
    public static final GenericHID.Hand left = GenericHID.Hand.kLeft;
    public static final GenericHID.Hand right = GenericHID.Hand.kRight;

    public static Limelight camera = new Limelight(0);
    public static DistanceSensor dist = new DistanceSensor(Rev2mDistanceSensor.Port.kOnboard);
    public static ColorSensor color = new ColorSensor(I2C.Port.kMXP);
    public static NavX gyro = new NavX();

    public static Drivebase base = new Drivebase(1, 2, 3, 4);
    public static BallHandler handler = new BallHandler(5,7, 12);
    public static Climber climber = new Climber(8, 11, 0);

    public static boolean flipDrive = false;

    public static SendableChooser<Integer> chooser = new SendableChooser<>();

    public static int autoCounter = 0;

    @Override
    public void robotInit() {
        dist.initialize();
        base.initialize();
        handler.initialize();
        climber.initialize();

        chooser.setDefaultOption("Disable Auto", 0);
        chooser.addOption("Score", 1);
        chooser.addOption("Back Up", 2);
        SmartDashboard.putData("Auto Mode", chooser);
    }

    @Override
    public void robotPeriodic() {
        dashboard();
    }

    @Override
    public void autonomousInit() {
        base.reset();
        handler.reset();
    }

    @Override
    public void autonomousPeriodic() {
        switch (chooser.getSelected()) {
            case 1:
                if (autoCounter == 0) {
                    base.setSetpoint(51);
                }
                if (!base.atSetpoint()) {
                    base.driveToTarget();
                } else {
                    base.stop();
                    if (autoCounter < 95) {
                        handler.spitOut();
                        autoCounter++;
                    } else {
                        handler.stop();
                        base.setSetpoint(20);
                        if (!base.atSetpoint()) {
                            base.driveToTarget();
                        } else {
                            base.stop();
                            handler.hoodIn();
                        }
                    }
                }
                break;
            case 2:
                base.setSetpoint(-10);
                if (!base.atSetpoint()) {
                    base.driveToTarget();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void teleopInit() {
        base.stop();
        handler.stop();

        base.reset();
        handler.reset();
    }

    @Override
    public void teleopPeriodic() {
        drive();
        handler.run();
        climber.run();
        camera.run();
    }

    public void dashboard() {
        camera.dashboard();
        dist.dashboard();
        color.dashboard();
        base.dashboard();
        handler.dashboard();
        climber.dashboard();
        gyro.dashboard();

        SmartDashboard.putNumber("Voltage", RobotController.getBatteryVoltage());
        SmartDashboard.putBoolean("Browned Out", RobotController.isBrownedOut());
        SmartDashboard.putNumber("Match Time", Timer.getMatchTime());
        SmartDashboard.putBoolean("FMS Status", DriverStation.getInstance().isFMSAttached());
    }
    
    public void drive() {
        if (driver.getBumper(right)) {
            flipDrive = true;
        } else {
            flipDrive = false;
        }

        if (driver.getTriggerAxis(right) > 0.5) {
            base.tankDrive();
        } else if (driver.getAButton()) {
            base.driveToTarget();
        } else {
            base.arcadeDrive();
        }
    }
}