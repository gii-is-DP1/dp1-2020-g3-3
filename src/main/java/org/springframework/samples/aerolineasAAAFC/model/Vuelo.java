package org.springframework.samples.aerolineasAAAFC.model;

import java.sql.Time;
import java.time.LocalDate;
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
@Table(name = "VUELOS")
public class Vuelo extends BaseEntity{
	
	//Atributos

	@Column(name = "fecha_vuelo")
	@NotEmpty
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate  fechaVuelo;
	
	@Column(name = "hora_salida")
	@DateTimeFormat(pattern = "hh:mm")
	private Time  horaSalida;

	@Column(name = "hora_llegada")
	@DateTimeFormat(pattern = "hh:mm")
	private Time  horaLlegada;
	
	@Column(name = "coste")
	private double coste;

	
}

