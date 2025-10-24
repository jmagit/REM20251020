package com.example.model;

import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Scope;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

@Component
@Scope("prototype")
public class Saludo {
    public Saludo() {
        System.out.println("Nuevo bean Saludo creado, soy " + this);
    }
    
    @PostConstruct
    public void init() {
        System.out.println("Inicializando Saludo...");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("Destruyendo Saludo...");
    }

    public String obtenerMensaje() {
        return "¡Bean Saludo completamente operativo!";
    }
}

// version: Paso 7
//
//@Component
//public class Saludo {
//    @PostConstruct
//    public void init() {
//        System.out.println("Inicializando Saludo...");
//    }
//
//    @PreDestroy
//    public void destroy() {
//        System.out.println("Destruyendo Saludo...");
//    }
//
//    public String obtenerMensaje() {
//        return "¡Bean Saludo completamente operativo!";
//    }
//}

// version: Paso 3
//
//public class Saludo {
//    public String obtenerMensaje() {
//        return "Hola desde Spring IoC con Spring Boot!";
//    }
//}