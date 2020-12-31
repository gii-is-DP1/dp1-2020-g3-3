package org.springframework.samples.aerolineasAAAFC.service;


import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

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
		Avion a = avionService.findAvionById(1);
		assertThat(a.getTipoAvion())
		.isNotEmpty()
		.hasToString("Airbus A320");
	}
	
	@Test
	void getCapacidadAvionSuccesful() {
		Avion a = avionService.findAvionById(1);
		assertThat(a.getCapacidadPasajero()).isEqualTo(300);
	}
	
	@Test
	void getPesoMaximoAvionSuccesful() {
		Avion a = avionService.findAvionById(1);
		assertThat(a.getPesoMaximoEquipaje()).isEqualTo(2);
	}
	
	@Test
	void getHorasAcumuladasAvionSuccesful() {
		Avion a = avionService.findAvionById(1);
		assertThat(a.getHorasAcumuladas()).isEqualTo(1700);
	}
	
	@Test
	void getFechaFabricacionAvionSuccesful() {
		Avion a = avionService.findAvionById(1);
		LocalDate fecha = LocalDate.of(2015, Month.SEPTEMBER, 24);
		assertThat(a.getFechaFabricacion()).isEqualTo(fecha);
	}
	
	@Test
	void getDisponibilidadAvionSuccesful() {
		Avion a = avionService.findAvionById(1);
		assertThat(a.getDisponibilidad()).isEqualTo(true);
	}
	
	@Test
	void getFechaRevisionAvionSuccesful() {
		Avion a = avionService.findAvionById(1);
		LocalDate fecha = LocalDate.of(2017, Month.SEPTEMBER, 24);
		assertThat(a.getFechaRevision()).isEqualTo(fecha);
	}
	
	@Test
	void getPlazasEconomicaAvionSuccesful() {
		Avion a = avionService.findAvionById(1);
		assertThat(a.getPlazasEconomica()).isEqualTo(200);
	}
	
	@Test
	void getPlazasEjecutivaAvionSuccesful() {
		Avion a = avionService.findAvionById(1);
		assertThat(a.getPlazasEjecutiva()).isEqualTo(80);
	}
	
	@Test
	void getPlazasPrimeraAvionSuccesful() {
		Avion a = avionService.findAvionById(1);
		assertThat(a.getPlazasPrimera()).isEqualTo(20);
	}
	
	// TEST DE ACTUALIZACIÓN:
	@Test
	@Transactional
	void updateCapacidadPasajeroAvionSuccessful() {
		Avion avion=avionService.findAvionById(1);
		avion.setCapacidadPasajero(100);
		assertThat(avion.getCapacidadPasajero()).isEqualTo(100);
	}

//	@Test
//	@Transactional
//	void updateAzafatosAvionSuccessful() {
//		Avion avion=avionService.findAvionById(1);
//		Set<Azafato> azafatos=new HashSet<>();
//		//azafatos.add(e);
//		avion.setAzafatos(azafatos);
//		assertThat(avion.getAzafatos()).isEqualTo(azafatos);
//	}

	@Test
	@Transactional
	void updateDisponibilidadAvionSuccessful() {
		Avion avion=avionService.findAvionById(1);
		avion.setDisponibilidad(false);
		assertThat(avion.getDisponibilidad()).isFalse();
	}

	@Test
	@Transactional
	void updateFechaFabricacionAvionSuccessful() {
		Avion avion=avionService.findAvionById(1);
		LocalDate fecha = LocalDate.parse("2010-05-16", DateTimeFormatter.ISO_DATE); 
		avion.setFechaFabricacion(fecha);
		assertThat(avion.getFechaFabricacion()).isEqualTo(fecha);
	}

	@Test
	@Transactional
	void updateFechaRevisionAvionSuccessful() {
		Avion avion=avionService.findAvionById(1);

		LocalDate fecha = LocalDate.parse("2015-12-28", DateTimeFormatter.ISO_DATE);
		avion.setFechaRevision(fecha);
		assertThat(avion.getFechaRevision()).isEqualTo("2015-12-28");
	}

	@Test
	@Transactional
	void updateHorasAcumuladasAvionSuccessful() {
		Avion avion=avionService.findAvionById(1);
		avion.setHorasAcumuladas(140);
		assertThat(avion.getHorasAcumuladas()).isEqualTo(140);
	}
	
