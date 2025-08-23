# 1A2B 猜數字遊戲

這是一款支援雙人線上對戰的 1A2B 猜數字益智遊戲。遊戲的核心機制源自經典的猜數字遊戲，透過簡單的邏輯推理與觀察對手的回應，嘗試推理出對手所設定的秘密數字。

## 遊戲規則

1. **遊戲設定**：玩家需要設定一組 4 位數、數字不重複的答案
2. **輪流猜測**：玩家輪流嘗試猜出對手的答案
3. **1A2B 回饋**：每次猜測後，系統將依據「幾A幾B」的回饋機制提供提示
   - A：數字和位置都對
   - B：數字對但位置錯
4. **勝利條件**：當某位玩家成功猜中對手的答案（即 4A），該玩家立即獲勝

## 技術架構

- **語言**：Java 17
- **框架**：Spring Boot 2.7.14
- **資料庫**：H2 (記憶體資料庫)
- **ORM**：Spring Data JPA
- **建構工具**：Maven

## 專案結構

```
src/main/java/tw/waterballsa/guessinggame/
├── GuessingGameApplication.java          # 主應用程式類別
├── controller/
│   └── GameController.java               # REST API 控制器
├── service/
│   └── GameService.java                  # 遊戲業務邏輯
├── repository/
│   ├── GameRepository.java               # 遊戲資料存取
│   ├── PlayerRepository.java             # 玩家資料存取
│   └── GuessRepository.java              # 猜測記錄資料存取
├── entity/
│   ├── Game.java                         # 遊戲實體
│   ├── Player.java                       # 玩家實體
│   ├── Guess.java                        # 猜測記錄實體
│   └── GameStatus.java                   # 遊戲狀態列舉
├── dto/
│   ├── ApiResponse.java                  # API 回應格式
│   ├── GameJoinRequest.java              # 加入遊戲請求
│   ├── SecretNumberRequest.java          # 設定秘密數字請求
│   └── GuessRequest.java                 # 猜測數字請求
└── exception/
    └── GlobalExceptionHandler.java       # 全域異常處理
```

## 功能特色

### Feature 1: 遊戲配對
- 玩家輸入 3-5 位字母或數字組成的遊戲 ID 和玩家名稱
- 系統自動創建新遊戲或加入現有遊戲
- 防止重複加入和遊戲已滿的情況

### Feature 2: 秘密數字設定
- 兩位玩家輪流設定 4 位不重複數字
- 系統驗證數字格式（必須是 4 位不重複數字）
- 當兩位玩家都設定完成後進入猜題階段

### Feature 3: 猜題階段
- 玩家在進入猜題階段前可以改變最多一次秘密數字
- 雙方輪流猜測，每次猜測都會得到 1A2B 結果
- 猜中 4A 立即獲勝，遊戲結束

## API 端點

### 1. 加入遊戲
```
POST /api/game/join
Content-Type: application/json

{
  "gameId": "ABC123",
  "playerId": "player1",
  "playerName": "玩家一"
}
```

### 2. 設定秘密數字
```
POST /api/game/{gameId}/secret
Content-Type: application/json

{
  "playerId": "player1",
  "secretNumber": "1234"
}
```

### 3. 修改秘密數字
```
PUT /api/game/{gameId}/secret
Content-Type: application/json

{
  "playerId": "player1",
  "secretNumber": "5678"
}
```

### 4. 猜測數字
```
POST /api/game/{gameId}/guess
Content-Type: application/json

{
  "playerId": "player1",
  "guessedNumber": "1234"
}
```

### 5. 獲取遊戲狀態
```
GET /api/game/{gameId}/status
```

### 6. 獲取玩家資訊
```
GET /api/game/{gameId}/player/{playerId}
```

### 7. 獲取猜測歷史
```
GET /api/game/{gameId}/player/{playerId}/history
```

## 執行方式

### 前置需求
- Java 17 或以上版本
- Maven 3.6 或以上版本

### 啟動應用程式
```bash
# 使用 Maven wrapper
./mvnw spring-boot:run

# 或使用 Maven
mvn spring-boot:run
```

### 存取應用程式
- 應用程式將在 `http://localhost:8080` 啟動
- H2 資料庫控制台：`http://localhost:8080/h2-console`
  - JDBC URL: `jdbc:h2:mem:testdb`
  - 使用者名稱: `sa`
  - 密碼: (留空)

## 遊戲流程範例

1. **玩家一創建遊戲**
   ```bash
   curl -X POST http://localhost:8080/api/game/join \
     -H "Content-Type: application/json" \
     -d '{"gameId":"GAME1","playerId":"p1","playerName":"玩家一"}'
   ```

2. **玩家二加入遊戲**
   ```bash
   curl -X POST http://localhost:8080/api/game/join \
     -H "Content-Type: application/json" \
     -d '{"gameId":"GAME1","playerId":"p2","playerName":"玩家二"}'
   ```

3. **玩家一設定秘密數字**
   ```bash
   curl -X POST http://localhost:8080/api/game/GAME1/secret \
     -H "Content-Type: application/json" \
     -d '{"playerId":"p1","secretNumber":"1234"}'
   ```

4. **玩家二設定秘密數字**
   ```bash
   curl -X POST http://localhost:8080/api/game/GAME1/secret \
     -H "Content-Type: application/json" \
     -d '{"playerId":"p2","secretNumber":"5678"}'
   ```

5. **玩家一猜測**
   ```bash
   curl -X POST http://localhost:8080/api/game/GAME1/guess \
     -H "Content-Type: application/json" \
     -d '{"playerId":"p1","guessedNumber":"5678"}'
   ```

## 資料庫結構

### games 表
- id: 主鍵
- game_id: 遊戲 ID (唯一)
- status: 遊戲狀態
- created_at: 創建時間
- started_at: 開始時間
- ended_at: 結束時間
- winner_id: 獲勝者 ID

### players 表
- id: 主鍵
- player_id: 玩家 ID
- name: 玩家名稱
- secret_number: 秘密數字
- secret_number_set_at: 設定時間
- secret_number_change_count: 修改次數
- game_id: 遊戲外鍵
- joined_at: 加入時間

### guesses 表
- id: 主鍵
- guessed_number: 猜測數字
- a_count: A 數量
- b_count: B 數量
- round_number: 回合數
- player_id: 玩家外鍵
- guessed_at: 猜測時間

## 開發注意事項

1. **資料驗證**：所有輸入都會進行格式驗證
2. **狀態管理**：遊戲狀態嚴格控制，防止非法操作
3. **事務管理**：使用 Spring 事務確保資料一致性
4. **異常處理**：全域異常處理器統一處理錯誤回應
5. **API 設計**：遵循 RESTful 設計原則

## 未來擴展

- 支援更多玩家同時遊戲
- 增加遊戲統計和排行榜
- 實作 WebSocket 即時通訊
- 增加遊戲房間管理
- 支援自定義遊戲規則
