package com.example.base.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {
    @RequestMapping("/")
    public String home(){
        return "patients/patientList";
    }

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }
}
