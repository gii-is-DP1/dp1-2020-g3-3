package org.springframework.samples.aerolineasAAAFC.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.aerolineasAAAFC.model.PersonalOficina;
import org.springframework.stereotype.Service;


@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class PersonalOficinaServiceTests {

	@Autowired
	protected PersonalOficinaService personalOficinaService;

	@Test
	void getApellidosPersonalSuccessful() {
		PersonalOficina personal=personalOficinaService.findPersonalOficinaById(1);
		assertThat(personal.getApellidos()).isNotEmpty();
	}

	@Test
	void getNifPersonalSuccessful() {
		PersonalOficina personal=personalOficinaService.findPersonalOficinaById(1);
		assertThat(personal.getNif()).isNotEmpty();
		assertThat(personal.getNif()).containsPattern("^\\d{8}[a-zA-Z]$");
	}

	@Test
	void getIbanPersonalSuccessful() {
		PersonalOficina personal=personalOficinaService.findPersonalOficinaById(1);
		assertThat(personal.getIban()).isNotEmpty();
		assertThat(personal.getIban()).containsPattern("^ES\\s\\d{22}$");
	}

	@Test
	void getSalarioPersonalSuccessful() {
		PersonalOficina personal=personalOficinaService.findPersonalOficinaById(1);
		assertThat(personal.getSalario()).isNotNull();
		assertThat(personal.getSalario()).isGreaterThan(100);
	}

	
}










