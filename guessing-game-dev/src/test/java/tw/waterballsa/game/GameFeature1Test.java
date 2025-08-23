package tw.waterballsa.game;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
public class GameFeature1Test {

    @Autowired
    private MockMvc mockMvc;

    /**
     * Feature 1: 玩家輸入一組 3-5 位字母或數字組成的「遊戲 ID」，和自己的名稱（3~10字元）點擊「開始遊戲」進行配對。
     * 若系統中尚無此 ID，則視為創建新遊戲，該玩家將成為該場遊戲的 P1。
     * 若該 ID 已存在且遊戲尚未進入猜題階段，則玩家加入為 P2。
     * 若該遊戲已經有兩位玩家，則顯示「該遊戲已滿，請選擇其他遊戲」。
     * 同一玩家不得重複加入同一場遊戲。
     * 若遊戲已經滿兩位玩家，則進入設定秘密數字階段。
     */
    
    // ==================== Feature 1 Test Group ====================
    
    /*
    @Test
    @TestGroup("Feature 1: 玩家輸入一組 3-5 位字母或數字組成的「遊戲 ID」，和自己的名稱（3~10字元）點擊「開始遊戲」進行配對。")
    public void testPlayerInputsGameIdAndNameToStartGame() throws Exception {
        // TODO: 實作測試邏輯
        // Given - 玩家輸入有效的遊戲 ID 和名稱
        // When - 點擊開始遊戲
        // Then - 系統接受輸入並進行配對
    }
    */
    
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
        assertEquals("Game created successfully. Player1 joined as P1.", response);
    }
    
    /*
    @Test
    @TestGroup("Feature 1: 若該 ID 已存在且遊戲尚未進入猜題階段，則玩家加入為 P2。")
    public void testJoinAsPlayer2WhenGameExistsAndNotInGuessingPhase() throws Exception {
        // TODO: 實作測試邏輯
        // Given - 遊戲 ID 已存在且遊戲尚未進入猜題階段
        // When - 新玩家嘗試加入該遊戲
        // Then - 玩家成功加入為 P2
    }
    */
    
    /*
    @Test
    @TestGroup("Feature 1: 若該遊戲已經有兩位玩家，則顯示「該遊戲已滿，請選擇其他遊戲」。")
    public void testShowGameFullMessageWhenTwoPlayersAlreadyJoined() throws Exception {
        // TODO: 實作測試邏輯
        // Given - 遊戲已經有兩位玩家
        // When - 第三位玩家嘗試加入
        // Then - 顯示「該遊戲已滿，請選擇其他遊戲」
    }
    */
    
    /*
    @Test
    @TestGroup("Feature 1: 同一玩家不得重複加入同一場遊戲。")
    public void testPreventSamePlayerFromJoiningSameGameTwice() throws Exception {
        // TODO: 實作測試邏輯
        // Given - 玩家已經在遊戲中
        // When - 同一玩家嘗試再次加入同一場遊戲
        // Then - 系統拒絕加入並顯示適當訊息
    }
    */
    
    /*
    @Test
    @TestGroup("Feature 1: 若遊戲已經滿兩位玩家，則進入設定秘密數字階段。")
    public void testEnterSecretNumberSettingPhaseWhenTwoPlayersJoined() throws Exception {
        // TODO: 實作測試邏輯
        // Given - 遊戲已經有兩位玩家
        // When - 第二位玩家成功加入
        // Then - 遊戲狀態變更為設定秘密數字階段
    }
    */
}
