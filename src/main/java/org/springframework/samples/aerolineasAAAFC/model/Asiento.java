package org.springframework.samples.aerolineasAAAFC.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "asientos")
public class Asiento extends BaseEntity{

	@Column(name = "nombre")
	@NotEmpty
	@Pattern(regexp="^[A-I]\\d{2}$")
	private String nombre;
	
	@Column(name = "libre")
	@NotEmpty
	private boolean libre;
	
	@ManyToOne(optional=false)
	@EqualsAndHashCode.Exclude
	@JoinColumn(name = "vuelo_id")
	private Vuelo vuelo;
	
	@OneToOne
	@EqualsAndHashCode.Exclude
	@JoinColumn(name = "billete_id")
	private Billete billete;
	
	
}











