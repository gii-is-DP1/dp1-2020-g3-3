package org.springframework.samples.aerolineasAAAFC.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.aerolineasAAAFC.model.Cliente;
import org.springframework.samples.aerolineasAAAFC.model.PersonalOficina;

public interface PersonalOficinaRepository extends CrudRepository<PersonalOficina, Integer>{
	
	@Query("SELECT personalOficina FROM PersonalOficina personalOficina WHERE personalOficina.nif = :nif")
	public PersonalOficina findByNif(@Param("nif") String nif);
	
	@Query("SELECT personalOficina FROM PersonalOficina personalOficina WHERE personalOficina.iban = :iban")
	public PersonalOficina findByIban(@Param("iban") String iban);
	
	public Page<PersonalOficina> findAll(Pageable pageable);
	
}
