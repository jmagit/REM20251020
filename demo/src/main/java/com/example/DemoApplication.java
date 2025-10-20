package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import com.example.ioc.AppConfig;
import com.example.ioc.ClaseNoComponente;
import com.example.ioc.NotificationService;
import com.example.ioc.contratos.Configuracion;
import com.example.ioc.contratos.ServicioCadenas;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
		// ...
	}

	@Autowired(required = false)
	NotificationService notify;
	
	@Override
	public void run(String... args) throws Exception {
		System.err.println("Aplicación arrancada ...");
		
//		if(notify == null) {
//			System.out.println("Sin inyectar");
//			return;
//		}
//		notify.add("Hola mundo");
//		notify.add(notify.getClass().getSimpleName());
//		notify.getListado().forEach(System.out::println);
		
//		var srv = new ServicioCadenasImpl(new RepositorioCadenasImpl(new ConfiguracionImpl(notify), notify), notify);
	}
	
//	@Bean
	CommandLineRunner ejemplosIoC(ServicioCadenas srv, ClaseNoComponente clase) {
		return arg -> {
			srv.get().forEach(notify::add);
			clase.saluda();
			System.out.println("------------------------------>");
			notify.getListado().forEach(System.out::println);
			System.out.println("<------------------------------");
		};
	}
	@Bean
	CommandLineRunner contexto() {
		return arg -> {
			try (var contexto = new AnnotationConfigApplicationContext(AppConfig.class)) {
				var c1 = contexto.getBean(Configuracion.class);
				var c2 = contexto.getBean(Configuracion.class);
				System.out.println("c1 = %d".formatted(c1.getNext()));
				System.out.println("c2 = %d".formatted(c2.getNext()));
				System.out.println("c1 = %d".formatted(c1.getNext()));
				System.out.println("c2 = %d".formatted(c2.getNext()));
				System.out.println("c1 = %d".formatted(c1.getNext()));
//				var comp = new ClaseNoComponente(contexto.getBean(Configuracion.class)); //contexto.getBean(ClaseNoComponente.class);
//				comp.saluda();
				contexto.getBean(NotificationService.class).getListado().forEach(System.out::println);
			}
		};
	}

}