//	@Test
//	@Transactional
//	void updatePersonalControlAvionSuccessful() {
//		Avion avion=avionService.findAvionById(1);
//		Set<PersonalControl> personal= new HashSet<>();
//		//personal.add();
//		avion.setPersonalControl(personal);
//		assertThat(avion.getPersonalControl()).isEqualTo(personal);
//	}

	@Test
	@Transactional
	void updatePesoMaximoEquipajeAvionSuccessful() {
		Avion avion=avionService.findAvionById(1);
		avion.setPesoMaximoEquipaje(32);
		assertThat(avion.getPesoMaximoEquipaje()).isEqualTo(32);
	}

	@Test
	@Transactional
	void updatePlazasEconomicaAvionSuccessful() {
		Avion avion=avionService.findAvionById(1);
		avion.setPlazasEconomica(14);
		assertThat(avion.getPlazasEconomica()).isEqualTo(14);
	}

	@Test
	@Transactional
	void updatePlazasPrimeraAvionSuccessful() {
		Avion avion=avionService.findAvionById(1);
		avion.setPlazasPrimera(40);
		assertThat(avion.getPlazasPrimera()).isEqualTo(40);
	}

	@Test
	@Transactional
	void updateVuelosAvionSuccessful() {
		Avion avion=avionService.findAvionById(1);
		Set<Vuelo> vuelos=new HashSet<>();
		//vuelos.add(e);
		avion.setVuelos(vuelos);
		assertThat(avion.getVuelos()).isEqualTo(vuelos);
	}
	
	// TEST DE INSERCIÓN:
	@Test
	@Transactional
	public void shouldInsertAvion() {
		Collection<Avion> aviones = this.avionService.findAviones();
		int found = aviones.size();
		
//		Azafato aza = this.azafatoService.findAzafatoById(1);
//		Set<Azafato> azas = new HashSet<Azafato>();
//		azas.add(aza);
		
		Vuelo vue = this.vueloService.findVueloById(1);
		Set<Vuelo> vues = new HashSet<Vuelo>();
		vues.add(vue);
		
//		PersonalControl pCon = this.pControlService.findPersonalControlById(1);
//		Set<PersonalControl> pCons = new HashSet<PersonalControl>();
//		pCons.add(pCon);
		
		
		Avion avi = new Avion();
		avi.setTipoAvion("Airbus 420");
		avi.setCapacidadPasajero(200);
		avi.setPesoMaximoEquipaje(20);
		avi.setHorasAcumuladas(1000);
		avi.setFechaFabricacion(LocalDate.of(2019, 8, 20));
		avi.setDisponibilidad(Boolean.TRUE);
		avi.setFechaRevision(LocalDate.of(2020, 1, 20));
		avi.setPlazasEconomica(140);
		avi.setPlazasEjecutiva(40);
		avi.setPlazasPrimera(20);
		
//		avi.setAzafatos(azas);
		avi.setVuelos(vues);
//		avi.setPersonalControl(pCons);
		
		// Por ahora no hay restricciones que puedan saltar por insercion de datos
		this.avionService.saveAvion(avi);
		
		assertThat(avi.getId()).isNotEqualTo(0);
		
		aviones = this.avionService.findAviones();
		assertThat(aviones.size()).isEqualTo(found+1);
	}
	
	// TEST DE BORRADO:
	@Test
	@Transactional
	public void shouldDeleteAvionById() {
		Collection<Avion> aviones = this.avionService.findAviones();
		int found = aviones.size();
		
		avionService.eliminarAvion(1);
		
		aviones = this.avionService.findAviones();
		assertThat(aviones.size()).isEqualTo(found-1);
	}
}
