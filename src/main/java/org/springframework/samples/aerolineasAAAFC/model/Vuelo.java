package org.springframework.samples.aerolineasAAAFC.model;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.format.annotation.DateTimeFormat;

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
	@NotEmpty
	@DateTimeFormat(pattern = "yyyy/MM/dd hh:mm")
	private LocalDate  fechaSalida;

	@Column(name = "fecha_llegada")
	@DateTimeFormat(pattern = "yyyy/MM/dd hh:mm")
	private LocalDate  fechaLlegada;
	
	@NotEmpty
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
	@UniqueElements
	@EqualsAndHashCode.Exclude
	private Aeropuerto aeropuertoOrigen;
	
	@ManyToOne(optional=false)
	@UniqueElements
	@EqualsAndHashCode.Exclude
	private Aeropuerto aeropuertoDestino;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@EqualsAndHashCode.Exclude
	private Set<Avion> aviones;
	
	
	
	
	//
	
	/*
	public String getCodigoIATAOrigen() {
		return this.getAeropuertoOrigen().getCodigoIATA();
	}
	
	public String getCodigoIATADestino() {
		return this.getAeropuertoDestino().getCodigoIATA();
	}

	public Set<Billete> getBilletes() {
		return this.billetes;
	}
	public void addBillete(Billete billete) {
		this.billetes.add(billete);
	}
	*/
}

