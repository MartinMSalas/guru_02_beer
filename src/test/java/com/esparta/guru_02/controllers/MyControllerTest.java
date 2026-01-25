package com.esparta.guru_02.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
//@ActiveProfiles("QAA")
class MyControllerTest {

    @Autowired
    MyController myController;


    @Test
    void sayHello() {
        String result = myController.sayHello();
        System.out.println(result);
        assertNotNull(result);

    }
}