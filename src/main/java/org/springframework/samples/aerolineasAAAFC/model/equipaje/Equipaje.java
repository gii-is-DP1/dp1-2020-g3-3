package org.springframework.samples.aerolineasAAAFC.model.equipaje;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.persistence.Column;
import org.hibernate.validator.constraints.Range;
import org.springframework.samples.aerolineasAAAFC.model.BaseEntity;
import org.springframework.samples.aerolineasAAAFC.model.Billete;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "equipajes")
  
public class Equipaje extends BaseEntity{
	
	//Atributos
	
	@Column(name = "peso")
	@Range(min=3,max=32,message="El peso debe de estar entre los 3kg y 32kg")
	@NotNull(message="Debe introducir algún valor en el peso")
	private Integer peso;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "equipajes_base_id")
	@EqualsAndHashCode.Exclude
	private EquipajeBase equipajeBase;
	
	@ManyToOne(optional=false)
	@JoinColumn(name = "billete_id")
	@EqualsAndHashCode.Exclude
	private Billete billete;
	
}