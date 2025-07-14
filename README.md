# 🔐 Login Template with Database (Spring Boot + PostgreSQL)

This is a personal starter template project built using **Java 21**, **Spring Boot 3**, and **PostgreSQL**. It is designed to **simplify the initial setup of authentication features**, particularly login and registration via REST API.

It uses **Spring Security** with session-based authentication and minimal configuration to allow quick development of secured endpoints.

> ⚠️ **Note**: This project is still under development. It will be updated regularly as more features are added. See the roadmap section below.

---

## 🛠️ Tech Stack

- **Java 21**
- **Spring Boot 3.5.3**
- **Spring Security**
- **Spring Data JPA**
- **PostgreSQL**
- **HikariCP** (for connection pooling)
- **Lombok**

---

## ✨ Features

- ✅ Register with email and username (both must be unique)
- ✅ Login using username and password (session-based auth)
- ✅ Passwords are hashed using BCrypt
- ✅ Secure logout with JSON response
- ✅ Minimal configuration for fast setup
- ✅ Uppercase, lowercase, symbol, number, and minimal character

---

## 🔐 API Endpoints
- Method	Endpoint	Description
- POST	/register	Register a new user
- POST	/login		Login using email+username+pass
- POST	/logout		Logout user (clears session)

## Sample Register Payload:

```json
{
  "username": "johndoe",
  "email": "johndoe@example.com",
  "password": "securePassword123#"
}
```
## The following features are planned and will be added gradually:
- Role-based access control
- JWT authentication support
- User profile endpoint
- Password reset (via token or email)
- Email verification (optional)

## 🧪 Tips for Testing
1. Use Postman or curl to test login and registration.
2. Session-based login will store authentication in HTTP session, so make sure to retain cookies if using Postman.

## 🙏 Contributing
This is a personal project, but feel free to fork or clone if you'd like to build on it.

## 📝 Note
This project is still a work in progress.
It will be updated regularly as new features are added, including advanced authentication mechanisms and personal integrations.
