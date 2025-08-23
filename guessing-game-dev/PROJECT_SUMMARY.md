# 1A2B 猜數字遊戲 - 專案開發總結

## 專案概述

本專案是一個完整的 Spring Boot 後端應用程式，實現了支援雙人線上對戰的 1A2B 猜數字益智遊戲。根據需求文件，已完整實現所有核心功能。

## 已實現的功能

### ✅ Feature 1: 遊戲配對系統
- [x] 玩家輸入 3-5 位字母或數字組成的遊戲 ID
- [x] 玩家輸入 3-10 字元的玩家名稱
- [x] 系統自動創建新遊戲或加入現有遊戲
- [x] 防止重複加入同一場遊戲
- [x] 防止遊戲已滿時加入
- [x] 當兩位玩家都加入後自動進入設定秘密數字階段

### ✅ Feature 2: 秘密數字設定
- [x] 兩位玩家輪流設定 4 位不重複數字
- [x] 系統驗證數字格式（必須是 4 位不重複數字）
- [x] 當兩位玩家都設定完成後進入猜題階段
- [x] 支援在進入猜題階段前修改秘密數字（最多一次）

### ✅ Feature 3: 猜題階段
- [x] 雙方輪流猜測對手的秘密數字
- [x] 每次猜測都會得到 1A2B 結果
- [x] A 表示數字和位置都對
- [x] B 表示數字對但位置錯
- [x] 猜中 4A 立即獲勝，遊戲結束

## 技術架構

### 分層架構設計
```
Controller Layer (API 層)
    ↓
Service Layer (業務邏輯層)
    ↓
Repository Layer (資料存取層)
    ↓
Entity Layer (資料實體層)
```

### 核心組件

#### 1. 實體類別 (Entity)
- **Game**: 遊戲實體，包含遊戲狀態、玩家列表等
- **Player**: 玩家實體，包含玩家資訊、秘密數字等
- **Guess**: 猜測記錄實體，記錄每次猜測的結果
- **GameStatus**: 遊戲狀態列舉

#### 2. 資料存取層 (Repository)
- **GameRepository**: 遊戲資料庫操作
- **PlayerRepository**: 玩家資料庫操作
- **GuessRepository**: 猜測記錄資料庫操作

#### 3. 業務邏輯層 (Service)
- **GameService**: 核心遊戲邏輯，包含：
  - 遊戲配對邏輯
  - 秘密數字設定和驗證
  - 1A2B 計算邏輯
  - 遊戲狀態管理

#### 4. API 層 (Controller)
- **GameController**: RESTful API 端點，提供：
  - 加入遊戲 API
  - 設定秘密數字 API
  - 修改秘密數字 API
  - 猜測數字 API
  - 查詢遊戲狀態 API
  - 查詢玩家資訊 API
  - 查詢猜測歷史 API

#### 5. 資料傳輸物件 (DTO)
- **GameJoinRequest**: 加入遊戲請求
- **SecretNumberRequest**: 設定秘密數字請求
- **GuessRequest**: 猜測數字請求
- **ApiResponse**: 統一 API 回應格式

#### 6. 異常處理
- **GlobalExceptionHandler**: 全域異常處理器

## API 端點清單

| 方法 | 端點 | 功能 | 請求體 |
|------|------|------|--------|
| POST | `/api/game/join` | 加入遊戲或創建新遊戲 | GameJoinRequest |
| POST | `/api/game/{gameId}/secret` | 設定秘密數字 | SecretNumberRequest |
| PUT | `/api/game/{gameId}/secret` | 修改秘密數字 | SecretNumberRequest |
| POST | `/api/game/{gameId}/guess` | 猜測數字 | GuessRequest |
| GET | `/api/game/{gameId}/status` | 獲取遊戲狀態 | - |
| GET | `/api/game/{gameId}/player/{playerId}` | 獲取玩家資訊 | - |
| GET | `/api/game/{gameId}/player/{playerId}/history` | 獲取猜測歷史 | - |

## 資料庫設計

