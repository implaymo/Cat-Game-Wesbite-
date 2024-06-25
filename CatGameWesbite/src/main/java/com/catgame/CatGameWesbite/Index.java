package com.catgame.CatGameWesbite;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Index {
    
    @GetMapping("/index")
    public String sayHello(){
        return "HELLO WORLD";
    }
}
