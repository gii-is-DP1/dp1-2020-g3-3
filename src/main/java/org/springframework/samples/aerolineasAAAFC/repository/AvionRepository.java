package org.springframework.samples.aerolineasAAAFC.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.aerolineasAAAFC.model.Avion;
import org.springframework.stereotype.Repository;

public interface AvionRepository extends CrudRepository<Avion, Integer>{
	
	public Page<Avion> findAll(Pageable pageable);
}
