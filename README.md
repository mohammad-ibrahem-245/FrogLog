# 🐸 FrogLog

> A microservices-based project management and collaboration platform built with Java and Spring.  
> Services cover users, projects, tasks, and friendships, with inter-service communication via Feign and HTTP.

---

## 🧭 Table of Contents
1. Overview
2. Architecture and Modules
3. Tech Stack
4. Features
5. Configuration
6. API Endpoints
   - User API
   - Project API
   - Task API
   - Friendship API
   - Gateway notes
7. Data Models (from code references)
8. Usage Flow Examples
9. Docker and Deployment
10. Author

---

## 🚀 Overview
FrogLog is organized as a set of Java/Spring microservices that together provide:
- User registration, profile updates, and account lifecycle
- Project creation, editing, membership management
- Task search and retrieval
- Social features (friend requests, friendship list, unfriend, friendship search)

Emphasis is placed on clear service boundaries, simple inter-service communication, and portability via Docker.

---

## 🧩 Architecture and Modules
The repository is structured into multiple services (modules). Key modules observed in the codebase include:

- Gateway  
  Purpose: Front-door service for clients; can orchestrate requests to downstream services.  
  Notable usage: Uses Spring WebClient to call user-api for signup in example code.

- User-api  
  Purpose: Manages SiteUser entities (signup, update, delete, lookup, random suggestions).  
  Integrations: Calls Project-api via Feign for removing members when a user leaves a project.

- Project-api  
  Purpose: Manages projects (create, delete, edit), fetch by name, and membership operations (add/remove members).

- Task-api  
  Purpose: Exposes creation, editing, deletion, and search endpoints for tasks (with ownership validation via associated project).

- Friendship-api  
  Purpose: Friendship requests and state transitions (add, answer), friend listing, unfriend, and friendship search.

Notes:
- Service names in Feign and HTTP requests imply service discovery or Docker DNS names (e.g., user-api, project-api).
- Gateway example currently hardcodes base URL `http://user-api:8080`.

---

## 🛠 Tech Stack
Based on code references and repository composition:

- Language: Java (primary)
- Frameworks and Libraries:
  - Spring Boot (web applications and REST controllers)
  - Spring Web (REST controllers)
  - Spring WebFlux WebClient (Gateway outbound HTTP)
  - Spring Data JPA (repositories, ORM)
  - Spring Cloud OpenFeign (inter-service HTTP clients)
  - BCrypt (password hashing)
- Build: Maven
- Containerization: Docker (Dockerfile present)
- Persistence: JPA repositories (database specifics depend on configuration)

---

## ✅ Features
- User management (signup, update, delete, search, random suggestions)
- Project lifecycle (create, edit, delete, membership management)
- Task CRUD & search operations
- Friendship requests, acceptance/rejection, listing, removal, targeted search
- Gateway coordination
- Password hashing for security

---

## ⚙ Configuration
Typical Spring configuration values (adjust per service):

```
server.port=8080
spring.application.name=user-api
spring.jpa.hibernate.ddl-auto=update
spring.datasource.url=jdbc:postgresql://localhost:5432/froglog_user
spring.datasource.username=postgres
spring.datasource.password=postgres
```

Additional:
- Feign: Service names must resolve (Docker network or discovery)
- Headers: Many modifying endpoints rely on `X-User-Name` for authorization

---

## 📡 API Endpoints

Important header convention:
- Mutating endpoints typically validate identity using: `X-User-Name: <username>`

### 1) User API
Source: `UserController`

| Method | Path | Description | Auth Header | Body |
|--------|------|-------------|-------------|------|
| GET | /search/{username} | Fetch user by username | Optional | — |
| GET | /random | Get random users (suggestions) | — | — |
| POST | /signup | Create new user (hashes password) | — | SiteUser |
| PUT | /update | Update profile (must match userid) | X-User-Name | SiteUser |
| DELETE | /delete/{username} | Delete account (must match path) | X-User-Name | — |
| POST | /leave | Leave a project (delegates to Project-api) | X-User-Name | ProjectLeave |


### 2) Project API
Source: `ProjectController`

| Method | Path | Description | Auth Header | Body |
|--------|------|-------------|-------------|------|
| POST | /create | Create project | — | Project |
| DELETE | /delete/{name} | Delete project by name | X-User-Name | — |
| PUT | /edit/{name} | Edit project by name | X-User-Name | Project |
| GET | /project/{name} | Get project by name | — | — |
| PUT | /addmember | Add member to project | X-User-Name | ProjectLeave |
| PUT | /delmember | Remove member from project | X-User-Name | ProjectLeave |
| GET | /userprojects/{name} | Get projects by owner or member | — | — |

