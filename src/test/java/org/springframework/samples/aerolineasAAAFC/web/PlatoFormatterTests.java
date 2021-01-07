package org.springframework.samples.aerolineasAAAFC.web;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.aerolineasAAAFC.model.menu.Plato;
import org.springframework.samples.aerolineasAAAFC.model.menu.PlatoBase;
import org.springframework.samples.aerolineasAAAFC.model.menu.PlatoType;
import org.springframework.samples.aerolineasAAAFC.service.PlatoBaseService;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class PlatoFormatterTests {

	@Mock
	private PlatoBaseService platoBaseService;

	private PlatoFormatter platoFormatter;

	@BeforeEach
	void setup() {
		platoFormatter = new PlatoFormatter(platoBaseService);
	}

	@Test
	void testPrint() {
		Mockito.when(platoBaseService.findPlatoBaseByName("Manzana")).thenReturn(makePlatoRandom());
		Plato p = new Plato();
		PlatoBase pb = this.platoBaseService.findPlatoBaseByName("Manzana");
		p.setPlatoBase(pb);
		String platoName = platoFormatter.print(p, Locale.ENGLISH);
		assertEquals("Manzana", platoName);
	}

	@Test
	void shouldParse() throws ParseException {
		Mockito.when(platoBaseService.findPlatosBase()).thenReturn(makePlatosBase());
		Plato p = platoFormatter.parse("Manzana dorada", Locale.ENGLISH);
		assertEquals("Manzana dorada", p.getPlatoBase().getName());
	}

	@Test
	void shouldThrowParseException() throws ParseException {
		Mockito.when(platoBaseService.findPlatosBase()).thenReturn(makePlatosBase());
		Assertions.assertThrows(ParseException.class, () -> {
			platoFormatter.parse("Asco de coronavirus, llevad mascarilla", Locale.ENGLISH);
		});
	}

	private Collection<PlatoBase> makePlatosBase() {
		Collection<PlatoBase> platosBase = new ArrayList<>();
		PlatoBase pb = new PlatoBase();
		pb.setName("Manzana dorada");
		pb.setPrecio(2193.54);
		
		PlatoType pt = new PlatoType();
		pt.setName("platoDeLaPera");
		
		pb.setTipoPlato(pt);
		
		platosBase.add(pb);
		
		return platosBase;
	}
	
	private PlatoBase makePlatoRandom() {
		PlatoBase pb = new PlatoBase();
		pb.setName("Manzana");
		pb.setPrecio(0.1);
		
		PlatoType pt = new PlatoType();
		pt.setName("llevadMascarilla");
		
		pb.setTipoPlato(pt);
		
		return pb;
	}

}