package com.example.domain.entities;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import com.example.core.domain.entities.AbstractEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

/**
 * The persistent class for the pets database table.
 * 
 */
@Entity
@Table(name = "pets")
@NamedQuery(name = "Pet.findAll", query = "SELECT p FROM Pet p")
public class Pet extends AbstractEntity<Pet> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private int id;

	@Column(nullable = false, length = 30)
	@NotBlank
	@Size(min = 2, max = 30)
	private String name;

	@Temporal(TemporalType.DATE)
	@Column(name = "birth_date")
	@PastOrPresent
	private LocalDate birthDate;

	// bi-directional many-to-one association to Owner
	@ManyToOne
	@JoinColumn(name = "owner_id", nullable = false)
	private Owner owner;

	// bi-directional many-to-one association to Type
	@ManyToOne
	@JoinColumn(name = "type_id", nullable = false)
	private Type type;

	// bi-directional many-to-one association to Visit
	@OneToMany(mappedBy = "pet", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private List<@Valid Visit> visits;

	public Pet() {
		visits = new ArrayList<>();
	}

	public Pet(int id) {
		this();
		this.id = id;
	}

	public Pet(int id, String name, LocalDate birthDate, Type type) {
		this(id);
		this.birthDate = birthDate;
		this.name = name;
		this.type = type;
	}

	public Pet(int id, String name, LocalDate birthDate, Type type, Owner owner) {
		this(id, name, birthDate, type);
		this.owner = owner;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDate getBirthDate() {
		return this.birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
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
		if (owner == null)
			throw new IllegalArgumentException("Argumento null");
		this.owner = owner;
		if (!owner.getPets().contains(this))
			owner.addPet(this);
	}
	public void clearOwner() {
		this.owner = null;
		if (owner.getPets().contains(this))
			owner.removePet(this);
	}

	public Type getType() {
		return this.type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public List<Visit> getVisits() {
		return this.visits;
	}

	public void setVisits(List<Visit> visits) {
		if (visits == null)
			throw new IllegalArgumentException("Argumento null");
		this.visits.clear();
		visits.forEach(item -> addVisit(item));
	}

	public Visit addVisit(Visit visit) {
		if (visit == null)
			throw new IllegalArgumentException("Argumento null");
		visits.add(visit);
		if (visit.getPet() != this)
			visit.setPet(this);
		return visit;
	}

	public Visit removeVisit(Visit visit) {
		getVisits().remove(visit);
		if (visit.getPet() != null)
			visit.clearPet();
		return visit;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Pet))
			return false;
		Pet other = (Pet) obj;
		return id == other.id;
	}

	@Override
	public String toString() {
		return "Pet [id=" + id + ", name=" + name + ", birthDate=" + birthDate 
				+ (type == null ? "" : (", type=" + type.getName())) 
				+ (owner == null ? "" : (", owner=" + owner.getFirstName() + " " + owner.getLastName()))
				+ "]";
	}

	public Visit programarRevision() {
		if(visits.size() > 0 && visits.stream().max(Comparator.comparing(Visit::getVisitDate)).get().getVisitDate().isAfter(LocalDate.now()))
			return visits.stream().max(Comparator.comparing(Visit::getVisitDate)).get();
		var next = LocalDate.now().plusMonths(1);
		var visit = new Visit(0, 
				next.plusDays(switch (next.getDayOfWeek()) { case DayOfWeek.SATURDAY  -> 2; case DayOfWeek.SUNDAY -> 1; default -> 0;}), 
				"revision");
		addVisit(visit);
		return visit;
	}
}