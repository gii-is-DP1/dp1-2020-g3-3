package org.springframework.samples.aerolineasAAAFC.web;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.aerolineasAAAFC.model.PersonalControl;
import org.springframework.samples.aerolineasAAAFC.model.Rol;
import org.springframework.samples.aerolineasAAAFC.service.PersonalControlService;

import java.text.ParseException;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class PersonalControlFormatterTests {

	@Mock
	private PersonalControlService personalControlService;

	private PersonalControlFormatter personalControlFormatter;

	@BeforeEach
	void setup() {
		personalControlFormatter = new PersonalControlFormatter(personalControlService);
	}

	@Test
	void testPrint() {
		Mockito.when(personalControlService.findPersonalControlById(1)).thenReturn(makePersonalControlRandom());
		PersonalControl pControl = this.personalControlService.findPersonalControlById(1);
		String personalControlPrint = personalControlFormatter.print(pControl, Locale.ENGLISH);
		assertEquals(pControl.getId() + " - " + pControl.getNombre() + " " + pControl.getApellidos() + ", " + pControl.getRol()
		, personalControlPrint);
	}

	@Test
	void shouldParse() throws ParseException {
		Mockito.when(personalControlService.findPersonalControlById(1)).thenReturn(makePersonalControlRandom());
		PersonalControl pControl = personalControlFormatter.parse("1 - Hola - Hello", Locale.ENGLISH);
		assertEquals("Vin", pControl.getNombre());
	}

	@Test
	void shouldThrowParseException() throws ParseException {
		Assertions.assertThrows(ParseException.class, () -> {
			personalControlFormatter.parse("2 - enero esto sale mal, si", Locale.ENGLISH);
		});
	}
	
	private PersonalControl makePersonalControlRandom() {
		PersonalControl pControl = new PersonalControl();
		pControl.setId(1);
		pControl.setNombre("Vin");
		pControl.setApellidos("Diesel");
		pControl.setRol(Rol.COPILOTO);
		
		return pControl;
	}

}