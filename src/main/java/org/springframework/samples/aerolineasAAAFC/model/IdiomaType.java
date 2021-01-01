package org.springframework.samples.aerolineasAAAFC.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "idioma_types")
public class IdiomaType extends BaseEntity{

	@Column(name = "idioma")
	@Pattern(regexp="^[A-Z]{2}$") //Siguiendo los códigos ISO 639-1
	private String idioma; 
	
	//Indicando que idiomas es de tipo Agregación (puede seguir habiendo idiomas sin X azafatos)
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
	@EqualsAndHashCode.Exclude 
	private Set<Azafato> azafatos;
	
	@Override
	public String toString() {
		return this.getIdioma();
	}
	
}
