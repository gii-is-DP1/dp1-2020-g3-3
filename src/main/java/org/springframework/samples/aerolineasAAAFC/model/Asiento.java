package org.springframework.samples.aerolineasAAAFC.model;

import java.util.Set;

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
import javax.validation.constraints.Pattern;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "asientos")
public class Asiento extends BaseEntity{

	@Column(name = "nombre")
	@NotEmpty
	@Pattern(regexp="^[A-I]\\d{2}$")
	private String nombre;
	
	@Column(name = "libre")
	private boolean libre;
	
	@Column(name = "clase")
	//@NotEmpty
	@NotNull
	@Enumerated(EnumType.ORDINAL)
	private Clase clase;
	
	@ManyToOne(optional=false)
	@EqualsAndHashCode.Exclude
	@JoinColumn(name = "vuelo_id")
	private Vuelo vuelos;
	
	@OneToOne
	@EqualsAndHashCode.Exclude
	@JoinColumn(name = "billete_id")
	private Billete billete;
	
	
}











