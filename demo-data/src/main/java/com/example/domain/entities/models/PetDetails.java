package com.example.domain.entities.models;

import java.time.LocalDate;
import java.util.List;

import com.example.domain.entities.Owner;
import com.example.domain.entities.Type;
import com.example.domain.entities.Visit;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.Valid;
import lombok.Data;

@Data
public class PetDetails {
	@JsonProperty("codigo")
	private int id;
	private String nombre;
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate fechaNacimiento;
	private String tipo;
	private String propietario;
	@JsonIgnore
	private List<String> visitas;
	
	public PetDetails(int id, String name, LocalDate birthDate, Type type, Owner owner/*, List<Visit> visits*/) {
		super();
		this.id = id;
		this.nombre = name;
		this.fechaNacimiento = birthDate; // Date.valueOf(birthDate);
		this.tipo = type.getName();
		this.propietario = owner.getFirstName() + (owner.getLastName() == null ? "" : " " + owner.getLastName());
//		this.visitas = visits.stream().map(item -> item.getVisitDate() + " " + item.getDescription()).toList();
	}

}
