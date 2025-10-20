package com.example.ioc;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.ioc.contratos.Configuracion;

import jakarta.annotation.PostConstruct;

public class ClaseNoComponente {
	private NotificationService notify;

	@Autowired
	public void setNotify(NotificationService notify) {
		notify.add("me configuran");
		this.notify = notify;
	}

	private final Configuracion configuracion;
	
	public ClaseNoComponente(@Autowired(required = false) Configuracion configuracion) {
		this.configuracion = configuracion;
//		notify.add(getClass().getSimpleName() + " Constructor");
	}
	public ClaseNoComponente() {
		this.configuracion = null;
//		notify.add(getClass().getSimpleName() + " Constructor");
	}
	@PostConstruct
	private void despuesDelConstructor() {
		notify.add(getClass().getSimpleName() + " Constructor");
	}
	
	public void saluda() {
		notify.add("Hola mundo");
	}
}
