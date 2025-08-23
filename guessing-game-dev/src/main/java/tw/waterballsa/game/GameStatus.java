package tw.waterballsa.game;

public enum GameStatus {
    WAITING_FOR_PLAYER2,    // 等待第二位玩家加入
    SETTING_SECRET_NUMBERS, // 設定秘密數字階段
    GUESSING_PHASE,         // 猜題階段
    FINISHED                // 遊戲結束
}
