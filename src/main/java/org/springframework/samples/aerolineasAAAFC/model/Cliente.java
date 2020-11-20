package org.springframework.samples.aerolineasAAAFC.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
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
	private String nombre;

	@Column(name = "apellidos")
	@NotEmpty
	private String apellidos;

	@Column(name = "nif")
	@NotEmpty
	@Pattern(regexp="^\\d{8}[a-zA-Z]$")
	private String nif;

	@Column(name = "direccion_facturacion")
	@NotEmpty
	private String direccionFacturacion;

	@Column(name = "iban")
	@NotEmpty
	@Pattern(regexp="^ES\\d{22}$")
	private String iban;

	@Column(name = "contrasenya")
	@NotEmpty
	private String contrasenya;

	@Column(name = "fecha_nacimiento")
	@NotEmpty
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private Date fechaNacimiento;

	
	//TODO relacion billete-cliente
	/*@OneToMany(cascade = CascadeType.ALL, mappedBy="cliente", fetch = FetchType.EAGER) 
	private Set<Billete> billetes;*/
	
}
