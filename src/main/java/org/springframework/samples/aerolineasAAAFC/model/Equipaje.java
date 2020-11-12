package org.springframework.samples.aerolineasAAAFC.model;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotEmpty;

/**
 * Entidad simple que representa un equipaje
 */

@MappedSuperclass
public class Equipaje extends IdEntity{
	
	//Atributos
	
	@NotEmpty
	protected Integer peso;
	
	@NotEmpty
	protected String dimensiones;

	public Integer getPeso() {
		return peso;
	}
	
	// Getters y setters

	public void setPeso(Integer peso) {
		this.peso = peso;
	}

	public String getDimensiones() {
		return dimensiones;
	}

	public void setDimensiones(String dimensiones) {
		this.dimensiones = dimensiones;
	}
	
	

}
