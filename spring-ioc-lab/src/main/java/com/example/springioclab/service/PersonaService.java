package com.example.springioclab.service;

import com.example.springioclab.model.Saludo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class PersonaService {

    private final Saludo saludoDefault;
    private final Saludo saludoPersonalizado;

    public PersonaService(Saludo saludoDefault, @Qualifier("saludoPersonalizado") Saludo saludoPersonalizado) {
        this.saludoDefault = saludoDefault;
        this.saludoPersonalizado = saludoPersonalizado;
    }

    public void decirHola() {
        System.out.println("PersonaService - decirHola: " + saludoDefault.obtenerMensaje());
    }

    public void mostrarMensajeCalificado() {
        System.out.println("PersonaService - saludo personalizado: " + saludoPersonalizado.obtenerMensaje());
    }
}
