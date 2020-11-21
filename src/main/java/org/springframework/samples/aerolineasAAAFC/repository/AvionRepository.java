package org.springframework.samples.aerolineasAAAFC.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.Repository;
import org.springframework.samples.aerolineasAAAFC.model.Avion;

public interface AvionRepository extends Repository<Avion, Integer>{
	
	// Guarda o actualiza un avion
	void save(Avion avion) throws DataAccessException;
	
	// Busca por ID
	Avion findById(int id) throws DataAccessException;
	
}
