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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import org.springframework.format.annotation.DateTimeFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper=true)
@Entity
@Table(name = "billetes")
public class Billete extends BaseEntity{

	// Atributos:
	
	@Column(name = "coste")
	@Positive
	private double coste;


	
	@Column(name = "fecha_reserva")
	//@NotEmpty
	@NotNull
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate fechaReserva;
	
	@Column(name = "clase")
	//@NotEmpty
	@NotNull
	@Enumerated(EnumType.ORDINAL)
	private Clase clase;
	
	// Relaciones de tabla:

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "billete")
	@EqualsAndHashCode.Exclude
	private Set<Equipaje> equipajes;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "billete")
	@EqualsAndHashCode.Exclude 
	private Set<Menu> menus;
	
	@ManyToOne(optional=true) //segun el model un billete no tiene por qué ir asociado a un cliente
	@JoinColumn(name = "cliente_id")
	private Cliente cliente;
	
	@OneToOne(optional=true) 
	@JoinColumn(name = "asiento_id")
	@NotEmpty
	private Asiento asiento;
	
	@ManyToOne
	private Vuelo vuelo;
	
	public String toString() {
		
		return "Id de billete: " + this.getId()+" Asiento: "+this.getAsiento()+
				" Menús: "+this.getMenus()+" Equipajes: "+this.getEquipajes();
	}
}
