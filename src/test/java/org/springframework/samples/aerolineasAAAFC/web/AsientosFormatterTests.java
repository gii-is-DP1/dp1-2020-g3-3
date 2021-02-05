package org.springframework.samples.aerolineasAAAFC.web;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.aerolineasAAAFC.model.Asiento;
import org.springframework.samples.aerolineasAAAFC.model.Clase;
import org.springframework.samples.aerolineasAAAFC.model.Vuelo;
import org.springframework.samples.aerolineasAAAFC.service.AsientoService;
import org.springframework.samples.aerolineasAAAFC.service.VueloService;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class AsientosFormatterTests {

	@Mock
	private AsientoService asientoService;
	
	@Mock
	private VueloService vueloService;

	private AsientoFormatter asientoFormatter;

	@BeforeEach
	void setup() {
		asientoFormatter = new AsientoFormatter(asientoService,vueloService);
	}

	@Test
	void testPrint() {
		Asiento a = makeAsientoRandom();
		String asiento = asientoFormatter.print(a, Locale.ENGLISH);
		assertEquals("A1", asiento);
	}
	
	@Test
	void shouldParse() throws ParseException {
		Asiento a = makeAsientoRandom();
		Mockito.when(asientoService.findAsientosSinOcupar(a.getVuelo())).thenReturn(makeRandomListAsiento(a));
		Mockito.when(vueloService.findVueloById(9999)).thenReturn(a.getVuelo());
		Asiento a2 = asientoFormatter.parse("9999,A1", Locale.ENGLISH);
		assertEquals("A1", a2.getNombre());
		assertEquals(9999, a2.getVuelo().getId());
	}
	
	@Test
	void shouldThrowParseException() throws ParseException {
		Mockito.when(vueloService.findVueloById(212)).thenReturn(null);
		Assertions.assertThrows(ParseException.class, () -> {
			asientoFormatter.parse("212,A2", Locale.ENGLISH);
		});
	}
	
	@Test
	void shouldThrowParseExceptionOcupado() throws ParseException {
		Asiento a = makeAsientoRandomOcupado();
		Mockito.when(asientoService.findAsientosSinOcupar(a.getVuelo())).thenReturn(makeRandomListAsiento(a));
		Mockito.when(vueloService.findVueloById(9999)).thenReturn(a.getVuelo());
		Assertions.assertThrows(ParseException.class, () -> {
			asientoFormatter.parse("9999,A1", Locale.ENGLISH);
		});
	}
	
	private List<Asiento> makeRandomListAsiento(Asiento a){
		List<Asiento> l = new ArrayList<Asiento>();
		l.add(a);
		
		return l;
	}

	private Asiento makeAsientoRandom() {
		Vuelo v = new Vuelo();
		v.setId(9999);
		Asiento a = new Asiento();
		a.setNombre("A1");
		a.setClase(Clase.ECONOMICA);
		a.setVuelo(v);
		a.setLibre(true);
		
		return a;
	}
	
	private Asiento makeAsientoRandomOcupado() {
		Vuelo v = new Vuelo();
		v.setId(9999);
		Asiento a = new Asiento();
		a.setNombre("A1");
		a.setClase(Clase.ECONOMICA);
		a.setVuelo(v);
		a.setLibre(false);
		
		return a;
	}

}