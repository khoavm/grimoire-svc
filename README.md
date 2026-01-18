# Grimoire Service 🔮

Grimoire Service is a backend application built with **Spring Boot 3.5.x** and **Java 25**, designed to provide AI-powered quest grading and management features using Google Gemini.

## 🚀 Tech Stack

- **Language:** Java 25
- **Framework:** Spring Boot 3.5.x (Snapshot)
- **Database:** PostgreSQL
- **Testing:** JUnit 5, Mockito, MockMvc
- **API Documentation:** SpringDoc OpenAPI, Swagger UI
- **AI Integration:** Spring AI (Google Gemini)
- **Resilience:** Resilience4j (Circuit Breaker)
- **Observability:** Micrometer Tracing (Brave), Log4j2
- **Concurrency:** Java Virtual Threads (Project Loom)

## 🛠️ Prerequisites

- **Java 25** (if running locally without Docker)
- **Docker** & **Docker Compose**
- **PostgreSQL** (if running locally)
- **Google Gemini API Key**



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


## 🏃‍♂️ Run Application

You can run the application either directly on your machine (Normal) or using Docker.

### Option 1: Run Locally (Normal)
*Best for development and debugging.*

**Prerequisites:**
* [JDK 25](https://adoptium.net/) installed.
* Gradle (optional, wrapper provided).

**1. Set up Environment Variables**
The application requires a Google Gemini API Key.

```bash
# Mac/Linux
export GEMINI_API_KEY=your_actual_key_here

# Windows (CMD)
set GEMINI_API_KEY=your_actual_key_here

# Windows (PowerShell)
$env:GEMINI_API_KEY="your_actual_key_here"
```

### Option 2: Running with Docker (Recommended)
You don't need to install Java 25 on your machine to run this project. Docker handles everything.

**1. Build the Docker Image**

We use a Multi-stage build process to ensure a small and secure image using `eclipse-temurin:25`.

```bash
docker build -t grimoire-svc:latest .
```

**2. Start the Container**

Start the application container in detached mode. Don't forget to replace `your_actual_api_key_here` with your real Gemini API key.

```bash
docker run -d \
  -p 8080:8080 \
  -e GEMINI_API_KEY=your_actual_api_key_here \
  --name grimoire-app \
  grimoire-svc:latest
```


## 🧪 Testing

This project includes both unit tests and live integration tests.

### Unit Tests

The project contains unit tests that verify internal business logic (e.g., Services, Controllers) using mocks. These tests run in isolation, execute quickly, and do not require external API keys.

To run these tests:

```bash
./gradlew test
```

### Live Integration Tests

The project contains live tests that interact with the actual Google Gemini API (e.g., `GeminiAiServiceLiveTest`). These are tagged with `@Tag("live")`.

To run these tests, you must provide the `GEMINI_API_KEY`:

```bash
export GEMINI_API_KEY=your_key_here
./gradlew liveTest 
```

## 📚 API Documentation

The project integrates **Swagger UI** (via SpringDoc) to provide interactive API documentation. This allows you to inspect endpoints, schemas, and test requests directly from your browser.

**Access the documentation:**
* **Swagger UI:** [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
* **OpenAPI JSON Spec:** [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

*(Note: The application must be running for these links to work.)*




