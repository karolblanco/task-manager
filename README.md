# 🗂️ Task Manager API

A simple yet extensible **Task Management REST API** built with **Spring Boot**, **JPA**, and **Gradle**.  
It allows you to create, update, delete, and retrieve tasks with filtering, pagination, and sorting.

---

## 🚀 Features
- ➕ **Create** new tasks with validation.
- 📖 **Retrieve** tasks with filters by title, status, and due date.
- 🔍 **Case-insensitive search** for task titles.
- 📅 **Pagination & sorting** (default sorted by nearest due date).
- ✏️ **Update** existing tasks.
- 🗑️ **Delete** tasks.
- 📄 **Swagger UI documentation**.

---

## 💻 Tech Stack
- ☕ **Java 17**
- 🌱 **Spring Boot**
- 🗃️ **Spring Data JPA**
- 🧭 **Swagger / OpenAPI**
- 📦 **Gradle**
- 🛢 **PostgreSQL** 
- 🧪 **JUnit & Mockito**

---

## 🌐 API Endpoints

| Method | Endpoint             | Description                             |
|--------|----------------------|---------------------------------------- |
| `POST` | `/api/tasks`         | Create a new task                       |
| `GET`  | `/api/tasks`         | Retrieve all tasks (filters & sort)     |
| `GET`  | `/api/tasks/{title}`    | Retrieve task by title                  |
| `PUT`  | `/api/tasks/{id}`    | Update an existing task                 |
| `DELETE` | `/api/tasks/{id}`  | Delete a task                           |

---

## 🔍 Example GET Request with Filters
```http
GET /api/tasks?title=meeting&status=COMPLETED&page=0&size=5&sort=dueDate,asc


