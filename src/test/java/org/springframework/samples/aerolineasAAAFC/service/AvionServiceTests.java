package org.springframework.samples.aerolineasAAAFC.service;


import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.aerolineasAAAFC.model.Avion;
import org.springframework.samples.aerolineasAAAFC.model.Azafato;
import org.springframework.samples.aerolineasAAAFC.model.PersonalControl;
import org.springframework.samples.aerolineasAAAFC.model.Vuelo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class AvionServiceTests {

	@Autowired
	protected AvionService avionService;

	@Test
	@Transactional
	void updateCapacidadPasajeroAvionSuccessful() {
		Avion avion=avionService.findAvionById(1);
		avion.setCapacidadPasajero(100);
		assertThat(avion.getCapacidadPasajero()).isEqualTo(100);
	}

	@Test
	@Transactional
	void updateAzafatosAvionSuccessful() {
		Avion avion=avionService.findAvionById(1);
		Set<Azafato> azafatos=new HashSet<>();
		//azafatos.add(e);
		avion.setAzafatos(azafatos);
		assertThat(avion.getAzafatos()).isEqualTo(azafatos);
	}

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
	@Test
	@Transactional
	void updatePersonalControlAvionSuccessful() {
		Avion avion=avionService.findAvionById(1);
		Set<PersonalControl> personal= new HashSet<>();
		//personal.add();
		avion.setPersonalControl(personal);
		assertThat(avion.getPersonalControl()).isEqualTo(personal);
	}

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
	void updateTipoAvionAvionSuccessful() {
		Avion avion=avionService.findAvionById(1);
		avion.setTipoAvion("Enterprise");
		assertThat(avion.getTipoAvion()).isEqualTo("Enterprise");
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
}
