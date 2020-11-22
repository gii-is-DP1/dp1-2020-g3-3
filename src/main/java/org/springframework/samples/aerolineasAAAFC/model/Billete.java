package org.springframework.samples.aerolineasAAAFC.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "billete")
	private Set<Equipaje> equipajes;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "billete")
	private Set<Menu> menus;
	
	@ManyToOne(optional=false)
	@JoinColumn(name = "cliente_id")
	private Cliente cliente;
}
