package frc.robot;

import com.revrobotics.Rev2mDistanceSensor;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;

import frc.robot.sensors.ColorSensor;
import frc.robot.sensors.DistanceSensor;
import frc.robot.sensors.Limelight;
import frc.robot.sensors.Pipeline;

public class Robot extends TimedRobot {

    public static XboxController driver = new XboxController(0);
    public static XboxController operator = new XboxController(1);
    public static final GenericHID.Hand left = GenericHID.Hand.kLeft;
    public static final GenericHID.Hand right = GenericHID.Hand.kRight;

    public static Limelight camera = new Limelight(Pipeline.RETRO);
    public static DistanceSensor dist = new DistanceSensor(Rev2mDistanceSensor.Port.kOnboard);
    public static ColorSensor color = new ColorSensor(I2C.Port.kMXP);

    @Override
    public void robotInit() {
        dist.initialize();
    }

    @Override
    public void autonomousInit() {
        dist.initialize();
    }

    @Override
    public void autonomousPeriodic() {
        dashboard();
    }

    @Override
    public void teleopInit() {
        dist.initialize();
    }

    @Override
    public void teleopPeriodic() {
        dashboard();
    }

    public void dashboard() {
        camera.dashboard();
        dist.dashboard();
        color.dashboard();
    }
}