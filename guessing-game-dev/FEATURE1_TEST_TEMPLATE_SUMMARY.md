# Feature 1 測試樣板完成總結

## 🎯 任務完成狀況

### ✅ 已完成項目

1. **測試套件配置**
   - JUnit 5
   - Spring Boot Test
   - MockMvc (用於 E2E 測試)

2. **自定義註解建立**
   - `@TestGroup` 註解用於標記測試群組
   - 保留原始 Feature 描述文字

3. **Feature 1 測試樣板**
   - 建立 `GameFeature1Test` 測試類別
   - 包含 6 個對應 Feature 1 規則的測試方法
   - 所有測試方法預設被註解

4. **相關實體類別**
   - `Game` - 遊戲實體
   - `GameStatus` - 遊戲狀態列舉
   - `GameRepository` - 資料存取層
   - `GameService` - 業務邏輯層
   - `GameController` - REST API 控制器

## 📁 建立的檔案結構

```
guessing-game-dev/
├── src/
│   ├── main/java/tw/waterballsa/game/
│   │   ├── Game.java                    # 遊戲實體
│   │   ├── GameStatus.java              # 遊戲狀態列舉
│   │   ├── GameRepository.java          # 資料存取層
│   │   ├── GameService.java             # 業務邏輯層
│   │   └── GameController.java          # REST API 控制器
│   └── test/java/tw/waterballsa/game/
│       ├── TestGroup.java               # 自定義註解
│       └── GameFeature1Test.java        # Feature 1 測試樣板
├── TEST_TEMPLATE_README.md              # 測試樣板說明
├── FEATURE1_TEST_TEMPLATE_SUMMARY.md    # 本文件
└── verify-test-template.bat             # 驗證腳本
```

## 🧪 Feature 1 測試方法樣板

### 測試方法清單：

1. **testPlayerInputsGameIdAndNameToStartGame()**
   - 規則：玩家輸入一組 3-5 位字母或數字組成的「遊戲 ID」，和自己的名稱（3~10字元）點擊「開始遊戲」進行配對。

2. **testCreateNewGameWhenGameIdDoesNotExist()**
   - 規則：若系統中尚無此 ID，則視為創建新遊戲，該玩家將成為該場遊戲的 P1。

3. **testJoinAsPlayer2WhenGameExistsAndNotInGuessingPhase()**
   - 規則：若該 ID 已存在且遊戲尚未進入猜題階段，則玩家加入為 P2。

4. **testShowGameFullMessageWhenTwoPlayersAlreadyJoined()**
   - 規則：若該遊戲已經有兩位玩家，則顯示「該遊戲已滿，請選擇其他遊戲」。

5. **testPreventSamePlayerFromJoiningSameGameTwice()**
   - 規則：同一玩家不得重複加入同一場遊戲。

6. **testEnterSecretNumberSettingPhaseWhenTwoPlayersJoined()**
   - 規則：若遊戲已經滿兩位玩家，則進入設定秘密數字階段。

## 🔧 技術規格

- **Java 17**
- **Spring Boot 2.7.14**
- **JUnit 5**
- **Spring Boot Test**
- **MockMvc** (E2E 測試)
- **H2 Database** (測試用)

## 📋 測試樣板特色

### 1. 測試群組管理
- 使用 `@TestGroup` 註解標記每個測試方法
- 註解值包含完整的原始需求描述
- 便於追蹤測試與需求的對應關係

### 2. Given/When/Then 結構
- 每個測試方法都遵循 Given/When/Then 模式
- 清楚分離測試的準備、執行和驗證階段
- 提高測試的可讀性和維護性

### 3. 預設註解狀態
- 所有測試方法預設都被註解
- 避免執行未實作的測試
- 逐步實作時可以選擇性啟用

### 4. MockMvc E2E 測試
- 使用 MockMvc 進行 API 層級的測試
- 模擬真實的 HTTP 請求
- 驗證完整的 API 流程

## 🚀 如何使用

### 1. 驗證測試樣板
```bash
# Windows
verify-test-template.bat
```

### 2. 開始 TDD 開發
1. 選擇一個測試方法
2. 移除註解標籤
3. 實作測試邏輯
4. 執行測試並實作功能
5. 重複步驟 1-4

### 3. 執行測試
```bash
# 執行特定測試方法
mvnw.cmd test -Dtest=GameFeature1Test#testCreateNewGameWhenGameIdDoesNotExist

# 執行所有測試
mvnw.cmd test
```

## 📝 測試方法範例

```java
@Test
@TestGroup("Feature 1: 若系統中尚無此 ID，則視為創建新遊戲，該玩家將成為該場遊戲的 P1。")
public void testCreateNewGameWhenGameIdDoesNotExist() throws Exception {
    // Given - 系統中不存在指定的遊戲 ID
    String gameId = "ABC123";
    String playerName = "Player1";
    
    // When - 玩家嘗試加入該遊戲
    MvcResult result = mockMvc.perform(post("/api/game/join")
            .param("gameId", gameId)
            .param("playerName", playerName)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();
    
    // Then - 系統創建新遊戲，該玩家成為 P1
    String response = result.getResponse().getContentAsString();
    // 驗證回應內容
}
```

## 🎉 結論

Feature 1 測試樣板已成功建立，提供了：

1. **完整的測試架構**：包含所有必要的實體類別和服務
2. **6 個測試方法樣板**：對應 Feature 1 的每個規則
3. **自定義註解系統**：便於管理測試群組
4. **Given/When/Then 結構**：標準化的測試模式
5. **預設註解狀態**：安全的逐步實作環境

這個測試樣板為 TDD 開發提供了完整的基礎，您可以基於此架構逐步實作 Feature 1 的所有功能。
