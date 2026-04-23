import React, { useState } from "react";
import ExpenseForm from "./components/ExpenseForm";
import ExpenseList from "./components/ExpensesList";

function App() {
  const [refreshKey, setRefreshKey] = useState(0);

  const handleSuccess = () => {
    setRefreshKey((prev) => prev + 1);
  };

  return (
    <div style={{ padding: "20px" }}>
      <h2>Expense Tracker</h2>

      <ExpenseForm onSuccess={handleSuccess} />
      <ExpenseList key={refreshKey} />
    </div>
  );
}

export default App;

