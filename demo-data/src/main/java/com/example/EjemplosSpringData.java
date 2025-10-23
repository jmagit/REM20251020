package com.example;

import java.time.LocalDate;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.contracts.domain.repositories.OwnersRepository;
import com.example.contracts.domain.repositories.PetsRepository;
import com.example.contracts.domain.repositories.VetsRepository;
import com.example.domain.entities.Owner;
import com.example.domain.entities.Pet;
import com.example.domain.entities.Specialty;
import com.example.domain.entities.Type;
import com.example.domain.entities.Vet;
import com.example.domain.entities.Visit;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class EjemplosSpringData {
	ObjectMapper mapper;

	public EjemplosSpringData(ObjectMapper mapper) {
		super();
		this.mapper = mapper;
	}

	@Transactional
	public void run() {
//		veterinarios();
//		mascotas();
		try {
			transaccion();

		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		veterinarios();

	}

	@Autowired
	VetsRepository daoVets;

	void veterinarios() {
//		var vet = new Vet("Pepito", "Grillo");
//		vet.addSpecialty(new Specialty(1));
//		daoVets.save(vet);
		daoVets.findAll().forEach(System.out::println);
	}

	@Autowired
	PetsRepository daoPets;

	void mascotas() {
		daoPets.findAll().forEach(System.out::println);
		var m = new Pet(0, "Tobby", LocalDate.of(2022, 10, 12), new Type(2));
		m.addVisit(new Visit(0, LocalDate.of(2022, 10, 12), "limpieza"));
		m = daoPets.save(m);
		System.out.println(m);
		m.getVisits().forEach(System.out::println);
//		m = daoPets.findById(7).get();
//		System.out.println(m);
//		m.getVisits().forEach(System.out::println);
//		m.setName(m.getName().toUpperCase());
//		m = daoPets.save(m);
//		daoPets.deleteById(13);
//		daoPets.findAll().forEach(System.out::println);

	}

	@Autowired
	OwnersRepository daoOwners;

	@Transactional
	public void propietarios() {
//		System.out.println(">>> findTop3ByFirstNameStartingWithIgnoreCaseOrderByLastNameDesc");
//		daoOwners.findTop3ByFirstNameStartingWithIgnoreCaseOrderByLastNameDesc("j").forEach(System.out::println);
//		System.out.println(">>> findTop3ByFirstNameStartingWithIgnoreCase");
//		daoOwners.findTop3ByFirstNameStartingWithIgnoreCase("j", Sort.by("FirstName")).forEach(System.out::println);
//		System.out.println(">>> findByIdGreaterThanEqual");
//		daoOwners.findByIdGreaterThanEqual(10).forEach(System.out::println);
//		System.out.println(">>> findNuevosConJPQL");
//		daoOwners.findNuevosConJPQL(10).forEach(System.out::println);
//		System.out.println(">>> findNuevosConSQL");
		daoOwners.findNuevosConSQL(10).forEach(System.out::println);
//		System.out.println(">>> Specification");
//		daoOwners.findAll((root, query, builder) -> builder.greaterThanOrEqualTo(root.get("id"), 10)).forEach(System.out::println);
//		System.out.println(">>> Otra Specification");
//		daoOwners.findAll((root, query, builder) -> builder.lessThan(root.get("id"), 3)).forEach(System.out::println);
//		daoOwners.findAll((root, query, builder) -> builder.lessThan(root.get("id"), 3)).forEach(item -> {
//			System.out.println(item);
//			item.getPets().forEach(m -> System.out.println("\t" + m));
//		});
	}

	@Transactional
	void transaccion() {
		var vet = new Vet("Pepito", "Grillo");
		vet.addSpecialty(new Specialty(1));
		daoVets.save(vet);
		daoOwners.save(new Owner(0, "Pepe", "Illo"));
		daoOwners.save(new Owner(0));
	}
	
	void validaciones() {
		var o = new Owner(0, "PP", "ddd", "ddd", null, "12345678z");
		System.out.println(o);
		if(o.isValid()) {
			System.out.println("es valido");
		} else {
			System.err.println(o.getErrorsMessage());
		}
	}
}
