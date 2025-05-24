#include <BLEDevice.h>
#include <BLEUtils.h>
#include <BLEServer.h>

#define BEACON_UUID "2D7A9F0C-E0E8-4CC9-A71B-A21DB2D034A1"
//"2ee9b455-ce9f-4423-8d1e-2f5c12580c6b"
// Change to your custom UUID
#define BEACON_NAME "ESP32_Cam_Beacon"

void setup() {
  Serial.begin(115200);
  
  // Initialize BLE
  BLEDevice::init(BEACON_NAME);  // Initialize BLE with a name

  // Create a BLE Server
  BLEServer *pServer = BLEDevice::createServer();

  // Create a BLE Advertisement
  BLEAdvertising *pAdvertising = pServer->getAdvertising();

  // Set the advertised service (you can create your own UUID if needed)
  pAdvertising->addServiceUUID(BEACON_UUID);

  // Set the advertisement parameters
  pAdvertising->setScanResponse(false);
  pAdvertising->setMinPreferred(0x06);  // 6ms
  pAdvertising->setMaxPreferred(0x12);  // 18ms

  // Start advertising
  pAdvertising->start();

  Serial.println("BLE Beacon is now broadcasting...");
}

void loop() {
  // Nothing needed here, BLE Beacon is running in the background
}
