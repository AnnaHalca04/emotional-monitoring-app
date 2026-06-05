# Emotional Monitoring & Analysis Application

A full-stack web application for tracking and analyzing emotional states over time. Users log daily emotions, somatic sensations, and life factors — the app then predicts a mood score and day type using machine learning models served via a Python microservice.

---

## Tech Stack

**Backend:** Java 17, Spring Boot 3, JPA/Hibernate, SQL Server  
**Frontend:** Thymeleaf, JavaScript, Canvas API  
**ML Microservice:** Python, Flask, scikit-learn  
**Database:** Microsoft SQL Server  
**Version Control:** Git

---

## Architecture

The application uses a microservice architecture where the Spring Boot backend handles user management, data persistence, and business logic, while a separate Flask microservice exposes the ML models via REST API.

```
User → Spring Boot (Java) → SQL Server
                ↓
           Flask API (Python)
                ↓
        Ridge Regression / Naive Bayes
```

---

## Features

- **Emotion Wheel** — interactive Canvas-based wheel with 21 distinct emotions
- **Somatic Body Map** — users mark physical sensations on a body diagram
- **Daily Logging** — tracks sleep, energy, stress, social interaction, and raw emotion input
- **Streak Tracking** — monitors consecutive days of logging
- **Alert System** — 21 pattern-based rules that detect concerning trends (e.g. 3+ consecutive days of low sleep)
- **Dashboard** — 7-day and 30-day charts, correlation graphs (sleep vs. mood)

---

## ML Models

Both models are trained on logged user data and served via Flask REST API.

| Model | Task | Result |
|-------|------|--------|
| Ridge Regression | Predict daily mood score (0–10) | R² = 0.9146, RMSE = 0.46 |
| Naive Bayes | Classify day type (good / neutral / difficult) | Accuracy = 78.5% |

The Naive Bayes model shows higher recall for neutral days (0.99), with lower performance on good/difficult days — expected given the class imbalance in synthetic training data (64% neutral).

---

## Database

- ~30 users, tracked over several months
- Entities: User, DailyLog, Emotion, BodyMap, Alert, Streak

---

## Setup

### Prerequisites
- Java 17+
- Python 3.10+
- SQL Server (or Docker image)
- Maven

### Run the Flask microservice
```bash
cd ml-service
pip install -r requirements.txt
python app.py
```

### Run the Spring Boot app
```bash
mvn spring-boot:run
```

Make sure SQL Server is running and update `application.properties` with your connection string.

---

## Project Status

Academic project — developed as part of the Multimedia Applications course at Politehnica University of Bucharest (2025–2026).
