#!/bin/bash

# This script automates the testing process for the application.

# Run the tests using Maven
./mvnw test

# Check the exit status of the test command
if [ $? -eq 0 ]; then
  echo "All tests passed successfully."
else
  echo "Some tests failed. Please check the output above."
fi