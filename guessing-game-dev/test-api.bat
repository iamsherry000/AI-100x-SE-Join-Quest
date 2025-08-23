@echo off
echo Testing 1A2B Guessing Game API...
echo.

echo 1. Creating a new game...
curl -X POST http://localhost:8080/api/game/join -H "Content-Type: application/json" -d "{\"gameId\":\"TEST1\",\"playerId\":\"p1\",\"playerName\":\"玩家一\"}"
echo.
echo.

echo 2. Joining the game with second player...
curl -X POST http://localhost:8080/api/game/join -H "Content-Type: application/json" -d "{\"gameId\":\"TEST1\",\"playerId\":\"p2\",\"playerName\":\"玩家二\"}"
echo.
echo.

echo 3. Setting secret number for player 1...
curl -X POST http://localhost:8080/api/game/TEST1/secret -H "Content-Type: application/json" -d "{\"playerId\":\"p1\",\"secretNumber\":\"1234\"}"
echo.
echo.

echo 4. Setting secret number for player 2...
curl -X POST http://localhost:8080/api/game/TEST1/secret -H "Content-Type: application/json" -d "{\"playerId\":\"p2\",\"secretNumber\":\"5678\"}"
echo.
echo.

echo 5. Player 1 guessing...
curl -X POST http://localhost:8080/api/game/TEST1/guess -H "Content-Type: application/json" -d "{\"playerId\":\"p1\",\"guessedNumber\":\"5678\"}"
echo.
echo.

echo 6. Getting game status...
curl -X GET http://localhost:8080/api/game/TEST1/status
echo.
echo.

echo Test completed!
pause
