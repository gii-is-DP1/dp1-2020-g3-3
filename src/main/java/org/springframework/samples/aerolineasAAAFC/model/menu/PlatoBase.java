package org.springframework.samples.aerolineasAAAFC.model.menu;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

import org.springframework.samples.aerolineasAAAFC.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "platos_base")
public class PlatoBase extends BaseEntity {

	@Column(name = "precio")
	@Positive
	private double precio;

	@ManyToOne(optional=false)
	@JoinColumn(name = "plato_types_id")
	private PlatoType tipoPlato;
	
	@Column(name = "name")
	@NotEmpty
	private String name;
	
}
