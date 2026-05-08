# 🏠 Home Services Marketplace Platform

A distributed microservices-based platform for booking home services such as plumbing, carpentry, electrical work, cleaning, painting, and more.

This project was built to demonstrate:

- Microservices Architecture
- REST API communication
- RabbitMQ asynchronous messaging
- Spring Boot microservices
- Jakarta EE / EJB concepts
- Session-based authentication
- Distributed transaction flow simulation
- Wallet & booking processing

---

# 📌 Project Overview

Customers can:

- Register and login
- Add money to wallet
- Browse available service offers
- Book services
- Receive booking/payment notifications

Service providers can:

- Register as professionals
- Create service offers
- Update offers
- View bookings

Admins can:

- Manage categories
- View all users
- View transaction history

---

# 🧱 System Architecture

The system follows a **Microservices Architecture** where every service owns:

- Its own database
- Its own business logic
- Its own REST APIs

Services communicate using:

- REST APIs (synchronous communication)
- RabbitMQ Events (asynchronous communication)

---

# 🧩 Microservices

---

## 1️⃣ User Service (Jakarta EE + EJB)

Responsible for:

- Authentication
- Session management
- Wallet management
- User registration/login

### Technologies

- Jakarta EE
- EJB
- JAX-RS
- PostgreSQL

### Used EJB Types

- Stateless Bean
- Stateful Bean / Singleton (depending on implementation)

### Features

- Register customer/provider/admin
- Login/logout
- Session handling
- Wallet balance
- Add funds
- Deduct funds

---

## 2️⃣ Service Catalog Service (Spring Boot)

Responsible for:

- Service categories
- Service offers

### Features

- Create category
- Create offer
- Update offer
- Browse offers
- Browse by category

### Communication

- Validates logged-in provider through User Service
- Publishes RabbitMQ events

---

## 3️⃣ Booking Service (Spring Boot)

Responsible for:

- Booking flow
- Payment verification
- Distributed transaction simulation

### Features

- Create booking
- Validate customer session
- Get offer details
- Deduct wallet balance
- Rollback payment if booking fails

### Communication

#### REST Calls

- User Service
- Catalog Service

#### RabbitMQ Events

- booking.created
- booking.rejected
- payment.success
- payment.rollback

---

## 4️⃣ Notification Service (Spring Boot)

Responsible for:

- Receiving RabbitMQ events
- Storing notifications
- Returning notifications via REST APIs

### Features

- Receive async events
- Save notifications
- Retrieve notifications

---
 
 
