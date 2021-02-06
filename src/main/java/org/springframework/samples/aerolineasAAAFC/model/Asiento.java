package org.springframework.samples.aerolineasAAAFC.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "asientos")
public class Asiento extends BaseEntity{

	@Column(name = "nombre")
	@NotEmpty
	@Pattern(regexp="^[A-I]{1}\\d{1,2}$", message="El nombre de este asiento no tiene un formato válido.")
	private String nombre;
	
	@Column(name = "libre")
	@NotNull
	private boolean libre;
	
	@Column(name = "clase")
	@NotNull
	@Enumerated(EnumType.ORDINAL)
	private Clase clase;
	
	@ManyToOne(optional=false)
	@EqualsAndHashCode.Exclude
	@JoinColumn(name = "vuelo_id")
	private Vuelo vuelo;
}