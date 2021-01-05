package org.springframework.samples.aerolineasAAAFC.model.menu;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import org.springframework.samples.aerolineasAAAFC.model.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "plato_types")
public class PlatoType extends BaseEntity{

	@Column(name = "name")
	@NotEmpty
	private String name; 
	
	@Override
	public String toString() {
		return this.getName();
	}
	
	
}
