package org.springframework.samples.aerolineasAAAFC.model;


import java.time.LocalDate;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
* Entidad simple que representa un avion
*/


@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "aviones")
public class Avion extends BaseEntity{
	
	// Atributos:

	@Column(name = "tipo_avion")
	private String tipoAvion;

	@Column(name = "capacidad_pasajero")
	private Integer capacidadPasajero;
	
	@Column(name = "peso_maximo_equipaje")
	@Range(min=0,max=32)
	private Integer pesoMaximoEquipaje;
	
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
	private Set<Vuelo> vuelos;
	
}
