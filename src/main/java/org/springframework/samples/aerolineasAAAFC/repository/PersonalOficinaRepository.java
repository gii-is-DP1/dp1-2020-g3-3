package org.springframework.samples.aerolineasAAAFC.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.aerolineasAAAFC.model.PersonalOficina;

public interface PersonalOficinaRepository extends CrudRepository<PersonalOficina, Integer>{
	
	@Query("SELECT DISTINCT personalOficina FROM PersonalOficina personalOficina WHERE personalOficina.nif LIKE :nif%")
	public PersonalOficina findByNif(@Param("nif") String nif);
	
	@Query("SELECT DISTINCT personalOficina FROM PersonalControl personalOficina WHERE personalOficina.iban LIKE :iban%")
	public PersonalOficina findByIban(@Param("iban") String iban);
	
}
