package org.springframework.samples.aerolineasAAAFC.model;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.samples.aerolineasAAAFC.model.equipaje.Equipaje;
import org.springframework.samples.aerolineasAAAFC.model.menu.Menu;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@EqualsAndHashCode(callSuper=true)
@Entity
@Table(name = "billetes")
public class Billete extends AuditableEntity{

	//Atributos
	@Column(name = "coste")
	@Positive
	private double coste;
	
	@Column(name = "fecha_reserva")
	@NotNull
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate fechaReserva;
	
	// Relaciones de tabla:
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "billete")
	@EqualsAndHashCode.Exclude
	private Set<Equipaje> equipajes;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "billete")
	@EqualsAndHashCode.Exclude 
	private Set<Menu> menus;
	
	@ManyToOne(optional=true)
	@JoinColumn(name = "cliente_id")
	private Cliente cliente;
	
	@OneToOne(optional=false) 
	@JoinColumn(name = "asiento_id")
	private Asiento asiento;
}
