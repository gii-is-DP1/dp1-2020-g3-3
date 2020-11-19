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
	
	

	
	
	@Query("SELECT vuelo FROM Vuelo vuelo left join fetch vuelo WHERE vuelo.id =:id")
	public Vuelo findById(@Param("id") int id);

	
	@Query("UPDATE Vuelo vuelo SET vuelo.fechaVuelo = fechaVuelo WHERE vuelo.id =:id")
	public Vuelo modificarFecha(@Param("fechaVuelo") Date fechaVuelo, @Param("id") int id);
	
	
	@Query("UPDATE Vuelo vuelo SET vuelo.horaSalida = horaSalida WHERE vuelo.id =:id")
	public Vuelo modificarHoraSalida(@Param("horaSalida") Date horaSalida, @Param("id") int id);
	
	
	@Query("UPDATE Vuelo vuelo SET vuelo.horaLlegada = horaLlegada WHERE vuelo.id =:id")
	public Vuelo modificarHoraLlegada(@Param("horaLlegada") Date horaLlegada, @Param("id") int id);
	
	
	@Query("UPDATE Vuelo vuelo SET vuelo.precio = precio WHERE vuelo.id =:id")
	public Vuelo modificarPrecio(@Param("precio") Double precio, @Param("id") int id);
	
	
	@Query("UPDATE Vuelo vuelo SET vuelo.codigoIATAOrigen = codigoIATAOrigen WHERE vuelo.id =:id")
	public Vuelo modificarCodigoIATAOrigen(
			@Param("codigoIATAOrigen") String codigoIATAOrigen, @Param("id") int id);
	
	
	@Query("UPDATE Vuelo vuelo SET vuelo.codigoIATADestino = codigoIATADestino WHERE vuelo.id =:id")
	public Vuelo modificarCodigoIATADestino(
			@Param("codigoIATADestino") String codigoIATADestino, @Param("id") int id);
	
	
	@Query("DELETE FROM Vuelo owner WHERE vuelo.id =:id")
	public Vuelo eliminarVuelo(@Param("id") int id);
	
	/*
	//TODO Hay que hacer el Query bien
	@Query("SELECT vuelo FROM Vuelo owner left join fetch vuelo WHERE vuelo.id =:id")
	public Vuelo crearVuelo();
	*/
}









