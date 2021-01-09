package org.springframework.samples.aerolineasAAAFC.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.aerolineasAAAFC.model.Avion;
import org.springframework.samples.aerolineasAAAFC.model.PersonalOficina;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class PersonalOficinaServiceTests {

	@Autowired
	protected PersonalOficinaService personalOficinaService;

	@Test
	void getApellidosOficinistaSuccessful() {
		PersonalOficina personal=personalOficinaService.findPersonalOficinaById(1);
		assertThat(personal.getApellidos()).isNotEmpty();
	}

	@Test
	void getNifOficinistaSuccessful() {
		PersonalOficina personal=personalOficinaService.findPersonalOficinaById(1);
		assertThat(personal.getNif()).isNotEmpty();
		assertThat(personal.getNif()).containsPattern("^\\d{8}[a-zA-Z]$");
	}

	@Test
	void getIbanOficinistaSuccessful() {
		PersonalOficina personal=personalOficinaService.findPersonalOficinaById(1);
		assertThat(personal.getIban()).isNotEmpty();
		assertThat(personal.getIban()).containsPattern("^ES\\s\\d{22}$");
	}

	@Test
	void getSalarioOficinistaSuccessful() {
		PersonalOficina personal=personalOficinaService.findPersonalOficinaById(1);
		assertThat(personal.getSalario()).isNotNull();
		assertThat(personal.getSalario()).isGreaterThan(1000);
	}
	
	// TESTS DE ACTUALIZACION	
	@Test
	@Transactional
	void updateNifUsernameOficinistaSuccessful() {
		PersonalOficina ofi = personalOficinaService.findPersonalOficinaById(1);
		ofi.setNif("04985857D");
		ofi.getUser().setUsername("04985857D");
		assertThat(ofi.getNif()).isNotEmpty().hasToString("04985857D");
		assertThat(ofi.getUser().getUsername()).isNotEmpty().hasToString("04985857D");
	}
	
	@Test
	@Transactional
	void updateIbanOficinistaSuccessful() {
		PersonalOficina ofi = personalOficinaService.findPersonalOficinaById(1);
		ofi.setIban("ES 9514651121125975429531");
		assertThat(ofi.getIban()).isNotEmpty().hasToString("ES 9514651121125975429531");
	}
	
	@Test
	@Transactional
	void updateSalarioOficinistaSuccessful() {
		PersonalOficina ofi = personalOficinaService.findPersonalOficinaById(1);
		ofi.setSalario(1900.0);
		assertThat(ofi.getSalario()).isEqualTo(1900.0);
	}

	
}










