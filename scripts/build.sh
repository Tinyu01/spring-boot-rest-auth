#!/bin/bash

# This script automates the build process for the application.

# Exit immediately if a command exits with a non-zero status
set -e

# Clean previous builds
./mvnw clean

# Build the project
./mvnw package

echo "Build completed successfully."