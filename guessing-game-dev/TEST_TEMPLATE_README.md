# Feature 1 測試樣板說明

## 📋 概述

本文件說明如何針對 Feature 1 的測試樣板進行 TDD 開發。

## 🏗️ 建立的測試結構

### 1. 測試類別
- **檔案**: `src/test/java/tw/waterballsa/game/GameFeature1Test.java`
- **功能**: 包含 Feature 1 的所有測試方法樣板

### 2. 自定義註解
- **檔案**: `src/test/java/tw/waterballsa/game/TestGroup.java`
- **功能**: 用於標記測試群組，保留原始 Feature 描述

### 3. 相關實體類別
- `Game.java` - 遊戲實體
- `GameStatus.java` - 遊戲狀態列舉
- `GameRepository.java` - 資料存取層
- `GameService.java` - 業務邏輯層
- `GameController.java` - REST API 控制器

## 🧪 測試方法樣板

### Feature 1 包含 6 個測試方法：

1. **testPlayerInputsGameIdAndNameToStartGame()**
   - 測試玩家輸入遊戲 ID 和名稱進行配對

2. **testCreateNewGameWhenGameIdDoesNotExist()**
   - 測試創建新遊戲，玩家成為 P1

3. **testJoinAsPlayer2WhenGameExistsAndNotInGuessingPhase()**
   - 測試加入現有遊戲成為 P2

4. **testShowGameFullMessageWhenTwoPlayersAlreadyJoined()**
   - 測試遊戲已滿時的錯誤處理

5. **testPreventSamePlayerFromJoiningSameGameTwice()**
   - 測試防止同一玩家重複加入

6. **testEnterSecretNumberSettingPhaseWhenTwoPlayersJoined()**
   - 測試進入設定秘密數字階段

## 🔧 如何進行 TDD 開發

### 步驟 1: 選擇測試方法
選擇一個測試方法，移除註解標籤：

```java
// 從這樣：
/*
@Test
@TestGroup("Feature 1: 若系統中尚無此 ID，則視為創建新遊戲，該玩家將成為該場遊戲的 P1。")
public void testCreateNewGameWhenGameIdDoesNotExist() throws Exception {
    // TODO: 實作測試邏輯
}
*/

// 變成這樣：
@Test
@TestGroup("Feature 1: 若系統中尚無此 ID，則視為創建新遊戲，該玩家將成為該場遊戲的 P1。")
public void testCreateNewGameWhenGameIdDoesNotExist() throws Exception {
    // TODO: 實作測試邏輯
}
```

### 步驟 2: 實作測試邏輯
使用 Given/When/Then 模式撰寫測試：

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

### 步驟 3: 執行測試
```bash
# 執行特定測試方法
mvnw.cmd test -Dtest=GameFeature1Test#testCreateNewGameWhenGameIdDoesNotExist

# 執行所有測試
mvnw.cmd test
```

## 📝 測試方法命名規範

- 測試方法名稱使用 `test` 開頭
- 描述測試的具體場景
- 使用駝峰命名法
- 方法名稱應該清楚表達測試目的

## 🎯 測試群組管理

- 每個 Feature 使用 `@TestGroup` 註解標記
- 註解值包含完整的原始需求描述
- 便於追蹤測試與需求的對應關係

## ⚠️ 注意事項

1. **測試方法預設被註解**：所有測試方法預設都被註解，避免執行未實作的測試
2. **逐步實作**：一次只實作一個測試方法，遵循 TDD 三步驟
3. **保持註解**：未實作的測試方法保持註解狀態
4. **驗證邏輯**：每個測試方法都應該包含完整的 Given/When/Then 邏輯

## 🚀 下一步

1. 選擇第一個測試方法開始實作
2. 遵循 TDD 循環：Red → Green → Refactor
3. 逐步完成所有 6 個測試方法
4. 確保所有測試都通過
