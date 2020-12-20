package org.springframework.samples.aerolineasAAAFC.model;


import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Entidad simple que representa un azafato
 */

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "azafatos")
public class Azafato extends Person{

	// Atributos
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "idiomas_azafato", 
	joinColumns = @JoinColumn(name = "azafato_id"),
	inverseJoinColumns = @JoinColumn(name = "idioma_id"))
	@EqualsAndHashCode.Exclude 
	private Set<Idioma> idiomas;

	@Column(name = "salario")
	@Min(value = 1000)
	private Double salario;

	// Relaciones de tabla:

	@ManyToMany(cascade = CascadeType.ALL)
	@EqualsAndHashCode.Exclude 
	private Set<Avion> aviones;
	
	public String toString() {
		
		return "Id azafato "+this.getId()+" User "+this.getNif()+" Id Idiomas " + this.getIdiomas();
	}

}
