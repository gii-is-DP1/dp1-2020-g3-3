package org.springframework.samples.aerolineasAAAFC.web;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.aerolineasAAAFC.model.PersonalOficina;
import org.springframework.samples.aerolineasAAAFC.service.PersonalOficinaService;

import java.text.ParseException;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class PersonalOficinaFormatterTests {

	@Mock
	private PersonalOficinaService personalOficinaService;

	private PersonalOficinaFormatter personalOficinaFormatter;

	@BeforeEach
	void setup() {
		personalOficinaFormatter = new PersonalOficinaFormatter(personalOficinaService);
	}

	@Test
	void testPrint() {
		Mockito.when(personalOficinaService.findPersonalOficinaById(1)).thenReturn(makePersonalOficinaRandom());
		PersonalOficina personalOficina = this.personalOficinaService.findPersonalOficinaById(1);
		String personalOficinaPrint = personalOficinaFormatter.print(personalOficina, Locale.ENGLISH);
		assertEquals(personalOficina.getId() + " - " + personalOficina.getNombre() + " " + 
		personalOficina.getApellidos(), personalOficinaPrint);
	}

	@Test
	void shouldParse() throws ParseException {
		Mockito.when(personalOficinaService.findPersonalOficinaById(1)).thenReturn(makePersonalOficinaRandom());
		PersonalOficina personalOficina = personalOficinaFormatter.parse("1 - Hola - Hello", Locale.ENGLISH);
		assertEquals("Vin", personalOficina.getNombre());
	}

	@Test
	void shouldThrowParseException() throws ParseException {
		Assertions.assertThrows(ParseException.class, () -> {
			personalOficinaFormatter.parse("2 - enero esto sale mal", Locale.ENGLISH);
		});
	}
	
	private PersonalOficina makePersonalOficinaRandom() {
		PersonalOficina pOficina = new PersonalOficina();
		pOficina.setId(1);
		pOficina.setNombre("Vin");
		pOficina.setApellidos("Diesel");
		
		return pOficina;
	}

}