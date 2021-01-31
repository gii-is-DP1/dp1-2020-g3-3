package org.springframework.samples.aerolineasAAAFC.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;
import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.aerolineasAAAFC.model.Vuelo;


public interface VueloRepository extends CrudRepository<Vuelo,Integer> {
	

/*
	@Query("UPDATE Vuelos vuelo SET vuelo.fecha_vuelo = fecha_vuelo WHERE vuelo.id =:id")
	public Vuelo modificarFecha(@Param("fecha_vuelo") Date fechaVuelo, @Param("id") int id);
	
	
	@Query("UPDATE Vuelos vuelo SET vuelo.hora_salida = hora_salida WHERE vuelo.id =:id")
	public Vuelo modificarHoraSalida(@Param("hora_salida") Date horaSalida, @Param("id") int id);
	
	
	@Query("UPDATE Vuelos vuelo SET vuelo.hora_llegada = hora_llegada WHERE vuelo.id =:id")
	public Vuelo modificarHoraLlegada(@Param("hora_llegada") Date horaLlegada, @Param("id") int id);
	
	
	@Query("UPDATE Vuelos vuelo SET vuelo.precio = precio WHERE vuelo.id =:id")
	public Vuelo modificarPrecio(@Param("precio") Double precio, @Param("id") int id);
	
	*/
	
	@Query("SELECT vuelo FROM Vuelo vuelo WHERE MONTH(vuelo.fechaSalida) = :mes AND YEAR(vuelo.fechaSalida) = :año ORDER BY vuelo.fechaSalida DESC")
	public Collection<Vuelo> findVuelosByDate(@Param("mes") int mes, @Param("año") int año);
	
	public Collection<Vuelo> findAllByOrderByFechaSalidaDesc();

	@Query("SELECT vuelo FROM Vuelo vuelo JOIN vuelo.personalControl p WHERE p.id = :pControlId AND MONTH(vuelo.fechaSalida) = :mes AND YEAR(vuelo.fechaSalida) = :año ORDER BY vuelo.fechaSalida ASC")
	public Collection<Vuelo> findVuelosControl(@Param("pControlId") int pControlId, @Param("mes")  int mes, @Param("año")  int año);
	
	@Query("SELECT vuelo FROM Vuelo vuelo JOIN vuelo.azafatos a WHERE a.id = :azafatoId AND MONTH(vuelo.fechaSalida) = :mes AND YEAR(vuelo.fechaSalida) = :año ORDER BY vuelo.fechaSalida ASC")
	public Collection<Vuelo> findVuelosAzafato(@Param("azafatoId") int azafatoId, @Param("mes")  int mes, @Param("año")  int año);
}









