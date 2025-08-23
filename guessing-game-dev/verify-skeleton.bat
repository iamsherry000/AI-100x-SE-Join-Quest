@echo off
echo ========================================
echo Verifying Walking Skeleton
echo ========================================

echo.
echo 1. Checking project structure...
if exist "src\main\java\tw\waterballsa\GuessingGameApplication.java" (
    echo ✓ Main application class exists
) else (
    echo ✗ Main application class missing
    exit /b 1
)

if exist "src\main\java\tw\waterballsa\example\HelloWorldController.java" (
    echo ✓ Controller exists
) else (
    echo ✗ Controller missing
    exit /b 1
)

if exist "src\main\java\tw\waterballsa\example\HelloWorldService.java" (
    echo ✓ Service exists
) else (
    echo ✗ Service missing
    exit /b 1
)

if exist "src\main\java\tw\waterballsa\example\HelloWorldRepository.java" (
    echo ✓ Repository exists
) else (
    echo ✗ Repository missing
    exit /b 1
)

if exist "src\test\java\tw\waterballsa\example\HelloWorldEndToEndTest.java" (
    echo ✓ Test class exists
) else (
    echo ✗ Test class missing
    exit /b 1
)

echo.
echo 2. Compiling project...
call mvnw.cmd clean compile -q
if %errorlevel% equ 0 (
    echo ✓ Compilation successful
) else (
    echo ✗ Compilation failed
    exit /b 1
)

echo.
echo 3. Running tests...
call mvnw.cmd test -q
if %errorlevel% equ 0 (
    echo ✓ Tests passed
) else (
    echo ✗ Tests failed
    exit /b 1
)

echo.
echo ========================================
echo Walking Skeleton Verification Complete!
echo ========================================
echo.
echo You can now:
echo 1. Run the application: mvnw.cmd spring-boot:run
echo 2. Test the API: http://localhost:8080/api/hello?name=Johnny
echo 3. View H2 console: http://localhost:8080/h2-console
echo.
