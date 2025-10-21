package com.example.springioclab.service;

import com.example.springioclab.model.Saludo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FieldInjectionService {

    @Autowired
    private Saludo saludo;

    public void saludar() {
        System.out.println("FieldInjectionService - saludo: " + saludo.obtenerMensaje());
    }
}
