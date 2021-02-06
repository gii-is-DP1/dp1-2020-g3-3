package org.springframework.samples.aerolineasAAAFC.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.aerolineasAAAFC.model.PersonalOficina;
import org.springframework.samples.aerolineasAAAFC.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class PersonalOficinaServiceTests {

	@Autowired
	protected PersonalOficinaService personalOficinaService;

	
	//TESTS DE CONSULTA
	@Test
	void getOficinistaSuccessful() {
		PersonalOficina personal = personalOficinaService.findPersonalOficinaById(1);

		//NOMBRE
		assertThat(personal.getNombre())
		.isNotEmpty();

		//APELLIDOS
		assertThat(personal.getApellidos())
		.isNotEmpty();

		//NIF
		assertThat(personal.getNif())
		.isNotEmpty()
		.containsPattern("^\\d{8}[a-zA-Z]$");

		//IBAN
		assertThat(personal.getIban())
		.isNotEmpty()
		.containsPattern("^ES\\s\\d{22}$");


		//SALARIO
		assertThat(personal.getSalario())
		.isNotNull()
		.isGreaterThan(1000);

		//VUELOS
		assertThat(personal.getVuelos())
		.isNotEmpty();
	}
	
	@Test
	void getOficinistaByNif() {
		PersonalOficina personal = personalOficinaService.findPersonalOficinaByNif("76188332G");
		
		assertThat(personal).isNotNull();
	}
	
	@Test
	void getOficinistaByIban() {
		PersonalOficina personal = personalOficinaService.findPersonalOficinaByIban("ES 4820381461196657997548");
		
		assertThat(personal).isNotNull();
	}

	
	//TESTS DE INSERCIÓN
	@Test
	@Transactional
	void insertarPersonal() {
		Collection<PersonalOficina> pos = this.personalOficinaService.findPersonalNoPageable();
		int found = pos.size();

		PersonalOficina po = new PersonalOficina();
		po.setNombre("Juan");
		po.setApellidos("Fernandez Romero");
		po.setNif("08493865B");
		po.setIban("ES 0159480518801639865810");
		po.setSalario(2000.);

		User poUser = new User();
		poUser.setUsername("08493865B");
		poUser.setPassword("juFerRo01");
		po.setUser(poUser);

		this.personalOficinaService.savePersonalOficina(po);

		assertThat(po.getId()).isNotNull();

		pos = this.personalOficinaService.findPersonalNoPageable();
		assertThat(pos.size()).isEqualTo(found + 1);
	}


	// TESTS DE ACTUALIZACION	
	@Test
	@Transactional
	void updateOficinistaSuccessful() {
		PersonalOficina ofi = personalOficinaService.findPersonalOficinaById(1);
		ofi.setIban("ES 4801283474013341774283");
		ofi.setSalario(1900.0);
		
		this.personalOficinaService.savePersonalOficina(ofi);
		
		ofi = this.personalOficinaService.findPersonalOficinaById(1);
		assertThat(ofi.getIban()).isNotEmpty().hasToString("ES 4801283474013341774283");
		assertThat(ofi.getSalario()).isEqualTo(1900.0);
	}
	

	//TEST ELIMINAR
	@Test
	@Transactional
	void deletePersonalById() {
		Collection<PersonalOficina> pos = this.personalOficinaService.findPersonalNoPageable();
		int found = pos.size();
		
		this.personalOficinaService.deletePersonalOficinaById(2);
		
		pos = this.personalOficinaService.findPersonalNoPageable();
		assertThat(pos.size()).isEqualTo(found - 1);
	}

}










