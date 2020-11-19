

package org.springframework.samples.aerolineasAAAFC.model;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
* Entidad simple que representa un vuelo
*/


@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "vuelos")
public class Vuelo extends BaseEntity{
	
	//Atributos

	@Column(name = "fechaVuelo")
	@NotEmpty
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	protected LocalDate  fechaVuelo;
	
	@Column(name = "horaSalida")
	@DateTimeFormat(pattern = "hh:mm:ss")
	protected Date  horaSalida;

	@Column(name = "horaLlegada")
	@DateTimeFormat(pattern = "hh:mm:ss")
	protected Date  horaLlegada;
	
	@Column(name = "coste")
	protected double coste;
	
	
	/*
	@Column(name = "codigoIATAOrigen")
	@NotEmpty
	protected String codigoIATAOrigen;
	
	@Column(name = "codigoIATADestino")
	@NotEmpty
	protected String codigoIATADestino;
	*/

		
	
}

