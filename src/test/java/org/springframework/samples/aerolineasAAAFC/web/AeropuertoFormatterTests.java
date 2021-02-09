package org.springframework.samples.aerolineasAAAFC.web;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.aerolineasAAAFC.model.Aeropuerto;
import org.springframework.samples.aerolineasAAAFC.service.AeropuertoService;
import java.text.ParseException;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class AeropuertoFormatterTests {

	@Mock
	private AeropuertoService aeropuertoService;

	private AeropuertoFormatter aeropuertoFormatter;

	@BeforeEach
	void setup() {
		aeropuertoFormatter = new AeropuertoFormatter(aeropuertoService);
	}

	@Test
	void testPrint() {
		Mockito.when(aeropuertoService.findAeropuertoById(1)).thenReturn(makeAeropuertoRandom());
		Aeropuerto aeropuerto = this.aeropuertoService.findAeropuertoById(1);
		String aeropuertoPrint = aeropuertoFormatter.print(aeropuerto, Locale.ENGLISH);
		assertEquals(aeropuerto.getId() + " - " + aeropuerto.getNombre() + ", " + aeropuerto.getCodigoIATA(), aeropuertoPrint);
	}

	@Test
	void shouldParse() throws ParseException {
		Mockito.when(aeropuertoService.findAeropuertoById(1)).thenReturn(makeAeropuertoRandom());
		Aeropuerto aeropuerto = aeropuertoFormatter.parse("1 - Hola - Hello", Locale.ENGLISH);
		assertEquals("Azeroth", aeropuerto.getNombre());
	}

	@Test
	void shouldThrowParseException() throws ParseException {
		Assertions.assertThrows(ParseException.class, () -> {
			aeropuertoFormatter.parse("2 - enero - esto sale mal", Locale.ENGLISH);
		});
	}
	
	private Aeropuerto makeAeropuertoRandom() {
		Aeropuerto aeropuerto = new Aeropuerto();
		aeropuerto.setId(1);
		aeropuerto.setNombre("Azeroth");
		aeropuerto.setCodigoIATA("AZC");
		
		return aeropuerto;
	}

}