package frc.robot;

import com.revrobotics.Rev2mDistanceSensor;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.Autos.Auto;
import frc.robot.procedures.DriveDistance;
import frc.robot.procedures.Procedure;
import frc.robot.procedures.SpitBalls;
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

    public static SendableChooser<Auto> chooser = new SendableChooser<>();

    public static Procedure[] scoreArray = {new DriveDistance(), new SpitBalls(), new DriveDistance()};
    public static double[] paramsScore = {0, 0, 0};

    public static Procedure[] backArray = {new DriveDistance()};
    public static double[] paramsBack = {0};

    public static Auto score = new Auto(scoreArray, paramsScore);
    public static Auto back = new Auto(backArray, paramsBack);

    @Override
    public void robotInit() {
        dist.initialize();
        base.initialize();
        handler.initialize();
        climber.initialize();

        chooser.setDefaultOption("Disable Auto", null);
        chooser.addOption("Score", score);
        chooser.addOption("Back Up", back);
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

        Auto autoAction = chooser.getSelected();

        if (autoAction != null) {
            for (int i = 0; i < autoAction.getProcedures().length; i++) {
                autoAction.getProcedures()[i].run(autoAction.getParams()[i]);
            }
        }
    }

    @Override
    public void autonomousPeriodic() {

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
        } else if (driver.getAButton()) {
            base.driveToTarget();
        } else {
            base.arcadeDrive();
        }
    }
}