package org.springframework.samples.aerolineasAAAFC.model;


import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.PreRemove;
import javax.persistence.Table;
import javax.validation.constraints.Min;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Entidad simple que representa un azafato
 */

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "azafatos")
public class Azafato extends Person{

	// Atributos
	@ManyToMany
	@EqualsAndHashCode.Exclude 
	@OrderBy("idioma")
	@JoinTable(name = "idiomas_azafato", 
	joinColumns = @JoinColumn(name = "azafato_id"),
	inverseJoinColumns = @JoinColumn(name = "idioma_types_id"))
	private Set<IdiomaType> idiomas;

	@Column(name = "salario")
	@Min(value = 1000, message = "El salario debe ser mayor o igual a 1000.0 euros.")
	private Double salario;

	// Relaciones de tabla:

	@ManyToMany(mappedBy="azafatos")
	@OrderBy("fechaSalida DESC")
	@EqualsAndHashCode.Exclude 
	private Set<Vuelo> vuelos;
	
	@PreRemove
	private void removeAzafatosFromEntities() {
	    for (Vuelo v: vuelos) {
	        v.getAzafatos().remove(this);
	    }	   
	}
}
