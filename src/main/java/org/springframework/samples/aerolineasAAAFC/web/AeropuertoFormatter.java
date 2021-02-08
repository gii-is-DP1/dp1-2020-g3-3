package org.springframework.samples.aerolineasAAAFC.web;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.samples.aerolineasAAAFC.model.Aeropuerto;
import org.springframework.samples.aerolineasAAAFC.service.AeropuertoService;
import org.springframework.stereotype.Component;

@Component
public class AeropuertoFormatter implements Formatter<Aeropuerto>{
	
	private final AeropuertoService aeropuertoService;
	
	@Autowired
	public AeropuertoFormatter(AeropuertoService aeropuertoService) {
		this.aeropuertoService = aeropuertoService;
	}

	@Override
	public String print(Aeropuerto object, Locale locale) {
		return object.getId() + " - " + object.getNombre() + ", " + object.getCodigoIATA();
	}

	@Override
	public Aeropuerto parse(String text, Locale locale) throws ParseException {
		String[] aux = text.split("-");
		Integer id = Integer.valueOf(aux[0].trim());
		Aeropuerto aeropuerto = this.aeropuertoService.findAeropuertoById(id);
		
		if(aeropuerto == null) {
			throw new ParseException("type not found: " + text, 0);
		}
		
		return aeropuerto;
	}

}
