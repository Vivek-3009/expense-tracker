# Expense Tracker Frontend

## 🔗 Live Application

https://expense-tracker-six-lyart-83.vercel.app

---

## 📌 Overview

This is the frontend for the Expense Tracker application.
It provides a simple and responsive UI to create, view, filter, and analyze personal expenses by interacting with the backend API.

---

## 🚀 Features

* Add new expense (amount, category, description, date)
* View list of expenses
* Filter expenses by category
* Sort expenses by date (newest first)
* Display total amount of visible expenses
* Handles loading and error states
* Retry-safe submission using **Idempotency-Key**

---

## 🧠 Key Concepts

### Idempotent Requests

Each expense creation request includes a unique **Idempotency-Key**:

* Prevents duplicate entries on multiple clicks or retries
* Ensures consistency in unreliable network conditions

---

### State Management

* Managed using React Hooks (`useState`, `useEffect`)
* Automatically refreshes expense list after adding new expense

---

## 🏗️ Tech Stack

* React (Functional Components)
* JavaScript (ES6+)
* Fetch API
* UUID (for idempotency key generation)
* Deployed on Vercel

---

## ⚙️ API Configuration

The frontend communicates with the backend API:

```javascript
const API_BASE_URL = "https://expense-tracker-nt9t.onrender.com";
```

---

## 📁 Project Structure

```text
src/
├── components/
│   ├── ExpenseForm.js
│   ├── ExpenseList.js
├── api.js
├── App.js
└── index.js
```

---

## 🛠️ Setup & Run Locally

### 1. Install dependencies

```bash
npm install
```

### 2. Start the application

```bash
npm start
```

App will run at:

```text
http://localhost:3000
```

---

## ⚠️ Known Limitations

* Basic UI (focus is on functionality, not styling)
* No authentication
* Category input is free text (no predefined list)

---

## 🚀 Future Improvements

* Add category dropdown or suggestions
* Improve UI/UX design
* Add charts/analytics (expense by category)
* Add pagination for large datasets
* Better error messaging

---

## 🎯 Highlights

* Clean and minimal UI
* Real-world handling of duplicate submissions
* Proper API integration with backend
* Dynamic filtering and sorting
* Accurate total calculation

---

## 👨‍💻 Author

Vivek Singh Bisht
