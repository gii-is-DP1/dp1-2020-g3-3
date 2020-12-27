package org.springframework.samples.aerolineasAAAFC.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "idioma_type")
public class IdiomaType extends BaseEntity{

	@Column(name = "idioma")
    @Size(min = 2, max = 2) //Siguiendo los códigos ISO 639-1
	private String idioma; 
	
	@Override
	public String toString() {
		return this.getIdioma();
	}
	
}
