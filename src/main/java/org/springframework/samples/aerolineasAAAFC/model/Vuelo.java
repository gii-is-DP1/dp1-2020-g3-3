

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

	@Column(name = "FECHAVUELO")
	@NotEmpty
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	protected LocalDate  fechaVuelo;
	
	@Column(name = "HORASALIDA")
	@DateTimeFormat(pattern = "hh:mm")
	protected Date  horaSalida;

	@Column(name = "HORALLEGADA")
	@DateTimeFormat(pattern = "hh:mm")
	protected Date  horaLlegada;
	
	@Column(name = "COSTE")
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

