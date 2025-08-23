package tw.waterballsa.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameService {
    
    @Autowired
    private GameRepository gameRepository;
    
    public String joinGame(String gameId, String playerName) {
        // 檢查遊戲是否已存在
        if (!gameRepository.existsByGameId(gameId)) {
            // 創建新遊戲，玩家成為 P1
            Game newGame = new Game(gameId, playerName);
            gameRepository.save(newGame);
            return "Game created successfully. " + playerName + " joined as P1.";
        }
        
        // 遊戲已存在，暫時返回簡單訊息
        return "Game joined successfully";
    }
}
