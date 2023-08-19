package me.iamwooki.toyproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    // 최초로 controller에서 받아 model 값을 가지고 view (mustache)로 전달
    @GetMapping("/hi")
    public String greetings(Model model){
        model.addAttribute("userName","Openhimer");
        return "grettings"; // templeate/mustache 브라우저로 전송
    }
}
