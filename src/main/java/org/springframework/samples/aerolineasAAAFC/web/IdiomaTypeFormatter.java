package org.springframework.samples.aerolineasAAAFC.web;

import java.text.ParseException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.samples.aerolineasAAAFC.model.IdiomaType;
import org.springframework.samples.aerolineasAAAFC.service.AzafatoService;
import org.springframework.stereotype.Component;

@Component
public class IdiomaTypeFormatter implements Formatter<Set<IdiomaType>> {

	private final AzafatoService azafatoService;

	@Autowired
	public IdiomaTypeFormatter(AzafatoService azafatoService) {
		this.azafatoService = azafatoService;
	}

	@Override
	public String print(Set<IdiomaType> idiomaTypes, Locale locale) {
		return idiomaTypes.toString();
	}

	@Override
	public Set<IdiomaType> parse(String text, Locale locale) throws ParseException {
		Collection<IdiomaType> findIdiomaTypes = this.azafatoService.findIdiomaTypes();
		Set<IdiomaType> idiomaTypes = new HashSet<IdiomaType>();

		for (IdiomaType type : findIdiomaTypes) {
			for (String lang : text.substring(1, text.length() - 1).split(",")) {
				if (type.getIdioma().equals(text)) {
					idiomaTypes.add(type);
				}
			}
		}

		if (idiomaTypes.size() != 0) {
			return idiomaTypes;
		}

		throw new ParseException("type not found: " + text, 0);
	}

}