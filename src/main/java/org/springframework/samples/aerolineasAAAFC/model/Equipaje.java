package org.springframework.samples.aerolineasAAAFC.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Entidad simple que representa un equipaje
 */


@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "equipajes")
  
public class Equipaje extends BaseEntity{
	
	//Atributos
	
	@Column(name = "peso")
	@NotEmpty
	private Integer peso;
	
	@Column(name = "dimensiones")
	@NotEmpty
	private String dimensiones;	
	
	@ManyToOne(optional=false)
	@JoinColumn(name = "billete_id")
	private Billete billete;
	
}
