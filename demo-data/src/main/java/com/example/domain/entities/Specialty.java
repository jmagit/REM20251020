package com.example.domain.entities;

import java.io.Serializable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.example.core.domain.entities.AbstractEntity;


/**
 * The persistent class for the specialties database table.
 * 
 */
@Entity
@Table(name="specialties")
@NamedQuery(name="Specialty.findAll", query="SELECT s FROM Specialty s")
public class Specialty extends AbstractEntity<Specialty> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private int id;

	@Column(nullable=false, length=80)
	@NotBlank
	@Size(min = 2, max = 80)
	private String name;

	//bi-directional many-to-many association to Vet
	@ManyToMany(mappedBy="specialties")
	private Set<Vet> vets;

	public Specialty() {
		vets = new HashSet<>();
	}

	public Specialty(int id) {
		this();
		this.id = id;
	}

	public Specialty(int id, String name) {
		this(id);
		this.name = name;
	}

	public Specialty(String name) {
		this(0, name);
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

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Specialty))
			return false;
		return id == ((Specialty) obj).id;
	}

	@Override
	public String toString() {
		return "Specialty [id=" + id + ", name=" + name + "]";
	}

	
}