package org.springframework.samples.aerolineasAAAFC.web;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.samples.aerolineasAAAFC.model.Avion;
import org.springframework.samples.aerolineasAAAFC.service.AvionService;
import org.springframework.stereotype.Component;

@Component
public class AvionFormatter implements Formatter<Avion>{

	
	private final AvionService avionService;
	
	@Autowired
	public AvionFormatter(AvionService avionService) {
		this.avionService = avionService;
	}
	
	@Override
	public String print(Avion object, Locale locale) {
		String res = object.getId() + " - " + object.getTipoAvion() + "(" + object.getCapacidadPasajero() + ")";
		return res;
	}

	@Override
	public Avion parse(String text, Locale locale) throws ParseException {
		
		Avion avion = this.avionService.findAvionById(Integer.valueOf(text.trim()));
		
		if(avion == null) {
			throw new ParseException("type not found: " + text, 0);
		}
		
		return avion;
	}

}
