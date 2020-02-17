package frc.robot;

import com.revrobotics.Rev2mDistanceSensor;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;

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

    @Override
    public void robotInit() {
        dist.initialize();
        base.initialize();
        handler.initialize();
        climber.initialize();
    }

    @Override
    public void autonomousInit() {
        base.reset();
        handler.reset();
    }

    @Override
    public void autonomousPeriodic() {
        dashboard();
    }

    @Override
    public void teleopInit() {
        base.reset();
        handler.reset();
    }

    @Override
    public void teleopPeriodic() {
        dashboard();
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
    }
    
    public void drive() {
        if (driver.getAButton()) {
            base.ballSeek();
        } else {
            base.arcadeDrive();
        }
    }
}