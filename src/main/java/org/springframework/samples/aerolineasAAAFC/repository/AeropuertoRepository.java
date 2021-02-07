package org.springframework.samples.aerolineasAAAFC.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.aerolineasAAAFC.model.Aeropuerto;
import org.springframework.stereotype.Repository;

public interface AeropuertoRepository extends CrudRepository<Aeropuerto,Integer>{

	public Page<Aeropuerto> findAll(Pageable pageable);
	
}


