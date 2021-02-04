package org.springframework.samples.aerolineasAAAFC.model;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PreRemove;

import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.samples.aerolineasAAAFC.service.businessrules.AeropuertoConstraint;
import org.springframework.samples.aerolineasAAAFC.service.businessrules.VueloPilotoConstraint;
import org.springframework.samples.aerolineasAAAFC.service.businessrules.VueloTCPConstraint;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
* Entidad simple que representa un vuelo
*/

@AeropuertoConstraint(value={"aeropuertoOrigen","aeropuertoDestino"})
@VueloPilotoConstraint
@VueloTCPConstraint
@Getter
@Setter
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
	
//  -- Personal
	@ManyToMany(mappedBy = "vuelos")
	@EqualsAndHashCode.Exclude
	private Set<PersonalOficina> personalOficina;
	
	@ManyToMany(mappedBy="vuelos")
	@EqualsAndHashCode.Exclude
	private Set<Azafato> azafatos;
	
	@ManyToMany(mappedBy="vuelos")
	@EqualsAndHashCode.Exclude
	private Set<PersonalControl> personalControl;
	
//	-- Billete
//	@OneToMany(mappedBy = "vuelos")
//	@EqualsAndHashCode.Exclude
//	private Set<Billete> billetes;
	
	@OneToMany(mappedBy="vuelo") 
	@EqualsAndHashCode.Exclude
	private Set<Asiento> asientos;
	
//	-- Aeropuertos y avión
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
	
	
	@PreRemove
	private void removeVuelosFromEntities() {
	    for (Azafato a: azafatos) {
	        a.getVuelos().remove(this);
	    }	   
	    for (PersonalControl p: personalControl) {
	        p.getVuelos().remove(this);
	    }	
	}


	public Integer getVersion() {
		return null;
	}


}

