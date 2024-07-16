package com.catgame.CatGameWesbite;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.stereotype.Controller;



@Controller
public class Main {

    
    @RequestMapping("/")
    public String homepage(){
        return "index";
    }
    
    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(){
        return "registration";
    }


    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/twofacauth")
    public String auth(){
        return "twofa";
    }
}
