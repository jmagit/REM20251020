package com.example.domain.entities;

import java.io.Serializable;
import jakarta.persistence.*;
import java.util.Set;


/**
 * The persistent class for the vets database table.
 * 
 */
@Entity
@Table(name="vets")
@NamedQuery(name="Vet.findAll", query="SELECT v FROM Vet v")
public class Vet implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private int id;

	@Column(name="first_name", nullable=false, length=30)
	private String firstName;

	@Column(name="last_name", length=30)
	private String lastName;

	//bi-directional many-to-many association to Specialty
	@ManyToMany(mappedBy="vets")
	private Set<Specialty> specialties;

	public Vet() {
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

	public Set<Specialty> getSpecialties() {
		return this.specialties;
	}

	public void setSpecialties(Set<Specialty> specialties) {
		this.specialties = specialties;
	}

}