package org.springframework.samples.aerolineasAAAFC.model;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.samples.aerolineasAAAFC.service.businessrules.AeropuertoConstraint;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
* Entidad simple que representa un vuelo
*/

@AeropuertoConstraint(value={"aeropuertoOrigen","aeropuertoDestino"})
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "vuelos")

public class Vuelo extends BaseEntity{
	
	// Atributos:
	
	@Column(name = "fecha_salida")
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private LocalDateTime  fechaSalida;

	@Column(name = "fecha_llegada")
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private LocalDateTime  fechaLlegada;
	
	@NotNull
	@Positive
	@Column(name = "coste")
	private double coste;
	
	// Relaciones de tabla:

	@ManyToMany(mappedBy = "vuelos")
	@EqualsAndHashCode.Exclude
	private Set<Billete> billetes;
	
	@ManyToMany(mappedBy = "vuelos")
	@EqualsAndHashCode.Exclude
	private Set<PersonalOficina> personalOficina;
	
	
	@ManyToOne(optional=false)
	@EqualsAndHashCode.Exclude
	private Aeropuerto aeropuertoOrigen;
	
	@ManyToOne(optional=false)
	@EqualsAndHashCode.Exclude
	private Aeropuerto aeropuertoDestino;
	
	@ManyToOne(optional=false)
	@EqualsAndHashCode.Exclude
	@JoinColumn(name = "avion_id")
	private Avion avion;
	
	@ManyToMany(mappedBy="vuelos")
	@EqualsAndHashCode.Exclude
	private Set<Azafato> azafatos;
	
	@ManyToMany(mappedBy="vuelos")
	@EqualsAndHashCode.Exclude
	private Set<PersonalControl> personalControl;

	
}