### 3) Task API
Source: `TaskController`

All task operations validate ownership through the related project’s owner (via Feign `ProjectClient`).

| Method | Path | Description | Auth Header | Body |
|--------|------|-------------|-------------|------|
| POST | /create | Create a task (sets status TODO, owner, timestamps) | X-User-Name | Task |
| PUT  | /edit | Edit an existing task (marks edited flag) | X-User-Name | Task |
| DELETE | /delete/{task} | Delete task by ID | X-User-Name | — |
| GET | /gettasks | Search tasks by project or assignee (uses `SearchBy` object; unconventional GET body) | X-User-Name | SearchBy |


### 4) Friendship API
Source: `FriendShipController`

| Method | Path | Description | Auth Header | Body |
|--------|------|-------------|-------------|------|
| POST | /add | Send friendship request | X-User-Name | Request |
| GET | /friendshipsrequests/{username} | List pending requests | X-User-Name | — |
| PUT | /answer | Accept/Reject friendship request | X-User-Name | RequestAnswer |
| GET | /allfriends/{username} | List all friends for user | X-User-Name | — |
| DELETE | /deletefriend | Remove friend (bidirectional delete) | X-User-Name | DeleteRequest |
| GET | /search | Search specific friendship relation (uses body) | X-User-Name | Request |

Authorization logic:
- Service methods enforce header matches on involved usernames.

### 5) Gateway notes (Gateway API Endpoints)

Source: `GatewayService` and Feign interface `UserClient`.

Currently exposed (or implementable) via Gateway layer:

| Method | Path | Description | Forwarded To | Body |
|--------|------|-------------|--------------|------|
| POST | /signup | Proxy user signup to User-api | user-api `/signup` | SiteUser |
| POST  | /login | login user and create a jwt  | user-api `/search/{username}` | — |

Behavior:
- WebClient used for `/signup` call (`http://user-api:8080/signup`).
- Feign client (`@FeignClient(name = "user-api")`) declares `/search/{username}`, `/signup`.

If Spring Cloud Gateway routing is added, document route predicates, filters, and any global authentication middleware here.

---

## 🧾 Data Models (inferred)

- SiteUser:
  - userid, password (hashed at signup), name, bio, image, career, dateOfBirth, created
- Project:
  - name plus metadata (see code for full structure)
- ProjectLeave:
  - USER_ID, project identifiers (used for membership add/remove and leave)
- Task:
  - id, title, projectName, owner, assignee, status (TaskStatus: e.g., TODO), createdDate, edited flag, dueDate (if present), etc.
- SearchBy:
  - projectName, assignee, status list (for filtering)
- Friendship-related:
  - Request (sender, receiver)
  - RequestAnswer (sender, receiver, status)
  - Friendship (siteUser, friend)
  - DeleteRequest (siteUser, friend)
  - RequestStatus enum: PENDING, ACCEPTED, REJECTED

---

## 🔎 Usage Flow Examples

- User signup → POST /signup  
- Look up user → GET /search/{username}  
- Update profile → PUT /update (header must match)  
- Delete user → DELETE /delete/{username} (header match)  
- Random suggestions → GET /random  
- Create project → POST /create  
- Edit/Delete project → PUT /edit/{name}, DELETE /delete/{name}  
- Manage project members → PUT /addmember /delmember  
- Leave project → POST /leave (User-api delegates to Project-api)  
- Create task → POST /create (Task-api)  
- Edit task → PUT /edit  
- Delete task → DELETE /delete/{task}  
- Search tasks → GET /gettasks (body: SearchBy)  
- Friendship lifecycle → /add → /friendshipsrequests/{username} → /answer → /allfriends/{username} → /deletefriend  
- Friendship search → GET /search (body: Request)  

---

## 🐳 Docker and Deployment

- Build each service:
  - `mvn clean package -DskipTests`
- Sample Dockerfile:
  ```
  FROM openjdk:25-jdk
  WORKDIR /app
  COPY target/*.jar app.jar
  ENTRYPOINT ["java","-jar","/app/app.jar"]
  ```
- Build images and run containers:
  ```
  docker compose up --build
  ```
- Run containers on a shared network (or Compose) so service names resolve (e.g., `user-api`, `project-api`).

---

## 👤 Author

- Name: Mohammad Ali Ibrahim  
- Role: Software Engineering Student  
- Location: Jordan  
- GitHub: [mohammad-ibrahem-245](https://github.com/mohammad-ibrahem-245)

---
