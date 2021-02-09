package org.springframework.samples.aerolineasAAAFC.web;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.aerolineasAAAFC.model.Azafato;
import org.springframework.samples.aerolineasAAAFC.service.AzafatoService;

import java.text.ParseException;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class AzafatoFormatterTests {

	@Mock
	private AzafatoService azafatoService;

	private AzafatoFormatter azafatoFormatter;

	@BeforeEach
	void setup() {
		azafatoFormatter = new AzafatoFormatter(azafatoService);
	}

	@Test
	void testPrint() {
		Mockito.when(azafatoService.findAzafatoById(1)).thenReturn(makeAzafatoRandom());
		Azafato azafato = this.azafatoService.findAzafatoById(1);
		String azafatoPrint = azafatoFormatter.print(azafato, Locale.ENGLISH);
		assertEquals(azafato.getId() + " - " + azafato.getNombre() + " " + azafato.getApellidos(), azafatoPrint);
	}

	@Test
	void shouldParse() throws ParseException {
		Mockito.when(azafatoService.findAzafatoById(1)).thenReturn(makeAzafatoRandom());
		Azafato azafato = azafatoFormatter.parse("1 - Hola - Hello", Locale.ENGLISH);
		assertEquals("Vin", azafato.getNombre());
	}

	@Test
	void shouldThrowParseException() throws ParseException {
		Assertions.assertThrows(ParseException.class, () -> {
			azafatoFormatter.parse("2 - enero esto sale mal", Locale.ENGLISH);
		});
	}
	
	private Azafato makeAzafatoRandom() {
		Azafato azafato = new Azafato();
		azafato.setId(1);
		azafato.setNombre("Vin");
		azafato.setApellidos("Diesel");
		
		return azafato;
	}

}