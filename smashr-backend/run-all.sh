#!/bin/bash

# List all your runnable modules here
MODULES=("user" "game-manager" "table-manager")

echo "Starting all modules..."

for MODULE in "${MODULES[@]}"
do
  echo "Starting $MODULE..."
  ./mvnw -pl $MODULE spring-boot:run &
  sleep 5  # Optional: wait a few seconds between starts to avoid race conditions
done

echo "All modules started."

# Wait for all background jobs so the script doesn't exit immediately
wait
