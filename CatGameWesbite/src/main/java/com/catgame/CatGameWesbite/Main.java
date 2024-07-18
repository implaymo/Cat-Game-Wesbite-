package com.catgame.CatGameWesbite;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;



@Controller
public class Main {

    
    @RequestMapping("/")
    public String homepage(){
        return "index";
    }

}
