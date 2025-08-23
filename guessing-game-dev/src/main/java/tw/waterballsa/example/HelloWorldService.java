package tw.waterballsa.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class HelloWorldService {
    
    @Autowired
    private HelloWorldRepository helloWorldRepository;
    
    public String greet(String name) {
        String greetingMessage = "Hello world '" + name + "'!";
        
        // 儲存問候訊息到資料庫
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        GreetingMessage greeting = new GreetingMessage(name, greetingMessage, timestamp);
        helloWorldRepository.save(greeting);
        
        return greetingMessage;
    }
}
