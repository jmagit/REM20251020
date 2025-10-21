package com.example;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.example.ioc.AppConfig;
import com.example.ioc.ClaseNoComponente;
import com.example.ioc.NotificationService;
import com.example.ioc.Rango;
import com.example.ioc.anotaciones.Remoto;
import com.example.ioc.contratos.Configuracion;
import com.example.ioc.contratos.Servicio;
import com.example.ioc.contratos.ServicioCadenas;
import com.example.ioc.notificaciones.ConstructorConValores;
import com.example.ioc.notificaciones.Sender;

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

//	@Bean
	CommandLineRunner cadenaDeDependencias(ServicioCadenas srv) {
		return args -> {
			srv.get().forEach(notify::add);
			srv.add("Hola mundo");
			notify.add(srv.get(1));
			srv.modify("modificado");
			System.out.println("===================>");
			notify.getListado().forEach(System.out::println);
			notify.clear();
			System.out.println("<===================");

		};
	}

//	@Bean
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
//	@Bean
	CommandLineRunner porNombre(Sender correo, Sender fichero, Sender twittea) {
		return arg -> {
			correo.send("Hola mundo");
			fichero.send("Hola mundo");
			twittea.send("Hola mundo");
		};
	}
//	@Bean
	CommandLineRunner cualificados(@Qualifier("local") Sender local, @Remoto Sender remoto, Sender primario) {
		return arg -> {
			primario.send("Hola por defecto");
			local.send("Hola local");
			remoto.send("Hola remoto");
		};
	}
//	@Bean
	CommandLineRunner cualificados(List<Sender> senders, Map<String, Sender> mapa, List<Servicio> servicios) {
		return arg -> {
			senders.forEach(s -> s.send(s.getClass().getCanonicalName()));
			mapa.forEach((k, v) -> System.out.println("%s -> %s".formatted(k, v.getClass().getCanonicalName())));
			servicios.forEach(s -> System.out.println(s.getClass().getCanonicalName()));
		};
	}

//	@Bean
	CommandLineRunner valores(ConstructorConValores obj, @Value("${mi.valor:Sin valor}") String cad, Rango rango ) {
		return arg -> {
			notify.add(cad);
			notify.add(rango.toString());
			notify.getListado().forEach(System.out::println);
		};
	}

	@Bean
	CommandLineRunner xml() {
		return arg -> {
//			System.out.println(System.getProperty("java.class.path"));
			try (var contexto = new FileSystemXmlApplicationContext("applicationContext.xml")) {
				var notify = contexto.getBean(NotificationService.class);
				System.out.println("===================>");
				var srv = (ServicioCadenas)contexto.getBean("servicioCadenas");
				System.out.println(srv.getClass().getName());
				contexto.getBean(NotificationService.class).getListado().forEach(System.out::println);
				System.out.println("===================>");
				srv.get().forEach(notify::add);
				srv.add("Hola mundo");
				notify.add(srv.get(1));
				srv.modify("modificado");
				System.out.println("===================>");
				notify.getListado().forEach(System.out::println);
				notify.clear();
				System.out.println("<===================");
				((Sender)contexto.getBean("sender")).send("Hola mundo");
			}
		};
	}


}
