package org.springframework.samples.aerolineasAAAFC.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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

	@Column(name = "fecha_nacimiento")
	@NotEmpty
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private Date fechaNacimiento;


	@OneToMany(cascade = CascadeType.ALL, mappedBy="cliente" ) //fetch = FetchType.EAGER 
	private Set<Billete> billetes;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "username", referencedColumnName = "username")
	private User user;
	
}
