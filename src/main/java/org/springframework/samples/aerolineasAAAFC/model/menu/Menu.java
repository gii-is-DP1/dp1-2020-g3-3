package org.springframework.samples.aerolineasAAAFC.model.menu;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
	
	//Atributos
	
	@ManyToOne(optional=false)
	@JoinColumn(name = "billete_id")
	@EqualsAndHashCode.Exclude 
	private Billete billete;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "menu")
	@EqualsAndHashCode.Exclude
	private Set<Plato> platos;
	

}
