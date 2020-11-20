package org.springframework.samples.aerolineasAAAFC.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "billete")
public class Billete extends BaseEntity{

	//Atributos

	
	@Column(name = "coste")
	private double coste;

	@Column(name = "asiento")
	@NotEmpty
	@Pattern(regexp="^[A-I]\\d{2}$")
	private String asiento;
	
	@Column(name = "fecha_reserva")
	@NotEmpty
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private Date fechaReserva;
	
	@Column(name = "clase")
	@NotEmpty
	private Clase clase;
	
	//TODO relacion billete-cliente
	/*
	@ManyToOne(optional=false)
	@JoinColumn(name = "cliente_id")
	private Cliente cliente;*/
}
