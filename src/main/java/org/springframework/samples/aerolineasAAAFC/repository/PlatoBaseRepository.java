package org.springframework.samples.aerolineasAAAFC.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.aerolineasAAAFC.model.menu.PlatoBase;

public interface PlatoBaseRepository extends CrudRepository<PlatoBase, Integer>{

	@Query("SELECT pbase FROM PlatoBase pbase WHERE pbase.name =:name")
	public PlatoBase findByName(@Param("name") String name);
	
	@Query("SELECT pbase FROM PlatoBase pbase")
	List<PlatoBase> findPlatosBase() throws DataAccessException;	
	
	@Query("SELECT pbase FROM PlatoBase pbase LEFT JOIN FETCH pbase.tipoPlato tipo WHERE tipo.name LIKE :tipoPlato")
	Collection<PlatoBase> findPlatosPorTipo(@Param("tipoPlato") String tipoPlato) throws DataAccessException;	
}
