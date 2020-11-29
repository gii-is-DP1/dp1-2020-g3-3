package org.springframework.samples.aerolineasAAAFC.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.aerolineasAAAFC.model.Aeropuerto;
import org.springframework.samples.aerolineasAAAFC.service.exceptions.TelefonoErroneoException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class AeropuertoServiceTests {

	@Autowired
	protected AeropuertoService aeropuertoService;
	
	
	//TEST DE CONSULTA
	@Test
	void getNombreAeropuertoSuccessful() {
		Aeropuerto aero = aeropuertoService.findAeropuertoById(1);
		assertThat(aero.getNombre())
		.isNotEmpty()
		.hasToString("Aeropuerto de São Paulo Guarulhos");
	}
	
	@Test
	void getLocalizacionAeropuertoSuccessful() {
		Aeropuerto aero = aeropuertoService.findAeropuertoById(1);
		assertThat(aero.getLocalizacion())
		.isNotEmpty()
		.hasToString("São Paulo, Brasil");
	}
	
	@Test
	void getCodigoIATAAeropuertoSuccessful() {
		Aeropuerto aero = aeropuertoService.findAeropuertoById(1);
		assertThat(aero.getCodigoIATA())
		.isNotEmpty()
		.hasToString("GRU");
	}
	
	@Test
	void getTelefonoAeropuertoSuccessful() {
		Aeropuerto aero = aeropuertoService.findAeropuertoById(1);
		assertThat(aero.getTelefono())
		.isNotEmpty()
		.containsPattern("^(\\+|\\d)[0-9]{7,16}$");
	}
	
	
	//TEST DE INSERCIÓN
	@Test
	@Transactional
	public void shouldInsertAeropuerto() {
		Collection<Aeropuerto> aeros = this.aeropuertoService.findAeropuertos();
		int found = aeros.size();
		
		Aeropuerto aero = new Aeropuerto();
		aero.setNombre("Aeropuerto Humberto Delgado");
		aero.setLocalizacion("Lisboa, Portugal");
		aero.setCodigoIATA("LIS");
		aero.setTelefono("+351218413500");
		
		try {
			this.aeropuertoService.saveAeropuerto(aero);
		} catch(TelefonoErroneoException ex) {
			Logger.getLogger(AeropuertoServiceTests.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		assertThat(aero.getId()).isNotEqualTo(0);
		
		aeros = this.aeropuertoService.findAeropuertos();
		assertThat(aeros.size()).isEqualTo(found + 1);
	}
	
	@Test
	@Transactional
	public void shoulNotInsertAeropuertoTelefonoInvalido() {
		Collection<Aeropuerto> aeros = this.aeropuertoService.findAeropuertos();
		int found = aeros.size();
		
		Aeropuerto aero = new Aeropuerto();
		aero.setNombre("Aeropuerto de Málaga - Costa del Sol");
		aero.setLocalizacion("Málaga, España");
		aero.setCodigoIATA("AGP");
		aero.setTelefono("12 345 678 910");
		
		Assertions.assertThrows(TelefonoErroneoException.class, ()->{ this.aeropuertoService.saveAeropuerto(aero); });
		aeros = this.aeropuertoService.findAeropuertos();
		assertThat(aeros.size()).isEqualTo(found);
	}
	
	
	//TEST DE ACTUALIZACIÓN
	@Test
	void shouldUpdateNombreAeropuerto() {
		Aeropuerto aero = aeropuertoService.findAeropuertoById(1);
		
		aero.setNombre("Aeropuerto Internacional de São Paulo Guarulhos");
		
		assertThat(aero.getNombre())
		.isNotEmpty()
		.isEqualTo("Aeropuerto Internacional de São Paulo Guarulhos");
	}
	
	//TEST DE BORRADO
	@Test
	@Transactional
	public void shouldDeleteAeropuertoById() {
		Collection<Aeropuerto> aeros = this.aeropuertoService.findAeropuertos();
		int found = aeros.size();
		
		aeropuertoService.eliminarAeropuerto(1);
		
		aeros = this.aeropuertoService.findAeropuertos();
		assertThat(aeros.size()).isEqualTo(found - 1);
	}
}
