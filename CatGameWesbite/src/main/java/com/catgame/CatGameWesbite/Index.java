package com.catgame.CatGameWesbite;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;


@Controller
public class Index {
    
    @RequestMapping("/")
    public String homepage(){
        return "index";
    }
    
    @RequestMapping("/registration")
    public String registration(){
        return "registration";
    }
}
