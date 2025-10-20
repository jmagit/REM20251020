package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.ioc.NotificationService;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
		// ...
	}

	@Autowired
	NotificationService notify;
	
	@Override
	public void run(String... args) throws Exception {
		System.err.println("Aplicación arrancada ...");
		
		notify.add("Hola mundo");
		notify.add(notify.getClass().getSimpleName());
		notify.getListado().forEach(System.out::println);
	}

}
