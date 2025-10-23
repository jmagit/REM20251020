package com.example.domain.entities;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Date;
import java.util.Objects;


/**
 * The persistent class for the visits database table.
 * 
 */
@Entity
@Table(name="visits")
@NamedQuery(name="Visit.findAll", query="SELECT v FROM Visit v")
public class Visit implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private int id;

	@Temporal(TemporalType.DATE)
	@Column(name="visit_date", nullable=false)
	@NotNull
	private LocalDate visitDate;

	@Column(length=255)
	@Size(max = 255)
	private String description;

	//bi-directional many-to-one association to Pet
	@ManyToOne
	@JoinColumn(name="pet_id", nullable=false)
	private Pet pet;

	public Visit() {
	}


	public Visit(int id, LocalDate visitDate) {
		super();
		this.id = id;
		this.visitDate = visitDate;
	}

	public Visit(int id, LocalDate visitDate, String description) {
		super();
		this.id = id;
		this.visitDate = visitDate;
		this.description = description;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getVisitDate() {
		return this.visitDate;
	}

	public void setVisitDate(LocalDate visitDate) {
		this.visitDate = visitDate;
	}

	public Pet getPet() {
		return this.pet;
	}

	public void setPet(Pet pet) {
		if(pet == null)
			throw new IllegalArgumentException("Argumento null");
		this.pet = pet;
		if(!pet.getVisits().contains(this))
			pet.addVisit(this);
	}
	public void clearPet() {
		this.pet = null;
		if(pet.getVisits().contains(this))
			pet.removeVisit(this);
	}


	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Visit))
			return false;
		Visit other = (Visit) obj;
		return id == other.id;
	}


	@Override
	public String toString() {
		return "Visit [id=" + id + ", pet=" + pet.getName() + ", visitDate=" + visitDate + ", description=" + description + "]";
	}



}