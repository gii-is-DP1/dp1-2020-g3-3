package org.springframework.samples.aerolineasAAAFC.model;

import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;
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
@Table(name = "equipaje")
  
public class Equipaje extends IdEntity{
	
	//Atributos
	
	@Column(name = "peso")
	@NotEmpty
	protected Integer peso;
	
	@Column(name = "dimensiones")
	@NotEmpty
	protected String dimensiones;	
	
}
