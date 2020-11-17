package org.springframework.samples.aerolineasAAAFC.repository;

import java.util.Collection;
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
	
	

	
	//TODO Hay que hacer el Query bien
	@Query("SELECT vuelo FROM Vuelo owner left join fetch vuelo WHERE vuelo.id =:id")
	public Vuelo findById(@Param("id") int id);

	//TODO Hay que hacer el Query bien
	@Query("SELECT vuelo FROM Vuelo owner left join fetch vuelo WHERE vuelo.id =:id")
	public Vuelo modificarFecha(@Param("date") Date date);
	
	//TODO Hay que hacer el Query bien
	@Query("SELECT vuelo FROM Vuelo owner left join fetch vuelo WHERE vuelo.id =:id")
	public Vuelo modificarHoraSalida(@Param("hora") Date hora);
	
	//TODO Hay que hacer el Query bien
	@Query("SELECT vuelo FROM Vuelo owner left join fetch vuelo WHERE vuelo.id =:id")
	public Vuelo modificarHoraLlegada(@Param("hora") Date hora);
	
	//TODO Hay que hacer el Query bien
	@Query("SELECT vuelo FROM Vuelo owner left join fetch vuelo WHERE vuelo.id =:id")
	public Vuelo modificarPrecio(@Param("precio") Double precio);
	
	//TODO Hay que hacer el Query bien
	@Query("SELECT vuelo FROM Vuelo owner left join fetch vuelo WHERE vuelo.id =:id")
	public Vuelo modificarCodigoIATAOrigen(@Param("codigo") String codigo);
	
	//TODO Hay que hacer el Query bien
	@Query("SELECT vuelo FROM Vuelo owner left join fetch vuelo WHERE vuelo.id =:id")
	public Vuelo modificarCodigoIATADestino(@Param("codigo") String codigo);
	
	//TODO Hay que hacer el Query bien
	@Query("SELECT vuelo FROM Vuelo owner left join fetch vuelo WHERE vuelo.id =:id")
	public Vuelo eliminarVuelo(@Param("id") int id);
	
	//TODO Hay que hacer el Query bien
	@Query("SELECT vuelo FROM Vuelo owner left join fetch vuelo WHERE vuelo.id =:id")
	public Vuelo crearVuelo();
}









