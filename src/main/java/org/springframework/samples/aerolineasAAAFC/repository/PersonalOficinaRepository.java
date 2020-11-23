package org.springframework.samples.aerolineasAAAFC.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.Repository;
import org.springframework.samples.aerolineasAAAFC.model.PersonalOficina;

public interface PersonalOficinaRepository extends Repository<PersonalOficina, Integer>{
	
	// Guardado de un oficinista
	void save(PersonalOficina pOficina) throws DataAccessException;
	
	// Busqueda por ID
	PersonalOficina findById(int id) throws DataAccessException;

}
