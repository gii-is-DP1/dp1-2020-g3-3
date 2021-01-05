package org.springframework.samples.aerolineasAAAFC.model.menu;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Positive;

import org.springframework.samples.aerolineasAAAFC.model.BaseEntity;
import org.springframework.samples.aerolineasAAAFC.model.Billete;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "menus2")
public class Menu2 extends BaseEntity {
	
	//Atributos

	@Column(name = "name")
	@Positive
	private double name;

	
	@ManyToOne(optional=false)
	@JoinColumn(name = "billete_id")
	@EqualsAndHashCode.Exclude 
	private Billete billete;
	

}
