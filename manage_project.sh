#!/bin/bash

# Function to stop and clean up Docker containers and images related to your app
clean_up() {
  echo "Stopping and removing containers for the app..."
  docker-compose down --volumes

  echo "Removing Docker images related to the app..."
  docker rmi -f user-service:latest task-service:latest notification-service:latest mysql:8 rabbitmq:3-management

  echo "Cleanup completed."
}

# Function to build and start Docker Compose services
build_and_start() {
  echo "Building and starting Docker Compose services..."
  docker-compose build
  docker-compose up -d
  echo "Services are up and running."
}

# Function to stop all running Docker Compose services
stop_services() {
  echo "Stopping all running services..."
  docker-compose down --volumes
  echo "Services stopped."
}

# Menu options
echo "Choose an option:"
echo "1. Run (Clean, Rebuild, and Start Services)"
echo "2. Stop (Stop and Remove Services)"
read -p "Enter your choice (1 or 2): " choice

case $choice in
  1)
    clean_up
    build_and_start
    ;;
  2)
    stop_services
    ;;
  *)
    echo "Invalid option. Please enter 1 or 2."
    ;;
esac

