package com.example.domain.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.contracts.domain.repositories.PetsRepository;
import com.example.contracts.domain.services.PetsService;
import com.example.core.contracts.domain.exceptions.DuplicateKeyException;
import com.example.core.contracts.domain.exceptions.InvalidDataException;
import com.example.core.contracts.domain.exceptions.NotFoundException;
import com.example.domain.entities.Pet;
import com.example.domain.entities.Visit;


@Service
public class PetsServiceImpl implements PetsService {
	private PetsRepository dao;

	public PetsServiceImpl(PetsRepository dao) {
		this.dao = dao;
	}

	@Override
	public List<Pet> getAll() {
		return dao.findAll();
	}

	@Override
	public Iterable<Pet> getAll(Sort sort) {
		return dao.findAll(sort);
	}

	@Override
	public Page<Pet> getAll(Pageable pageable) {
		return dao.findAll(pageable);
	}

	@Override
	public <T> List<T> getByProjection(Class<T> type) {
		return dao.findAllBy(type);
	}

	@Override
	public <T> Iterable<T> getByProjection(Sort sort, Class<T> type) {
		return dao.findAllBy(sort, type);
	}

	@Override
	public <T> Page<T> getByProjection(Pageable pageable, Class<T> type) {
		return dao.findAllBy(pageable, type);
	}

	@Override
	public Optional<Pet> getOne(Integer id) {
		return dao.findById(id);
	}

	@Override
	public Pet add(Pet item) throws DuplicateKeyException, InvalidDataException {
		if(item == null)
			throw new InvalidDataException("No puede ser nulo");
		if(item.isInvalid())
			throw new InvalidDataException(item.getErrorsMessage(), item.getErrorsFields());
		if(item.getId() != 0 && dao.existsById(item.getId()))
			throw new DuplicateKeyException("Ya existe");
		return dao.save(item);
	}

	@Override
	public Pet modify(Pet item) throws NotFoundException, InvalidDataException {
		if(item == null)
			throw new InvalidDataException("No puede ser nulo");
		if(item.isInvalid())
			throw new InvalidDataException(item.getErrorsMessage(), item.getErrorsFields());
		if(!dao.existsById(item.getId()))
			throw new NotFoundException();
		return dao.save(item);
	}

	@Override
	public void delete(Pet item) throws InvalidDataException {
		if(item == null)
			throw new InvalidDataException("No puede ser nulo");
		dao.delete(item);
	}

	@Override
	public void deleteById(Integer id) {
		if(!dao.existsById(id))
			throw new NotFoundException();
		dao.deleteById(id);
	}

	@Override
	public Visit programarProximaRevision(Integer id) throws NotFoundException {
		var pet = dao.findById(id).orElseThrow(() -> new NotFoundException());
		var visit = pet.programarRevision();
		dao.save(pet);
		return visit;
		
	}
	
}
