package com.example.contracts.domain.services;

import com.example.core.contracts.domain.exceptions.NotFoundException;
import com.example.core.contracts.domain.services.PagingAndSortingDomainService;
import com.example.core.contracts.domain.services.ProjectionDomainService;
import com.example.domain.entities.Pet;
import com.example.domain.entities.Visit;

public interface PetsService extends ProjectionDomainService<Pet, Integer> {
	Visit programarProximaRevision(Integer id) throws NotFoundException;
}
