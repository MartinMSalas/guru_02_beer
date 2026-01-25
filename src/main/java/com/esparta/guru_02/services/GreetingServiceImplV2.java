package com.esparta.guru_02.services;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/*
 * Author: M
 * Date: 23-Jan-26
 * Project Name: guru-02
 * Description: beExcellent
 */
@Profile("uat")
@Service("GreetingServiceImpl")
public class GreetingServiceImplV2 implements GreetingService {
    @Override
    public String sayGreeting() {
        return "uat - Hello from GreetingServiceImpl base Service";
    }
}
