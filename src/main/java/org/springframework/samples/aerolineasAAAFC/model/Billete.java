package org.springframework.samples.aerolineasAAAFC.model;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.samples.aerolineasAAAFC.model.equipaje.Equipaje;
import org.springframework.samples.aerolineasAAAFC.model.menu.Menu;

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
	

	// Relaciones de tabla:

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "billete")
	@EqualsAndHashCode.Exclude
	//@Size(min = 0, max = 3) Actualmente se usan excepciones
	private Set<Equipaje> equipajes;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "billete")
	@EqualsAndHashCode.Exclude 
	private Set<Menu> menus;
	
	@ManyToOne(optional=true) //segun el model un billete no tiene por qué ir asociado a un cliente
	@JoinColumn(name = "cliente_id")
	private Cliente cliente;
	
	@OneToOne(optional=false) 
	@JoinColumn(name = "asiento_id")
	@NotEmpty
	private Asiento asiento;

//	@ManyToOne(optional = false)
//	@JoinColumn(name = "vuelo_id")
//	@EqualsAndHashCode.Exclude 
//	private Vuelo vuelos;

	public String toString() {
		
		return "Id de billete: " + this.getId();
	}

	public Integer getVersion() {
		return null;
	}
}
