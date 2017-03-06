package com.myworkflow.server.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * created by: Vitalii Pasichnyk
 * creation date: 3/6/2017
 * email: code.crosser@gmail.com
 */
@Controller
@RequestMapping("/test")
public class TestController {

    @RequestMapping(value = "/teststring", method = RequestMethod.GET)
    @ResponseBody
    public String getTestString(){
        return "Test string from TEST controller";
    }

}
