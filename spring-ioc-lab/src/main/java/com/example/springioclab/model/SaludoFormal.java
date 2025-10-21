package com.example.springioclab.model;

import org.springframework.stereotype.Component;

@Component("formalSaludo")
public class SaludoFormal implements Saludo {

    @Override
    public String obtenerMensaje() {
        return "Buenos días, estimado usuario.";
    }
}
