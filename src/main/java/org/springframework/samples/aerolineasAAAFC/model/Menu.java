/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.aerolineasAAAFC.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
* Entidad simple que representa un men�
*/

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "menus")
public class Menu extends BaseEntity {
	
	//Atributos

	@Column(name = "precio")
	private double precio;

	@Column(name = "primer_plato")
	@NotEmpty
	private String primerPlato;
	
	@Column(name = "segundo_plato")
	@NotEmpty
	private String segundoPlato;
	
	@Column(name = "postre")
	@NotEmpty
	private String postre;
	
	@ManyToOne(optional=false)
	@JoinColumn(name = "billete_id")
	private Billete billete;
	

}
