package com.hepic.tucana.web.controller;

import com.hepic.dao.mysql.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private UserDao userDao;

    @RequestMapping("/get")
    public String get(){
        return userDao.findUserById(1).getUserName();
    }
}
