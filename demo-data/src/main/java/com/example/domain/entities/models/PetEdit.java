package com.example.domain.entities.models;

import java.time.LocalDate;

import com.example.domain.entities.Owner;
import com.example.domain.entities.Type;

import lombok.Data;

@Data
public class PetEdit {
	private int id;
	private String nombre;
	private LocalDate fechaNacimiento;
	private int tipo;
	private int propietario;

	public PetEdit(int id, String name, LocalDate birthDate, Type type, Owner owner) {
		super();
		this.id = id;
		this.nombre = name;
		this.fechaNacimiento = birthDate; // Date.valueOf(birthDate);
		this.tipo = type.getId();
		this.propietario = owner.getId();
	}

}
