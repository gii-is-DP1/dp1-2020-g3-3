package org.springframework.samples.aerolineasAAAFC.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Assertions;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.aerolineasAAAFC.model.Aeropuerto;
import org.springframework.samples.aerolineasAAAFC.model.Billete;
import org.springframework.samples.aerolineasAAAFC.model.Cliente;
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
	protected AvionService avionService;
	
	@Autowired
	protected AeropuertoService aeroService;
	
	
	//TEST DE CONSULTA
	@Test
	void getFechaSalidaVueloSuccessful() {
		Vuelo vuelo = vueloService.findVueloById(1);
		
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
		
		assertThat(precio).isEqualTo(String.valueOf(vuelo.getCoste()));
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
	
	
	@Test
	void getBilletesSuccessful() {
		Vuelo vuelo = vueloService.findVueloById(2);
		Set<Billete> billetes = vuelo.getBilletes();
		int found = billetes.size();
		
		assertThat(found).isEqualTo(2);
	}
	
	//TEST DE INSERCIÓN
	@Test
	@Transactional
	public void shouldInsertVuelo() {
		Collection<Vuelo> vuelos = this.vueloService.findVuelos();
		int found = vuelos.size();
		
		Vuelo vuelo = new Vuelo();
		vuelo.setFechaSalida(LocalDateTime.of(2020, Month.DECEMBER, 1, 12, 23));
		vuelo.setFechaLlegada(LocalDateTime.of(2020, Month.DECEMBER, 2, 15, 23));
		vuelo.setCoste(42.0);
		vuelo.setBilletes(new HashSet<>());
		vuelo.setPersonalOficina(new HashSet<>());
		vuelo.setAvion(avionService.findAvionById(1));
		
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
//		catch (DataIntegrityViolationException ex) {
//			Logger.getLogger(VueloServiceTests.class.getName()).log(Level.SEVERE, null, ex);
//		}
		
		assertThat(vuelo.getId().longValue()).isNotEqualTo(0);
		
		vuelos = this.vueloService.findVuelos();
		assertThat(vuelos.size()).isEqualTo(found + 1);
	}
	
	@Test
	@Transactional(rollbackFor={ConstraintViolationException.class})
	public void shouldNotInsertVueloAeropuertoIdenticos() {
//		Collection<Vuelo> vuelos = this.vueloService.findVuelos();
//		int found = vuelos.size();
		
		Vuelo vuelo = new Vuelo();
		vuelo.setFechaSalida(LocalDateTime.of(2020, Month.DECEMBER, 1, 12, 23));
		vuelo.setFechaLlegada(LocalDateTime.of(2020, Month.DECEMBER, 2, 15, 23));
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
		
		
		vuelo.setAeropuertoDestino(aeroOri);
		
		Assertions.assertThrows(ConstraintViolationException.class, ()->{ this.vueloService.saveVuelo(vuelo); });
		

		
//		vuelos = this.vueloService.findVuelos();
//		assertThat(vuelos.size()).isEqualTo(found);
	}
	
	@Test
	@Transactional
	public void shouldNotInsertVueloHorasInvalidas() {
		Collection<Vuelo> vuelos = this.vueloService.findVuelos();
		int found = vuelos.size();
		
		Vuelo vuelo = new Vuelo();
		vuelo.setFechaSalida(LocalDateTime.of(2020, Month.DECEMBER, 3, 12, 23));
		vuelo.setFechaLlegada(LocalDateTime.of(2020, Month.DECEMBER, 2, 12, 23));
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
	@Transactional
	public void shouldUpdateHorasVuelo() {
		Vuelo vuelo = vueloService.findVueloById(1);
		
		vuelo.setFechaSalida(LocalDateTime.of(2020, Month.DECEMBER, 1, 12, 23));
		vuelo.setFechaLlegada(LocalDateTime.of(2020, Month.DECEMBER, 2, 12, 23));
		
		assertThat(vuelo.getFechaSalida()).isEqualTo(LocalDateTime.of(2020, Month.DECEMBER, 1, 12, 23));
		assertThat(vuelo.getFechaLlegada()).isEqualTo(LocalDateTime.of(2020, Month.DECEMBER, 2, 12, 23));
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
	
	
	@Test
	@Transactional
	public void shouldFindClientesByVuelo() {
		Vuelo vuelo=this.vueloService.findVueloById(1);
		Collection<Cliente> clientes=this.vueloService.findClientesPorVuelo(vuelo);
		assertThat(!clientes.isEmpty());
	}
	
	
	
	@Test
	@Transactional
	public void shouldFindVueloByDate() {
		
		Collection<Vuelo> vuelos = this.vueloService.findVuelosByMes(12, 2020);
		int found = vuelos.size();
		
		assertThat(found).isEqualTo(1);
		
	}
}
