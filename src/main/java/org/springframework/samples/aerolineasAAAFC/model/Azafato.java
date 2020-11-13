package org.springframework.samples.aerolineasAAAFC.model;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotEmpty;

/**
* Entidad simple que representa un señor/a azafato
*/

@MappedSuperclass
public class Azafato extends IdEntity{

	
	//Atributos
	
	@NotEmpty
	protected String nombre;
	
	@NotEmpty
	protected String apellidos;
	
	@NotEmpty
	protected String nif;
	
	@NotEmpty
	protected String iban;
	
	@NotEmpty
	protected Integer idiomas;
	
	@NotEmpty
	protected Double Salario;

	

	//Getters and Setters
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public Integer getIdiomas() {
		return idiomas;
	}

	public void setIdiomas(Integer idiomas) {
		this.idiomas = idiomas;
	}

	public Double getSalario() {
		return Salario;
	}

	public void setSalario(Double salario) {
		Salario = salario;
	}
	
}