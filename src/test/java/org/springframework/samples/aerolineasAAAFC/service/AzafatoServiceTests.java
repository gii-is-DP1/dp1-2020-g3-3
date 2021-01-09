package org.springframework.samples.aerolineasAAAFC.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.samples.aerolineasAAAFC.model.Azafato;
import org.springframework.samples.aerolineasAAAFC.model.IdiomaType;
import org.springframework.samples.aerolineasAAAFC.model.User;
import org.springframework.samples.aerolineasAAAFC.model.Vuelo;
import org.springframework.samples.aerolineasAAAFC.service.exceptions.IbanDuplicadoException;
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
		azafato.setIban("ES 1201284722814468736382");
		azafato.setNif("84493294B");
		azafato.setSalario(2340.);
		
		IdiomaType i1 = new IdiomaType();
		i1.setIdioma("ES");
		Set<IdiomaType> idiomas = new HashSet<IdiomaType>();
		idiomas.add(i1);
		
		azafato.setIdiomas(idiomas);
		
		User user = new User();
		user.setUsername("84493294B");
		user.setPassword("*pepe_csfay");
		user.setEnabled(true);
		azafato.setUser(user);    
		


		Assertions.assertThrows(IdiomasNoSuficientesException.class, () -> {azafatoService.saveAzafato(azafato);});

		azafatos = this.azafatoService.findAzafatos();
		assertThat(azafatos.size()).isEqualTo(found);

	}
	
	@Test
	@Transactional
	public void shouldInsertAzafato2Idiomas() {

		Collection<Azafato> azafatos = this.azafatoService.findAzafatos();
		int found = azafatos.size();
		
		Azafato azafato = new Azafato();

		azafato.setNombre("Rafael");
		azafato.setApellidos("Nadal Parera");
		azafato.setIban("ES 3332020458401202067812");
		azafato.setNif("84493294B");
		azafato.setSalario(2340.);
		
		IdiomaType i1 = new IdiomaType();
		i1.setIdioma("ES");
		IdiomaType i2 = new IdiomaType();
		i2.setIdioma("FR");
		Set<IdiomaType> idiomas = new HashSet<IdiomaType>();
		idiomas.add(i1);
		idiomas.add(i2);
		
		azafato.setIdiomas(idiomas);
		
		User user = new User();
		user.setUsername("84493294B");
		user.setPassword("*pepe_csfay");
		user.setEnabled(true);
		azafato.setUser(user);    

		try {
			azafatoService.saveAzafato(azafato);
		} catch (DataIntegrityViolationException e) {
            Logger.getLogger(AzafatoServiceTests.class.getName()).log(Level.SEVERE, null, e);
			e.printStackTrace();
		} catch (DataAccessException e) {
            Logger.getLogger(AzafatoServiceTests.class.getName()).log(Level.SEVERE, null, e);
			e.printStackTrace();
		} catch (IbanDuplicadoException e) {
            Logger.getLogger(AzafatoServiceTests.class.getName()).log(Level.SEVERE, null, e);
			e.printStackTrace();
		} catch (IdiomasNoSuficientesException e) {
            Logger.getLogger(AzafatoServiceTests.class.getName()).log(Level.SEVERE, null, e);
			e.printStackTrace();
		}

		azafatos = this.azafatoService.findAzafatos();
		assertThat(azafatos.size()).isEqualTo(found + 1);
		
		assertThat(azafato.getId()).isNotNull();

	}
	
	@Test
	void getVuelosByDateSuccessful() {
		Azafato personal = azafatoService.findAzafatoById(1);
		Collection<Vuelo> vuelos = azafatoService.findVuelosByDate(personal.getId(), 01, 2015);
		
		int found = vuelos.size();
		
		assertThat(found).isEqualTo(1);
	}
	
	
}

