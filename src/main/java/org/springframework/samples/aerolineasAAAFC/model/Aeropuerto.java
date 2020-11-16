package org.springframework.samples.aerolineasAAAFC.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Entidad simple que representa un aeropuerto
 */

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "aeropuerto")
public class Aeropuerto extends BaseEntity{
	
	//Atributos

	@Column(name = "nombre")
	@NotEmpty
	protected String nombre;

	@Column(name = "localizacion")
	@NotEmpty
	protected String localizacion;

	@Column(name = "codigoIATA")
	@NotEmpty
	protected String codigoIATA;

	@Column(name = "telefono")
	@NotEmpty
	@Pattern(regexp="^(\\+34|0034|34)?[6|7|9][0-9]{8}$")
	protected String telefono;

	

}
