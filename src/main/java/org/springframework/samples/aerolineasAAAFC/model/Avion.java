package org.springframework.samples.aerolineasAAAFC.model;

import java.util.Date;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotEmpty;

/**
 * Entidad simple que representa una aeronave
 */
@MappedSuperclass
public class Avion extends IdEntity{
	
	// Atributos
	
	@NotEmpty
	protected String tipoAvion;
	
	@NotEmpty
	protected Integer capacidadPasajero;
	
	@NotEmpty
	protected Integer pesoMaximoEquipaje;
	
	@NotEmpty
	protected Integer horasAcumuladas;
	
	@NotEmpty
	protected Date fechaFabricacion;
	
	@NotEmpty
	protected Boolean disponibilidad;
	
	//La fecha de Revision coincidirá con la fecha de fabricacion si no ha habido ninguna
	@NotEmpty
	protected Date fechaRevision;
	
	// Getters y setters

	public String getTipoAvion() {
		return tipoAvion;
	}

	public void setTipoAvion(String tipoAvion) {
		this.tipoAvion = tipoAvion;
	}

	public Integer getCapacidadPasajero() {
		return capacidadPasajero;
	}

	public void setCapacidadPasajero(Integer capacidadPasajero) {
		this.capacidadPasajero = capacidadPasajero;
	}

	public Integer getPesoMaximoEquipaje() {
		return pesoMaximoEquipaje;
	}

	public void setPesoMaximoEquipaje(Integer pesoMaximoEquipaje) {
		this.pesoMaximoEquipaje = pesoMaximoEquipaje;
	}

	public Integer getHorasAcumuladas() {
		return horasAcumuladas;
	}

	public void setHorasAcumuladas(Integer horasAcumuladas) {
		this.horasAcumuladas = horasAcumuladas;
	}

	public Date getFechaFabricacion() {
		return fechaFabricacion;
	}

	public void setFechaFabricacion(Date fechaFabricacion) {
		this.fechaFabricacion = fechaFabricacion;
	}

	public Boolean getDisponibilidad() {
		return disponibilidad;
	}

	public void setDisponibilidad(Boolean disponibilidad) {
		this.disponibilidad = disponibilidad;
	}
	public Date getFechaRevision() {
		return fechaRevision;
	}

	public void setFechaRevision(Date fechaRevision) {
		this.fechaRevision = fechaRevision;
	}
		
}