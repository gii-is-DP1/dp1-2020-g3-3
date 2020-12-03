package org.springframework.samples.aerolineasAAAFC.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.Month;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.jupiter.api.Assertions;

//import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.aerolineasAAAFC.model.Aeropuerto;
import org.springframework.samples.aerolineasAAAFC.model.Vuelo;
import org.springframework.samples.aerolineasAAAFC.service.exceptions.HorasImposiblesException;
import org.springframework.samples.aerolineasAAAFC.service.exceptions.TelefonoErroneoException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class VueloServiceTests {
	
	@Autowired
	protected VueloService vueloService;
	
	@Autowired
	protected AeropuertoService aeroService;
	
	//TEST DE CONSULTA
	@Test
	void getFechaSalidaVueloSuccessful() {
		Vuelo vuelo = vueloService.findVueloById(1);
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm");
		
		assertThat(vuelo.getFechaSalida())
		.isBefore(vuelo.getFechaLlegada());
		
//		assertThat(vuelo.getFechaSalida().format(formatter))
//		.isEqualTo("2040-12-11 10:40");
	}
	
	@Test
	void getFechaLlegadaVueloSuccessful() {
		Vuelo vuelo = vueloService.findVueloById(1);
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm");
		
		assertThat(vuelo.getFechaLlegada())
		.isAfter(vuelo.getFechaSalida());
		
//		assertThat(vuelo.getFechaLlegada().format(formatter))
//		.isEqualTo("2040-12-11 10:40");
	}

	@Test
	void getPrecioVueloSuccesful() {
		Vuelo vuelo = vueloService.findVueloById(1);
		String precio = String.valueOf(vuelo.getCoste());
		
		assertThat(precio).isEqualTo("64.0");
	}
	
	@Test
	void getNombreAeropuertoOrigenVueloSuccessful() {
		Vuelo vuelo = vueloService.findVueloById(1);
		String nombreAeroOrigen = vuelo.getAeropuertoOrigen().getNombre();
		
		assertThat(nombreAeroOrigen).isEqualTo("Aeropuerto de São Paulo Guarulhos");
	}
	
	@Test
	void getCodigoIATAAeropuertoDestinoVueloSuccessful() {
		Vuelo vuelo = vueloService.findVueloById(1);
		String codigoAeroDestino = vuelo.getAeropuertoDestino().getCodigoIATA();
		
		assertThat(codigoAeroDestino).isEqualTo("MAD");
	}
	
	//TEST DE INSERCIÓN
	@Test
	@Transactional
	public void shouldInsertVuelo() {
		Collection<Vuelo> vuelos = this.vueloService.findVuelos();
		int found = vuelos.size();
		
		Vuelo vuelo = new Vuelo();
		vuelo.setFechaSalida(LocalDate.of(2020, Month.DECEMBER, 1));
		vuelo.setFechaLlegada(LocalDate.of(2020, Month.DECEMBER, 2));
		vuelo.setCoste(42.0);
		
		Aeropuerto aeroOri = new Aeropuerto();
		aeroOri.setNombre("Aeropuerto Humberto Delgado");
		aeroOri.setLocalizacion("Lisboa, Portugal");
		aeroOri.setCodigoIATA("LIS");
		aeroOri.setTelefono("+351218413500");
		try {
			this.aeroService.saveAeropuerto(aeroOri);
		} catch(TelefonoErroneoException ex) {
			Logger.getLogger(AeropuertoServiceTests.class.getName()).log(Level.SEVERE, null, ex);
		}
		vuelo.setAeropuertoOrigen(aeroOri);
		
		Aeropuerto aeroDest = new Aeropuerto();
		aeroDest.setNombre("Aeropuerto de Málaga - Costa del Sol");
		aeroDest.setLocalizacion("Málaga, España");
		aeroDest.setCodigoIATA("AGP");
		aeroDest.setTelefono("+3491321100");
		try {
			this.aeroService.saveAeropuerto(aeroDest);
		} catch(TelefonoErroneoException ex) {
			Logger.getLogger(AeropuertoServiceTests.class.getName()).log(Level.SEVERE, null, ex);
		}
		vuelo.setAeropuertoDestino(aeroDest);
		
		try {
			this.vueloService.saveVuelo(vuelo);
		} catch (HorasImposiblesException ex) {
			Logger.getLogger(VueloServiceTests.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		assertThat(vuelo.getId().longValue()).isNotEqualTo(0);
		
		vuelos = this.vueloService.findVuelos();
		assertThat(vuelos.size()).isEqualTo(found + 1);
	}
	
	@Test
	@Transactional
	public void shouldNotInsertVueloHorasInvalidas() {
		Collection<Vuelo> vuelos = this.vueloService.findVuelos();
		int found = vuelos.size();
		
		Vuelo vuelo = new Vuelo();
		vuelo.setFechaSalida(LocalDate.of(2020, Month.DECEMBER, 1));
		vuelo.setFechaLlegada(LocalDate.of(2020, Month.NOVEMBER, 30));
		vuelo.setCoste(42.0);
		
		Aeropuerto aeroOri = new Aeropuerto();
		aeroOri.setNombre("Aeropuerto Humberto Delgado");
		aeroOri.setLocalizacion("Lisboa, Portugal");
		aeroOri.setCodigoIATA("LIS");
		aeroOri.setTelefono("+351218413500");
		try {
			this.aeroService.saveAeropuerto(aeroOri);
		} catch(TelefonoErroneoException ex) {
			Logger.getLogger(AeropuertoServiceTests.class.getName()).log(Level.SEVERE, null, ex);
		}
		vuelo.setAeropuertoOrigen(aeroOri);
		
		Aeropuerto aeroDest = new Aeropuerto();
		aeroDest.setNombre("Aeropuerto de Málaga - Costa del Sol");
		aeroDest.setLocalizacion("Málaga, España");
		aeroDest.setCodigoIATA("AGP");
		aeroDest.setTelefono("+3491321100");
		try {
			this.aeroService.saveAeropuerto(aeroDest);
		} catch(TelefonoErroneoException ex) {
			Logger.getLogger(AeropuertoServiceTests.class.getName()).log(Level.SEVERE, null, ex);
		}
		vuelo.setAeropuertoDestino(aeroDest);
		
		Assertions.assertThrows(HorasImposiblesException.class, ()->{ this.vueloService.saveVuelo(vuelo); });
		vuelos = this.vueloService.findVuelos();
		assertThat(vuelos.size()).isEqualTo(found);
	}
	
	//TEST DE ACTUALIZACIÓN
	@Test
	void shouldUpdateHorasVuelo() {
		Vuelo vuelo = vueloService.findVueloById(1);
		
		vuelo.setFechaSalida(LocalDate.of(2020, Month.FEBRUARY, 12));
		vuelo.setFechaLlegada(LocalDate.of(2020, Month.FEBRUARY, 13));
		
		assertThat(vuelo.getFechaSalida()).isEqualTo(LocalDate.of(2020, Month.FEBRUARY, 12));
		assertThat(vuelo.getFechaLlegada()).isEqualTo(LocalDate.of(2020, Month.FEBRUARY, 13));
	}
	
	//TEST DE BORRADO
	@Test
	@Transactional
	public void shouldDeleteVueloById() {
		Collection<Vuelo> vuelos = this.vueloService.findVuelos();
		int found = vuelos.size();
		
		vueloService.eliminarVuelo(1);
		
		vuelos = this.vueloService.findVuelos();
		assertThat(vuelos.size()).isEqualTo(found - 1);
	}
}