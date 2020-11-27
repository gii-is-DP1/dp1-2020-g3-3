package org.springframework.samples.aerolineasAAAFC.service;


import static org.assertj.core.api.Assertions.assertThat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class AvionServiceTests {

	@Autowired
	protected AvionService avionService;

	@Test
	void updateCapacidadPasajeroAvionSuccessful() {
		Avion avion=avionService.findAvionById(1);
		avion.setCapacidadPasajero(100);
		assertThat(avion.getCapacidadPasajero()).isEqualTo(100);
	}

	@Test
	void updateAzafatosAvionSuccessful() {
		Avion avion=avionService.findAvionById(1);
		Set<Azafato> azafatos=new HashSet<>();
		//azafatos.add(e);
		avion.setAzafatos(azafatos);
		assertThat(avion.getAzafatos()).isEqualTo(azafatos);
	}

	@Test
	void updateDisponibilidadAvionSuccessful() {
		Avion avion=avionService.findAvionById(1);
		avion.setDisponibilidad(false);
		assertThat(avion.getDisponibilidad()).isFalse();
	}

	@Test
	void updateFechaFabricacionAvionSuccessful() {
		Avion avion=avionService.findAvionById(1);
		Date fecha;
			try {
				fecha = new SimpleDateFormat("yyyy/MM/dd").parse("2010/05/16");
				avion.setFechaFabricacion(fecha);
				assertThat(avion.getFechaFabricacion()).isEqualTo(fecha);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
	}

	@Test
	void updateFechaRevisionAvionSuccessful() {
		Avion avion=avionService.findAvionById(1);
		Date fecha;
		try {
			fecha = new SimpleDateFormat("yyyy/MM/dd").parse("2015/12/28");
			avion.setFechaRevision(fecha);
			assertThat(avion.getFechaRevision()).isEqualTo("2015-12-28");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Test
	void updateHorasAcumuladasAvionSuccessful() {
		Avion avion=avionService.findAvionById(1);
		avion.setHorasAcumuladas(140);
		assertThat(avion.getHorasAcumuladas()).isEqualTo(140);
	}
	@Test
	void updatePersonalControlAvionSuccessful() {
		Avion avion=avionService.findAvionById(1);
		Set<PersonalControl> personal= new HashSet<>();
		//personal.add();
		avion.setPersonalControl(personal);
		assertThat(avion.getPersonalControl()).isEqualTo(personal);
	}

	@Test
	void updatePesoMaximoEquipajeAvionSuccessful() {
		Avion avion=avionService.findAvionById(1);
		avion.setPesoMaximoEquipaje(32);
		assertThat(avion.getPesoMaximoEquipaje()).isEqualTo(32);
	}

	@Test
	void updatePlazasEconomicaAvionSuccessful() {
		Avion avion=avionService.findAvionById(1);
		avion.setPlazasEconomica(14);
		assertThat(avion.getPlazasEconomica()).isEqualTo(14);
	}

	@Test
	void updatePlazasPrimeraAvionSuccessful() {
		Avion avion=avionService.findAvionById(1);
		avion.setPlazasPrimera(40);
		assertThat(avion.getPlazasPrimera()).isEqualTo(40);
	}
	@Test
	void updateTipoAvionAvionSuccessful() {
		Avion avion=avionService.findAvionById(1);
		avion.setTipoAvion("Enterprise");
		assertThat(avion.getTipoAvion()).isEqualTo("Enterprise");
	}

	@Test
	void updateVuelosAvionSuccessful() {
		Avion avion=avionService.findAvionById(1);
		Set<Vuelo> vuelos=new HashSet<>();
		//vuelos.add(e);
		avion.setVuelos(vuelos);
		assertThat(avion.getVuelos()).isEqualTo(vuelos);
	}
}
