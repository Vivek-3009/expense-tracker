# Expense Tracker

## 🔗 Live Demo

* Frontend: https://expense-tracker-six-lyart-83.vercel.app
* Backend API: https://expense-tracker-nt9t.onrender.com

---

## 📌 Features

* Create a new expense (amount, category, description, date)
* View list of expenses
* Filter expenses by category
* Sort expenses by date (newest first)
* Display total of visible expenses
* Retry-safe API using **Idempotency-Key**
* Input validation with meaningful error responses

---

## 🏗️ Tech Stack

### Backend

* Java 17
* Spring Boot
* Spring Data JPA
* H2 Database (in-memory)
* Bean Validation (Jakarta Validation)

### Frontend

* React (Functional Components + Hooks)
* Fetch API
* UUID (for idempotency key)

### Deployment

* Frontend: Vercel
* Backend: Render

---

## 🧠 Design Decisions

### 1. Idempotency Handling

To handle unreliable networks and retries, the backend uses an **Idempotency-Key**:

* Same request + same key → returns existing expense
* Same key + different payload → returns **409 Conflict**
* Prevents duplicate data creation

---

### 2. Money Handling

Used `BigDecimal` for the `amount` field:

* Avoids floating-point precision issues
* Suitable for financial applications

---

### 3. DTO + Validation

* Used **Java records** for DTOs (immutable & clean)
* Applied validation annotations (`@NotNull`, `@DecimalMin`, etc.)
* Ensures invalid data never reaches business logic

---

### 4. Error Handling

* Implemented a **Global Exception Handler**
* Standardized error response format:

```json
{
  "timestamp": "...",
  "status": 400,
  "message": "Validation failed",
  "errors": {
    "amount": "Amount must be greater than zero"
  }
}
```

---

### 5. Category Design

* Used `String` instead of enum
* Keeps system flexible (real-world categories are dynamic)

---

## ⚖️ Trade-offs

* Used **H2 in-memory database** for simplicity

  * Data resets on application restart
* No authentication/authorization implemented
* UI kept minimal (focus on correctness over styling)
* Pagination not implemented (not required in assignment)

---

## 🚀 Future Improvements

* Persistent database (PostgreSQL/MySQL)
* Pagination for large datasets
* Category management (dynamic categories)
* Better UI/UX (charts, dashboards)
* Authentication & user accounts
* Caching frequently accessed data
* API documentation (Swagger/OpenAPI)

---

## 🧪 Testing

Added targeted tests for:

* Idempotency behavior (duplicate request prevention)
* Validation (invalid inputs return 400)

---

## 🛠️ How to Run Locally

### Backend

```bash
mvn clean install
mvn spring-boot:run
```

Backend runs at:

```text
http://localhost:8080
```

---

### Frontend

```bash
npm install
npm start
```

Frontend runs at:

```text
http://localhost:3000
```

---

## 📁 Project Structure

expense-tracker/
├── backend/
│   ├── controller/
│   ├── service/
│   ├── repository/
│   ├── entity/
│   ├── dto/
│   └── exception/
├── frontend/
│   ├── src/
│   │   ├── components/
│   │   ├── api.js
│   │   ├── App.js
│   │   └── index.js
└── README.md

---

## 🎯 Key Highlights

* Idempotent API design (production-grade concept)
* Proper financial data handling using BigDecimal
* Clean separation of concerns (DTO vs Entity)
* Global error handling with consistent responses
* Full-stack deployment (React + Spring Boot)

---

## 👨‍💻 Author

Vivek Singh Bisht

---
