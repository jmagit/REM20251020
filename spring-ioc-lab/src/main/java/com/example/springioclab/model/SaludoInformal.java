package com.example.springioclab.model;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component("informalSaludo")
@Primary
public class SaludoInformal implements Saludo {

    @Override
    public String obtenerMensaje() {
        return "¡Qué tal! (saludo informal)";
    }
}
