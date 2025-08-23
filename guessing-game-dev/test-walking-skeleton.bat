@echo off
echo ========================================
echo Testing Walking Skeleton
echo ========================================

echo.
echo 1. Compiling the project...
call mvnw.cmd clean compile

echo.
echo 2. Running tests...
call mvnw.cmd test

echo.
echo 3. Starting the application...
echo Starting Spring Boot application on port 8080...
echo You can test the API at: http://localhost:8080/api/hello?name=Johnny
echo Press Ctrl+C to stop the application
echo.

call mvnw.cmd spring-boot:run
