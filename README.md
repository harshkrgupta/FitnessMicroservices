FitnessMicroservices

A multi-service fitness platform built with Spring Boot microservices architecture. It handles user management, activity tracking, and AI-powered fitness recommendations, with service discovery, centralized configuration, and a secure API gateway.

Architecture

The system is split into independently deployable services:

Service	Responsibility
ConfigServer	Centralized configuration management for all services
Eureka	Service discovery / registry
Gateway	API gateway (Spring Cloud Gateway, reactive/WebFlux) — single entry point for all client requests
userservice	User registration, authentication, and profile management
ActivityMicroservice	Tracks and manages user fitness activities
aiservice	AI-powered fitness recommendations (Gemini API integration)
fitness-app-frontend	Frontend client for the platform
Tech Stack
Backend: Java, Spring Boot, Spring Cloud (Gateway, Config, Eureka)
Databases: MongoDB, PostgreSQL
Messaging: RabbitMQ (Spring AMQP)
Auth: Keycloak (OAuth2 / PKCE)
AI: Gemini API
Frontend: (add framework here, e.g. React/Vite)
Getting Started
Prerequisites
Java 17+ (or your project's JDK version)
Maven
MongoDB and PostgreSQL instances running
RabbitMQ instance running
Keycloak instance configured for OAuth2/PKCE
A Gemini API key (for aiservice)
Running the services

Start the services in this order so dependent services can register/discover correctly:

bash
# 1. Config Server
cd ConfigServer && ./mvnw spring-boot:run

# 2. Eureka (service registry)
cd Eureka && ./mvnw spring-boot:run

# 3. Core services
cd userservice && ./mvnw spring-boot:run
cd ActivityMicroservice && ./mvnw spring-boot:run
cd aiservice && ./mvnw spring-boot:run

# 4. Gateway (entry point)
cd Gateway && ./mvnw spring-boot:run
Frontend
bash
cd fitness-app-frontend
npm install
npm run dev
Configuration

Each service reads sensitive configuration (database credentials, Keycloak client secrets, Gemini API key, RabbitMQ credentials) via environment variables / application.yml. Do not commit real secrets — use .env files or environment variables and keep them out of version control.

Notes
Built on Spring Boot 4.0.7 + Spring Cloud 2025.1.2.
Uses Spring Cloud Gateway's reactive (WebFlux) stack — be mindful of reactive vs. servlet dependency conflicts when adding new dependencies.
