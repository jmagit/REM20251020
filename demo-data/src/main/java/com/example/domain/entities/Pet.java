package com.example.domain.entities;

import java.io.Serializable;
import jakarta.persistence.*;
import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the pets database table.
 * 
 */
@Entity
@Table(name="pets")
@NamedQuery(name="Pet.findAll", query="SELECT p FROM Pet p")
public class Pet implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private int id;

	@Temporal(TemporalType.DATE)
	@Column(name="birth_date")
	private Date birthDate;

	@Column(nullable=false, length=30)
	private String name;

	//bi-directional many-to-one association to Owner
	@ManyToOne
	@JoinColumn(name="owner_id", nullable=false)
	private Owner owner;

	//bi-directional many-to-one association to Type
	@ManyToOne
	@JoinColumn(name="type_id", nullable=false)
	private Type type;

	//bi-directional many-to-one association to Visit
	@OneToMany(mappedBy="pet")
	private Set<Visit> visits;

	public Pet() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getBirthDate() {
		return this.birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Owner getOwner() {
		return this.owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}

	public Type getType() {
		return this.type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Set<Visit> getVisits() {
		return this.visits;
	}

	public void setVisits(Set<Visit> visits) {
		this.visits = visits;
	}

	public Visit addVisit(Visit visit) {
		getVisits().add(visit);
		visit.setPet(this);

		return visit;
	}

	public Visit removeVisit(Visit visit) {
		getVisits().remove(visit);
		visit.setPet(null);

		return visit;
	}

}