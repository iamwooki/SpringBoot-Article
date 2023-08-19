package me.iamwooki.toyproject.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // RestAPI용 컨트롤러
public class HelloApiController {
    @GetMapping("/api/hello")
    public String hello(){
        return "hello world!";
    }
}
