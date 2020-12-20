package org.springframework.samples.aerolineasAAAFC.model;


import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.springframework.data.annotation.Id;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
* Entidad simple que representa un personal de oficina
*/

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "personal_oficina")
public class PersonalOficina extends Person{

	// Atributos
	
	@Column(name = "salario")
	@Min(value = 1000)
	private Double Salario;

	// Relaciones de tabla:
	
	@ManyToMany(cascade = CascadeType.ALL)
	@EqualsAndHashCode.Exclude
	private Collection<Vuelo> vuelos;
	
}
