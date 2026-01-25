package com.esparta.guru_02.controllers;

/*
 * Author: M
 * Date: 23-Jan-26
 * Project Name: guru-02
 * Description: beExcellent
 */

import com.esparta.guru_02.services.GreetingService;
import org.springframework.stereotype.Controller;

@Controller
public class MyController {

    private final GreetingService  greetingService;

    public MyController(GreetingService greetingService) {
        this.greetingService = greetingService;
    }


    public String sayHello() {
        System.out.println("In MyController, sayHello()");
        return greetingService.sayGreeting();
    }
}
