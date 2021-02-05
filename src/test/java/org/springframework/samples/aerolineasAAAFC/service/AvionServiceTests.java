package org.springframework.samples.aerolineasAAAFC.service;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.aerolineasAAAFC.model.Avion;
import org.springframework.samples.aerolineasAAAFC.model.Vuelo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class AvionServiceTests {

	@Autowired
	protected AvionService avionService;
	@Autowired
	protected AzafatoService azafatoService;
	@Autowired
	protected VueloService vueloService;
	@Autowired
	protected PersonalControlService pControlService;
	
//TODO: testear con las entradas de la db + Tests de inserción y borrado
	
	// TEST DE CONSULTA:
	@Test
	void getTipoAvionSuccesful() {
		Avion a = this.avionService.findAvionById(1);
		
		//TIPO DE AVIÓN
		assertThat(a.getTipoAvion())
		.isNotEmpty()
		.hasToString("Airbus A320");
		
		//CAPACIDAD
		assertThat(a.getCapacidadPasajero())
		.isEqualTo(300);
		
		//HORAS ACUMULADAS
		assertThat(a.getHorasAcumuladas())
		.isEqualTo(400);
		
		//FECHA DE FABRICACIÓN
		assertThat(a.getFechaFabricacion())
		.isEqualTo(LocalDate.of(2015, Month.SEPTEMBER, 24));
		
		//DISPONIBILIDAD
		assertThat(a.getDisponibilidad())
		.isEqualTo(true);
		
		//FECHA DE REVISIÓN
		assertThat(a.getFechaRevision())
		.isEqualTo(LocalDate.of(2017, Month.SEPTEMBER, 24));
		
		//PLAZAS ECONÓMICAS
		assertThat(a.getPlazasEconomica())
		.isEqualTo(200);
		
		//PLAZAS EJECUTIVA
		assertThat(a.getPlazasEjecutiva())
		.isEqualTo(80);
		
		//PLAZAS PRIMERA
		assertThat(a.getPlazasPrimera())
		.isEqualTo(20);
	}

	
	//TEST INSERTAR:
	@Test
	@Transactional
	public void shouldInsertAvion() {
		Collection<Avion> aviones = this.avionService.findAviones();
		int found = aviones.size();

		//CREACIÓN DEL AVIÓN
		Avion avi = new Avion();
		avi.setTipoAvion("Airbus 420");
		avi.setCapacidadPasajero(200);
		avi.setHorasAcumuladas(1000);
		avi.setFechaFabricacion(LocalDate.of(2019, 8, 20));
		avi.setDisponibilidad(Boolean.TRUE);
		avi.setFechaRevision(LocalDate.of(2020, 1, 20));
		avi.setPlazasEconomica(140);
		avi.setPlazasEjecutiva(40);
		avi.setPlazasPrimera(20);
		
		//AÑADIMOS UN VUELO AL AVIÓN
		Vuelo vue = this.vueloService.findVueloById(1);
		List<Vuelo> vues = new ArrayList<Vuelo>();
		vues.add(vue);
		avi.setVuelos(vues);
		
		this.avionService.saveAvion(avi);
		
		assertThat(avi.getId()).isNotEqualTo(0);
		
		aviones = this.avionService.findAviones();
		assertThat(aviones.size()).isEqualTo(found+1);
	}
	
	@Test
	@Transactional
	public void shouldNotInsertAvionNull() {

		//CREACIÓN DEL AVIÓN
		Avion avi = new Avion();
		avi.setCapacidadPasajero(200);
		avi.setHorasAcumuladas(1000);
		avi.setFechaRevision(LocalDate.of(2020, 1, 20));
		avi.setPlazasEconomica(140);
		avi.setPlazasPrimera(20);
		
		//AÑADIMOS UN VUELO AL AVIÓN
		Vuelo vue = this.vueloService.findVueloById(2);
		List<Vuelo> vues = new ArrayList<Vuelo>();
		vues.add(vue);
		avi.setVuelos(vues);
		
		assertThrows(ConstraintViolationException.class, () -> { this.avionService.saveAvion(avi); });
	}
	
	// TEST DE ACTUALIZACIÓN:
	@Test
	@Transactional
	void updateAvionSuccessful() {
		
		Avion avion = this.avionService.findAvionById(1);
		avion.setCapacidadPasajero(100);
		avion.setDisponibilidad(false);
		avion.setFechaFabricacion(LocalDate.of(2010, 5, 16));
		avion.setFechaRevision(LocalDate.of(2010, 12, 28));
		avion.setHorasAcumuladas(140);
		
		this.avionService.saveAvion(avion);
		
		avion = this.avionService.findAvionById(1);
		assertThat(avion.getCapacidadPasajero()).isEqualTo(100);
		assertThat(avion.getDisponibilidad()).isFalse();
		assertThat(avion.getFechaFabricacion()).isEqualTo(LocalDate.of(2010, 5, 16));
		assertThat(avion.getFechaRevision()).isEqualTo(LocalDate.of(2010, 12, 28));
		assertThat(avion.getHorasAcumuladas()).isEqualTo(140);
	}
	
	// TEST DE BORRADO:
	@Test
	@Transactional
	public void shouldDeleteAvionById() {
		Collection<Avion> aviones = this.avionService.findAviones();
		int found = aviones.size();
		
		this.avionService.eliminarAvion(1);
		
		aviones = this.avionService.findAviones();
		assertThat(aviones.size()).isEqualTo(found-1);
	}
}
