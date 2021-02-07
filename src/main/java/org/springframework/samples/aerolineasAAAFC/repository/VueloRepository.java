package org.springframework.samples.aerolineasAAAFC.repository;

import java.time.LocalDateTime;
import java.util.Collection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.aerolineasAAAFC.model.Vuelo;

public interface VueloRepository extends CrudRepository<Vuelo,Integer> {
	
	
	@Query("SELECT vuelo FROM Vuelo vuelo WHERE MONTH(vuelo.fechaSalida) = :mes AND YEAR(vuelo.fechaSalida) = :año ORDER BY vuelo.fechaSalida DESC")
	public Page<Vuelo> findVuelosByDate(@Param("mes") int mes, @Param("año") int año, Pageable pageable);
	
	public Vuelo findVueloByfechaLlegada(LocalDateTime fechaLLegada);

	@Query("SELECT vuelo FROM Vuelo vuelo JOIN vuelo.personalControl p WHERE p.id = :pControlId AND MONTH(vuelo.fechaSalida) = :mes AND YEAR(vuelo.fechaSalida) = :año ORDER BY vuelo.fechaSalida ASC")
	public Collection<Vuelo> findVuelosControl(@Param("pControlId") int pControlId, @Param("mes")  int mes, @Param("año")  int año);
	
	@Query("SELECT vuelo FROM Vuelo vuelo JOIN vuelo.azafatos a WHERE a.id = :azafatoId AND MONTH(vuelo.fechaSalida) = :mes AND YEAR(vuelo.fechaSalida) = :año ORDER BY vuelo.fechaSalida ASC")
	public Collection<Vuelo> findVuelosAzafato(@Param("azafatoId") int azafatoId, @Param("mes")  int mes, @Param("año")  int año);
	
	public Page<Vuelo> findAllByOrderByFechaSalidaDesc(Pageable pageable);
	
	//QUERYS HOME (siempre hay que pasar fecha, ya que no tiene sentido obtener vuelos ya realizados en el portal de compra)

	//1 atributo de filtro
	@Query("SELECT vuelo FROM Vuelo vuelo WHERE vuelo.fechaSalida > :fecha ORDER BY vuelo.fechaSalida ASC")
	public Page<Vuelo> findVuelosConFecha(@Param("fecha") LocalDateTime fecha, Pageable pageable);
	
	//2 atributos de filtro
	@Query("SELECT vuelo FROM Vuelo vuelo WHERE vuelo.fechaSalida > :fecha AND vuelo.coste <= :precio ORDER BY vuelo.fechaSalida ASC")
	public Page<Vuelo> findVuelosConFechaYPrecio(@Param("fecha") LocalDateTime fecha, @Param("precio") double precio, Pageable pageable);
	
	@Query("SELECT vuelo FROM Vuelo vuelo WHERE vuelo.fechaSalida > :fecha AND vuelo.aeropuertoOrigen.codigoIATA = :aeropuertoSalida ORDER BY vuelo.fechaSalida ASC")
	public Page<Vuelo> findVuelosConFechaYOrigen(@Param("fecha") LocalDateTime fecha, @Param("aeropuertoSalida") String aeropuertoSalida, Pageable pageable);
	
	@Query("SELECT vuelo FROM Vuelo vuelo WHERE vuelo.fechaSalida > :fecha AND vuelo.aeropuertoDestino.codigoIATA = :aeropuertoDestino ORDER BY vuelo.fechaSalida ASC")
	public Page<Vuelo> findVuelosConFechaYDestino(@Param("fecha") LocalDateTime fecha, @Param("aeropuertoDestino") String aeropuertoDestino, Pageable pageable);
	
	//3 atributos de filtro
	@Query("SELECT vuelo FROM Vuelo vuelo WHERE vuelo.fechaSalida > :fecha AND vuelo.coste <= :precio AND vuelo.aeropuertoOrigen.codigoIATA = :aeropuertoSalida ORDER BY vuelo.fechaSalida ASC")
	public Page<Vuelo> findVuelosConFechaPrecioYOrigen(@Param("fecha") LocalDateTime fecha, @Param("precio") double precio, @Param("aeropuertoSalida") String aeropuertoSalida, Pageable pageable);
	
	@Query("SELECT vuelo FROM Vuelo vuelo WHERE vuelo.fechaSalida > :fecha AND vuelo.coste <= :precio AND vuelo.aeropuertoDestino.codigoIATA = :aeropuertoDestino ORDER BY vuelo.fechaSalida ASC")
	public Page<Vuelo> findVuelosConFechaPrecioYDestino(@Param("fecha") LocalDateTime fecha, @Param("precio") double precio, @Param("aeropuertoDestino") String aeropuertoDestino, Pageable pageable);
	
	@Query("SELECT vuelo FROM Vuelo vuelo WHERE vuelo.fechaSalida > :fecha AND (vuelo.aeropuertoOrigen.codigoIATA = :aeropuertoSalida AND vuelo.aeropuertoDestino.codigoIATA = :aeropuertoDestino) ORDER BY vuelo.fechaSalida ASC")
	public Page<Vuelo> findVuelosConFechaOrigenYDestino(@Param("fecha") LocalDateTime fecha, @Param("aeropuertoSalida") String aeropuertoSalida, @Param("aeropuertoDestino") String aeropuertoDestino, Pageable pageable);
	
	//4 atributos de filtro
	@Query("SELECT vuelo FROM Vuelo vuelo WHERE vuelo.fechaSalida > :fecha AND vuelo.coste <= :precio AND vuelo.aeropuertoOrigen.codigoIATA = :aeropuertoSalida AND vuelo.aeropuertoDestino.codigoIATA = :aeropuertoDestino ORDER BY vuelo.fechaSalida ASC")
	public Page<Vuelo> findVuelosConTodo(@Param("fecha") LocalDateTime fecha, @Param("precio") double precio, @Param("aeropuertoSalida") String aeropuertoSalida, @Param("aeropuertoDestino") String aeropuertoDestino, Pageable pageable);
}









