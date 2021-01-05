package org.springframework.samples.aerolineasAAAFC.deprecated;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.samples.aerolineasAAAFC.model.BaseEntity;
import org.springframework.samples.aerolineasAAAFC.model.Billete;
import org.springframework.samples.aerolineasAAAFC.service.businessrules.Menu1AvailabilityConstraint;
import org.springframework.samples.aerolineasAAAFC.service.businessrules.Menu2AvailabilityConstraint;
import org.springframework.samples.aerolineasAAAFC.service.businessrules.Menu3AvailabilityConstraint;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
* Entidad simple que representa un men�
*/

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "menus")
public class Menu extends BaseEntity {
	
	//Atributos

	@Column(name = "precio")
	//No hace falta añadir @Positive, hacemos una comprobación de precio con excepciones
	private double precio;

	@Column(name = "primer_plato")
	@Menu1AvailabilityConstraint
	private String primerPlato;
	
	@Column(name = "segundo_plato")
	@Menu2AvailabilityConstraint
	private String segundoPlato;
	
	@Column(name = "postre")
	@Menu3AvailabilityConstraint
	private String postre;
	
	@ManyToOne(optional=false)
	@JoinColumn(name = "billete_id")
	@EqualsAndHashCode.Exclude 
	private Billete billete;
	
	public String toString() {
		
		return "Id menú "+this.getId()+" Precio "+this.getPrecio()+" Id Billete "+(this.getBillete()==null?"":this.getBillete().getId());
	}
	

}
