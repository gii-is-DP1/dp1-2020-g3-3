package org.springframework.samples.aerolineasAAAFC.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
* Entidad simple que representa un vuelo
*/


@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "vuelos")
public class Vuelo extends BaseEntity{
	
	// Atributos:
	
	@Column(name = "fecha_salida")
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime  fechaSalida;

	@Column(name = "fecha_llegada")
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
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
	
	// Relacion con aeropuerto: ¿dos ManyToOne? o se hace algo diferente
	@ManyToOne(optional=false)
//	@UniqueElements
	@EqualsAndHashCode.Exclude
	private Aeropuerto aeropuertoOrigen;
	
	@ManyToOne(optional=false)
//	@UniqueElements
	@EqualsAndHashCode.Exclude
	private Aeropuerto aeropuertoDestino;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@EqualsAndHashCode.Exclude
	private Set<Avion> aviones;
	
}

