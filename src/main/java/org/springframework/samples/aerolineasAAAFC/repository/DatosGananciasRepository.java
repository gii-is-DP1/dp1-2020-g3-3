package org.springframework.samples.aerolineasAAAFC.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.aerolineasAAAFC.model.Billete;
import org.springframework.samples.aerolineasAAAFC.model.DatosGanancias;

public interface DatosGananciasRepository extends CrudRepository<DatosGanancias, Integer>{
	
	@Query("SELECT billete FROM Billete billete WHERE billete.fechaReserva >= :fecha")
	public List<Billete> findByFecha(@Param("fecha") LocalDate fecha);
	
}