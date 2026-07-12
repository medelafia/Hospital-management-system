# 🏥 Hospital Management System

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.1-brightgreen)
![Spring Security](https://img.shields.io/badge/Spring-Security-green)
![Thymeleaf](https://img.shields.io/badge/Thymeleaf-Template-blue)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue)
![Docker](https://img.shields.io/badge/Docker-Ready-2496ED)
![Maven](https://img.shields.io/badge/Maven-Build-red)

A web-based **Hospital Management System** built with **Spring Boot**, **Spring Security**, **Spring Data JPA**, **Thymeleaf**, and **MySQL**. The application streamlines hospital operations by managing patients, doctors, appointments, and user authentication through a clean and secure interface.

---

# ✨ Features

- 👨‍⚕️ Doctor Management
- 🧑‍🤝‍🧑 Patient Management
- 📅 Appointment Scheduling
- 🔐 User Authentication & Authorization
- 👤 Role-Based Access Control
- 📊 Dashboard & Statistics
- 🔍 Search and View Records
- ✏️ Create, Update, and Delete Operations
- ⚠️ Global Exception Handling
- 🐳 Docker Support

---

# 🏗️ System Architecture

```
                +----------------------+
                |     Web Browser      |
                +----------+-----------+
                           |
                           ▼
                +----------------------+
                | Spring Boot MVC      |
                | Controllers          |
                +----------+-----------+
                           |
                           ▼
                +----------------------+
                | Service Layer        |
                +----------+-----------+
                           |
                           ▼
                +----------------------+
                | Spring Data JPA      |
                +----------+-----------+
                           |
                           ▼
                +----------------------+
                | MySQL Database       |
                +----------------------+
```

---

# 📂 Project Structure

```
Hospital-management-system/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── config/
│   │   │   ├── controller/
│   │   │   ├── entity/
│   │   │   ├── repository/
│   │   │   ├── service/
│   │   │   ├── customExceptions/
│   │   │   └── enums/
│   │   │
│   │   ├── resources/
│   │   │   ├── templates/
│   │   │   ├── static/
│   │   │   └── application.properties
│
├── Dockerfile
├── docker-compose.yaml
├── pom.xml
└── README.md
```

---

# 🩺 Modules

## Doctor Management

- Register doctors
- Update doctor information
- Delete doctors
- View doctor profiles

---

## Patient Management

- Register patients
- Update patient details
- Delete patients
- View patient history

---

## Appointment Management

- Schedule appointments
- Update appointment status
- Cancel appointments
- Manage doctor-patient assignments

---

## User Management

- Secure login
- User registration
- Role management
- Authentication using Spring Security

---

# 🔒 Security

The application uses **Spring Security** to provide:

- User authentication
- Password encryption
- Role-based authorization
- Protected routes
- Secure login/logout

---

# 🛠️ Technologies

### Backend

- Java 17
- Spring Boot 3
- Spring MVC
- Spring Data JPA
- Spring Security

### Frontend

- Thymeleaf
- HTML5
- CSS3
- JavaScript

### Database

- MySQL

### Build Tool

- Maven

### Deployment

- Docker
- Docker Compose

---

# 🚀 Getting Started

## Clone the repository

```bash
git clone https://github.com/medelafia/Hospital-management-system.git

cd Hospital-management-system
```

---

## Prerequisites

- Java 17+
- Maven
- Docker (optional)
- MySQL

---

## Configure the Database

Update your database configuration in:

```
src/main/resources/application.properties
```

Example:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/hospital_db
spring.datasource.username=root
spring.datasource.password=your_password
```

---

## Run the application

Using Maven

```bash
./mvnw spring-boot:run
```

or

```bash
mvn spring-boot:run
```

---

## Run with Docker

```bash
docker compose up --build
```

The application will be available at:

```
http://localhost:8080
```

---

# 📋 Main Entities

- User
- Role
- Doctor
- Patient
- Appointment
- Statistic

---

# 📊 Application Workflow

```
User Login
     │
     ▼
Dashboard
     │
     ├───────────────┐
     │               │
     ▼               ▼
Doctors          Patients
     │               │
     └──────┬────────┘
            ▼
     Appointments
            ▼
     Hospital Statistics
```

---

# 📁 Project Highlights

- Layered Architecture (MVC)
- Repository Pattern
- Exception Handling
- Secure Authentication
- Dockerized Deployment
- Responsive Thymeleaf Views
- MySQL Persistence

---

# 📈 Future Improvements

- REST API support
- JWT Authentication
- Email Notifications
- SMS Appointment Reminders
- Medical Records Management
- Prescription Management
- File Uploads (Medical Reports)
- Payment Integration
- Dashboard Charts
- Audit Logging
- Unit & Integration Testing
- CI/CD Pipeline
- Kubernetes Deployment

---

# 🎯 Learning Objectives

This project demonstrates:

- Spring Boot development
- MVC Architecture
- Spring Security
- CRUD operations
- JPA & Hibernate
- Thymeleaf templating
- MySQL integration
- Docker deployment

---

# 🤝 Contributing

Contributions are welcome!

1. Fork the repository
2. Create a new branch

```bash
git checkout -b feature/new-feature
```

3. Commit your changes

```bash
git commit -m "Add new feature"
```

4. Push your branch

```bash
git push origin feature/new-feature
```

5. Open a Pull Request.

---

# 👨‍💻 Author

**Mohamed El Afia**

GitHub: https://github.com/medelafia

---

# ⭐ Support

If you found this project helpful, please consider giving it a ⭐ on GitHub.