package com.example.domain.entities;

import java.io.Serializable;
import jakarta.persistence.*;
import java.util.Set;


/**
 * The persistent class for the specialties database table.
 * 
 */
@Entity
@Table(name="specialties")
@NamedQuery(name="Specialty.findAll", query="SELECT s FROM Specialty s")
public class Specialty implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private int id;

	@Column(nullable=false, length=80)
	private String name;

	//bi-directional many-to-many association to Vet
	@ManyToMany(mappedBy="specialties")
	private Set<Vet> vets;

	public Specialty() {
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

	public Set<Vet> getVets() {
		return this.vets;
	}

	public void setVets(Set<Vet> vets) {
		this.vets = vets;
	}

}