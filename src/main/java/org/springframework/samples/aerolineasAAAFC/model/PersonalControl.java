package org.springframework.samples.aerolineasAAAFC.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.springframework.data.annotation.Id;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
* Entidad simple que representa un personal de control
*/

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "personal_control")
public class PersonalControl extends Person{

	// Atributos
	
	@Column(name = "rol")
	@NotEmpty
	@Enumerated(EnumType.ORDINAL)
	private Rol rol;
	
	@Column(name = "salario")
	@Min(value = 1000)
	private Double Salario;
	
	// Relaciones de tabla:
	
	@ManyToMany(cascade = CascadeType.ALL)
	@EqualsAndHashCode.Exclude
	private Set<Avion> aviones;

}
