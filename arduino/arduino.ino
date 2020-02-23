#include <Wire.h>
#include <SparkFunLSM9DS1.h>

#define LSM9DS1_M 0x1E
#define LSM9DS1_AG 0x6B

LSM9DS1 imu;

bool err;

double pitch();

void setup() {
  Serial.begin(115200);

  Wire.begin();
  
  imu.settings.device.commInterface = IMU_MODE_I2C;
  imu.settings.device.mAddress = LSM9DS1_M;
  imu.settings.device.agAddress = LSM9DS1_AG;

  if (!imu.begin()) {
    err = true;
  }
}

void loop() {
  if (err) {
    Serial.println("Error");
    return;
  }
  
  imu.readGyro();
  imu.readAccel();
  imu.readMag();

  Serial.println(pitch(imu.ax, imu.ay, imu.az, -imu.my, -imu.mx, imu.mz));
}

double pitch(float ax, float ay, float az, float mx, float my, float mz) {
  float pitch = atan2(-ax, sqrt(ay * ay + az * az));

  pitch *= 180.0 / PI;

  return pitch;
}
