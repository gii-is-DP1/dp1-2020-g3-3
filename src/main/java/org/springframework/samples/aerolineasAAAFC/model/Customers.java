package org.springframework.samples.aerolineasAAAFC.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Customers {
	@CreatedBy
	private String creator;
	@CreatedDate
	private LocalDateTime createdDate;
	@LastModifiedBy
	private String modifier;
	@LastModifiedDate
	private LocalDateTime lastModifiedDate;

}
