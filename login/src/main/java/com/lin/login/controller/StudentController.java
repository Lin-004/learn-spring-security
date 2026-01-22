package com.lin.login.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stu")
public class StudentController {
    @RequestMapping("/insert")
    public String insert() {
        return "添加学⽣";
    }

    @RequestMapping("/update")
    public String update() {
        return "修改学⽣";
    }

    @RequestMapping("/del")
    public String del() {
        return "删除学⽣";
    }

    @RequestMapping("/select")
    public String select() {
        return "查询学⽣";
    }
}