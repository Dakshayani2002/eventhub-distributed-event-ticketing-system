# EventHub – Distributed Event Ticketing System

A scalable and secure event ticketing backend built using **Java, Spring Boot, PostgreSQL, Redis, Apache Kafka, and Docker**.

EventHub is a role-based event management platform that supports event management, secure ticket booking, payment processing, and refund management. It incorporates distributed system concepts and fault-tolerance mechanisms to improve performance, reliability, and maintainability.

## Key Features

- **Authentication & Authorization:** JWT-based authentication, Role-Based Access Control (ADMIN, ORGANIZER, CUSTOMER), and BCrypt password hashing.
- **Event Management:** REST APIs for event creation, retrieval, updating, and management.
- **Booking Management:** Secure ticket booking, cancellation, transaction management, and Redis-based distributed locking to handle concurrent booking requests.
- **Payment & Refund Management:** Payment processing, payment history, and refund workflows.
- **Redis Caching:** Caching frequently accessed data to reduce database load and improve API performance.
- **Kafka Messaging:** Asynchronous event processing and notification workflows.
- **API Rate Limiting:** Redis-based rate limiting to control excessive API requests.
- **Fault Tolerance:** Resilience4j Retry and Circuit Breaker mechanisms to handle transient failures.
- **Structured Logging:** SLF4J and MDC-based request logging for improved debugging and traceability.

## Technology Stack

| Category | Technologies |
|---|---|
| Language | Java |
| Framework | Spring Boot |
| Database | PostgreSQL |
| Caching & Locking | Redis |
| Messaging | Apache Kafka, Zookeeper |
| Security | Spring Security, JWT, BCrypt |
| Resilience | Resilience4j |
| Containerization | Docker |
| Build Tool | Maven |
| Logging | SLF4J, MDC |

## Architecture Overview

EventHub follows a modular backend architecture using Spring Boot.

- **Spring Boot:** Handles REST APIs and business logic.
- **PostgreSQL:** Stores application data.
- **Redis:** Supports caching, distributed locking, and API rate limiting.
- **Kafka:** Enables asynchronous communication and notification workflows.
- **Resilience4j:** Provides retry and circuit breaker capabilities.

## Getting Started

### Prerequisites

- Java
- Maven
- PostgreSQL
- Redis
- Docker

### Configuration

Configure the following environment variables before running the application:

| Variable | Description |
|---|---|
| `DB_PASSWORD` | PostgreSQL database password |
| `JWT_SECRET` | JWT signing secret |

Update the database connection details in `application.properties` according to your local environment.

**Note:** Never commit actual credentials, passwords, or JWT secrets to the repository.

### Run Locally

Clone the repository:

```bash
git clone https://github.com/Dakshayani2002/eventhub-distributed-event-ticketing-system.git
```

Navigate to the project directory:

```bash
cd eventhub-distributed-event-ticketing-system
```

Start the required infrastructure services using Docker Compose.

Run the Spring Boot application on Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

Application URL: `http://localhost:8081`

## Future Enhancements

- Expand automated unit and integration test coverage.
- Introduce centralized monitoring and distributed tracing.
- Enhance CI/CD and deployment automation.

---

**EventHub** – Building reliable and scalable backend systems through real-world event ticketing workflows.
