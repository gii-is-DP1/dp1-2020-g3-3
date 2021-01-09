package org.springframework.samples.aerolineasAAAFC.web;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.samples.aerolineasAAAFC.model.equipaje.EquipajeBase;
import org.springframework.samples.aerolineasAAAFC.service.EquipajeBaseService;
import org.springframework.stereotype.Component;

@Component
public class EquipajeBaseFormatter implements Formatter<EquipajeBase> {
	
	private final EquipajeBaseService equipajeBaseService;

	@Autowired
	public EquipajeBaseFormatter(EquipajeBaseService equipajeBaseService) {
		this.equipajeBaseService = equipajeBaseService;
	}

	@Override
	public String print(EquipajeBase equipajeBase, Locale locale) {
		return equipajeBase.getName();
	}

	@Override
	public EquipajeBase parse(String text, Locale locale) throws ParseException {
		Collection<EquipajeBase> equipajesBase = this.equipajeBaseService.findEquipajesBase();
		for (EquipajeBase eb : equipajesBase) {
			if (eb.getName().equals(text)) {
				return eb;
			}
		}
		throw new ParseException("type not found: " + text, 0);
	}

} 