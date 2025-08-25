package com.example.beanlifecycle.bean;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;

@Component
public class HelloBean {

    private String message;

    public HelloBean() {
        System.out.println("HelloBean constructor called");
    }

    @PostConstruct
    public void init() {
        System.out.println("HelloBean @PostConstruct called");
        message = "Hello World from Spring Bean!";
    }

    public String getMessage() {
        return message;
    }

    @PreDestroy
    public void destroy() {
        System.out.println("HelloBean @PreDestroy called");
    }
}
