#!/bin/bash

# Base URL for the actuator API
base_url="http://localhost:9090/api/actuators"

# POST - Create a new actuator
echo "Creating a new actuator..."
curl -X POST $base_url \
-H "Content-Type: application/json" \
-d '{
    "code": "ACT001",
    "latitude": 48.8566,
    "longitude": 2.3522,
    "isIrrigating": false,
    "irrigationDuration": 0
}' -w "\n"
echo "//////////////Creating a new actuator with the same code"

curl -X POST $base_url \
-H "Content-Type: application/json" \
-d '{
    "code": "ACT001",
    "latitude": 48.8566,
    "longitude": 2.3522,
    "isIrrigating": false,
    "irrigationDuration": 0
}' -w "\n"

# GET - Retrieve all actuators
echo "Retrieving all actuators..."
curl -X GET $base_url -w "\n"

# Assuming you know the code, replace ACT001 with the actual actuator code
actuator_code="ACT001"

# GET - Retrieve an actuator by code
echo "Retrieving actuator by code..."
curl -X GET "$base_url/$actuator_code" -w "\n"

# POST - Start irrigation for an actuator
echo "Starting irrigation for an actuator..."
curl -X POST "$base_url/$actuator_code/start-irrigation?duration=3600" -w "\n"

# POST - Stop irrigation for an actuator
echo "Stopping irrigation for an actuator..."
curl -X POST "$base_url/$actuator_code/stop-irrigation" -w "\n"

# GET - Retrieve all logs for an actuator
echo "Retrieving all irrigation logs for an actuator..."
curl -X GET "$base_url/$actuator_code/logs" -w "\n"

# DELETE - Delete an actuator by code
echo "Deleting actuator by code..."
curl -X DELETE "$base_url/$actuator_code" -w "\n"
