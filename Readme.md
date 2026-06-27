
      # TaskForge

A distributed asynchronous task processing platform designed to handle high volumes of background jobs reliably and efficiently.

TaskForge enables applications to offload time-consuming operations such as notifications, data processing, and third-party API integrations into asynchronous workflows, improving responsiveness, scalability, and fault tolerance.

---

## Problem Statement

Modern applications often perform operations that are slow, failure-prone, or dependent on external services.

Examples include:

* Sending emails and notifications
* Generating reports and analytics
* Processing large datasets
* Calling external APIs such as GitHub, OpenAI, or payment gateways

Executing these operations synchronously increases response time and reduces system reliability.

TaskForge addresses this by decoupling task creation from task execution using an asynchronous, event-driven architecture.

---

## Features

### Notification Processing

* Email notifications
* SMS notifications
* Push notifications
* Retry support
* Dead Letter Queue (DLQ) handling

### Data Processing

* Report generation
* CSV exports
* Log analysis
* Analytics aggregation

### Third-Party API Integrations

* GitHub API analysis
* OpenAI API integrations
* Weather API processing
* Payment provider integrations

### Reliability Features

* Idempotent task execution
* Automatic retries
* Dead Letter Queues
* Rate limiting
* Failure recovery

---

## Architecture
                                        +--------------------+
                                        |       Client       |
                                        +---------+----------+
                                                  |
                                             HTTP Request
                                                  |
                                                  v
                               +----------------------------------+
                               |       AsyncEvents Service        |
                               |----------------------------------|
                               | Controller                       |
                               | TaskService                      |
                               | TaskRepository                   |
                               | PostgreSQL                       |
                               | Kafka Producer                   |
                               +----------------+-----------------+
                                                |
                                      TaskCreatedEvent
                                                |
                                                v
                                   +-------------------------+
                                   |          Kafka          |
                                   |-------------------------|
                                   | task-created-topic      |
                                   | task-dlq-topic          |
                                   +------------+------------+
                                                |
                                                |
                                                v
                              +-----------------------------------+
                              |        Worker Service             |
                              |-----------------------------------|
                              | Kafka Consumer                    |
                              | TaskRepository                    |
                              | TaskHandlerFactory                |
                              | EmailTaskHandler                  |
                              | DataProcessingTaskHandler         |
                              | ThirdPartyApiTaskHandler          |
                              | Retry Logic                       |
                              | DLQ Producer                      |
                              | PostgreSQL                        |
                              +----------------+------------------+
                                               |
                                               |
                              +----------------+----------------+
                              |                |                |
                              v                v                v
                     Email Service     Data Processing    Third Party API




## Task Lifecycle

PENDING

↓

PROCESSING

↓

COMPLETED

or

PENDING

↓

PROCESSING

↓

FAILED

↓

RETRY

↓

DLQ

---

## Tech Stack

### Backend

* Java
* Spring Boot

### Database

* PostgreSQL

### Messaging

* Apache Kafka

### Cache

* Redis

### Infrastructure

* Docker
* Kubernetes(Future Prospect)

### Monitoring

* Spring Boot Actuator

---

## Task Types

### Notification Tasks

Examples:

* Connection Request Email
* Welcome Email
* Password Reset Notification

### Data Processing Tasks

Examples:

* Activity Report Generation
* CSV Export
* Log Analytics

### Third Party API Tasks

Examples:

* GitHub Repository Analysis
* OpenAI Content Generation
* Weather Data Collection

---

## Future Enhancements

* Kafka Consumer Groups
* Distributed Scheduling
* Debezium CDC Integration
* Multi-Tenant Task Processing
* Metrics Dashboard
* Task Priority Queues
* Kubernetes Horizontal Scaling

---

## Learning Objectives

This project demonstrates:

* Event Driven Architecture
* Distributed Systems Concepts
* Message Queues
* Fault Tolerance
* Microservices Communication
* Retry and Recovery Mechanisms
* Database Design
* Scalability Patterns








                     
![alt text](image.png)




# 🚀 Running the Project

## Prerequisites

Ensure the following are installed on your system:

* Java 21+
* Maven 3.9+
* Docker
* Docker Compose

---

## Clone the Repository

```bash
git clone https://github.com/ar-p-it/TaskForge---Distributed-Asynchronous-Task-Processing-Platform.git
cd TaskForge
```

---

## Build the Services

### Build API Service

```bash
cd AsyncEvents
mvn clean package
cd ..
```

### Build Worker Service

```bash
cd worker-service
mvn clean package
cd ..
```

---

## Start the Distributed Platform

Run the following command from the project root:

```bash
docker compose up --build
```

This starts the complete distributed system consisting of:

* PostgreSQL Database
* Apache Kafka Broker
* Task API Service
* Worker Service

---

## Test the API

### Create a Task

**Endpoint**

```http
POST http://localhost:8080/tasks
```

### Successful Task

```json
{
    "type": "THIRD_PARTY_API",
    "payload": "hello"
}
```

Expected Flow:

```
API
    ↓
Save Task
    ↓
Kafka
    ↓
Worker
    ↓
External API
    ↓
COMPLETED
```

---

### Failed Task (Retry + Dead Letter Queue)

```json
{
    "type": "THIRD_PARTY_API",
    "payload": "fail"
}
```

Expected Flow:

```
API
    ↓
Kafka
    ↓
Worker
    ↓
Retry 1
    ↓
Retry 2
    ↓
Retry 3
    ↓
Dead Letter Queue (DLQ)
```

---

## Stop the Platform

Stop all running services:

```bash
docker compose down
```

To also remove the PostgreSQL data volume:

```bash
docker compose down -v
```

---

# 💻 Running Without Docker

The project can also be executed without Docker.

Start PostgreSQL and Kafka locally.

### Run the API

```bash
cd AsyncEvents
mvn spring-boot:run
```

### Run the Worker

```bash
cd worker-service
mvn spring-boot:run
```

The application automatically switches between local development and Docker environments using Spring Boot environment variables.

* Local Development

  * PostgreSQL → `localhost:5432`
  * Kafka → `localhost:9092`

* Docker

  * PostgreSQL → `postgres:5432`
  * Kafka → `kafka:9092`

No code changes are required to switch between environments.

---

# 📦 Services

| Service        | Port |
| -------------- | ---- |
| Task API       | 8080 |
| Worker Service | 8081 |
| PostgreSQL     | 5432 |
| Apache Kafka   | 9092 |

---

# 🛑 Stopping the Project

Stop all containers:

```bash
docker compose down
```

Stop containers and remove database volume:

```bash
docker compose down -v
```

---

# 📋 Logs

View logs for all services:

```bash
docker compose logs -f
```

View logs for the API only:

```bash
docker compose logs -f api
```

View logs for the Worker:

```bash
docker compose logs -f worker
```

View logs for Kafka:

```bash
docker compose logs -f kafka
```

View logs for PostgreSQL:

```bash
docker compose logs -f postgres
```


```bash
git clone https://github.com/ar-p-it/TaskForge---Distributed-Asynchronous-Task-Processing-Platform.git
cd TaskForge