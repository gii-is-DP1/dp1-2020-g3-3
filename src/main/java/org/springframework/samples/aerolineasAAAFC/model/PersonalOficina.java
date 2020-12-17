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
	
//	@Column(name = "nombre")
//	@NotEmpty
//	protected String nombre;
//	
//	@Column(name = "apellidos")
//	@NotEmpty
//	protected String apellidos;
//	
//	@NotEmpty
//	@Pattern(regexp="^\\d{8}[a-zA-Z]$")
//	protected String nif;
//	
//	@NotEmpty
//	@Pattern(regexp="^ES\\s\\d{22}$")
//	protected String iban;
	
	@Column(name = "salario")
	@Min(value = 1000)
	private Double Salario;
	
	
//	@OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "username", referencedColumnName = "username")
//	@EqualsAndHashCode.Exclude
//	private User user;

	// Relaciones de tabla:
	
	@ManyToMany(cascade = CascadeType.ALL)
	@EqualsAndHashCode.Exclude
	private Collection<Vuelo> vuelos;
}
