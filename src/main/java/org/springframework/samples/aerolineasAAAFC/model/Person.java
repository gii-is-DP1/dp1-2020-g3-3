package org.springframework.samples.aerolineasAAAFC.model;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.springframework.samples.aerolineasAAAFC.service.businessrules.NifConstraint;
import org.springframework.samples.aerolineasAAAFC.service.businessrules.UsuarioConstraint;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@UsuarioConstraint
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@MappedSuperclass
public class Person extends BaseEntity{

	@Column(name = "nombre")
	@NotEmpty
	protected String nombre;
	
	@Column(name = "apellidos")
	@NotEmpty
	protected String apellidos;
	
	@Column(name = "nif", unique = true)
	@NotEmpty
	@NifConstraint
	protected String nif;
	
	@Column(name = "iban", unique = true)
	@NotEmpty
	@Pattern(regexp="^ES\\s\\d{22}$", message = "Debe corresponder al formato: ES, un espacio en blanco y 22 dígitos")
	protected String iban;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "username", referencedColumnName = "username")
	@EqualsAndHashCode.Exclude
	private User user;
	
}
