package org.springframework.samples.aerolineasAAAFC.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
* Entidad simple que representa un avion
*/


@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "avion")
public class Avion extends IdEntity{
	
	//Atributos

	@Column(name = "tipoAvion")
	protected String tipoAvion;

	@Column(name = "capacidadPasajero")
	protected Integer capacidadPasajero;
	
	@Column(name = "pesoMaximoEquipaje")
	@Range(min=0,max=32)
	protected Integer pesoMaximoEquipaje;
	
	@Column(name = "horasAcumuladas")
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	protected Integer horasAcumuladas;
	
	@Column(name = "fechaFabricacion")
	@NotEmpty
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	protected Date fechaFabricacion;
	
	@Column(name = "disponibilidad")
	@NotEmpty
	protected Boolean disponibilidad;
	

	
}
