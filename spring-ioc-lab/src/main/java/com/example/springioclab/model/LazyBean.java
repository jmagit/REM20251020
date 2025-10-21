package com.example.springioclab.model;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component("lazyBean")
@Lazy
public class LazyBean {

    public LazyBean() {
        System.out.println("LazyBean constructor called");
    }

    @PostConstruct
    public void init() {
        System.out.println("LazyBean @PostConstruct init");
    }

    @Override
    public String toString() {
        return "LazyBean instance";
    }
}
