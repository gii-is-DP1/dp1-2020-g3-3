package org.springframework.samples.aerolineasAAAFC.service;


import static org.assertj.core.api.Assertions.assertThat;
import java.util.Collection;
import java.util.Map;

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
	
	@Test
	public void shouldSearchPlatoByType() {
		String prueba = "postre";
		String prueba2 = "Manzana";
		
		Collection<PlatoBase> l = platoBaseService.findPlatosPorTipo(prueba);
		Boolean testeo = l.stream().anyMatch(x -> x.getName().equals(prueba2));
		assertThat(testeo).isTrue();
	}

	@Test
	public void shouldSearchPricing() {
		String prueba = "Manzana";
		
		Map<String, Double> mapaPrecios = platoBaseService.findPlatosPricing();
		assertThat(mapaPrecios).isNotNull();
		
		assertThat(mapaPrecios.get(prueba)).isEqualTo(platoBaseService.findPlatoBaseByName(prueba).getPrecio());
	}
	
}
