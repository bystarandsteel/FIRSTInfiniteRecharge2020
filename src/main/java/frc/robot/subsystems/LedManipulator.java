package frc.robot.subsystems;

import edu.wpi.first.wpilibj.*;

import frc.robot.Robot;

public class LedManipulator {

    private AddressableLEDBuffer ledBuffer;
    private AddressableLED led;

    public LedManipulator(int port) {
        led = new AddressableLED(port);
        led.setData(ledBuffer);
        led.start();
    }

    public void setColor(int r, int g, int b) {
        for (var i = 0; i < ledBuffer.getLength(); i++) {
            // Sets the specified LED to the RGB values for red
            ledBuffer.setRGB(i, r, g, b);
        }

        led.setData(ledBuffer);
    }

    public void run() {
        if (Robot.operator.getTriggerAxis(Robot.left) > 0.5) {
            for(int i = 0; i < ledBuffer.getLength(); i++){
                ledBuffer.setRGB(i, 0, 255, 0);
            }
        } else if (Robot.operator.getBumper(Robot.left)) {
            for(int i = 0; i < ledBuffer.getLength(); i++){
                ledBuffer.setRGB(i, 0, 255, 0);
            }
        } else if (Robot.operator.getTriggerAxis(Robot.right) > 0.5) {
            for(int i = ledBuffer.getLength() - 1; i >= 0; i++){
                ledBuffer.setRGB(i, 0, 255, 0);
            }
        } else {
            for(int i = ledBuffer.getLength() - 1; i >= 0; i++){
                ledBuffer.setRGB(i, 0, 0, 0);
            }
        }
        led.setData(ledBuffer);
    }
}