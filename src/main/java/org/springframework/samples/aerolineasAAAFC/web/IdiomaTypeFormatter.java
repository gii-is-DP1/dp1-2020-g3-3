package org.springframework.samples.aerolineasAAAFC.web;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.samples.aerolineasAAAFC.model.IdiomaType;
import org.springframework.samples.aerolineasAAAFC.service.AzafatoService;
import org.springframework.stereotype.Component;

@Component
public class IdiomaTypeFormatter implements Formatter<IdiomaType> {

	private final AzafatoService azafatoService;

	@Autowired
	public IdiomaTypeFormatter(AzafatoService azafatoService) {
		this.azafatoService = azafatoService;
	}

	@Override
	public String print(IdiomaType idiomaType, Locale locale) {
		return idiomaType.getIdioma();
	}

	@Override
	public IdiomaType parse(String text, Locale locale) throws ParseException {
		Collection<IdiomaType> findIdiomaTypes = this.azafatoService.findIdiomaTypes();
		for (IdiomaType type : findIdiomaTypes) {
			if (type.getIdioma().equals(text)) {
				return type;
			}
		}
		throw new ParseException("type not found: " + text, 0);
	}

} 