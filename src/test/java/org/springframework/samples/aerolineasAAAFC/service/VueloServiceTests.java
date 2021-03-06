package org.springframework.samples.aerolineasAAAFC.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Assertions;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.samples.aerolineasAAAFC.model.Aeropuerto;
import org.springframework.samples.aerolineasAAAFC.model.Asiento;
import org.springframework.samples.aerolineasAAAFC.model.Avion;
import org.springframework.samples.aerolineasAAAFC.model.Azafato;
import org.springframework.samples.aerolineasAAAFC.model.Billete;
import org.springframework.samples.aerolineasAAAFC.model.Cliente;
import org.springframework.samples.aerolineasAAAFC.model.PersonalControl;
import org.springframework.samples.aerolineasAAAFC.model.PersonalOficina;
import org.springframework.samples.aerolineasAAAFC.model.Vuelo;
import org.springframework.samples.aerolineasAAAFC.service.exceptions.DisponibilidadAvionException;
import org.springframework.samples.aerolineasAAAFC.service.exceptions.HorasImposiblesException;
import org.springframework.samples.aerolineasAAAFC.service.exceptions.HorasMaximasVueloException;
import org.springframework.samples.aerolineasAAAFC.service.exceptions.TelefonoErroneoException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class VueloServiceTests {
	
	@Autowired
	protected VueloService vueloService;
	
	@Autowired
	protected AvionService avionService;
	
	@Autowired
	protected AeropuertoService aeroService;
	
	@Autowired
	protected PersonalControlService pControlService;
	
	@Autowired
	protected PersonalOficinaService pOficinaService;
	
	@Autowired
	protected AzafatoService AzafatoService;
	
	@Autowired
	protected BilleteService billeteService; 
	
	
	//TEST DE CONSULTA
	@Test
	void getDatosVueloSuccessful() {
		Vuelo vuelo = this.vueloService.findVueloById(1);
		
		assertThat(vuelo.getFechaSalida())
		.isBefore(vuelo.getFechaLlegada());
		
		assertThat(vuelo.getFechaLlegada())
		.isAfter(vuelo.getFechaSalida());
		

		String precio = String.valueOf(vuelo.getCoste());
		
		assertThat(precio).isEqualTo(String.valueOf(vuelo.getCoste()));
	
		String nombreAeroOrigen = vuelo.getAeropuertoOrigen().getNombre();
		
		assertThat(nombreAeroOrigen).isEqualTo("Aeropuerto de Sao Paulo Guarulhos");

		String codigoAeroDestino = vuelo.getAeropuertoDestino().getCodigoIATA();
		
		assertThat(codigoAeroDestino).isEqualTo("MAD");
	}
	
	@Test
	void getBilletesSuccessful() {
		Vuelo vuelo = this.vueloService.findVueloById(2);
		List<Billete> billetes = this.billeteService.findBilletesByVuelo(vuelo.getId());
		int found = billetes.size();
		
		assertThat(found).isEqualTo(billetes.size());
	}
	
	@Test
	@Transactional 
	public void shouldFindClientesByVuelo() {
		Pageable page =  PageRequest.of(0, 20);
		Vuelo vuelo = this.vueloService.findVueloById(2);
		Page<Cliente> clientes = this.vueloService.findClientesPorVuelo(vuelo,page); 
		assertThat(clientes).isNotEmpty();
	}

	
	@Test
	@Transactional
	public void shouldFindVueloByDate() {
		
		//POR MES
		Pageable page =  PageRequest.of(0, 20);
		Page<Vuelo> vuelos = this.vueloService.findVuelosByMes(06, 2019,page);
		int found = vuelos.getContent().size();
		
		//OFERTADOS
		Page<Vuelo> vuelos1 = this.vueloService.findVuelosOfertadosByMes(01, 2021,page);
		int found1 = vuelos1.getContent().size();
		
		assertThat(found).isEqualTo(1);
		assertThat(found1).isEqualTo(3);
		
  }
  
	@Test
	@Transactional
	public void shouldFindMenusByVuelo() {
		int idVueloConMenu = 2;

		Vuelo v = this.vueloService.findVueloById(idVueloConMenu);
		
		int contadorMenus = this.billeteService.findBilletesByVuelo(v.getId())
				.stream().mapToInt(x -> x.getMenus().size()).sum();
		
		Map<String, Long> mapaContador = this.vueloService.findMenusPorVuelo(v);
		
		// Dividimos por 3 ya que cada menu tiene 3 platos
		assertThat(mapaContador.values().stream().mapToLong(x -> x).sum()
				/3).isEqualTo(contadorMenus); 
	}
	
	@Test
	@Transactional
	public void shouldCountMenusInVuelo() {
		int idVueloConMenu = 2;

		Vuelo v = this.vueloService.findVueloById(idVueloConMenu);
		
		int contadorMenus = this.billeteService.findBilletesByVuelo(v.getId())
				.stream().mapToInt(x -> x.getMenus().size()).sum();
		
		Map<String, Long> mapaContador = this.vueloService.findMenusPorVuelo(v);
		
		// Dividimos por 3 ya que cada menu tiene 3 platos
		assertThat(this.vueloService.countMenusInVuelo(mapaContador)).isEqualTo(contadorMenus); 
	}
	
	
	//TEST DE INSERCIÓN
	@Test
	@Transactional
	public void shouldInsertVuelo() throws TelefonoErroneoException{
		Collection<Vuelo> vuelos = this.vueloService.findVuelos();
		int found = vuelos.size();
		
		//CREACIÓN DEL VUELO
		Vuelo vuelo = new Vuelo();
		vuelo.setFechaSalida(LocalDateTime.of(2020, Month.DECEMBER, 1, 12, 23));
		vuelo.setFechaLlegada(LocalDateTime.of(2020, Month.DECEMBER, 1, 19, 23));
		vuelo.setCoste(42.0);
		vuelo.setAsientos(new ArrayList<Asiento>());
		vuelo.setPersonalOficina(new HashSet<>());
		vuelo.setAvion(this.avionService.findAvionById(1));
		
		//CREACIÓN AEROPUERTO DE ORIGEN
		Aeropuerto aeroOri = new Aeropuerto();
		aeroOri.setNombre("Aeropuerto Humberto Delgado");
		aeroOri.setLocalizacion("Lisboa, Portugal");
		aeroOri.setCodigoIATA("LIS");
		aeroOri.setTelefono("+351218413500");
		this.aeroService.saveAeropuerto(aeroOri);
		
		vuelo.setAeropuertoOrigen(aeroOri);
		
		//CREACIÓN AEROPUERTO DE DESTINO
		Aeropuerto aeroDest = new Aeropuerto();
		aeroDest.setNombre("Aeropuerto de Málaga - Costa del Sol");
		aeroDest.setLocalizacion("Málaga, España");
		aeroDest.setCodigoIATA("AGP");
		aeroDest.setTelefono("+3491321100");
		this.aeroService.saveAeropuerto(aeroDest);

		vuelo.setAeropuertoDestino(aeroDest);
		
		//AÑADIMOS UN AVIÓN AL VUELO
		vuelo.setAvion(avionService.findAvionById(1));
		
		//AÑADIMOS CONTROLADORES AL VUELO
		Set<PersonalControl> personalControl = new HashSet<>();
		personalControl.add(this.pControlService.findPersonalControlById(1));
		personalControl.add(this.pControlService.findPersonalControlById(4));
		personalControl.add(this.pControlService.findPersonalControlById(5));
		vuelo.setPersonalControl(personalControl);
		
		//AÑADIMOS AZAFATOS AL VUELO
		Set<Azafato> azafatos = new HashSet<>();
		azafatos.add(this.AzafatoService.findAzafatoById(1));
		azafatos.add(this.AzafatoService.findAzafatoById(2));
		azafatos.add(this.AzafatoService.findAzafatoById(3));
		azafatos.add(this.AzafatoService.findAzafatoById(4));
		azafatos.add(this.AzafatoService.findAzafatoById(5));
		azafatos.add(this.AzafatoService.findAzafatoById(6));
		azafatos.add(this.AzafatoService.findAzafatoById(7));
		vuelo.setAzafatos(azafatos);
		
		try {
			this.vueloService.saveVuelo(vuelo);
		} catch (HorasImposiblesException ex) {
			log.error("Horas imposibles",ex);
		}catch (HorasMaximasVueloException ex1) {
			log.error("Excedido el número de horas de vuelo",ex1);
		}catch (DisponibilidadAvionException ex2) {
			log.error("El avión no está disponible",ex2);
		}catch (DataIntegrityViolationException ex3) {
			log.error("Error en la inserción de datos",ex3);
		}
		
		assertThat(vuelo.getAsientos().size()).isEqualTo(vuelo.getAvion().getCapacidadPasajero());
		assertThat(vuelo.getId().longValue()).isNotEqualTo(0);
		
		vuelos = this.vueloService.findVuelos();
		assertThat(vuelos.size()).isEqualTo(found + 1);
	}
	
	@Test
	@Transactional(rollbackFor={ConstraintViolationException.class})
	public void shouldNotInsertVueloAeropuertoIdenticos() throws TelefonoErroneoException{
		
		//CREACIÓN DEL VUELO
		Vuelo vuelo = new Vuelo();
		vuelo.setFechaSalida(LocalDateTime.of(2020, Month.DECEMBER, 1, 12, 23));
		vuelo.setFechaLlegada(LocalDateTime.of(2020, Month.DECEMBER, 1, 20, 23));
		vuelo.setCoste(42.0);
		
		//CREACIÓN AEROPUERTO
		Aeropuerto aeroOri = new Aeropuerto();
		aeroOri.setNombre("Aeropuerto Humberto Delgado");
		aeroOri.setLocalizacion("Lisboa, Portugal");
		aeroOri.setCodigoIATA("LIS");
		aeroOri.setTelefono("+351218413500");
		this.aeroService.saveAeropuerto(aeroOri);

		vuelo.setAeropuertoOrigen(aeroOri);
		vuelo.setAeropuertoDestino(aeroOri);
		
		//AÑADIMOS UN AVIÓN AL VUELO
		vuelo.setAvion(this.avionService.findAvionById(1));
		
		//AÑADIMOS CONTROLADORES AL VUELO
		Set<PersonalControl> personalControl = new HashSet<PersonalControl>();
		personalControl.add(this.pControlService.findPersonalControlById(1));
		personalControl.add(this.pControlService.findPersonalControlById(4));
		personalControl.add(this.pControlService.findPersonalControlById(5));
		vuelo.setPersonalControl(personalControl);
		
		//AÑADIMOS AZAFATOS AL VUELO
		Set<Azafato> azafatos = new HashSet<Azafato>();
		azafatos.add(this.AzafatoService.findAzafatoById(1));
		azafatos.add(this.AzafatoService.findAzafatoById(2));
		azafatos.add(this.AzafatoService.findAzafatoById(3));
		azafatos.add(this.AzafatoService.findAzafatoById(4));
		azafatos.add(this.AzafatoService.findAzafatoById(5));
		azafatos.add(this.AzafatoService.findAzafatoById(6));
		azafatos.add(this.AzafatoService.findAzafatoById(7));
		vuelo.setAzafatos(azafatos);
		
		//AÑADIMOS EL PERSONAL DE OFICINA AL VUELO
		Set<PersonalOficina> personalOficina = new HashSet<PersonalOficina>();
		personalOficina.add(this.pOficinaService.findPersonalOficinaById(1));
		personalOficina.add(this.pOficinaService.findPersonalOficinaById(2));
		vuelo.setPersonalOficina(personalOficina);
		vuelo.setHorasVuelo((long)0);
		
		Assertions.assertThrows(ConstraintViolationException.class, ()->{ this.vueloService.saveVuelo(vuelo); });
	}	
	
	@Test
	@Transactional
	public void shouldNotInsertVueloHorasInvalidas() throws TelefonoErroneoException {
		Pageable page =  PageRequest.of(0, 20);
		Page<Vuelo> vuelos = this.vueloService.findVuelosOrdered(page);
		int found = vuelos.getContent().size();
		
		//CREACIÓN DEL VUELO
		Vuelo vuelo = new Vuelo();
		vuelo.setFechaSalida(LocalDateTime.of(2020, Month.DECEMBER, 3, 12, 23));
		vuelo.setFechaLlegada(LocalDateTime.of(2020, Month.DECEMBER, 2, 12, 23));
		vuelo.setCoste(42.0);
		
		//CREACIÓN AEROPUERTO DE ORIGEN
		Aeropuerto aeroOri = new Aeropuerto();
		aeroOri.setNombre("Aeropuerto Humberto Delgado");
		aeroOri.setLocalizacion("Lisboa, Portugal");
		aeroOri.setCodigoIATA("LIS");
		aeroOri.setTelefono("+351218413500");
		this.aeroService.saveAeropuerto(aeroOri);
		
		vuelo.setAeropuertoOrigen(aeroOri);
		
		//CREACIÓN AEROPUERTO DE DESTINO
		Aeropuerto aeroDest = new Aeropuerto();
		aeroDest.setNombre("Aeropuerto de Málaga - Costa del Sol");
		aeroDest.setLocalizacion("Málaga, España");
		aeroDest.setCodigoIATA("AGP");
		aeroDest.setTelefono("+3491321100");
		this.aeroService.saveAeropuerto(aeroDest);

		vuelo.setAeropuertoDestino(aeroDest);
		
		//AÑADIMOS UN AVIÓN AL VUELO
		vuelo.setAvion(this.avionService.findAvionById(1));
		
		//AÑADIMOS CONTROLADORES AL VUELO
		Set<PersonalControl> personalControl = new HashSet<>();
		personalControl.add(this.pControlService.findPersonalControlById(1));
		personalControl.add(this.pControlService.findPersonalControlById(4));
		personalControl.add(this.pControlService.findPersonalControlById(5));
		vuelo.setPersonalControl(personalControl);
		
		//AÑADIMOS AZAFATOS AL VUELO
		Set<Azafato> azafatos = new HashSet<>();
		azafatos.add(this.AzafatoService.findAzafatoById(1));
		azafatos.add(this.AzafatoService.findAzafatoById(2));
		azafatos.add(this.AzafatoService.findAzafatoById(3));
		azafatos.add(this.AzafatoService.findAzafatoById(4));
		azafatos.add(this.AzafatoService.findAzafatoById(5));
		azafatos.add(this.AzafatoService.findAzafatoById(6));
		azafatos.add(this.AzafatoService.findAzafatoById(7));
		vuelo.setAzafatos(azafatos);
		
		Assertions.assertThrows(HorasImposiblesException.class, ()->{ this.vueloService.saveVuelo(vuelo); });
		vuelos = this.vueloService.findVuelosOrdered(page);
		assertThat(vuelos.getContent().size()).isEqualTo(found);
	}
	
	
	//TEST DE ACTUALIZACIÓN
	@Test
	@Transactional
	public void shouldUpdateHorasVuelo() throws DataAccessException, HorasImposiblesException, HorasMaximasVueloException, DisponibilidadAvionException {
		Vuelo vuelo = vueloService.findVueloById(1);
		
		vuelo.setFechaSalida(LocalDateTime.of(2021, 1, 11, 12, 40));
		vuelo.setFechaLlegada(LocalDateTime.of(2021, 1, 11, 22, 40));
		
		this.vueloService.saveVuelo(vuelo);
		
		assertThat(vuelo.getFechaSalida()).isEqualTo(LocalDateTime.of(2021, 1, 11, 12, 40));
		assertThat(vuelo.getFechaLlegada()).isEqualTo(LocalDateTime.of(2021, 1, 11, 22, 40));
	}
	
	@Test
	@Transactional
	public void shouldNotUpdateHorasMaximasVuelo() {
		Vuelo vuelo = vueloService.findVueloById(1);
		
		vuelo.setFechaSalida(LocalDateTime.of(2020, Month.DECEMBER, 1, 12, 23));
		vuelo.setFechaLlegada(LocalDateTime.of(2020, Month.DECEMBER, 2, 12, 23));
		
		assertThrows(HorasImposiblesException.class, () -> { this.vueloService.saveVuelo(vuelo); });
	}
	
	@Test
	@Transactional
	public void shouldNotUpdateAvionNoDisponible() {
		Avion avion = avionService.findAvionById(2);
		avion.setHorasAcumuladas(1230);
		
		Vuelo vuelo = vueloService.findVueloById(1);
		vuelo.setAvion(avion);
		
		assertThrows(DisponibilidadAvionException.class, () -> { this.vueloService.saveVuelo(vuelo); });
	}
//	
//	
//	//TEST DE BORRADO
//	@Test
//	@Transactional
//	public void shouldDeleteVueloById() {
//		Collection<Vuelo> vuelos = this.vueloService.findVuelos();
//		int found = vuelos.size();
//
//		this.vueloService.eliminarVuelo(1);
//		
//		int found2 = this.vueloService.findVuelos().size();
//		assertThat(found2).isEqualTo(found - 1);
//	}

}
