import React, { useEffect, useState, useRef, useCallback } from "react";
import { fetchExpenses } from "../api";

export default function ExpenseList() {
  const [expenses, setExpenses] = useState([]);
  const [category, setCategory] = useState("");
  const [sort, setSort] = useState("date_desc");
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const debounceTimer = useRef(null);

  // ✅ FIX: wrap in useCallback
  const loadExpenses = useCallback(
    async (cat = category, s = sort) => {
      setLoading(true);
      setError(null);

      try {
        const data = await fetchExpenses({ category: cat, sort: s });
        setExpenses(data || []);
      } catch (err) {
        setError(err.message || "Failed to load expenses");
        setExpenses([]);
      } finally {
        setLoading(false);
      }
    },
    [category, sort]
  );

  useEffect(() => {
    if (debounceTimer.current) {
      clearTimeout(debounceTimer.current);
    }

    debounceTimer.current = setTimeout(() => {
      loadExpenses();
    }, 500);

    return () => clearTimeout(debounceTimer.current);
  }, [category, sort, loadExpenses]);

  const handleClearFilter = () => {
    setCategory("");
  };

  const total = expenses.reduce(
    (sum, e) => sum + parseFloat(e.amount || 0),
    0
  );

  return (
    <div>
      <h3>Expenses</h3>

      {/* Controls */}
      <div style={{ marginBottom: "15px" }}>
        <input
          placeholder="Filter by category"
          value={category}
          onChange={(e) => setCategory(e.target.value)}
          style={{ padding: "8px", marginRight: "10px" }}
        />

        <select
          value={sort}
          onChange={(e) => setSort(e.target.value)}
          style={{ padding: "8px", marginRight: "10px" }}
        >
          <option value="date_desc">Newest First</option>
          <option value="date_asc">Oldest First</option>
        </select>

        {category && (
          <button onClick={handleClearFilter} style={{ padding: "8px 12px" }}>
            Clear Filter
          </button>
        )}
      </div>

      {error && <p style={{ color: "red", fontWeight: "bold" }}>{error}</p>}

      {/* List */}
      {loading ? (
        <p>Loading...</p>
      ) : expenses.length === 0 ? (
        <p style={{ fontStyle: "italic", color: "#666" }}>
          {category
            ? `No expenses found for category "${category}"`
            : "No expenses yet"}
        </p>
      ) : (
        <table border="1" style={{ width: "100%", marginBottom: "20px" }}>
          <thead>
            <tr style={{ backgroundColor: "#f5f5f5" }}>
              <th style={{ padding: "8px" }}>Amount</th>
              <th style={{ padding: "8px" }}>Category</th>
              <th style={{ padding: "8px" }}>Description</th>
              <th style={{ padding: "8px" }}>Date</th>
            </tr>
          </thead>

          <tbody>
            {expenses.map((e) => (
              <tr key={e.id}>
                <td style={{ padding: "8px" }}>
                  ₹{parseFloat(e.amount).toFixed(2)}
                </td>
                <td style={{ padding: "8px" }}>{e.category}</td>
                <td style={{ padding: "8px" }}>{e.description || "-"}</td>
                <td style={{ padding: "8px" }}>{e.date}</td>
              </tr>
            ))}
          </tbody>
        </table>
      )}

      {/* Total */}
      {expenses.length > 0 && (
        <h4>Total: ₹{total.toFixed(2)}</h4>
      )}
    </div>
  );
}