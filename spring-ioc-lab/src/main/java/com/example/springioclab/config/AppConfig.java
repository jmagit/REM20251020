package com.example.springioclab.config;

import com.example.springioclab.model.Saludo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public Saludo saludoPersonalizado() {
        return new Saludo() {
            @Override
            public String obtenerMensaje() {
                return "Hola desde un @Bean (AppConfig)!";
            }
        };
    }
}
