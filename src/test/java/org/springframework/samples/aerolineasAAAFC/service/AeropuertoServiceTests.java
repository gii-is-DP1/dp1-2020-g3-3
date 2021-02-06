package org.springframework.samples.aerolineasAAAFC.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.aerolineasAAAFC.model.Aeropuerto;
import org.springframework.samples.aerolineasAAAFC.service.exceptions.TelefonoErroneoException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class AeropuertoServiceTests {

	@Autowired
	protected AeropuertoService aeropuertoService;
	
	@Autowired
	protected VueloService vueloService;
	
	@Autowired
	protected AvionService avionService;
	
	//TEST DE CONSULTA
	@Test
	void getNombreAeropuertoSuccessful() {
		Aeropuerto aero = this.aeropuertoService.findAeropuertoById(1);
		
		//NOMBRE
		assertThat(aero.getNombre())
		.isNotEmpty()
		.hasToString("Aeropuerto de Sao Paulo Guarulhos");
		
		//LOCALIZACION
		assertThat(aero.getLocalizacion())
		.isNotEmpty()
		.hasToString("Sao Paulo, Brasil");
		
		//CODIGO IATA
		assertThat(aero.getCodigoIATA())
		.isNotEmpty()
		.hasToString("GRU");
		
		//TELEFONO
		assertThat(aero.getTelefono())
		.isNotEmpty()
		.containsPattern("^(\\+|\\d)[0-9]{7,16}$");
		
		//AEROPUERTO LLEGADA
		assertThat(aero.getVuelosLlegada())
		.isNotEmpty();
		
		//AEROPUERTO SALIDA
		assertThat(aero.getVuelosSalida())
		.isNotEmpty();
	}
	
	
	//TEST DE INSERCIÓN
	@Test
	@Transactional
	public void shouldInsertAeropuerto() {
		Collection<Aeropuerto> aeros = this.aeropuertoService.findAeropuertosNoPageable();
		int found = aeros.size();
		
		Aeropuerto aero = new Aeropuerto();
		aero.setNombre("Aeropuerto Humberto Delgado");
		aero.setLocalizacion("Lisboa, Portugal");
		aero.setCodigoIATA("LIS");
		aero.setTelefono("+351218413500");
		
		try {
			this.aeropuertoService.saveAeropuerto(aero);
		} catch(TelefonoErroneoException ex) {
			log.error("Teléfono erróneo",ex);
		}
		
		assertThat(aero.getId()).isNotEqualTo(0);
		
		aeros = this.aeropuertoService.findAeropuertosNoPageable();
		assertThat(aeros.size()).isEqualTo(found + 1);
	}
	
	@Test
	@Transactional
	public void shoulNotInsertAeropuertoTelefonoInvalido() {
		Collection<Aeropuerto> aeros = this.aeropuertoService.findAeropuertosNoPageable();
		int found = aeros.size();
		
		Aeropuerto aero = new Aeropuerto();
		aero.setNombre("Aeropuerto de Málaga - Costa del Sol");
		aero.setLocalizacion("Málaga, España");
		aero.setCodigoIATA("AGP");
		aero.setTelefono("12 345 678 910");
		
		assertThrows(TelefonoErroneoException.class, ()->{ this.aeropuertoService.saveAeropuerto(aero); });
		
		aeros = this.aeropuertoService.findAeropuertosNoPageable();
		assertThat(aeros.size()).isEqualTo(found);
	}
	
	
	//TEST DE ACTUALIZACIÓN
	@Test
	void shouldUpdateNombreAeropuerto() throws TelefonoErroneoException {
		Aeropuerto aero = aeropuertoService.findAeropuertoById(1);
		
		aero.setNombre("Aeropuerto Internacional de São Paulo Guarulhos");
		
		this.aeropuertoService.saveAeropuerto(aero);
		
		aero = this.aeropuertoService.findAeropuertoById(1);
		assertThat(aero.getNombre())
		.isNotEmpty()
		.isEqualTo("Aeropuerto Internacional de São Paulo Guarulhos");
	}
	
	//TEST DE BORRADO
	@Test
	@Transactional
	public void shouldDeleteAeropuertoById() {
		Collection<Aeropuerto> aeros = this.aeropuertoService.findAeropuertosNoPageable();
		int found = aeros.size();
		
		this.aeropuertoService.eliminarAeropuerto(1);
		
		aeros = this.aeropuertoService.findAeropuertosNoPageable();
		assertThat(aeros.size()).isEqualTo(found - 1);
	}
}
