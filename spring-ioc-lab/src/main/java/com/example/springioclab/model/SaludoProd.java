package com.example.springioclab.model;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component("profileSaludo")
@Profile("prod")
public class SaludoProd implements Saludo {

    @Override
    public String obtenerMensaje() {
        return "Bienvenido al sistema (perfil prod)";
    }
}
