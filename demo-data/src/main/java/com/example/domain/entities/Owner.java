package com.example.domain.entities;

import java.io.Serializable;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import com.example.core.domain.entities.AbstractEntity;

/**
 * The persistent class for the owners database table.
 * 
 */
@Entity
@Table(name = "owners")
@NamedQuery(name = "Owner.findAll", query = "SELECT o FROM Owner o")
public class Owner extends AbstractEntity<Owner> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private int id;

	@Column(name = "first_name", nullable = false, length = 30)
	@NotBlank
	@Size(min = 2, max = 30)
	private String firstName;

	@Column(name = "last_name", nullable = false, length = 30)
	@NotBlank
	@Size(min = 2, max = 30)
	private String lastName;

	@Embedded
	@Valid
	private Direction direction;

	@Column(length = 20)
	@Size(max = 20)
	private String telephone;

	// bi-directional many-to-one association to Pet
	@OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
	private List<Pet> pets;

	public Owner() {
		this.pets = new ArrayList<>();
	}

	public Owner(int id) {
		this();
		this.id = id;
	}

	public Owner(int id, String firstName, String lastName) {
		this(id);
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Owner(int id, String firstName, String lastName, Direction direction, String telephone) {
		this(id, firstName, lastName);
		this.direction = direction;
		this.telephone = telephone;
	}

	public Owner(int id, String firstName, String lastName, String address, String city, String telephone) {
		this(id, firstName, lastName, new Direction(address, city), telephone);
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public List<Pet> getPets() {
		return this.pets;
	}

	public void setPets(List<Pet> pets) {
		if (pets == null)
			throw new IllegalArgumentException("Argumento null");
		this.pets.clear();
		pets.forEach(item -> addPet(item));
	}

	public Pet addPet(Pet pet) {
		if (pet == null)
			throw new IllegalArgumentException("Argumento null");
		getPets().add(pet);
		if (pet.getOwner() != this)
			pet.setOwner(this);
		return pet;
	}

	public Pet removePet(Pet pet) {
		getPets().remove(pet);
		if (pet.getOwner() != null)
			pet.clearOwner();
		return pet;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Owner))
			return false;
		Owner other = (Owner) obj;
		return id == other.id;
	}

	@Override
	public String toString() {
		return "Owner [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", " 
				+ (direction == null ? "[Sin direccion]" : direction)
				+ ", telephone=" + telephone + "]";
	}

	
}