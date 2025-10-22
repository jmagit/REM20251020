package com.example.domain.entities;

import java.io.Serializable;
import jakarta.persistence.*;
import java.util.Set;


/**
 * The persistent class for the types database table.
 * 
 */
@Entity
@Table(name="types")
@NamedQuery(name="Type.findAll", query="SELECT t FROM Type t")
public class Type implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private int id;

	@Column(nullable=false, length=80)
	private String name;

	//bi-directional many-to-one association to Pet
	@OneToMany(mappedBy="type")
	private Set<Pet> pets;

	public Type() {
	}

	public Type(int id) {
		super();
		this.id = id;
	}

	public Type(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Pet> getPets() {
		return this.pets;
	}

	public void setPets(Set<Pet> pets) {
		this.pets = pets;
	}

	public Pet addPet(Pet pet) {
		getPets().add(pet);
		pet.setType(this);

		return pet;
	}

	public Pet removePet(Pet pet) {
		getPets().remove(pet);
		pet.setType(null);

		return pet;
	}

}