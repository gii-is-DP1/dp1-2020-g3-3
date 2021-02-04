package org.springframework.samples.aerolineasAAAFC.service;


import static org.assertj.core.api.Assertions.assertThat;
import java.util.Collection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.aerolineasAAAFC.model.equipaje.EquipajeBase;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class EquipajeBaseServiceTests {

	@Autowired
	protected EquipajeBaseService equipajeBaseService;

	@Test
	public void shouldNotSearchEquipajeNameNoValido() {
		String prueba = "sisisisii";
		Assertions.assertNull(this.equipajeBaseService.findEquipajeBaseByName(prueba));
	}

	@Test
	public void shouldSearchEquipajeNameValido() {
		String prueba = "Grande";
		EquipajeBase e = this.equipajeBaseService.findEquipajeBaseByName(prueba);
		assertThat(e.getId()).isNotNull();
	}
	
	@Test
	public void shouldSearchAllEquipajesBase() {
		int numeroEquipajesBaseActual = 3;
		
		Collection<EquipajeBase> l = this.equipajeBaseService.findEquipajesBase();
		assertThat(l.size()).isEqualTo(numeroEquipajesBaseActual);
	}
	
}
