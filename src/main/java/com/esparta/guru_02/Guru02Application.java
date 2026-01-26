package com.esparta.guru_02;


import com.esparta.guru_02.controllers.MyController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
//@EnableJpaAuditing
public class Guru02Application {

	public static void main(String[] args) {

		ApplicationContext ctx = SpringApplication.run(Guru02Application.class, args);

		MyController controller = ctx.getBean(MyController.class);

		System.out.println("In main: " + controller);
		System.out.println(controller.sayHello());
	}

}
