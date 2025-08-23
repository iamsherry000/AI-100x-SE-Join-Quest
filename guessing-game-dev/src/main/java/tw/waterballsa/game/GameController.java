package tw.waterballsa.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/game")
public class GameController {
    
    @Autowired
    private GameService gameService;
    
    @PostMapping("/join")
    public String joinGame(@RequestParam String gameId, @RequestParam String playerName) {
        return gameService.joinGame(gameId, playerName);
    }
}
