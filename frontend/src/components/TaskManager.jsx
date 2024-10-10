// src/components/TaskManager.js
import React, { useState } from 'react';
import { useQuery, useMutation, useQueryClient } from 'react-query';
import { fetchTasks, createTask, updateTask, deleteTask } from '../services/taskApi';

const TaskManager = () => {
  const queryClient = useQueryClient();
  const [newTask, setNewTask] = useState('');
  const [editingTask, setEditingTask] = useState(null);

  // Fetch tasks using React Query
  const { data: tasks, isLoading, isError } = useQuery('tasks', fetchTasks);

  // Create task mutation
  const createTaskMutation = useMutation(createTask, {
    onSuccess: () => {
      queryClient.invalidateQueries('tasks');
    },
  });

  // Update task mutation
  const updateTaskMutation = useMutation(({ taskId, task }) => updateTask(taskId, task), {
    onSuccess: () => {
      queryClient.invalidateQueries('tasks');
    },
  });

  // Delete task mutation
  const deleteTaskMutation = useMutation(deleteTask, {
    onSuccess: () => {
      queryClient.invalidateQueries('tasks');
    },
  });

  const handleCreateTask = (e) => {
    e.preventDefault();
    createTaskMutation.mutate({ title: newTask , userId: "5cbb191b-0fd2-4faa-b61f-a36d3ce66e01" });
    setNewTask('');
  };

  const handleUpdateTask = (taskId) => {
    updateTaskMutation.mutate({ taskId, task: { title: editingTask } });
    setEditingTask(null);
  };

  const handleDeleteTask = (taskId) => {
    deleteTaskMutation.mutate(taskId);
  };

//   if (isLoading) return <div className="text-center text-lg">Loading...</div>;
//   if (isError) return <div className="text-center text-lg text-red-500">Error fetching tasks</div>;

  return (
    <div className="container mx-auto p-4">
      <h2 className="text-2xl font-bold mb-4 text-center">Task Manager</h2>

      {/* Create Task Form */}
      <form onSubmit={handleCreateTask} className="mb-6 flex justify-center">
        <input
          type="text"
          className="border border-gray-300 rounded p-2 w-64 mr-2"
          placeholder="New task"
          value={newTask}
          onChange={(e) => setNewTask(e.target.value)}
        />
        <button
          type="submit"
          className="bg-blue-500 hover:bg-blue-600 text-white px-4 py-2 rounded"
        >
          Add Task
        </button>
      </form>

      {/* Task List */}
      <ul className="space-y-4">
        {tasks?.map((task) => (
          <li key={task.id} className="flex justify-between items-center bg-gray-100 p-4 rounded shadow">
            {editingTask !== null && editingTask.id === task.id ? (
              <input
                type="text"
                className="border border-gray-300 rounded p-2 w-full mr-4"
                value={editingTask.title}
                onChange={(e) => setEditingTask({ ...editingTask, title: e.target.value })}
              />
            ) : (
              <span className="text-lg">{task.title}</span>
            )}

            <div className="space-x-2">
              {/* Edit Task */}
              {editingTask !== null && editingTask.id === task.id ? (
                <button
                  className="bg-green-500 hover:bg-green-600 text-white px-3 py-1 rounded"
                  onClick={() => handleUpdateTask(task.id)}
                >
                  Save
                </button>
              ) : (
                <button
                  className="bg-yellow-500 hover:bg-yellow-600 text-white px-3 py-1 rounded"
                  onClick={() => setEditingTask(task)}
                >
                  Edit
                </button>
              )}

              {/* Delete Task */}
              <button
                className="bg-red-500 hover:bg-red-600 text-white px-3 py-1 rounded"
                onClick={() => handleDeleteTask(task.id)}
              >
                Delete
              </button>
            </div>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default TaskManager;
