@echo off
setlocal

:: Maven Wrapper configuration
set MAVEN_WRAPPER_JAR=.mvn\wrapper\maven-wrapper.jar
set MAVEN_WRAPPER_PROPERTIES=.mvn\wrapper\maven-wrapper.properties

:: Check if the Maven Wrapper JAR exists
if not exist "%MAVEN_WRAPPER_JAR%" (
    echo Maven Wrapper JAR not found. Please run 'mvn -N io.takari:maven:wrapper' to generate it.
    exit /b 1
)

:: Execute the Maven Wrapper
java -jar "%MAVEN_WRAPPER_JAR%" %*

endlocal