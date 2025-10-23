package com.example.domain.entities.models;

import org.springframework.beans.factory.annotation.Value;

public interface PetShort {
	int getId();
	@Value("#{target.name}")
	String getNombre();
}
