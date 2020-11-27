package org.springframework.samples.aerolineasAAAFC.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "idiomas")
public class Idioma extends BaseEntity{

	@Column(name = "idioma")
	@NotEmpty
	private String idioma; 
}
