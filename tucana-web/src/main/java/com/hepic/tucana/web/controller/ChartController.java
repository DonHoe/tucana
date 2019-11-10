package com.hepic.tucana.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/chart")
@Controller
public class ChartController {

    @GetMapping
    public String index() {
        return "chart";
    }

}
