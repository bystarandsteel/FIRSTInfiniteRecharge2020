package frc.robot;

import com.revrobotics.Rev2mDistanceSensor;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.*;

import frc.robot.sensors.*;
import frc.robot.subsystems.*;

public class Robot extends TimedRobot {

    public static XboxController driver = new XboxController(0);
    public static XboxController operator = new XboxController(1);
    public static final GenericHID.Hand left = GenericHID.Hand.kLeft;
    public static final GenericHID.Hand right = GenericHID.Hand.kRight;

    public static Limelight camera = new Limelight(2);
    public static DistanceSensor dist = new DistanceSensor(Rev2mDistanceSensor.Port.kOnboard);
    public static ColorSensor color = new ColorSensor(I2C.Port.kMXP);
    public static NavX gyro = new NavX();
    public static IMU imu = new IMU();

    public static Drivebase base = new Drivebase(1, 2, 3, 4);
    public static BallHandler handler = new BallHandler(5,7, 12);
    public static Climber climber = new Climber(8, 11, 0);

    //public static LedManipulator led = new LedManipulator(-1);

    public static boolean flipDrive = false;

    public static SendableChooser<Integer> chooser = new SendableChooser<>();

    public static int autoCounter = 0;

    @Override
    public void robotInit() {
        dist.initialize();
        base.initialize();
        handler.initialize();
        climber.initialize();
        gyro.reset();


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
        gyro.reset();

        base.setRampAuto();
    }

    @Override
    public void autonomousPeriodic() {
        switch (chooser.getSelected()) {
            case 1:
                if (autoCounter == 0) {
                    base.setSetpointDistance(5);
                }
                if (!base.atSetpointDistance() && autoCounter == 0) {
                    base.driveToDistance();
                } else {
                    if (autoCounter < 20 && autoCounter > -1) {
                        handler.lowerShelf();
                        autoCounter++;
                    } else if (autoCounter < 115 && autoCounter > -1) {
                        handler.spitOut();
                        autoCounter++;
                    } else {
                        handler.stop();
                        base.setSetpointDistance(30);
                        if (!base.atSetpointDistance()) {
                            base.driveToDistance();
                        } else {
                            base.setSetpointGyro(90);
                            if (!base.atSetpointGyro()) {
                                base.turnToGyro();
                                base.reset();
                                autoCounter = -1;
                            } else {
                                base.stop();
                                base.setSetpointEncoder(15);
                                if (autoCounter > -30) {
                                    base.driveToEncoder();
                                    autoCounter--;
                                } else {
                                    base.stop();
                                }
                            }
                        }
                    }
                }
                break;
            case 2:
                base.setSetpointEncoder(-5);
                if (!base.atSetpointEncoder()) {
                    base.driveToEncoder();
                } else {
                    base.stop();
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

        base.setRampTeleop();

        base.reset();
    }

    @Override
    public void teleopPeriodic() {
        drive();
        handler.run();
        climber.run();
        camera.run();
        imu.run();
        IMUReset();
        //led.run();
    }

    public void dashboard() {
        camera.dashboard();
        dist.dashboard();
        color.dashboard();
        base.dashboard();
        handler.dashboard();
        climber.dashboard();
        gyro.dashboard();
        imu.dashboard();

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
        } else {
            base.arcadeDrive();
        }

        if (driver.getAButtonReleased()) {
            gyro.reset();
        }
    }

    public void IMUReset() {
        if (operator.getStickButtonReleased(left)) {
            imu = null;
            imu = new IMU();
        }
    }
}