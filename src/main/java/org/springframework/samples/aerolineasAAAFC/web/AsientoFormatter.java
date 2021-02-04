package org.springframework.samples.aerolineasAAAFC.web;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.samples.aerolineasAAAFC.model.Asiento;
import org.springframework.samples.aerolineasAAAFC.model.menu.Plato;
import org.springframework.samples.aerolineasAAAFC.model.menu.PlatoBase;
import org.springframework.samples.aerolineasAAAFC.service.AsientoService;
import org.springframework.samples.aerolineasAAAFC.service.PlatoBaseService;
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
		return null;
	}

} 