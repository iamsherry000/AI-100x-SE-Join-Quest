@echo off
echo ========================================
echo Verifying Feature 1 Test Template
echo ========================================

echo.
echo 1. Checking test template structure...
if exist "src\test\java\tw\waterballsa\game\GameFeature1Test.java" (
    echo ✓ Feature 1 test class exists
) else (
    echo ✗ Feature 1 test class missing
    exit /b 1
)

if exist "src\test\java\tw\waterballsa\game\TestGroup.java" (
    echo ✓ TestGroup annotation exists
) else (
    echo ✗ TestGroup annotation missing
    exit /b 1
)

if exist "src\main\java\tw\waterballsa\game\Game.java" (
    echo ✓ Game entity exists
) else (
    echo ✗ Game entity missing
    exit /b 1
)

if exist "src\main\java\tw\waterballsa\game\GameController.java" (
    echo ✓ Game controller exists
) else (
    echo ✗ Game controller missing
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
echo 3. Running tests (should pass with commented tests)...
call mvnw.cmd test -q
if %errorlevel% equ 0 (
    echo ✓ Tests passed (all test methods are commented)
) else (
    echo ✗ Tests failed
    exit /b 1
)

echo.
echo ========================================
echo Feature 1 Test Template Verification Complete!
echo ========================================
echo.
echo Test template includes:
echo 1. 6 test methods for Feature 1 rules
echo 2. All test methods are commented by default
echo 3. Each test method has @TestGroup annotation
echo 4. Test methods follow Given/When/Then pattern
echo.
echo Next steps:
echo 1. Choose a test method to implement
echo 2. Remove comment tags from the chosen test
echo 3. Implement the test logic
echo 4. Run the test and implement the feature
echo 5. Repeat for all 6 test methods
echo.
