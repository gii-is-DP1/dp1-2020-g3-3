package org.springframework.samples.aerolineasAAAFC.service;


import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.aerolineasAAAFC.model.menu.PlatoBase;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class PlatoBaseServiceTests {

	@Autowired
	protected PlatoBaseService platoBaseService;

	@Test
	public void shouldNotSearchPlatoNameNoValido() {
		String prueba = "vaya telita los mergeos";
		Assertions.assertNull(platoBaseService.findPlatoBaseByName(prueba));
	}

	@Test
	public void shouldSearchPlatoNameValido() {
		String prueba = "Manzana";
		PlatoBase p = platoBaseService.findPlatoBaseByName(prueba);
		assertThat(p.getId()).isNotNull();
	}

}
