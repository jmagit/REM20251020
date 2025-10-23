package com.example.domain.entities.models;

import com.example.domain.entities.Owner;

import lombok.Data;

@Data
public class OwnerDTO {
	private int id;
	private String nombre;
	private String aplellidos;
	private String telefono;
	
	public OwnerDTO(int id, String firstName, String lastName, String telephone) {
		super();
		this.id = id;
		this.nombre = firstName;
		this.aplellidos = lastName;
		this.telefono = telephone;
	}

	public static Owner from(OwnerDTO source) {
		return new Owner(source.getId(), source.getNombre(), source.getAplellidos(), null, source.getTelefono());
	}

	public static OwnerDTO from(Owner source) {
		return new OwnerDTO(source.getId(), source.getFirstName(), source.getLastName(), source.getTelephone());
	}
	
}
