package org.springframework.samples.aerolineasAAAFC.web;

import java.text.ParseException;
import java.util.List;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.samples.aerolineasAAAFC.model.Asiento;
import org.springframework.samples.aerolineasAAAFC.model.Vuelo;
import org.springframework.samples.aerolineasAAAFC.service.AsientoService;
import org.springframework.samples.aerolineasAAAFC.service.VueloService;
import org.springframework.stereotype.Component;

@Component
public class AsientoFormatter implements Formatter<Asiento> {
	
	private final AsientoService asientoService;
	
	private final VueloService vueloService;

	@Autowired
	public AsientoFormatter(AsientoService asientoService, VueloService vueloService) {
		this.asientoService = asientoService;
		this.vueloService = vueloService;
	}

	@Override
	public String print(Asiento asiento, Locale locale) {
		return asiento.getNombre();
	}

	@Override
	public Asiento parse(String text, Locale locale) throws ParseException {
		String[] cadena = text.split(",");
		Vuelo v = this.vueloService.findVueloById(Integer.valueOf(cadena[0].trim()));
		
		if(v == null) {
			throw new ParseException("type not found: " + text, 0);
		}
		
		List<Asiento> asientos = this.asientoService.findAsientosSinOcupar(v);
		
		for(Asiento a : asientos) {
			if (a.getNombre().equals(cadena[1].trim()) && a.isLibre() == true) {
				return a;
			}			
		}
		throw new ParseException("type not found: " + text, 0);
	}

} 