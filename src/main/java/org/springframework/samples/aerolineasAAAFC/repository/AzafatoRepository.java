package org.springframework.samples.aerolineasAAAFC.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.aerolineasAAAFC.model.Azafato;
import org.springframework.samples.aerolineasAAAFC.model.Cliente;
import org.springframework.samples.aerolineasAAAFC.model.IdiomaType;
import org.springframework.samples.aerolineasAAAFC.model.Vuelo;

public interface AzafatoRepository extends CrudRepository<Azafato, Integer>{

	@Query("SELECT azafato FROM Azafato azafato WHERE azafato.nif = :nif")
	public Azafato findByNif(@Param("nif") String nif);
	
	@Query("SELECT azafato FROM Azafato azafato WHERE azafato.iban = :iban")
	public Azafato findByIban(@Param("iban") String iban);

	@Query("SELECT itype FROM IdiomaType itype ORDER BY itype.idioma")
	List<IdiomaType> findIdiomaTypes() throws DataAccessException;
	
	public Page<Azafato> findAll(Pageable pageable);

}
