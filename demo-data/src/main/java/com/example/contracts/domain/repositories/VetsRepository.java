package com.example.contracts.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.domain.entities.Vet;

public interface VetsRepository extends JpaRepository<Vet, Integer> {

}
