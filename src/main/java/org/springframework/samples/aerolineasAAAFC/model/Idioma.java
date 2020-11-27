package org.springframework.samples.aerolineasAAAFC.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
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
	
	//Indicando que idiomas es de tipo Agregación (puede seguir habiendo idiomas sin X azafatos)
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
	@EqualsAndHashCode.Exclude 
	private Set<Azafato> azafatos;
}
