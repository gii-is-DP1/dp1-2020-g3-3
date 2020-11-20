package org.springframework.samples.aerolineasAAAFC.repository;

import java.util.Date;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.aerolineasAAAFC.model.Vuelo;



/**
 */

public interface VueloRepository extends Repository<Vuelo, Integer> {

	void save(Vuelo vuelo) throws DataAccessException;
	
	

	
	
	@Query("SELECT vuelo FROM Vuelos vuelo left join fetch vuelo WHERE vuelo.id =:id")
	public Vuelo findById(@Param("id") int id);

	
	@Query("UPDATE Vuelos vuelo SET vuelo.fecha_vuelo = fecha_vuelo WHERE vuelo.id =:id")
	public Vuelo modificarFecha(@Param("fecha_vuelo") Date fechaVuelo, @Param("id") int id);
	
	
	@Query("UPDATE Vuelos vuelo SET vuelo.hora_salida = hora_salida WHERE vuelo.id =:id")
	public Vuelo modificarHoraSalida(@Param("hora_salida") Date horaSalida, @Param("id") int id);
	
	
	@Query("UPDATE Vuelos vuelo SET vuelo.hora_llegada = hora_llegada WHERE vuelo.id =:id")
	public Vuelo modificarHoraLlegada(@Param("hora_llegada") Date horaLlegada, @Param("id") int id);
	
	
	@Query("UPDATE Vuelos vuelo SET vuelo.precio = precio WHERE vuelo.id =:id")
	public Vuelo modificarPrecio(@Param("precio") Double precio, @Param("id") int id);
	
	
	@Query("UPDATE Vuelos vuelo SET vuelo.codigo_IATA_origen = codigo_IATA_origen WHERE vuelo.id =:id")
	public Vuelo modificarCodigoIATAOrigen(
			@Param("codigo_IATA_origen") String codigoIATAOrigen, @Param("id") int id);
	
	
	@Query("UPDATE Vuelos vuelo SET vuelo.codigo_IATA_destino = codigo_IATA_destino WHERE vuelo.id =:id")
	public Vuelo modificarCodigoIATADestino(
			@Param("codigo_IATA_destino") String codigoIATADestino, @Param("id") int id);
	
	
	@Query("DELETE FROM Vuelos owner WHERE vuelo.id =:id")
	public Vuelo eliminarVuelo(@Param("id") int id);
	
	/*
	//TODO Hay que hacer el Query bien
	@Query("SELECT vuelo FROM Vuelo owner left join fetch vuelo WHERE vuelo.id =:id")
	public Vuelo crearVuelo();
	*/
}









