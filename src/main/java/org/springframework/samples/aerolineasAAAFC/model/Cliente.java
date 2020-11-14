package org.springframework.samples.aerolineasAAAFC.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Entidad simple que representa un cliente
 */

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "cliente")
public class Cliente extends IdEntity{

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

	@Column(name = "direccionFacturacion")
	@NotEmpty
	protected String direccionFacturacion;

	@Column(name = "iban")
	@NotEmpty
	@Pattern(regexp="^ES\\d{22}$")
	protected String iban;

	@Column(name = "contrasenya")
	@NotEmpty
	protected String contrasenya;

	@Column(name = "fechaNacimiento")
	@NotEmpty
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	protected Date fechaNacimiento;

}
