# Grimoire Service 🔮

Grimoire Service is a backend application built with **Spring Boot 3.5.x** and **Java 25**, designed to provide AI-powered quest grading and management features using Google Gemini.

## 🚀 Tech Stack

- **Language:** Java 25
- **Framework:** Spring Boot 3.5.x (Snapshot)
- **Database:** PostgreSQL
- **AI Integration:** Spring AI (Google Gemini)
- **Resilience:** Resilience4j (Circuit Breaker)
- **Observability:** Micrometer Tracing (Brave), Log4j2
- **Concurrency:** Java Virtual Threads (Project Loom)

## 🛠️ Prerequisites

- **Java 25** (if running locally without Docker)
- **Docker** & **Docker Compose**
- **PostgreSQL** (if running locally)
- **Google Gemini API Key**

## 🏃‍♂️ Running with Docker (Recommended)

You don't need to install Java 25 on your machine to run this project. Docker handles everything.

### 1. Build the Docker Image

We use a Multi-stage build process to ensure a small and secure image using `eclipse-temurin:25`.

```bash
docker build -t grimoire-svc:latest .
```

## ⚙️ Configuration Reference

The application can be configured using the following environment variables.

| Environment Variable | Description | Default Value |
|----------------------|-------------|---------------|
| `DB_HOST` | Database host address | `localhost` |
| `DB_PORT` | Database port | `5430` |
| `DB_NAME` | Database name | `postgres` |
| `DB_SCHEMA_NAME` | Database schema name | `grimoire` |
| `DB_USERNAME` | Database username | `admin` |
| `DB_PASSWORD` | Database password | `admin` |
| `DB_POOL_MAX` | Maximum connection pool size | `20` |
| `DB_POOL_MIN` | Minimum idle connections | `5` |
| `DB_TIMEOUT` | Connection timeout (ms) | `30000` |
| `SHOW_SQL` | Enable Hibernate SQL logging | `true` |
| `GEMINI_API_KEY` | **Required.** Google Gemini API Key | - |


## 🏃‍♂️ Running with Docker (Recommended)

You don't need to install Java 25 on your machine to run this project. Docker handles everything.

### 1. Build the Docker Image

We use a Multi-stage build process to ensure a small and secure image using `eclipse-temurin:25`.

```bash
docker build -t grimoire-svc:latest .
```


