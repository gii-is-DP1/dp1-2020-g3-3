package org.springframework.samples.aerolineasAAAFC.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Entidad simple que representa un aeropuerto
 */

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "aeropuertos")
public class Aeropuerto extends BaseEntity{
	
	// Atributos:

	@Column(name = "nombre")
	@NotEmpty
	private String nombre;

	@Column(name = "localizacion")
	@NotEmpty
	private String localizacion;

	@Column(name = "codigo_IATA")
	@NotEmpty
	private String codigoIATA;

	@Column(name = "telefono")
	@NotEmpty
	@Pattern(regexp="^(\\+|\\d)[0-9]{7,16}$", message="El número de teléfono asociado a este aeropuerto no es correcto.")
	private String telefono;

	// Relaciones de tabla:
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="aeropuertoOrigen")
	@EqualsAndHashCode.Exclude
	@OrderBy("fechaSalida DESC")
	private Set<Vuelo> vuelosSalida;

	@OneToMany(cascade = CascadeType.ALL, mappedBy="aeropuertoDestino")
	@EqualsAndHashCode.Exclude
	@OrderBy("fechaLlegada DESC")
	private Set<Vuelo> vuelosLlegada;


	
}
