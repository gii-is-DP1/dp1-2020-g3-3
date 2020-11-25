package org.springframework.samples.aerolineasAAAFC.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.aerolineasAAAFC.model.PersonalControl;
import org.springframework.stereotype.Service;


@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class PersonalControlServiceTests {

	@Autowired
	protected PersonalControlService personalControlService;

	@Test
	void getNombrePersonalSuccessful() {
		PersonalControl personal=personalControlService.findPersonalControlById(1);
		assertThat(personal.getNombre()).isNotEmpty();
	}

	
	@Test
	void getApellidosPersonalSuccessful() {
		PersonalControl personal=personalControlService.findPersonalControlById(1);
		assertThat(personal.getApellidos()).isNotEmpty();
	}

	@Test
	void getNifPersonalSuccessful() {
		PersonalControl personal=personalControlService.findPersonalControlById(1);
		assertThat(personal.getNif()).isNotEmpty();
		assertThat(personal.getNif()).containsPattern("^\\d{8}[a-zA-Z]$");
	}

	@Test
	void getIbanPersonalSuccessful() {
		PersonalControl personal=personalControlService.findPersonalControlById(1);
		assertThat(personal.getIban()).isNotEmpty();
		assertThat(personal.getIban()).containsPattern("^ES\\d{22}$");
	}

//	@Test --- ROL
//	void getRolPersonalSuccessful() {
//		PersonalControl personal=personalControlService.findPersonalControlById(1);
//		assertThat(personal.getRol()).isNotNull();
//		assertThat(personal.getRol()).;
//	}
	
	@Test
	void getSalarioPersonalSuccessful() {
		PersonalControl personal=personalControlService.findPersonalControlById(1);
		assertThat(personal.getSalario()).isNotNull();
		assertThat(personal.getSalario()).isGreaterThan(100);
	}

	
}










