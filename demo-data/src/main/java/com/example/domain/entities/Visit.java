package com.example.domain.entities;

import java.io.Serializable;
import jakarta.persistence.*;
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

	@Column(length=255)
	private String description;

	@Temporal(TemporalType.DATE)
	@Column(name="visit_date", nullable=false)
	private Date visitDate;

	//bi-directional many-to-one association to Pet
	@ManyToOne
	@JoinColumn(name="pet_id", nullable=false)
	private Pet pet;

	public Visit() {
	}


	public Visit(int id, Date visitDate) {
		super();
		this.id = id;
		this.visitDate = visitDate;
	}

	public Visit(int id, Date visitDate, String description) {
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

	public Date getVisitDate() {
		return this.visitDate;
	}

	public void setVisitDate(Date visitDate) {
		this.visitDate = visitDate;
	}

	public Pet getPet() {
		return this.pet;
	}

	public void setPet(Pet pet) {
		this.pet = pet;
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
		return "Visit [id=" + id + ", visitDate=" + visitDate + ", description=" + description + "]";
	}

}