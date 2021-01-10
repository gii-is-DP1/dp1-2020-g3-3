package org.springframework.samples.aerolineasAAAFC.web;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.samples.aerolineasAAAFC.model.menu.Plato;
import org.springframework.samples.aerolineasAAAFC.model.menu.PlatoBase;
import org.springframework.samples.aerolineasAAAFC.service.PlatoBaseService;
import org.springframework.stereotype.Component;

@Component
public class PlatoFormatter implements Formatter<Plato> {
	
	private final PlatoBaseService platoBaseService;

	@Autowired
	public PlatoFormatter(PlatoBaseService platoBaseService) {
		this.platoBaseService = platoBaseService;
	}

	@Override
	public String print(Plato plato, Locale locale) {
		return plato.getPlatoBase().getName();
	}

	@Override
	public Plato parse(String text, Locale locale) throws ParseException {
		Collection<PlatoBase> platosBase = this.platoBaseService.findPlatosBase();
		for (PlatoBase pb : platosBase) {
			if (pb.getName().equals(text)) {
				Plato p = new Plato();
				p.setPlatoBase(pb);
				return p;
			}
		}
		throw new ParseException("type not found: " + text, 0);
	}

} 