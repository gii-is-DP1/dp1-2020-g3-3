package org.springframework.samples.aerolineasAAAFC.model.menu;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.springframework.samples.aerolineasAAAFC.model.BaseEntity;
import org.springframework.samples.aerolineasAAAFC.model.Billete;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "menus")
public class Menu extends BaseEntity {

	// Atributos

	@ManyToOne(optional = false)
	@JoinColumn(name = "billete_id")
	@EqualsAndHashCode.Exclude
	private Billete billete;
	
	@OneToOne(optional = true,cascade = {CascadeType.ALL})
	@JoinColumn(name = "plato1_id")
	@EqualsAndHashCode.Exclude
	private Plato plato1;
	
	@OneToOne(optional = true,cascade = {CascadeType.ALL})
	@JoinColumn(name = "plato2_id")
	@EqualsAndHashCode.Exclude
	private Plato plato2;
	
	@OneToOne(optional = true,cascade = {CascadeType.ALL})
	@JoinColumn(name = "plato3_id")
	@EqualsAndHashCode.Exclude
	private Plato plato3;

}
