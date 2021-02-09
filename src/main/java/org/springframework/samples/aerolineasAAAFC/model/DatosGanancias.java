package org.springframework.samples.aerolineasAAAFC.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "datos_ganancias")
public class DatosGanancias extends BaseEntity{
	
	@Column(name = "ganancias_semanales")
	private double gananciasSemanales;
}