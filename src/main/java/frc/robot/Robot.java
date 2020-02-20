package frc.robot;

import com.revrobotics.Rev2mDistanceSensor;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.routines.Back;
import frc.robot.routines.Nothing;
import frc.robot.routines.Routine;
import frc.robot.routines.Score;
import frc.robot.sensors.ColorSensor;
import frc.robot.sensors.DistanceSensor;
import frc.robot.sensors.Limelight;
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

    public static Drivebase base = new Drivebase(1, 2, 3, 4);

    public static BallHandler handler = new BallHandler(5,7, 12);

    public static Climber climber = new Climber(8, 11, 0);

    public static boolean flipDrive = false;

    public static boolean endAuto = false;

    SendableChooser<Routine> chooser = new SendableChooser<>();

    @Override
    public void robotInit() {
        dist.initialize();
        base.initialize();
        handler.initialize();
        climber.initialize();

        chooser.setDefaultOption("Disable Auto", new Nothing());
        chooser.addOption("Score", new Score());
        chooser.addOption("Back Up", new Back());
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

        Routine autoRoutine = chooser.getSelected();
        autoRoutine.run();
    }

    @Override
    public void autonomousPeriodic() {
        dashboard();
    }

    @Override
    public void teleopInit() {
        base.reset();
        handler.reset();

        endAuto = true;
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
    }
}