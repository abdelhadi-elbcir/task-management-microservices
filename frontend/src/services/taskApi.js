// src/taskApi.js
import api from './api';

// Get all tasks
export const fetchTasks = async () => {
  const response = await api.get('/task-service/api/tasks');
  return response.data;
};

// Create a new task
export const createTask = async (task) => {
  const response = await api.post('/task-service/api/tasks', task);
  return response.data;
};

// Update an existing task
export const updateTask = async (taskId, task) => {
  const response = await api.put(`/task-service/api/tasks/${taskId}`, task);
  return response.data;
};

// Delete a task
export const deleteTask = async (taskId) => {
  const response = await api.delete(`/task-service/api/tasks/${taskId}`);
  return response.data;
};
