package com.hepic.tucana.web.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestController {

    @RequestMapping("/get")
    public String get(){
        return "hello";
    }
}
