import React, { useEffect, useState } from "react";
import axios from "axios";
import "bootstrap/dist/css/bootstrap.min.css";
import "./App.css";

function App() {
  // State for todo list
  const [todos, setTodos] = useState([]);

  // States for todo input and status
  const [newTodoItem, setNewTodoItem] = useState("");
  const [newTodoStatus, setNewTodoStatus] = useState("PENDING"); // Default to "PENDING"

  // States for user details
  const [userName, setUserName] = useState("");
  const [userEmail, setUserEmail] = useState("");

  // States for due time and duration (in minutes)
  const [dueTime, setDueTime] = useState("");
  const [durationInMinutes, setDurationInMinutes] = useState("");

  // Fetch todos from the backend
  const fetchTodos = async () => {
    try {
      const response = await axios.get("/api/todos");
      setTodos(response.data);
    } catch (error) {
      console.error("Error fetching todos:", error);
    }
  };

  useEffect(() => {
    fetchTodos();
  }, []);

  // Add a new todo with user details, due time, and/or duration in minutes
  const addTodo = async (e) => {
    e.preventDefault();
    try {
      const newTodo = {
        todoItem: newTodoItem,
        completed: newTodoStatus,
        userName,
        userEmail,
        // Pass the dueTime as an ISO string if provided
        dueTime: dueTime ? new Date(dueTime).toISOString() : null,
        // Pass duration in minutes as entered (string)
        durationInMinutes,
      };
      await axios.post("/api/todos", newTodo);

      // Reset fields after submission
      setNewTodoItem("");
      setNewTodoStatus("PENDING");
      setUserName("");
      setUserEmail("");
      setDueTime("");
      setDurationInMinutes("");
      fetchTodos();
    } catch (error) {
      console.error("Error adding todo:", error);
    }
  };

  // Toggle the status of a todo
  const toggleTodo = async (id) => {
    try {
      await axios.put(`/api/todos/${id}`);
      fetchTodos();
    } catch (error) {
      console.error("Error toggling todo:", error);
    }
  };

  // Delete a todo
  const deleteTodo = async (id) => {
    try {
      await axios.delete(`/api/todos/${id}`);
      fetchTodos();
    } catch (error) {
      console.error("Error deleting todo:", error);
    }
  };

  return (
    <div className="container mt-5">
      <h2 className="text-center mb-4">Todo Manager</h2>
      {/* Form to add a new todo */}
      <form onSubmit={addTodo} className="mb-4">
        <div className="row g-3 align-items-center">
          {/* User Name Field */}
          <div className="col-md-4">
            <input
              type="text"
              className="form-control"
              placeholder="Your Name"
              value={userName}
              onChange={(e) => setUserName(e.target.value)}
              required
            />
          </div>
          {/* User Email Field */}
          <div className="col-md-4">
            <input
              type="email"
              className="form-control"
              placeholder="Your Email"
              value={userEmail}
              onChange={(e) => setUserEmail(e.target.value)}
              required
            />
          </div>
          {/* Duration Field (in minutes, Optional) */}
          <div className="col-md-4">
            <input
              type="number"
              className="form-control"
              placeholder="Duration (minutes) - Optional"
              value={durationInMinutes}
              onChange={(e) => setDurationInMinutes(e.target.value)}
            />
          </div>
          {/* OR Due Date/Time Field (Optional) */}
          <div className="col-md-4">
            <input
              type="datetime-local"
              className="form-control"
              placeholder="Due Date/Time (Optional)"
              value={dueTime}
              onChange={(e) => setDueTime(e.target.value)}
            />
          </div>
          {/* Todo Item Field */}
          <div className="col-md-6">
            <input
              type="text"
              className="form-control"
              placeholder="Enter Todo"
              value={newTodoItem}
              onChange={(e) => setNewTodoItem(e.target.value)}
              required
            />
          </div>
          {/* Todo Status Field */}
          <div className="col-md-3">
            <select
              className="form-select"
              value={newTodoStatus}
              onChange={(e) => setNewTodoStatus(e.target.value)}
            >
              <option value="DONE">Done</option>
              <option value="PENDING">Pending</option>
            </select>
          </div>
          {/* Submit Button */}
          <div className="col-md-3">
            <button type="submit" className="btn btn-primary w-100">
              Add Todo
            </button>
          </div>
        </div>
      </form>

      {/* Display the todos in a table */}
      <table className="table table-bordered">
        <thead className="table-light">
          <tr>
            <th scope="col">No.</th>
            <th scope="col">Todo</th>
            <th scope="col">Status</th>
            <th scope="col">Due Time</th>
            <th scope="col">Toggle</th>
            <th scope="col">Delete</th>
          </tr>
        </thead>
        <tbody>
          {todos && todos.length > 0 ? (
            todos.map((todo, index) => (
              <tr key={todo.id}>
                {/* Serial number (index + 1) */}
                <td>{index + 1}</td>
                <td>{todo.todoItem}</td>
                <td>{todo.completed}</td>
                <td>
                  {todo.dueTime
                    ? new Date(todo.dueTime).toLocaleString()
                    : "N/A"}
                </td>
                <td>
                  <button
                    className="btn btn-warning btn-sm"
                    onClick={() => toggleTodo(todo.id)}
                  >
                    Toggle
                  </button>
                </td>
                <td>
                  <button
                    className="btn btn-danger btn-sm"
                    onClick={() => deleteTodo(todo.id)}
                  >
                    Delete
                  </button>
                </td>
              </tr>
            ))
          ) : (
            <tr>
              <td colSpan="6" className="text-center">
                No todos found.
              </td>
            </tr>
          )}
        </tbody>
      </table>
    </div>
  );
}

export default App;
