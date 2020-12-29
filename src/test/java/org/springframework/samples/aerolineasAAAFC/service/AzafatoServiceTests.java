package org.springframework.samples.aerolineasAAAFC.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.aerolineasAAAFC.model.Azafato;
import org.springframework.samples.aerolineasAAAFC.model.Idioma;
import org.springframework.samples.aerolineasAAAFC.model.User;
import org.springframework.samples.aerolineasAAAFC.service.exceptions.IdiomasNoSuficientesException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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

	
	@Test
	@Transactional
	public void shouldNotInsertAzafato1Idioma() {

		Collection<Azafato> azafatos = this.azafatoService.findAzafatos();
		int found = azafatos.size();
		
		Azafato azafato = new Azafato();

		azafato.setNombre("Rafael");
		azafato.setApellidos("Nadal Parera");
		azafato.setIban("ES 33335905228408934564567");
		azafato.setNif("23165805A");
		azafato.setSalario(2340.);
		
		Idioma i1 = new Idioma();
		i1.setIdioma("Castellano");
		Set<Idioma> idiomas = new HashSet<Idioma>();
		idiomas.add(i1);
		
		azafato.setIdiomas(idiomas);
		
		User user = new User();
		user.setUsername("23165805A");
		user.setPassword("*pepe_csfay");
		user.setEnabled(true);
		azafato.setUser(user);    
		


		Assertions.assertThrows(IdiomasNoSuficientesException.class, () -> {azafatoService.saveAzafato(azafato);});

		azafatos = this.azafatoService.findAzafatos();
		assertThat(azafatos.size()).isEqualTo(found);

	}
	
	
}










