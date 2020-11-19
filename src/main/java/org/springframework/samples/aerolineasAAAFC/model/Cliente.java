package org.springframework.samples.aerolineasAAAFC.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Entidad simple que representa un cliente
 */

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "clientes")
public class Cliente extends BaseEntity{

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

	
	//TODO relacion billete-cliente
	/*@OneToMany(cascade = CascadeType.ALL, mappedBy="cliente", fetch = FetchType.EAGER) 
	private Set<Billete> billetes;*/
	
}
