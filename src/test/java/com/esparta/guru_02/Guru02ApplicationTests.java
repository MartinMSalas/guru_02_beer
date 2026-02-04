package com.esparta.guru_02;

import com.esparta.guru_02.controllers.MyController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

//@SpringBootTest
class Guru02ApplicationTests {

	@Autowired
	ApplicationContext applicationContext;

	@Autowired
	MyController myController;

//	@Test
	void testAutowireOfController(){
		System.out.println(myController.sayHello());
	}


//	@Test
	void testGetControllerFromCtx() {
		MyController myController = applicationContext.getBean(MyController.class);
		System.out.println("In test: " + myController.sayHello());

	}

//	@Test
	void contextLoads() {
	}

}
