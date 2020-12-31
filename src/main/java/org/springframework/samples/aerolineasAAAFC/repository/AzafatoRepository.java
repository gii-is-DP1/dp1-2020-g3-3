package org.springframework.samples.aerolineasAAAFC.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.aerolineasAAAFC.model.Azafato;
import org.springframework.samples.aerolineasAAAFC.model.Vuelo;

/**
 * 
 * 
 * */
public interface AzafatoRepository extends CrudRepository<Azafato, Integer>{

	@Query("SELECT DISTINCT azafato FROM Azafato azafato WHERE azafato.nif LIKE :nif%")
	public Azafato findByNif(@Param("nif") String nif);
	
	@Query("SELECT DISTINCT azafato FROM Azafato azafato WHERE azafato.iban LIKE :iban%")
	public Azafato findByIban(@Param("iban") String iban);

	@Query("SELECT azafato.vuelos FROM Azafato azafato JOIN azafato.vuelos v WHERE MONTH(v.fechaSalida) = :mes AND YEAR(v.fechaSalida) = :año AND azafato.id = :id")
	public Collection<Vuelo> findVuelosByDate(@Param("id") int id, @Param("mes") int mes, @Param("año") int año);

}
