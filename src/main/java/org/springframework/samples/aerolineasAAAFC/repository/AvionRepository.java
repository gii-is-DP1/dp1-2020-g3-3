package org.springframework.samples.aerolineasAAAFC.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.aerolineasAAAFC.model.Avion;
import org.springframework.stereotype.Repository;

public interface AvionRepository extends CrudRepository<Avion, Integer>{
	
	
}
