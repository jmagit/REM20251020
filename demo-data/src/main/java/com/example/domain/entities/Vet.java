package com.example.domain.entities;

import java.io.Serializable;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
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
	@ManyToMany
	@JoinTable(
			name="vet_specialties"
			, joinColumns={
				@JoinColumn(name="vet_id", nullable=false)
				}
			, inverseJoinColumns={
				@JoinColumn(name="specialty_id", nullable=false)
				}
			)
	private Set<Specialty> specialties;

	public Vet() {
		specialties = new HashSet<>();
	}

	public Vet(int id) {
		this();
		this.id = id;
	}

	public Vet(String firstName, String lastName) {
		this();
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Vet(int id, String firstName, String lastName) {
		this();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
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

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Vet))
			return false;
		Vet other = (Vet) obj;
		return id == other.id;
	}

	@Override
	public String toString() {
		return "Vet [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + "]";
	}
	
	public void jubilate() {
		
	}

}