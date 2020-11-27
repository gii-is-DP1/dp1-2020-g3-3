package org.springframework.samples.aerolineasAAAFC.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.aerolineasAAAFC.model.Azafato;
import org.springframework.stereotype.Service;


@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class AzafatoServiceTests {

	@Autowired
	protected AzafatoService azafatoService;

	@Test
	void getNombreAzafatoSuccessful() {
		Azafato azafato = azafatoService.findAzafatoById(1);
		assertThat(azafato.getNombre()).isNotEmpty();
	}

	@Test
	void getApellidosAzafatoSuccessful() {
		Azafato azafato = azafatoService.findAzafatoById(1);
		assertThat(azafato.getApellidos()).isNotEmpty();
	}

	@Test
	void getNifAzafatoSuccessful() {
		Azafato azafato = azafatoService.findAzafatoById(1);
		assertThat(azafato.getNif()).isNotEmpty();
		assertThat(azafato.getNif()).containsPattern("^\\d{8}[a-zA-Z]$");
	}

	@Test
	void getIbanAzafatoSuccessful() {
		Azafato azafato = azafatoService.findAzafatoById(1);
		assertThat(azafato.getIban()).isNotEmpty();
		assertThat(azafato.getIban()).containsPattern("^ES\\s\\d{22}$");
	}
	
	@Test
	void getIdiomasAzafatoSuccessful() {
		Azafato azafato = azafatoService.findAzafatoById(1);
		assertThat(azafato.getIdiomas()).isNotNull();
		assertThat(azafato.getIdiomas().size()).isGreaterThanOrEqualTo(2);
	}

	@Test
	void getSalarioAzafatoSuccessful() {
		Azafato azafato = azafatoService.findAzafatoById(1);
		assertThat(azafato.getSalario()).isNotNull();
		assertThat(azafato.getSalario()).isGreaterThan(1000);
	}

	
}










