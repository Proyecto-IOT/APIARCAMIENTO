# Smart Parking Management

**Intelligent parking management through IoT and real-time monitoring.**

*iOS · Swift · UIKit · IoT · REST APIs · WebSockets · Sensor Integration*

Smart Parking Management is an iOS application designed to improve the management and monitoring of commercial parking facilities through IoT technology.

The platform integrates physical sensors to detect vehicle occupancy in parking spaces, allowing users to identify available spots in real time and register their own vehicles within the application.

Additionally, it incorporates a security monitoring system that detects potential hazards, including gas leaks and unusually loud noises, generating notifications to keep users informed about incidents within the parking facility.

## ✨ Features

### 🚗 Smart Parking Management
- **Real-Time Occupancy Detection:** Monitor parking space availability through integrated IoT sensors.
- **Weight Sensors:** Detect vehicle presence by measuring the weight applied to individual parking spaces.
- **Object Detection Sensors:** Identify when an object or vehicle occupies a monitored parking spot.
- **Available Parking Spaces:** Identify unoccupied spaces to help users find parking more efficiently.
- **Parking Registration:** Allow users to indicate which parking space they have occupied and associate it with their registered vehicle.

### 🚙 Vehicle Management
- **Vehicle Registration:** Register personal vehicles within the application.
- **Vehicle Profiles:** Manage vehicle information through personalized records.
- **Vehicle Management (CRUD):** Create, view, update, and delete registered vehicles.
- **Parking Association:** Associate a registered vehicle with an occupied parking space.

### 🔔 Security & Monitoring
- **Centralized Notifications:** Access security alerts and detected incidents through a dedicated notification center.
- **Gas Leak Detection:** Receive alerts when sensors detect potential gas leaks within the parking facility.
- **Loud Noise Detection:** Monitor unusually loud sounds and generate security notifications.
- **Early-Warning Alerts:** Notify users about potentially hazardous situations detected by the monitoring system.

### 📡 IoT Integration
- **Sensor Communication:** Integrate physical sensors to monitor individual parking spaces.
- **Automated Occupancy Monitoring:** Update parking availability based on sensor readings.
- **Real-Time Updates:** Keep parking occupancy information synchronized with the application.

## 🛠 Tech Stack

| Technology | Purpose |
|---|---|
| Swift | Main programming language |
| UIKit | Native iOS user interface |
| Laravel REST API | Backend communication and business logic |
| WebSockets | Real-time communication and updates |
| IoT Sensors | Vehicle occupancy detection |
| Weight Sensors | Weight-based vehicle detection |
| Object Detection Sensors | Parking space occupancy detection |
| Gas Sensors | Potential gas leak detection |
| Sound Sensors | Loud noise detection |

## 🏗 Architecture

The application follows a client-server architecture integrated with IoT hardware:

- **iOS Application:** Native iOS frontend responsible for parking management, vehicle registration, notifications, and user interaction.
- **Backend:** REST API responsible for managing users, vehicles, parking spaces, and application data.
- **IoT Integration:** Physical sensors provide information about parking occupancy and environmental conditions.
- **Real-Time Communication:** WebSockets enable the application to receive live parking updates and security notifications.
- **Security Monitoring:** Sensor-based detection generates alerts for potentially dangerous environmental conditions.

## 📱 Platform

Smart Parking Management is designed as a native iOS application, providing an intuitive interface for monitoring parking availability, managing vehicles, and receiving real-time security alerts.

## 👨‍💻 Development

This repository contains the iOS frontend of the Smart Parking Management system.

The application communicates with a backend through REST API endpoints and real-time WebSocket connections, integrating information from physical IoT sensors.

---

*Developed as a project focused on smart infrastructure, IoT integration, and intelligent parking management.*
