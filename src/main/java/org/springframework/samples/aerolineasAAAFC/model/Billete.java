	package org.springframework.samples.aerolineasAAAFC.model;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "billetes")
public class Billete extends BaseEntity{

	// Atributos:
	
	@Column(name = "coste")
	private double coste;

	@Column(name = "asiento")
	@NotEmpty
	@Pattern(regexp="^[A-I]\\d{2}$")
	private String asiento;
	
	@Column(name = "fecha_reserva")
	@NotNull
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate fechaReserva;
	
	@Column(name = "clase")
	@NotNull
	@Enumerated(EnumType.ORDINAL)
	private Clase clase;
	
	// Relaciones de tabla:

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "billete")
	private Set<Equipaje> equipajes;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "billete")
	private Set<Menu> menus;
	
	@ManyToOne(optional=true) //segun el model un billete no tiene por qué ir asociado a un cliente
	@JoinColumn(name = "cliente_id")
	private Cliente cliente;
	
	@ManyToMany(cascade = CascadeType.ALL)
	private Set<Vuelo> vuelos;
}
