package org.springframework.samples.aerolineasAAAFC.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.Repository;
import org.springframework.samples.aerolineasAAAFC.model.PersonalControl;

public interface PersonalControlRepository extends Repository<PersonalControl, Integer>{
	
	// Guardado de un personal de control
	void save(PersonalControl pOficina) throws DataAccessException;
	
	// Busqueda por ID
	PersonalControl findById(int id) throws DataAccessException;

}
