package org.springframework.samples.aerolineasAAAFC.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PreRemove;

import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.samples.aerolineasAAAFC.service.businessrules.AeropuertoConstraint;
import org.springframework.samples.aerolineasAAAFC.service.businessrules.VueloPilotoConstraint;
import org.springframework.samples.aerolineasAAAFC.service.businessrules.VueloTCPConstraint;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
* Entidad simple que representa un vuelo
*/

@AeropuertoConstraint
@VueloPilotoConstraint
@VueloTCPConstraint
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "vuelos")
public class Vuelo extends BaseEntity{
	
	// Atributos:
	
	@Column(name = "fecha_salida")
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private LocalDateTime  fechaSalida;

	@Column(name = "fecha_llegada")
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private LocalDateTime  fechaLlegada;
	
	@NotNull
	@Positive
	@Column(name = "coste")
	private double coste;	// este coste pasa a ser el precio base del billete asociado a un avión, lo que sería clase turista sin ningún extra
							// de esta manera lo podemos usar para el filtro mediante precio, usando éste en lugar del de los billetes asociados, que no tienen coste fijo
	
	// Relaciones de tabla:
	
//  -- Personal
	@ManyToMany(mappedBy = "vuelos")
	@EqualsAndHashCode.Exclude
	@OrderBy("id")
	private Set<PersonalOficina> personalOficina;
	
	@ManyToMany
	@EqualsAndHashCode.Exclude
	@JoinTable(name = "azafatos_vuelo",
	   joinColumns = @JoinColumn(name = "vuelos_id"),
	   inverseJoinColumns = @JoinColumn(name= "azafatos_id"))
	@OrderBy("id")
	private Set<Azafato> azafatos;
	
	@ManyToMany(mappedBy="vuelos")
	@EqualsAndHashCode.Exclude
	@OrderBy("id")
	private Set<PersonalControl> personalControl;
	
//	-- Billete
//	@OneToMany(mappedBy = "vuelos")
//	@EqualsAndHashCode.Exclude
//	private Set<Billete> billetes;
	
	@OneToMany(mappedBy="vuelo", cascade = CascadeType.ALL) 
	@EqualsAndHashCode.Exclude
	private List<Asiento> asientos;
	
//	-- Aeropuertos y avión
	@ManyToOne(optional=false)
	@EqualsAndHashCode.Exclude
	private Aeropuerto aeropuertoOrigen;
	
	@ManyToOne(optional=false)
	@EqualsAndHashCode.Exclude
	private Aeropuerto aeropuertoDestino;
	
	@ManyToOne(optional=false)
	@EqualsAndHashCode.Exclude
	@JoinColumn(name = "avion_id")
	private Avion avion;
	
	
	@PreRemove
	private void removeVuelosFromEntities() {
	    for (Azafato a: azafatos) {
	        a.getVuelos().remove(this);
	    }	   
	    for (PersonalControl p: personalControl) {
	        p.getVuelos().remove(this);
	    }	
	    for (PersonalOficina p: personalOficina) {
	        p.getVuelos().remove(this);
	    }	
	}

}

