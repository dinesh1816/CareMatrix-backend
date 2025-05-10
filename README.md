# CareMatrix EHR Backend

This is the backend for the CareMatrix Electronic Health Records (EHR) system, built with Spring Boot. It provides RESTful APIs for managing patients, doctors, appointments (including telemedicine with Google Meet integration), prescriptions, allergies, conditions, insurance, and more.

---

## Tech Stack

- **Java Version:** 17 (project uses Java 21 for compilation)
- **Spring Boot Version:** 3.4.4

---

## Features

- **User Authentication & Authorization** (JWT, Spring Security)
- **Patient, Doctor, Appointment, Prescription, Allergy, Condition, Insurance, Surgery, Consultation Management**
- **Google Calendar/Meet Integration** for telemedicine appointments
- **Zoom Integration** (optional, see config)
- **Pagination, Sorting, and Filtering** for list APIs
- **Database Migrations** with Flyway
- **MySQL** (default) or **PostgreSQL** support

---

## Project Structure

```
src/main/java/com/careMatrix/backend/
├── Controller/   # REST API endpoints
├── Service/      # Business logic (GoogleCalendarService, AppointmentService, etc.)
├── Entity/       # JPA entities (Patient, Doctor, Appointment, etc.)
├── Repo/         # Spring Data JPA repositories
├── Dto/          # Data Transfer Objects
├── config/       # Security and app configuration
├── filter/       # JWT and other filters
└── BackendApplication.java # Main entry point
src/main/resources/
├── application.properties  # Main configuration
└── db/migration/           # Flyway migration scripts
```

---

## Prerequisites

- Java 17 or higher (project uses Java 21 for compilation)
- Maven 3.6+
- MySQL (default) or PostgreSQL (see config)
- Google Cloud Project (for Google Calendar/Meet integration)
- (Optional) Zoom Developer Account

---

## Setup Instructions

### 1. Clone the Repository

```bash
git clone https://github.com/dinesh1816/backend.git
cd backend
```

### 2. Database Setup

- **MySQL** (default):
  - Create a database named `care`
  - Update `src/main/resources/application.properties` if your username/password differ:
    ```
    spring.datasource.url=jdbc:mysql://localhost:3306/care
    spring.datasource.username=root
    spring.datasource.password=root
    ```

### 3. Google Calendar/Meet Integration

- Go to [Google Cloud Console](https://console.cloud.google.com/).
- Create a project and enable the Google Calendar API.
- Create OAuth 2.0 credentials (type: Desktop App).
- Download the `credentials.json` file and place it in `src/main/resources/`.
- In the Google Cloud Console, set the redirect URI to:
  ```
  http://localhost:8888/oauth2callback
  ```
- The first time you create a telemedicine appointment, a browser window will open for Google authentication.

### 4. Zoom Integration (Optional)

- Set your Zoom OAuth credentials in `application.properties` if you want to use Zoom meetings.

### 5. Build the Project

```bash
mvn clean install
```

### 6. Run the Application

```bash
mvn spring-boot:run
```
or
```bash
./mvnw spring-boot:run
```

The backend will start on [http://localhost:8080](http://localhost:8080).

---

## API Overview

- All endpoints are under `/api/` (e.g., `/api/patients`, `/api/appointments`, etc.)
- JWT authentication is required for most endpoints.
- See the `Controller` classes for detailed API routes.

---

## Database Migrations

- Flyway is used for automatic schema migrations.
- Migration scripts are in `src/main/resources/db/migration/`.

---

## Configuration

Edit `src/main/resources/application.properties` for:

- Database connection
- Zoom API credentials
- Logging and other settings

---

## Testing

- Basic test class: `src/test/java/com/careMatrix/backend/BackendApplicationTests.java`
- Run tests with:
  ```bash
  mvn test
  ```

---

## Troubleshooting

- **Google Calendar/Meet:** If you get a redirect URI or port error, ensure your Google Cloud Console and `credentials.json` match the port and callback path in the code (`http://localhost:8888/oauth2callback`).
- **Dependency Issues:** If Maven cannot resolve dependencies, ensure you are using the versions specified in `pom.xml` and run with the `-U` flag.

---

## Useful References

- [Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Google Calendar API Java Quickstart](https://developers.google.com/calendar/api/quickstart/java)
- [Flyway Migration](https://flywaydb.org/documentation/)

---

## License

This project is for demonstration and educational purposes. 