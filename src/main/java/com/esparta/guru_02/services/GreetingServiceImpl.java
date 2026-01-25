package com.esparta.guru_02.services;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/*
 * Author: M
 * Date: 23-Jan-26
 * Project Name: guru-02
 * Description: beExcellent
 */
@Profile("prod")
@Service("GreetingServiceImpl")
public class GreetingServiceImpl implements GreetingService {
    @Override
    public String sayGreeting() {
        return "prod - Hello from GreetingServiceImpl base Service";
    }
}
