package org.springframework.samples.aerolineasAAAFC.model;


import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.springframework.samples.petclinic.model.User;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
* Entidad simple que representa un azafato
*/

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "azafato")
public class Azafato extends BaseEntity{

	// Atributos:
	
	@Column(name = "nombre")
	@NotEmpty
	private String nombre;
	
	@Column(name = "apellidos")
	@NotEmpty
	private String apellidos;
	
	@Column(name = "nif")
	@NotEmpty
	@Pattern(regexp="^\\d{8}[a-zA-Z]$")
	private String nif;
	
	@Column(name = "iban")
	@NotEmpty
	@Pattern(regexp="^ES\\d{22}$")
	private String iban;
	
	@Column(name = "idiomas")
	@NotEmpty
	private String idiomas;
	
	@Column(name = "salario")
	@NotEmpty
	private Double Salario;
	
	// Relaciones de tabla:
	
	@ManyToMany(cascade = CascadeType.ALL)
	private Set<Avion> aviones;
  
  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "username", referencedColumnName = "username")
	private User user;
	
}