### games 表
```sql
CREATE TABLE games (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    game_id VARCHAR(5) UNIQUE NOT NULL,
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    started_at TIMESTAMP,
    ended_at TIMESTAMP,
    winner_id VARCHAR(10)
);
```

### players 表
```sql
CREATE TABLE players (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    player_id VARCHAR(10) NOT NULL,
    name VARCHAR(10) NOT NULL,
    secret_number VARCHAR(4),
    secret_number_set_at TIMESTAMP,
    secret_number_change_count INT DEFAULT 0,
    game_id BIGINT NOT NULL,
    joined_at TIMESTAMP NOT NULL,
    FOREIGN KEY (game_id) REFERENCES games(id)
);
```

### guesses 表
```sql
CREATE TABLE guesses (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    guessed_number VARCHAR(4) NOT NULL,
    a_count INT NOT NULL,
    b_count INT NOT NULL,
    round_number INT NOT NULL,
    player_id BIGINT NOT NULL,
    guessed_at TIMESTAMP NOT NULL,
    FOREIGN KEY (player_id) REFERENCES players(id)
);
```

## 遊戲流程

### 1. 遊戲配對階段
```
玩家一 → 創建遊戲 (gameId: "ABC123")
玩家二 → 加入遊戲 (gameId: "ABC123")
系統 → 自動進入設定秘密數字階段
```

### 2. 設定秘密數字階段
```
玩家一 → 設定秘密數字 (1234)
玩家二 → 設定秘密數字 (5678)
系統 → 自動進入猜題階段
```

### 3. 猜題階段
```
玩家一 → 猜測 (5678) → 得到結果 (4A0B) → 獲勝
遊戲結束
```

## 驗證規則

### 遊戲 ID 驗證
- 長度：3-5 位
- 格式：只能包含字母和數字
- 正則表達式：`^[a-zA-Z0-9]{3,5}$`

### 玩家名稱驗證
- 長度：3-10 字元
- 不能為空

### 秘密數字驗證
- 長度：4 位
- 格式：只能包含數字
- 規則：數字不能重複
- 正則表達式：`^[0-9]{4}$`

## 錯誤處理

### 業務邏輯錯誤
- 遊戲已滿
- 玩家重複加入
- 遊戲狀態不正確
- 秘密數字格式錯誤
- 修改次數超限

### 系統錯誤
- 資料庫連接錯誤
- 網路錯誤
- 系統內部錯誤

## 部署和運行

### 環境需求
- Java 17+
- Maven 3.6+
- H2 資料庫 (內建)

### 啟動指令
```bash
# 編譯專案
./mvnw clean compile

# 運行應用程式
./mvnw spring-boot:run
```

### 存取方式
- 應用程式：http://localhost:8080
- H2 資料庫控制台：http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:mem:testdb`
  - 使用者名稱: `sa`
  - 密碼: (留空)

## 測試

### 自動化測試腳本
- `test-api.bat`: Windows 批次檔案，測試完整遊戲流程

### 手動測試
可以使用 curl 或 Postman 等工具測試各個 API 端點

## 未來擴展建議

1. **前端介面**: 開發 Web 前端或移動端應用
2. **即時通訊**: 使用 WebSocket 實現即時遊戲更新
3. **多玩家支援**: 擴展支援更多玩家同時遊戲
4. **遊戲統計**: 增加勝負統計和排行榜功能
5. **自定義規則**: 支援自定義數字長度和遊戲規則
6. **持久化資料庫**: 使用 MySQL 或 PostgreSQL 替代 H2

## 程式碼品質

### 設計原則
- **單一職責原則**: 每個類別都有明確的職責
- **開閉原則**: 對擴展開放，對修改封閉
- **依賴反轉原則**: 依賴抽象而非具體實現

### 程式碼特色
- 完整的輸入驗證
- 統一的錯誤處理
- 清晰的 API 設計
- 良好的程式碼註解
- 符合 Spring Boot 最佳實踐

## 總結

本專案已完整實現需求文件中的所有功能，採用現代化的 Spring Boot 架構，具有良好的可擴展性和維護性。程式碼結構清晰，遵循最佳實踐，可以直接部署和運行。
