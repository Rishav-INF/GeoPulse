# 🚀 ESP32 iBeacon Implementation using Arduino

This project demonstrates how to configure an ESP32 as an **iBeacon** using the Arduino IDE. It broadcasts a custom iBeacon UUID that can be detected using BLE scanner in our geolocation based attendance apps
---

## 📦 Features

- ESP32 BLE Advertising
- Apple iBeacon Protocol Format
- Customizable UUID, Major, Minor, Tx Power
- Verifiable via Android BLE scanner apps

---

## 📋 iBeacon Specification Overview

An iBeacon packet includes:
- **Company ID** (Apple: `0x004C`)
- **Type**: `0x02`
- **Length**: `0x15`
- **UUID**: 16 bytes
- **Major**: 2 bytes
- **Minor**: 2 bytes
- **Tx Power**: 1 byte (signal strength at 1m)

---

## ⚙️ Requirements

- ESP32 Development Board
- Arduino IDE (1.8.19 or later)
- ESP32 Board Package (v3.0.4 recommended)

---

## 🔧 Setup Instructions

1. Open Arduino IDE.
2. Install the **ESP32 Board Package**.
3. Select your board under **Tools > Board > ESP32 Wrover Module** or your ESP32 variant.
4. Paste the code from [`esp32_beacon.ino`](#code).
5. Upload the code to your ESP32 via USB.
