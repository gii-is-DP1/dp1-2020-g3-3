package org.springframework.samples.aerolineasAAAFC.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.aerolineasAAAFC.model.Aeropuerto;

public interface AeropuertoRepository extends Repository<Aeropuerto,Integer>{
	void save(Aeropuerto aeropuerto) throws DataAccessException;
	
	@Query("SELECT DISTINCT aeropuerto FROM Aeropuerto aeropuerto WHERE aeropuerto.id = :id")
	public Aeropuerto findById(@Param("id") int id) throws DataAccessException;
	
	//@Query("DELETE aeropuerto FROM Aeropuerto aeropuerto WHERE aeropuerto.id = :id")
	public Aeropuerto deleteById(@Param("id") int id) throws DataAccessException;


}



