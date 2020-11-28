package org.springframework.samples.aerolineasAAAFC.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.aerolineasAAAFC.model.PersonalControl;

public interface PersonalControlRepository extends CrudRepository<PersonalControl, Integer>{
	
	@Query("SELECT DISTINCT personalControl FROM PersonalControl personalControl WHERE personalControl.nif LIKE :nif%")
	public PersonalControl findByNif(@Param("nif") String nif);
	
	@Query("SELECT DISTINCT personalControl FROM PersonalControl personalControl WHERE personalControl.iban LIKE :iban%")
	public PersonalControl findByIban(@Param("iban") String iban);

}
