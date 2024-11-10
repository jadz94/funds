#!/bin/bash

cd ./funds-backend

if ! command -v mvn &> /dev/null
then
    echo "Maven could not be found. Please install Maven to continue."
    exit 1
fi

# Run Maven build
echo "Building the application ..."

if ! mvn clean install; then
    echo "Maven build failed. Please check the output for errors."
    exit 1
fi

echo "Maven build completed successfully."

echo "Building application docker image"
docker build -t funds/funds-backend .

echo "Docker image build completed successfully."

echo "Starting db and application"
docker-compose -f ../infra/docker-compose.yaml up