package org.springframework.samples.aerolineasAAAFC.web;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.samples.aerolineasAAAFC.model.Azafato;
import org.springframework.samples.aerolineasAAAFC.service.AzafatoService;
import org.springframework.stereotype.Component;

@Component
public class AzafatoFormatter implements Formatter<Azafato> {

	private final AzafatoService azafatoService;

	@Autowired
	public AzafatoFormatter(AzafatoService azafatoService) {
		this.azafatoService = azafatoService;
	}

	@Override
	public String print(Azafato azafato, Locale locale) {
		return azafato.getId() + " - " + azafato.getNombre() + " " + azafato.getApellidos();
	}

	@Override
	public Azafato parse(String text, Locale locale) throws ParseException {
		String[] aux = text.split("-");
		int id = Integer.valueOf(aux[0].trim());
		Azafato a = this.azafatoService.findAzafatoById(id);

			if (a != null && a.getId().equals(id)) {
				return a;
			}

		throw new ParseException("type not found: " + text, 0);
	}

} 