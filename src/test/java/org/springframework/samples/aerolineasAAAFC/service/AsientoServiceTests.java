package org.springframework.samples.aerolineasAAAFC.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.aerolineasAAAFC.model.Asiento;
import org.springframework.samples.aerolineasAAAFC.model.Clase;
import org.springframework.samples.aerolineasAAAFC.model.Vuelo;
import org.springframework.stereotype.Service;


@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class AsientoServiceTests {

	@Autowired
	protected AsientoService asientoService;
	
	@Autowired
	protected VueloService vueloService;
	
	@Autowired
	protected AvionService avionService;
	
	@Test
	void shouldFindAsientoById() {
		Vuelo v = this.vueloService.findVueloById(2);
		
		Asiento a = new Asiento();
		a.setClase(Clase.PRIMERACLASE);
		a.setLibre(true);
		a.setNombre("B1");
		a.setVuelo(v);
		this.asientoService.saveAsiento(a);
		v.getAsientos().add(a);
		
		Asiento aV2 = this.asientoService.findAsientoById(a.getId());
		
		assertThat(a.getId()).isEqualTo(aV2.getId());
		
	}
	
	@Test
	void shouldFindAllAsientosByVuelo() {
		Vuelo v = this.vueloService.findVueloById(2);
		int asientos1 = v.getAsientos().size();
		
		int asientos2 = this.asientoService.findAllAsientosByVuelo(v).size();
		
		assertThat(asientos1).isEqualTo(asientos2);
	}
	
	@Test
	void shouldInsertNewAsiento() {
		Vuelo v = this.vueloService.findVueloById(2);
		
		List<Asiento> asientos = v.getAsientos();
		int numberAsientos1 = asientos.size();
		
		Asiento a = new Asiento();
		a.setClase(Clase.PRIMERACLASE);
		a.setLibre(true);
		a.setNombre("B1");
		a.setVuelo(v);
		this.asientoService.saveAsiento(a);
		v.getAsientos().add(a);
		
		int numberAsientos2 = this.asientoService.findAllAsientosByVuelo(v).size();
		
		assertThat(numberAsientos1+1).isEqualTo(numberAsientos2);
		
	}
	
	@Test
	void shouldFindAsientos() {
		assertThat(this.asientoService.findAsientos().size()).isNotEqualTo(0);
	}
	
	@Test
	void shouldFindAllAsientosSinOcupar() {
		Vuelo v = this.vueloService.findVueloById(2);
		int asientosSinOcupar1 = this.asientoService.findAsientosSinOcupar(v).size();
		
		Asiento a = new Asiento();
		a.setClase(Clase.PRIMERACLASE);
		a.setLibre(true);
		a.setNombre("B1");
		a.setVuelo(v);
		this.asientoService.saveAsiento(a);
		v.getAsientos().add(a);
		
		int asientosSinOcupar2 = this.asientoService.findAsientosSinOcupar(v).size();
		
		assertThat(asientosSinOcupar1).isEqualTo(asientosSinOcupar2 - 1);
	}
	
	@Test
	void shouldSaveManyAsientos() {
		int numAsientos1 = this.asientoService.findAsientos().size();
		Vuelo v = this.vueloService.findVueloById(2);
		
		this.asientoService.saveManyAsientos(v);
		int numAsientos2 = this.asientoService.findAsientos().size();
		
		assertThat(numAsientos1 + v.getAvion().getCapacidadPasajero()).isEqualTo(numAsientos2);
		
		Long newAsientosPrimera = v.getAsientos().stream().filter(x -> x.getClase().equals(Clase.PRIMERACLASE))
				.collect(Collectors.counting());
		Long newAsientosEjecutiva = v.getAsientos().stream().filter(x -> x.getClase().equals(Clase.EJECUTIVA))
				.collect(Collectors.counting());
		Long newAsientosEconomica = v.getAsientos().stream().filter(x -> x.getClase().equals(Clase.ECONOMICA))
				.collect(Collectors.counting());
		
		assertThat(v.getAvion().getPlazasPrimera()).isEqualTo(newAsientosPrimera.intValue());
		assertThat(v.getAvion().getPlazasEjecutiva()).isEqualTo(newAsientosEjecutiva.intValue());
		assertThat(v.getAvion().getPlazasEconomica()).isEqualTo(newAsientosEconomica.intValue());
	}

}