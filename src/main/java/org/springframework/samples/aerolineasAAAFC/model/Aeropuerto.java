package org.springframework.samples.aerolineasAAAFC.model;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotEmpty;

/**
 * Entidad simple que representa un aeropuerto
 */

@MappedSuperclass
public class Aeropuerto extends IdEntity{
	
	// Atributos
	
	@NotEmpty
	protected String nombre;
	
	@NotEmpty
	protected String localizacion;
	
	@NotEmpty
	protected String codigoIATA;
	
	@NotEmpty
	protected String telefono;
	
	// Getters y setters

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getLocalizacion() {
		return localizacion;
	}

	public void setLocalizacion(String localizacion) {
		this.localizacion = localizacion;
	}

	public String getCodigoIATA() {
		return codigoIATA;
	}

	public void setCodigoIATA(String codigoIATA) {
		this.codigoIATA = codigoIATA;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
}