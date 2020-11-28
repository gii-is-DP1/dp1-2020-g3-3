package org.springframework.samples.aerolineasAAAFC.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.aerolineasAAAFC.model.Cliente;

public interface ClienteRepository extends CrudRepository<Cliente,Integer>{

	
	@Query("SELECT cliente FROM Cliente cliente WHERE cliente.nif LIKE :nif%")
	public Cliente findByNif(@Param("nif") String nif);
	
	
}
