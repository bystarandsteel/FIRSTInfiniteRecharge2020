package frc.robot.sensors;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.stream.Stream;

public class IMU {

    private SerialPort arduino;

    private double angle = 0;

    public IMU() {
        arduino = new SerialPort(115200, SerialPort.Port.kUSB1, 8, SerialPort.Parity.kNone);
    }

    public void run() {
        String angleString = arduino.readString();

        Stream<String> temp = angleString.lines();

        Object[] arr = temp.toArray();

        for (int i = 0; i < arr.length; i++) {
            try {
                angle = Double.parseDouble(arr[i].toString());
                break;
            } catch (Exception ex) {
                continue;
            }
        }
    }

    public double getAngle() {
        return angle;
    }

    public void dashboard() {
        SmartDashboard.putNumber("IMU Angle", getAngle());
    }
}