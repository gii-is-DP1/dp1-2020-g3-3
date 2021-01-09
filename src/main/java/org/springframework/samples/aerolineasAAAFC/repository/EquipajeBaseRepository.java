package org.springframework.samples.aerolineasAAAFC.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.aerolineasAAAFC.model.equipaje.EquipajeBase;

public interface EquipajeBaseRepository extends CrudRepository<EquipajeBase, Integer>{

	@Query("SELECT ebase FROM EquipajeBase ebase WHERE ebase.name =:name")
	public EquipajeBase findByName(@Param("name") String name);
	
	@Query("SELECT ebase FROM EquipajeBase ebase")
	List<EquipajeBase> findEquipajesBase() throws DataAccessException;	
}
