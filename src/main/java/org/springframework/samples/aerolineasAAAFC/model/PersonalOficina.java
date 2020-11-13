package org.springframework.samples.aerolineasAAAFC.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
* Entidad simple que representa un personal de oficina
*/

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "personalOficina")
public class PersonalOficina extends IdEntity{

	//Atributos
	
	@Column(name = "nombre")
	@NotEmpty
	protected String nombre;
	
	@Column(name = "apellidos")
	@NotEmpty
	protected String apellidos;
	
	@Column(name = "nif")
	@NotEmpty
	protected String nif;
	
	@Column(name = "iban")
	@NotEmpty
	protected String iban;
	
	@Column(name = "salario")
	@NotEmpty
	protected Double Salario;

	
}
