package com.example.contracts.domain.repositories;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;

import com.example.domain.entities.Owner;

public interface OwnersRepository extends JpaRepository<Owner, Integer>, JpaSpecificationExecutor<Owner> {
	List<Owner> findTop3ByFirstNameStartingWithIgnoreCaseOrderByLastNameDesc(String prefijo);
	List<Owner> findTop3ByFirstNameStartingWithIgnoreCase(String prefijo, Sort orderBy);
	
	List<Owner> findByIdGreaterThanEqual(int primero);
	@Query("from Owner o where o.id >= ?1")
	List<Owner> findNuevosConJPQL(int primero);
	@NativeQuery("select * from owners o where o.id >= :primero")
	List<Owner> findNuevosConSQL(int primero);
	
}
