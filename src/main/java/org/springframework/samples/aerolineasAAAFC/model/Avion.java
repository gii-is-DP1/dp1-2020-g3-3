package org.springframework.samples.aerolineasAAAFC.model;


import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
* Entidad simple que representa un avion
*/


@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "aviones")
public class Avion extends BaseEntity{
	
	// Atributos:

	@Column(name = "tipo_avion")
	private String tipoAvion;

	@Column(name = "capacidad_pasajero")
	private Integer capacidadPasajero;
	
	@Column(name = "horas_acumuladas")
	@PositiveOrZero
	private Integer horasAcumuladas;
	
	@Column(name = "fecha_fabricacion")
	@NotNull
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate fechaFabricacion;
	
	@Column(name = "disponibilidad")
	@NotNull
	private Boolean disponibilidad;
	
	@Column(name = "fecha_revision")
	@NotNull
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate fechaRevision;
	
	@Column(name = "plazas_economica")
	private int plazasEconomica;
	
	@Column(name = "plazas_ejecutiva")
	private int plazasEjecutiva;
	
	@Column(name = "plazas_primera")
	private int plazasPrimera;
	
	// Relaciones de tablas:
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="avion")
	@EqualsAndHashCode.Exclude
	@OrderBy("fechaSalida DESC")
	private List<Vuelo> vuelos;



}
