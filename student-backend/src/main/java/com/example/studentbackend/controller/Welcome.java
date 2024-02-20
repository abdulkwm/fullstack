package com.example.studentbackend.controller;

import com.example.studentbackend.model.UserInfo;
import com.example.studentbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class Welcome {
    @Autowired
    private UserService userService;
    @RequestMapping(path = "/welcome", method = RequestMethod.GET)
    public String welcome(){
        return "Welcome";
    }
    @PostMapping("/addUser")
    public String addStudent(@RequestBody UserInfo userInfo){
        userService.addUser(userInfo);
        return "user is added";
    }
}
