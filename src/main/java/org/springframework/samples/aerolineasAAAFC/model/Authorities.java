package org.springframework.samples.aerolineasAAAFC.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "authorities")
public class Authorities extends BaseEntity{
	
	@ManyToOne
	@JoinColumn(name = "username")
	@EqualsAndHashCode.Exclude
	User user;
	
	@Size(min = 3, max = 50)
	String authority;
	
	
}
