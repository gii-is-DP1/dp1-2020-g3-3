package org.springframework.samples.aerolineasAAAFC.model;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import lombok.Data;

@Data
@Entity
public class Payment extends AuditableEntity{
	double amount;
	@OneToOne
	Cliente cliente;
}
