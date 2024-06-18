#!/bin/bash

# Base URL for the sensor API
base_url="http://localhost:8090/api/sensors"

# POST - Create a new sensor
echo "Creating a new sensor..."
curl -X POST $base_url \
-H "Content-Type: application/json" \
-d '{
    "code": "SENSOR001",
    "latitude": 48.8566,
    "longitude": 2.3522,
    "temperature": 22.5,
    "humidity": 45.0,
    "lastUpdated": "2023-06-18T15:03:00",
    "measurementInterval": 10
}' -w "\n"

curl -X POST $base_url \
-H "Content-Type: application/json" \
-d '{
    "code": "SENSOR002",
    "latitude": 48.8566,
    "longitude": 2.3522,
    "temperature": 22.5,
    "humidity": 45.0,
    "lastUpdated": "2023-06-18T15:03:00",
    "measurementInterval": 10
}' -w "\n"

# GET - Retrieve all sensors
echo "Retrieving all sensors..."
curl -X GET $base_url -w "\n"

# Assuming you know the ID, replace {id} with actual sensor ID
sensor_id="your_sensor_id_here"

# GET - Retrieve a sensor by ID
echo "Retrieving a sensor by ID..."
curl -X GET "$base_url/$sensor_id" -w "\n"

# PUT - Update a sensor by ID
echo "Updating a sensor by ID..."
curl -X PUT "$base_url/$sensor_id" \
-H "Content-Type: application/json" \
-d '{
    "code": "SENSOR001",
    "latitude": 48.8566,
    "longitude": 2.3522,
    "temperature": 23.0,
    "humidity": 50.0,
    "lastUpdated": "2023-06-18T16:00:00",
    "measurementInterval": 5
}' -w "\n"

# DELETE - Delete a sensor by ID
echo "Deleting a sensor by ID..."
curl -X DELETE "$base_url/$sensor_id" -w "\n"
