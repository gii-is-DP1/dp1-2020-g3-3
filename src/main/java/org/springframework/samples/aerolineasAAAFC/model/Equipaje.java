package org.springframework.samples.aerolineasAAAFC.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Column;
import org.hibernate.validator.constraints.Range;
import org.springframework.samples.aerolineasAAAFC.service.businessrules.MedidasEquipajesConstraint;

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
	@Range(min=3,max=32)
	private Integer peso;
	
	@Column(name = "dimensiones")
	@MedidasEquipajesConstraint
	private String dimensiones;	
	
	@ManyToOne(optional=false)
	@JoinColumn(name = "billete_id")
	@EqualsAndHashCode.Exclude
	private Billete billete;
	
	public String toString() {
		
		return "Id equipaje "+this.getId()+" Dimensiones "+this.getDimensiones()+" Id Billete "+(this.getBillete()==null?"":this.getBillete().getId());
	}
	
	
}
