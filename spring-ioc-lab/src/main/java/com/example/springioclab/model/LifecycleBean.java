package com.example.springioclab.model;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;

@Component
public class LifecycleBean {

    @PostConstruct
    public void init() {
        System.out.println("LifecycleBean @PostConstruct - inicializando");
    }

    @PreDestroy
    public void beforeDestroy() {
        System.out.println("LifecycleBean @PreDestroy - antes de destruir");
    }
}
