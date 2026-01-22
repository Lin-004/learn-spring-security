package com.lin.login.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

//    @Resource
//    private UserService userService;

    @RequestMapping("/show")
    public String show() {
        return "Hello Show!";
    }

    @RequestMapping("/query")
    public String query() {
        return "Hello Query!";
    }

//    @PostMapping("/login")
//    public ResponseResult login(@RequestBody String username, String password) throws Exception {
//        System.out.println(username);
//        System.out.println(password);
//        return userService.login(username,password);
//    }

    @GetMapping("/ok")
    public String ok() {
        return "okkkkkkkkkkk";
    }

    @PostMapping("/err")
    public String err() {
        return "err";
    }
}