package com.example.mycontact.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

//@Controller
//@ResponseBody
@RestController // 이걸로 위에 두개 치환가능
public class HelloWorldController {

    ///*
    @GetMapping(value = "/api/helloWorld")
    public String helloWorld() {
        return "HelloWorld";
    }
    //*/
    /*
    @PostMapping(value = "/api/helloWorld")
    public String helloWorld() {
        return "HelloWorld";
    }
    */
}
