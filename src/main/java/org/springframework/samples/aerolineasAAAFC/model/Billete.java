package org.springframework.samples.aerolineasAAAFC.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "billete")
public class Billete extends IdEntity{

	@Column(name = "coste")
	@NotEmpty
	private Double coste;

	@Column(name = "asiento")
	@NotEmpty
	private String asiento;
	
	@Column(name = "fecha_reserva")
	@NotEmpty
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate fechaReserva;
	
	@Column(name = "equipaje_facturado")
	@NotEmpty
	@Range(min=0,max=3)
	private Integer equipajeFacturado;
	
	@Column(name = "clase")
	@NotEmpty
	private Clase clase;
	
}
