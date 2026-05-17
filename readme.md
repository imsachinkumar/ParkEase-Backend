# 🅿️ ParkEase Backend — Smart Parking Management System

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-green)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue)
![RabbitMQ](https://img.shields.io/badge/RabbitMQ-3.x-orange)

> **ParkEase** is a full-stack Smart Parking Management Platform built using a Microservices Architecture. It connects drivers seeking parking with lot operators managing parking spaces.

---

## 📋 Table of Contents

- [Architecture Overview](#architecture-overview)
- [Microservices](#microservices)
- [Tech Stack](#tech-stack)
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
- [API Documentation](#api-documentation)
- [Environment Variables](#environment-variables)
- [RabbitMQ Setup](#rabbitmq-setup)
- [Database Setup](#database-setup)
- [Project Structure](#project-structure)

---

## 🏗️ Architecture Overview

ParkEase follows a **Microservices Architecture** where each service is independently deployable and owns its own database.

```
┌─────────────────────────────────────────────────────┐
│                   Angular Frontend                   │
│                  (localhost:4200)                    │
└──────────────────────┬──────────────────────────────┘
                       │
┌──────────────────────▼──────────────────────────────┐
│               Parkease Web (MVC Layer)               │
│                  (localhost:8090)                    │
└──────────────────────┬──────────────────────────────┘
                       │
┌──────────────────────▼──────────────────────────────┐
│              Eureka Service Discovery                │
│                  (localhost:8761)                    │
└──────┬────────┬────────┬────────┬────────┬──────────┘
       │        │        │        │        │
  ┌────▼──┐ ┌──▼───┐ ┌──▼──┐ ┌──▼───┐ ┌──▼──────┐
  │ Auth  │ │ Lot  │ │Spot │ │Book  │ │ Payment │
  │ :8081 │ │:8082 │ │:8083│ │:8084 │ │  :8085  │
  └───────┘ └──────┘ └─────┘ └──────┘ └─────────┘
  ┌────────┐ ┌──────────┐ ┌────────────┐
  │Vehicle │ │Notific.  │ │ Analytics  │
  │ :8086  │ │  :8087   │ │   :8088   │
  └────────┘ └──────────┘ └────────────┘
                       │
              ┌────────▼────────┐
              │    RabbitMQ     │
              │  (localhost:    │
              │    5672)        │
              └─────────────────┘
```

---

## 🔧 Microservices

| Service | Port | Database | Description |
|---------|------|----------|-------------|
| **eureka-server** | 8761 | — | Service Discovery |
| **auth-service** | 8081 | parkease_auth | User Registration, Login, JWT |
| **parkinglot-service** | 8082 | parkease_parking | Parking Lot Management |
| **spot-service** | 8083 | parkease_spots | Parking Spot Management |
| **booking-service** | 8084 | parkease_booking | Booking Lifecycle |
| **payment-service** | 8085 | parkease_payment | Payment Processing |
| **vehicle-service** | 8086 | parkease_vehicle | Vehicle Management |
| **notification-service** | 8087 | parkease_notification | Notifications via RabbitMQ |
| **analytics-service** | 8088 | parkease_analytics | Analytics & Reports |
| **parkease-web** | 8090 | — | Spring MVC Gateway Layer |

---

## 🛠️ Tech Stack

| Layer | Technology |
|-------|-----------|
| Language | Java 17 |
| Framework | Spring Boot 3.2.0 |
| Service Discovery | Spring Cloud Netflix Eureka |
| Database | MySQL 8.0 |
| Message Broker | RabbitMQ 3.x |
| Security | JWT + Spring Security |
| Build Tool | Maven |

---

##  Prerequisites

- Java 17+
- Maven 3.8+
- MySQL 8.0+
- RabbitMQ 3.x

---

## 🚀 Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/imsachinkumar/ParkEase-Backend.git
cd ParkEase-Backend
```

### 2. Setup Databases

Run this SQL script in MySQL:

```sql
CREATE DATABASE IF NOT EXISTS parkease_auth;
CREATE DATABASE IF NOT EXISTS parkease_parking;
CREATE DATABASE IF NOT EXISTS parkease_spots;
CREATE DATABASE IF NOT EXISTS parkease_booking;
CREATE DATABASE IF NOT EXISTS parkease_payment;
CREATE DATABASE IF NOT EXISTS parkease_vehicle;
CREATE DATABASE IF NOT EXISTS parkease_notification;
CREATE DATABASE IF NOT EXISTS parkease_analytics;
```

RabbitMQ Dashboard: `http://localhost:15672` (guest/guest)

### 4. Start Services in Order

Open separate terminals for each service:

```bash
# Terminal 1 - Eureka Server
cd eureka-server
mvn spring-boot:run

# Terminal 2 - Auth Service
cd auth-service
mvn spring-boot:run

# Terminal 3 - ParkingLot Service
cd parkinglot-service
mvn spring-boot:run

# Terminal 4 - Spot Service
cd spot-service
mvn spring-boot:run

# Terminal 5 - Booking Service
cd booking-service
mvn spring-boot:run

# Terminal 6 - Payment Service
cd payment-service
mvn spring-boot:run

# Terminal 7 - Vehicle Service
cd vehicle-service
mvn spring-boot:run

# Terminal 8 - Notification Service
cd notification-service
mvn spring-boot:run

# Terminal 9 - Parkease Web
cd parkease-web
mvn spring-boot:run
```

---



## 🔑 Environment Variables

Each service uses `application.properties`. Key configurations:

```properties
# Database
spring.datasource.url=jdbc:mysql://localhost:3306/{database_name}
spring.datasource.username=root
spring.datasource.password=your_password

# Eureka
eureka.client.service-url.defaultZone=http://localhost:8761/eureka/

# RabbitMQ
spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest

# JWT (auth-service)
jwt.secret=your_jwt_secret
jwt.expiration=86400000
```

---

## 🐰 RabbitMQ Setup

RabbitMQ is used for async notifications between booking-service and notification-service.

```
Exchange: notification.exchange (TopicExchange)
Queue:    notification.queue (durable)
Routing:  notification.routing.key
```

Flow:
```
booking-service → RabbitMQ → notification-service → MySQL
```

---

## 🗄️ Database Setup

Each microservice has its own MySQL database:

| Service | Database |
|---------|---------|
| auth-service | parkease_auth |
| parkinglot-service | parkease_parking |
| spot-service | parkease_spots |
| booking-service | parkease_booking |
| payment-service | parkease_payment |
| vehicle-service | parkease_vehicle |
| notification-service | parkease_notification |
| analytics-service | parkease_analytics |

---


## 📁 Project Structure

```
ParkEase-Backend/
├── eureka-server/
├── auth-service/
├── parkinglot-service/
├── spot-service/
├── booking-service/
├── payment-service/
├── vehicle-service/
├── notification-service/
├── analytics-service/
├── parkease-web/
├── docker-compose.yml
└── README.md
```

---

## 👥 User Roles

| Role | Description |
|------|------------|
| **DRIVER** | Can search lots, book spots, manage vehicles, make payments |
| **MANAGER** | Can manage parking lots, spots, view bookings & revenue |
| **ADMIN** | Full platform access — users, lots, bookings, payments |

---


>  Admin registration is disabled from UI — only existing admin can manage platform.

---

## 📞 Contact

**Developer:** Sachin Kumar  
**GitHub:** [@imsachinkumar](https://github.com/imsachinkumar)

---

*ParkEase — Find. Reserve. Park. Effortlessly.* 🅿️
