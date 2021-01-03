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
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

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
		IdiomaType idiomaType2 = new IdiomaType();
		idiomaType2.setIdioma("EN");
		
		Set<IdiomaType> idiomaTypes = new HashSet<IdiomaType>();
		idiomaTypes.add(idiomaType);
		idiomaTypes.add(idiomaType2);
		
		
		String idiomaTypeName = idiomaTypeFormatter.print(idiomaTypes, Locale.ENGLISH);
		assertEquals("[ES, EN]", idiomaTypeName);
	}

	@Test
	void shouldParse() throws ParseException {
		Mockito.when(azafatoService.findIdiomaTypes()).thenReturn(makeIdiomaTypes());
		Set<IdiomaType> idiomaType = idiomaTypeFormatter.parse("EN", Locale.ENGLISH);
		assertEquals("[EN]", idiomaType.toString());
	}

	@Test
	void shouldThrowParseException() throws ParseException {
		Mockito.when(azafatoService.findIdiomaTypes()).thenReturn(makeIdiomaTypes());
		Assertions.assertThrows(ParseException.class, () -> {
			idiomaTypeFormatter.parse("[ES]", Locale.ENGLISH);
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
