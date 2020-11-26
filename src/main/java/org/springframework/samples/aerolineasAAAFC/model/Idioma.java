package org.springframework.samples.aerolineasAAAFC.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "idiomas")
public class Idioma extends BaseEntity{

	@Column(name = "idioma")
	private String idioma; 
}
