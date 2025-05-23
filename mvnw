#!/bin/sh
# Maven wrapper script for Unix-based systems

MAVEN_WRAPPER_JAR="./.mvn/wrapper/maven-wrapper.jar"

if [ ! -f "$MAVEN_WRAPPER_JAR" ]; then
  echo "Maven wrapper jar not found. Please run 'mvn -N io.takari:maven:wrapper' to generate it."
  exit 1
fi

exec java -jar "$MAVEN_WRAPPER_JAR" "$@"