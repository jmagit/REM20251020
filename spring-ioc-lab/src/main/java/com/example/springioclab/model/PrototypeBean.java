package com.example.springioclab.model;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component("prototypeBean")
@Scope("prototype")
public class PrototypeBean {

    private static int counter = 0;
    private final int id;

    public PrototypeBean() {
        id = ++counter;
    }

    @PostConstruct
    public void init() {
        System.out.println("PrototypeBean init, id=" + id);
    }

    @Override
    public String toString() {
        return "PrototypeBean#" + id;
    }
}
