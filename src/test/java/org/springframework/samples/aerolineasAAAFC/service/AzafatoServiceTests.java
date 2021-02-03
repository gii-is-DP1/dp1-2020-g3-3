package org.springframework.samples.aerolineasAAAFC.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
	void getAzafatoSuccessful() {
		Azafato azafato = this.azafatoService.findAzafatoById(1);
		
		//NOMBRE
		assertThat(azafato.getNombre())
		.isNotEmpty();
		
		//APELLIDOS
		assertThat(azafato.getApellidos())
		.isNotEmpty();
		
		//NIF
		assertThat(azafato.getNif())
		.isNotEmpty()
		.containsPattern("^\\d{8}[a-zA-Z]$");
		
		//IBAN
		assertThat(azafato.getIban())
		.isNotEmpty()
		.containsPattern("^ES\\s\\d{22}$");
		
		//IDIOMAS
		assertThat(azafato.getIdiomas())
		.isNotNull();
		assertThat(azafato.getIdiomas().size())
		.isGreaterThanOrEqualTo(2);
		
		//SALARIO
		assertThat(azafato.getSalario())
		.isNotNull()
		.isGreaterThan(1000);
	}
	
	@Test
	void getAzafatoByNifAndByIBAN() {
		
		Azafato az = this.azafatoService.findAzafatoByNif("21333214R");
		assertThat(az).isNotNull();
		
		Azafato az2 = this.azafatoService.findAzafatoByIban("ES 4730045887188485547854");
		assertThat(az2).isNotNull();
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
		azafato.setUser(user);    
		


		assertThrows(IdiomasNoSuficientesException.class, () -> {azafatoService.saveAzafato(azafato);});

		azafatos = this.azafatoService.findAzafatos();
		assertThat(azafatos.size()).isEqualTo(found);

	}
	
	@Test
	@Transactional
	public void shouldInsertAzafato2Idiomas() throws IbanDuplicadoException, IdiomasNoSuficientesException {

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
		user.setMatchingPassword("*pepe_csfay");
		azafato.setUser(user);    

		
		azafatoService.saveAzafato(azafato);
		

		azafatos = this.azafatoService.findAzafatos();
		assertThat(azafatos.size()).isEqualTo(found + 1);
		

	}
	
	@Test
	@Transactional
	public void shouldNotInsertAzafatoIbanDuplicado(){
		
		Azafato azafato = new Azafato();
		azafato.setNombre("Rafael");
		azafato.setApellidos("Nadal Parera");
		azafato.setIban("ES 4730045887188485547854");
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
		user.setMatchingPassword("*pepe_csfay");
		azafato.setUser(user);    
		
		assertThrows(IbanDuplicadoException.class, () -> { this.azafatoService.saveAzafato(azafato); });
		
	}
	
	@Test
	void shouldDeleteAzafatoById() {
		Collection<Azafato> azafatos = this.azafatoService.findAzafatos();
		int found = azafatos.size();
		
		this.azafatoService.eliminarAzafato(1);
		
		azafatos = this.azafatoService.findAzafatos();
		assertThat(azafatos.size()).isEqualTo(found-1);
	}
	
	@Test
	void getVuelosByDateSuccessful() {
		Azafato personal = azafatoService.findAzafatoById(1);
		Collection<Vuelo> vuelos = azafatoService.horario(personal.getId(), 01, 2015);
		
		int found = vuelos.size();
		
		assertThat(found).isEqualTo(1);
	}
	
	@Test
	void shouldFindIdiomaType() {
		Collection<IdiomaType> idiomas = this.azafatoService.findIdiomaTypes();
		
		assertThat(idiomas).isNotEmpty();
	}
	
	
}

