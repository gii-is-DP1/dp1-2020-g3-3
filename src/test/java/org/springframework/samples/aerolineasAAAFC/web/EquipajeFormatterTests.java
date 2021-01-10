package org.springframework.samples.aerolineasAAAFC.web;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.aerolineasAAAFC.model.equipaje.EquipajeBase;
import org.springframework.samples.aerolineasAAAFC.service.EquipajeBaseService;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class EquipajeFormatterTests {

	@Mock
	private EquipajeBaseService equipajeBaseService;

	private EquipajeBaseFormatter equipajeBaseFormatter;

	@BeforeEach
	void setup() {
		equipajeBaseFormatter = new EquipajeBaseFormatter(equipajeBaseService);
	}

	@Test
	void testPrint() {
		Mockito.when(equipajeBaseService.findEquipajeBaseByName("Grande")).thenReturn(makeEquipajeRandom());
		EquipajeBase eb = this.equipajeBaseService.findEquipajeBaseByName("Grande");
		String equipajeName = equipajeBaseFormatter.print(eb, Locale.ENGLISH);
		assertEquals("Grande - Dimensiones: "+eb.getDimensiones()+" (cm)  Precio: "+eb.getPrecio()+"€", equipajeName);
	}

	@Test
	void shouldParse() throws ParseException {
		Mockito.when(equipajeBaseService.findEquipajesBase()).thenReturn(makeEquipajesBase());
		EquipajeBase eb = equipajeBaseFormatter.parse("Intermedio", Locale.ENGLISH);
		assertEquals("Intermedio", eb.getName());
	}

	@Test
	void shouldThrowParseException() throws ParseException {
		Mockito.when(equipajeBaseService.findEquipajesBase()).thenReturn(makeEquipajesBase());
		Assertions.assertThrows(ParseException.class, () -> {
			equipajeBaseFormatter.parse("enero", Locale.ENGLISH);
		});
	}

	private Collection<EquipajeBase> makeEquipajesBase() {
		Collection<EquipajeBase> equipajesBase = new ArrayList<>();
		EquipajeBase eb = new EquipajeBase();
		eb.setDimensiones("55x55x55");
		eb.setName("Intermedio");
		eb.setPrecio(20.);
		
		equipajesBase.add(eb);
		
		return equipajesBase;
	}
	
	private EquipajeBase makeEquipajeRandom() {
		EquipajeBase e = new EquipajeBase();
		e.setDimensiones("12x12x12");
		e.setName("Grande");
		e.setPrecio(25.);
		
		return e;
	}

}