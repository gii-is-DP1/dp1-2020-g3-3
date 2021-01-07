package org.springframework.samples.aerolineasAAAFC.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.aerolineasAAAFC.model.menu.PlatoBase;

public interface PlatoBaseRepository extends CrudRepository<PlatoBase, Integer>{

	@Query("SELECT pbase FROM PlatoBase pbase WHERE pbase.name =:name")
	public PlatoBase findByName(@Param("name") String name);
	
}
