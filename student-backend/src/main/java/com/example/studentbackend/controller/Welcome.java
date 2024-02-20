package com.example.studentbackend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Welcome {
    @RequestMapping(path = "/welcome", method = RequestMethod.GET)
    public String welcome(){
        return "Welcome";
    }
}
