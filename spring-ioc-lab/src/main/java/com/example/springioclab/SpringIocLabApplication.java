package com.example.springioclab;

import com.example.springioclab.service.PersonaService;
import com.example.springioclab.service.SetterService;
import com.example.springioclab.service.FieldInjectionService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SpringIocLabApplication implements CommandLineRunner {

    private final PersonaService personaService;
    private final SetterService setterService;
    private final FieldInjectionService fieldService;
    private final ApplicationContext ctx;

    public SpringIocLabApplication(PersonaService personaService,
                                   SetterService setterService,
                                   FieldInjectionService fieldService,
                                   ApplicationContext ctx) {
        this.personaService = personaService;
        this.setterService = setterService;
        this.fieldService = fieldService;
        this.ctx = ctx;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringIocLabApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("--- Laboratorio Spring IoC: Inversión de Control ---");

        System.out.println("\n1) Inyección por constructor (PersonaService):");
        personaService.decirHola();

        System.out.println("\n2) Inyección por setter (SetterService):");
        setterService.saludar();

        System.out.println("\n3) Inyección por campo (FieldInjectionService):");
        fieldService.saludar();

        System.out.println("\n4) Uso de @Primary y @Qualifier:");
        personaService.mostrarMensajeCalificado();

        System.out.println("\n5) Bean con scope prototype (creamos 2 instancias):");
        Object p1 = ctx.getBean("prototypeBean");
        Object p2 = ctx.getBean("prototypeBean");
        System.out.println("prototypeBean instance 1 == instance 2 ? " + (p1 == p2));

        System.out.println("\n6) Beans por perfil activo (consulta):");
        String[] profiles = ctx.getEnvironment().getActiveProfiles();
        System.out.println("Active profiles: " + java.util.Arrays.toString(profiles));
        if (ctx.containsBean("profileSaludo")) {
            Object bean = ctx.getBean("profileSaludo");
            System.out.println("Bean 'profileSaludo' presente: " + bean.getClass().getSimpleName());
        } else {
            System.out.println("Bean 'profileSaludo' no definido para el perfil activo.");
        }

        System.out.println("\n7) Bean @Lazy demo (no inicializado hasta uso):");
        System.out.println("Pidiendo lazyBean...");
        Object lazy = ctx.getBean("lazyBean");
        System.out.println("lazyBean obtenido: " + lazy.getClass().getSimpleName());

        System.out.println("\n--- Fin del laboratorio ---");
        // Close context to trigger @PreDestroy for singleton beans (only needed for demo)
        if (ctx instanceof org.springframework.context.ConfigurableApplicationContext cac) {
            cac.close();
        }
    }
}
