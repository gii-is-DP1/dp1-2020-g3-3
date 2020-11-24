package org.springframework.samples.aerolineasAAAFC.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.Repository;
import org.springframework.samples.aerolineasAAAFC.model.Azafato;
import org.springframework.samples.aerolineasAAAFC.model.PersonalOficina;

/**
 * 
 * 
 * */
public interface AzafatoRepository extends Repository<Azafato, Integer>{

	// Guardado de un azafato
	void save(Azafato azafato) throws DataAccessException;
	
	// Busqueda por ID
	Azafato findById(int id) throws DataAccessException;

}
