# Smart RGB Table Lamp Server 💡

Secure Java-based backend server for a smart RGB lamp with a built-in web interface. This server manages lamp states, colors, and patterns, and communicates with microcontroller-based hardware (ESP32/Arduino).

## 🛠 Tech Stack
* **Language:** Java 21
* **Framework:** Spring Boot 3.x
* **Security:** Spring Security (Form-based & API authentication)
* **Database:** PostgreSQL 15
* **DevOps:** Docker, Docker Compose

![Работа RGB лампы](assets/demonstration.gif)


## 🚀 Quick Start (Docker)
You don't need to install Java or Maven on your system. The entire infrastructure can be launched with a single command.

1. Clone the repository and navigate to the project root.
2. Create a `.env` file based on the template:
   ```bash
   cp .env.example .env
   ```
3. Open `.env` and fill in your database credentials and application password (`DEF_PASS`).
4. Run the application via Docker Compose:
   ```bash
   docker compose up --build -d
   ```
5. Open your browser and navigate to `http://localhost:8080` to access the Web UI.

## 🔐 Security & Features
* **Role-Based Access Control:** Secured web interface endpoints using Spring Security.
* **Database Persistence:** user configuration is safely stored in PostgreSQL.
* **Hardware Ready:** Provides API endpoints for ESP32/Arduino to fetch current lamp settings in real-time.
