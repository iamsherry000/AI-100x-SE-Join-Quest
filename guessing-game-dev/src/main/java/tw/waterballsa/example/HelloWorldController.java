package tw.waterballsa.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class HelloWorldController {
    
    @Autowired
    private HelloWorldService helloWorldService;
    
    @GetMapping("/hello")
    public String greet(@RequestParam String name) {
        return helloWorldService.greet(name);
    }
}
