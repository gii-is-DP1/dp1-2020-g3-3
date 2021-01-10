package org.springframework.samples.aerolineasAAAFC.model.menu;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.springframework.samples.aerolineasAAAFC.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "platos")
public class Plato extends BaseEntity {

	@ManyToOne(optional = false)
	@JoinColumn(name = "platos_base_id")
	@EqualsAndHashCode.Exclude
	private PlatoBase platoBase;

	@Override
	public String toString() {
		return platoBase.getName();
	}

}
