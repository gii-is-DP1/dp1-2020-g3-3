package org.springframework.samples.aerolineasAAAFC.model;


import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
* Entidad simple que representa un personal de oficina
*/

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "personal_oficina")
public class PersonalOficina extends Person{

	// Atributos
	
	@Column(name = "salario")
	@Min(value = 1000, message = "El salario debe ser mayor o igual a 1000.0 euros.")
	private Double Salario;

	// Relaciones de tabla:
	
	@ManyToMany
	@OrderBy("fechaSalida DESC")
	@EqualsAndHashCode.Exclude	
	private Collection<Vuelo> vuelos;

	
}
