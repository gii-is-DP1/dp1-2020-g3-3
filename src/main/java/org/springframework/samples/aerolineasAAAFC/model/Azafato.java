package org.springframework.samples.aerolineasAAAFC.model;


import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Entidad simple que representa un azafato
 */

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "azafatos")
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
	@Pattern(regexp="^ES\\s\\d{22}$")
	private String iban;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "idiomas_azafato", 
	joinColumns = @JoinColumn(name = "azafato_id"),
	inverseJoinColumns = @JoinColumn(name = "idioma_id"))
	@EqualsAndHashCode.Exclude 
	private Set<Idioma> idiomas;

	@Column(name = "salario")
	@NotEmpty
	private Double salario;

	// Relaciones de tabla:

	@ManyToMany(cascade = CascadeType.ALL)
	@EqualsAndHashCode.Exclude 
	private Set<Avion> aviones;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "username", referencedColumnName = "username")
	@EqualsAndHashCode.Exclude 
	private User user;

}
