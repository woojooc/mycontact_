package com.example.mycontact.controller;

import com.example.mycontact.exception.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    // 항상 오류를 발생시키는 api
    @GetMapping(value = "/api/helloException")
    public String helloException(){
        throw new RuntimeException("Hello RuntimeException");
    }

}
