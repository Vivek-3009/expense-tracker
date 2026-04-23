const API_BASE_URL = "https://expense-tracker-nt9t.onrender.com";

export async function createExpense(payload, idempotencyKey) {
  const res = await fetch(`${API_BASE_URL}/api/v1/expenses`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      "Idempotency-Key": idempotencyKey
    },
    body: JSON.stringify(payload)
  });

  if (!res.ok) {
    const errResponse = await res.json();
    const error = new Error(errResponse.message || "Failed to create expense");
    error.status = errResponse.status;
    error.validationErrors = errResponse.errors || {};
    throw error;
  }

  return res.json();
}

export async function fetchExpenses({ category, sort }) {
  const params = new URLSearchParams();

  if (category) params.append("category", category);
  if (sort) params.append("sort", sort);

  const res = await fetch(`${API_BASE_URL}/api/v1/expenses?${params.toString()}`);

  if (!res.ok) {
    throw new Error("Failed to fetch expenses");
  }

  return res.json();
}