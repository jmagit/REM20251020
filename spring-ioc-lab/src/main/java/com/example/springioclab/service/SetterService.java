package com.example.springioclab.service;

import com.example.springioclab.model.Saludo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SetterService {

    private Saludo saludo;

    @Autowired
    public void setSaludo(Saludo saludo) {
        this.saludo = saludo;
    }

    public void saludar() {
        System.out.println("SetterService - saludo: " + saludo.obtenerMensaje());
    }
}
