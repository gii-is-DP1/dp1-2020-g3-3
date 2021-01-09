package org.springframework.samples.aerolineasAAAFC.model.equipaje;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.persistence.Column;
import org.springframework.samples.aerolineasAAAFC.model.BaseEntity;
import org.springframework.samples.aerolineasAAAFC.service.businessrules.MedidasEquipajesConstraint;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "equipajes_base")
  
public class EquipajeBase extends BaseEntity{
	
	//Atributos
	@Column(name = "dimensiones")
	@MedidasEquipajesConstraint
	private String dimensiones;	
	
	@Column(name = "precio")
	private double precio;
	
	@Column(name = "name")
	@NotEmpty
	private String name;
	
}
