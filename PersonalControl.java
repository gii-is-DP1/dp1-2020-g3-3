package org.springframework.samples.aerolineasAAAFC.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
* Entidad simple que representa un personal de control
*/

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "personalControl")
public class PersonalControl extends IdEntity{

	//Atributos
	
	@Column(name = "nombre")
	@NotEmpty
	protected String nombre;
	
	@Column(name = "apellidos")
	@NotEmpty
	protected String apellidos;
	
	@Column(name = "nif")
	@NotEmpty
	@Pattern(regexp="^\\d{8}[a-zA-Z]$")
	protected String nif;
	
	@Column(name = "iban")
	@NotEmpty
	@Pattern(regexp="^ES\\d{22}$")
	protected String iban;
	
	@Column(name = "rol")
	@NotEmpty
	protected String rol;
	
	@Column(name = "salario")
	@NotEmpty
	protected Double Salario;

	
}
