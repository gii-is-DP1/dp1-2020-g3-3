package org.springframework.samples.aerolineasAAAFC.web;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.aerolineasAAAFC.model.Avion;
import org.springframework.samples.aerolineasAAAFC.service.AvionService;

import java.text.ParseException;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class AvionFormatterTests {

	@Mock
	private AvionService avionService;

	private AvionFormatter avionFormatter;

	@BeforeEach
	void setup() {
		avionFormatter = new AvionFormatter(avionService);
	}

	@Test
	void testPrint() {
		Mockito.when(avionService.findAvionById(1)).thenReturn(makeAvionRandom());
		Avion avion = this.avionService.findAvionById(1);
		String avionPrint = avionFormatter.print(avion, Locale.ENGLISH);
		assertEquals(avion.getId() + " - " + avion.getTipoAvion() + " (" + avion.getCapacidadPasajero() + ")", avionPrint);
	}

	@Test
	void shouldParse() throws ParseException {
		Mockito.when(avionService.findAvionById(1)).thenReturn(makeAvionRandom());
		Avion avion = avionFormatter.parse("1 - Hola - Hello", Locale.ENGLISH);
		assertEquals("Vin Diesel", avion.getTipoAvion());
	}

	@Test
	void shouldThrowParseException() throws ParseException {
		Assertions.assertThrows(ParseException.class, () -> {
			avionFormatter.parse("2 - enero - esto sale mal", Locale.ENGLISH);
		});
	}
	
	private Avion makeAvionRandom() {
		Avion avion = new Avion();
		avion.setId(1);
		avion.setTipoAvion("Vin Diesel");
		avion.setCapacidadPasajero(200);
		
		return avion;
	}

}