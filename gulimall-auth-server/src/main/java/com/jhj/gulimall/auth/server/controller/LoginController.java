package com.jhj.gulimall.auth.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

@Controller
public class LoginController {

    @GetMapping("/login")
    public void loginPage(HttpSession session){
        HashMap<Object, Object> map = new HashMap<>();
        map.put("userName","aaa");
        map.put("password","123456");
        session.setAttribute("user",map);
    }

    @PostMapping("/login2")
    public void loginPage2(HttpSession session){
        HashMap<Object, Object> map = new HashMap<>();
        map.put("userName","aaa2");
        map.put("password","123456");
        session.setAttribute("user",map);
    }

    @PostMapping("/reg")
    public void regPage(HttpSession session){
        System.out.println(session.getAttribute("user"));
    }
}
