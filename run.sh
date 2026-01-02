#!/bin/bash
set -e

# Create bin directory if it doesn't exist
mkdir -p bin

# Compile the project
echo "Compiling..."
javac -d bin -cp "lib/*" -sourcepath src src/ui/MainFrame.java

# Run the application
echo "Running ATM Simulation..."
java -cp "bin:lib/*" ui.MainFrame
