package org.springframework.samples.aerolineasAAAFC.model;

import java.sql.Time;
import java.time.LocalDate;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
* Entidad simple que representa un vuelo
*/


@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "VUELOS")
public class Vuelo extends BaseEntity{
	
	// Atributos:

	@Column(name = "fecha_vuelo")
	@NotEmpty
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate  fechaVuelo;
	
	@Column(name = "hora_salida")
	@DateTimeFormat(pattern = "hh:mm")
	private Time  horaSalida;

	@Column(name = "hora_llegada")
	@DateTimeFormat(pattern = "hh:mm")
	private Time  horaLlegada;
	
	@Column(name = "coste")
	private double coste;
	
	// Relaciones de tabla:

	@ManyToMany(mappedBy = "VUELOS")
	private Set<Billete> billetes;
	
	@ManyToMany(mappedBy = "VUELOS")
	private Set<PersonalOficina> personalOficina;
	
	// Relacion con aeropuerto: ¿dos ManyToOne? o se hace algo diferente
	@ManyToOne(optional=false)
	private Aeropuerto aeropuerto;
	
	@ManyToMany(cascade = CascadeType.ALL)
	private Set<Avion> aviones;
	
	
	
	
	//getters
	
	public Set<Billete> getBilletes() {
		return this.billetes;
	}
	
	//setters
	
	//other
	
	public void addBillete(Billete billete) {
		this.billetes.add(billete);
	}
	
}

