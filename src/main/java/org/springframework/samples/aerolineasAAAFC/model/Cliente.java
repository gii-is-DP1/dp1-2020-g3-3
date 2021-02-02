package org.springframework.samples.aerolineasAAAFC.model;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.samples.aerolineasAAAFC.service.businessrules.MayoriaEdadConstraint;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Entidad simple que representa un cliente
 */

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "clientes")
public class Cliente extends Person{

	//Atributos

	@Column(name = "email")
	@Email
	@NotEmpty
	private String email;
	
	@Column(name = "direccion_facturacion")
	@NotEmpty
	private String direccionFacturacion;

	@Column(name = "fecha_nacimiento")
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@MayoriaEdadConstraint
	private LocalDate fechaNacimiento;

	// Relaciones de tabla

	@OneToMany(cascade = CascadeType.ALL, mappedBy="cliente") //fetch = FetchType.EAGER 
	@EqualsAndHashCode.Exclude
	private Set<Billete> billetes;

	public Integer getVersion() {
		return null;
	}
	
}
