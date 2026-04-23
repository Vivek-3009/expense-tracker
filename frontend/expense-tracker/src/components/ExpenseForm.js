import React, { useState } from "react";
import { v4 as uuid } from "uuid";
import { createExpense } from "../api";

export default function ExpenseForm({ onSuccess }) {
  const [form, setForm] = useState({
    amount: "",
    category: "",
    description: "",
    date: ""
  });

  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [fieldErrors, setFieldErrors] = useState({});

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const getTodayDate = () => {
    const today = new Date();
    const year = today.getFullYear();
    const month = String(today.getMonth() + 1).padStart(2, '0');
    const day = String(today.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError(null);

    const idempotencyKey = uuid();

    try {
      await createExpense(
        {
          amount: Number(form.amount),
          category: form.category,
          description: form.description,
          date: form.date
        },
        idempotencyKey
      );

      setForm({ amount: "", category: "", description: "", date: "" });
      onSuccess();
    } catch (err) {
      if (err.validationErrors) {
        setFieldErrors(err.validationErrors);
      }
      setError(err.message || "Something went wrong");
    } finally {
      setLoading(false);
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <h3>Add Expense</h3>

      <div>
        <input
          name="amount"
          type="number"
          step="0.01"
          placeholder="Amount"
          value={form.amount}
          onChange={handleChange}
          required
        />
        {fieldErrors.amount && <p style={{ color: "red", fontSize: "12px", margin: "2px 0" }}>{fieldErrors.amount}</p>}
      </div>

      <div>
        <input
          name="category"
          placeholder="Category"
          value={form.category}
          onChange={handleChange}
          required
        />
        {fieldErrors.category && <p style={{ color: "red", fontSize: "12px", margin: "2px 0" }}>{fieldErrors.category}</p>}
      </div>

      <div>
        <input
          name="description"
          placeholder="Description"
          value={form.description}
          onChange={handleChange}
        />
        {fieldErrors.description && <p style={{ color: "red", fontSize: "12px", margin: "2px 0" }}>{fieldErrors.description}</p>}
      </div>

      <div>
        <input
          max={getTodayDate()}
          type="date"
          name="date"
          value={form.date}
          onChange={handleChange}
          required
        />
        {fieldErrors.date && <p style={{ color: "red", fontSize: "12px", margin: "2px 0" }}>{fieldErrors.date}</p>}
      </div>

      <button type="submit" disabled={loading}>
        {loading ? "Adding..." : "Add Expense"}
      </button>

      {error && <p style={{ color: "red", fontWeight: "bold" }}>{error}</p>}
    </form>
  );
}