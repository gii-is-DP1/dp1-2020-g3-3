package org.springframework.samples.aerolineasAAAFC.repository;

import java.time.LocalDate;
import java.util.Collection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.aerolineasAAAFC.model.Billete;
import org.springframework.samples.aerolineasAAAFC.model.Cliente;

public interface BilleteRepository extends CrudRepository<Billete, Integer>{

	
	@Query("SELECT DISTINCT billete FROM Billete billete JOIN billete.cliente cliente WHERE upper(cliente.apellidos) LIKE :apellidos%")
	public Page<Billete> findByApellido(@Param("apellidos") String apellidos, Pageable page);
	
	@Query("SELECT billete FROM Billete billete WHERE billete.fechaReserva = :fecha")
	public Collection<Billete> findByFecha(@Param("fecha") LocalDate fecha);
	
	@Query("SELECT billete FROM Billete billete WHERE billete.cliente = :cliente")
	public Collection<Billete> findByCliente(@Param("cliente") Cliente cliente);
	
	public Page<Billete> findAll(Pageable page);
	
}
