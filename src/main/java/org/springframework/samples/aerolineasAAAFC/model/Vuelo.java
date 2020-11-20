

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

	@Column(name = "fecha_vuelo")
	@NotEmpty
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate  fechaVuelo;
	
	@Column(name = "hora_salida")
	@DateTimeFormat(pattern = "hh:mm")
	private Date  horaSalida;

	@Column(name = "hora_llegada")
	@DateTimeFormat(pattern = "hh:mm")
	private Date  horaLlegada;
	
	@Column(name = "coste")
	private double coste;
	
	@Column(name = "codigo_IATA_origen")
	@NotEmpty
	private String codigoIATAOrigen;
	
	@Column(name = "codigo_IATA_destino")
	@NotEmpty
	private String codigoIATADestino;

		
	
}

