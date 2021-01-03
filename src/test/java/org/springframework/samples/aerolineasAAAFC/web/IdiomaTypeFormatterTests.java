package org.springframework.samples.aerolineasAAAFC.web;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.aerolineasAAAFC.model.IdiomaType;
import org.springframework.samples.aerolineasAAAFC.service.AzafatoService;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class IdiomaTypeFormatterTests {

	@Mock
	private AzafatoService azafatoService;

	private IdiomaTypeFormatter idiomaTypeFormatter;

	@BeforeEach
	void setup() {
		idiomaTypeFormatter = new IdiomaTypeFormatter(azafatoService);
	}

	@Test
	void testPrint() {
		IdiomaType idiomaType = new IdiomaType();
		idiomaType.setIdioma("ES");
		String idiomaTypeName = idiomaTypeFormatter.print(idiomaType, Locale.ENGLISH);
		assertEquals("ES", idiomaTypeName);
	}

	@Test
	void shouldParse() throws ParseException {
		Mockito.when(azafatoService.findIdiomaTypes()).thenReturn(makeIdiomaTypes());
		IdiomaType idiomaType = idiomaTypeFormatter.parse("EN", Locale.ENGLISH);
		assertEquals("EN", idiomaType.getIdioma());
	}

	@Test
	void shouldThrowParseException() throws ParseException {
		Mockito.when(azafatoService.findIdiomaTypes()).thenReturn(makeIdiomaTypes());
		Assertions.assertThrows(ParseException.class, () -> {
			idiomaTypeFormatter.parse("ES", Locale.ENGLISH);
		});
	}

	private Collection<IdiomaType> makeIdiomaTypes() {
		Collection<IdiomaType> idiomaTypes = new ArrayList<>();
		idiomaTypes.add(new IdiomaType() {
			{
				setIdioma("EN");
			}
		});
		idiomaTypes.add(new IdiomaType() {
			{
				setIdioma("FR");
			}
		});
		return idiomaTypes;
	}

}