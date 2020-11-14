package org.springframework.samples.aerolineasAAAFC.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "billete")
public class Billete extends IdEntity{

	//Atributos

	
	@Column(name = "coste")
	protected double coste;

	@Column(name = "asiento")
	@NotEmpty
	@Pattern(regexp="^[A-I]\\d{2}$")
	protected String asiento;
	
	@Column(name = "fecha_reserva")
	@NotEmpty
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	protected LocalDate fechaReserva;
	
	@Column(name = "equipaje_facturado")
	@Range(min=0,max=3)
	protected Integer equipajeFacturado;
	
	@Column(name = "clase")
	@NotEmpty
	protected Clase clase;
	
}
