#include <BLEDevice.h>
#include <BLEUtils.h>
#include <BLEBeacon.h>
#include <BLEAdvertising.h>

#define BEACON_UUID "12345678-1234-1234-1234-123456789abc" 
//"2D7A9F0C-E0E8-4CC9-A71B-A21DB2D034A1"

BLEAdvertising *pAdvertising;
BLEBeacon oBeacon;

void updateBeacon(int txPowerLevel, int signalPower) {
  BLEDevice::setPower((esp_power_level_t)txPowerLevel);

  oBeacon.setSignalPower(signalPower);

  BLEAdvertisementData advertisementData;
  advertisementData.setFlags(0x04); // BR_EDR_NOT_SUPPORTED
  String strServiceData = "";
strServiceData += (char)26;
strServiceData += (char)0xFF;
strServiceData += oBeacon.getData();
advertisementData.addData(strServiceData);

  pAdvertising->stop();
  pAdvertising->setAdvertisementData(advertisementData);
  pAdvertising->start();

  Serial.print("Updated Tx Power: ");
  Serial.print(txPowerLevel);
  Serial.print(", Signal Power (RSSI @ 1m): ");
  Serial.println(signalPower);
}

void setup() {
  Serial.begin(115200);
  Serial.println("Starting dynamic iBeacon...");

  BLEDevice::init("ESP32_Dynamic_Beacon");

  oBeacon.setManufacturerId(0x004C);
  oBeacon.setProximityUUID(BLEUUID(BEACON_UUID));
  oBeacon.setMajor(100);
  oBeacon.setMinor(1);
  oBeacon.setSignalPower(-59);  // Default

  pAdvertising = BLEDevice::getAdvertising();
  pAdvertising->setScanResponse(false);
  pAdvertising->setMinPreferred(0x06);
  pAdvertising->setMaxPreferred(0x12);

  updateBeacon(ESP_PWR_LVL_P3, -59);  // Start with default values
}

void loop() {
  if (Serial.available()) {
    String input = Serial.readStringUntil('\n');
    input.trim();

    // Expected format: <txPowerLevel> <signalPower>
    // Example: 4 -65
    int spaceIdx = input.indexOf(' ');
    if (spaceIdx > 0) {
      int txLevel = input.substring(0, spaceIdx).toInt();
      int sigPower = input.substring(spaceIdx + 1).toInt();

      // Clamp txLevel to valid enum range (0-7 for ESP32)
      if (txLevel >= 0 && txLevel <= 7) {
        updateBeacon(txLevel, sigPower);
      } else {
        Serial.println("Invalid Tx level (0-7 allowed).");
      }
    } else {
      Serial.println("Usage: <txLevel 0-7> <signalPower RSSI e.g., -65>");
    }
  }
}
