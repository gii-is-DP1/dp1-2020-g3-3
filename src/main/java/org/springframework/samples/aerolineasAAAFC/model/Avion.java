package org.springframework.samples.aerolineasAAAFC.model;


import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

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
@Table(name = "aviones")
public class Avion extends BaseEntity{
	
	// Atributos:

	@Column(name = "tipo_avion")
	private String tipoAvion;

	@Column(name = "capacidad_pasajero")
	private Integer capacidadPasajero;
	
	@Column(name = "peso_maximo_equipaje")
	@Range(min=0,max=32)
	private Integer pesoMaximoEquipaje;
	
	@Column(name = "horas_acumuladas")
	private Integer horasAcumuladas;
	
	@Column(name = "fecha_fabricacion")
	@NotEmpty
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private Date fechaFabricacion;
	
	@Column(name = "disponibilidad")
	@NotEmpty
	private Boolean disponibilidad;
	
	@Column(name = "fecha_revision")
	@NotEmpty
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private Date fechaRevision;
	
	@Column(name = "plazas_economica")
	private int plazasEconomica;
	
	@Column(name = "plazas_ejecutiva")
	private int plazasEjecutiva;
	
	@Column(name = "plazas_primera")
	private int plazasPrimera;
	
	// Relaciones de tablas:
	
	@ManyToMany(mappedBy="aviones")
	private Set<Vuelo> vuelos;
	
	@ManyToMany(mappedBy="aviones")
	private Set<Azafato> azafatos;
	
	@ManyToMany(mappedBy="aviones")
	private Set<PersonalControl> personalControl;
	
}
