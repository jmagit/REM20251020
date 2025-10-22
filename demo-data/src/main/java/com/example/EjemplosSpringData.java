package com.example;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.contracts.domain.repositories.PetsRepository;
import com.example.contracts.domain.repositories.VetsRepository;
import com.example.domain.entities.Pet;
import com.example.domain.entities.Type;
import com.example.domain.entities.Visit;

@Service
public class EjemplosSpringData {
	
	public void run() {
//		veterinarios();
		mascotas();
	}
	
	@Autowired
	VetsRepository daoVets;
	
	void veterinarios() {
		daoVets.findAll().forEach(System.out::println);
	}
	@Autowired
	PetsRepository daoPets;
	
	void mascotas() {
		daoPets.findAll().forEach(System.out::println);
		var m = new Pet(0, "Tobby", new Date("10/12/2022"), new Type(2));
		m.addVisit(new Visit(0,  new Date("10/12/2024"), "limpieza"));
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
}
