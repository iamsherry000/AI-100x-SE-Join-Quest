# Walking Skeleton - 自動化單元測試環境

## 概述

這是一個完整的 Walking skeleton，提供了自動化單元測試的最小可行基礎建設。

## 專案結構

```
src/
├── main/
│   ├── java/tw/waterballsa/
│   │   ├── GuessingGameApplication.java          # Spring Boot 主應用程式
│   │   └── example/
│   │       ├── HelloWorldController.java         # REST API 控制器
│   │       ├── HelloWorldService.java            # 業務邏輯服務
│   │       ├── HelloWorldRepository.java         # 資料存取層
│   │       └── GreetingMessage.java              # JPA 實體類別
│   └── resources/
│       └── application.properties                # 應用程式配置
└── test/
    ├── java/tw/waterballsa/example/
    │   └── HelloWorldEndToEndTest.java           # E2E 測試類別
    └── resources/
        └── application-test.properties           # 測試環境配置
```

## 技術堆疊

- **Java 17**
- **Spring Boot 2.7.14**
- **Spring Data JPA**
- **H2 Database** (記憶體資料庫)
- **JUnit 5**
- **Spring Boot Test**
- **MockMvc** (用於 E2E 測試)

## 功能說明

### 1. Hello World API

- **端點**: `GET /api/hello?name={name}`
- **功能**: 回傳問候訊息並儲存到資料庫
- **範例**: `GET /api/hello?name=Johnny`
- **回應**: `"Hello world 'Johnny'!"`

### 2. E2E 測試

測試案例實作了 Given/When/Then 模式：

```gherkin
Given my name is "Johnny"
When someone is greeting me
Then he says "Hello world 'Johnny'!" to me
```

測試程式碼使用 MockMvc 來模擬 HTTP 請求，驗證完整的 API 流程。

## 如何執行

### 1. 執行測試

```bash
# 使用 Maven Wrapper
./mvnw test

# 或使用 Windows
mvnw.cmd test
```

### 2. 啟動應用程式

```bash
# 使用 Maven Wrapper
./mvnw spring-boot:run

# 或使用 Windows
mvnw.cmd spring-boot:run
```

### 3. 使用測試腳本

```bash
# Windows
test-walking-skeleton.bat
```

## 測試驗證

1. **編譯測試**: 確認專案可以正常編譯
2. **單元測試**: 執行 E2E 測試，驗證 API 功能
3. **整合測試**: 啟動應用程式，手動測試 API 端點

## API 測試

啟動應用程式後，可以使用以下方式測試：

1. **瀏覽器**: 訪問 `http://localhost:8080/api/hello?name=Johnny`
2. **curl**: `curl "http://localhost:8080/api/hello?name=Johnny"`
3. **Postman**: 發送 GET 請求到 `/api/hello?name=Johnny`

## 資料庫

- 使用 H2 記憶體資料庫
- 開發環境可透過 `http://localhost:8080/h2-console` 存取
- 測試環境使用獨立的測試資料庫配置

## 下一步

這個 Walking skeleton 提供了：

1. ✅ 完整的測試環境配置
2. ✅ 基本的 API 端點
3. ✅ E2E 測試範例
4. ✅ 資料庫整合
5. ✅ 分層架構 (Controller -> Service -> Repository)

您可以基於此基礎繼續開發更多功能，並新增相應的測試案例。
