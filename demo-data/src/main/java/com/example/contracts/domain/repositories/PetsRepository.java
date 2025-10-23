package com.example.contracts.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.core.contracts.domain.repositories.RepositoryWithProjections;
import com.example.domain.entities.Pet;

public interface PetsRepository extends JpaRepository<Pet, Integer>, RepositoryWithProjections {

}
