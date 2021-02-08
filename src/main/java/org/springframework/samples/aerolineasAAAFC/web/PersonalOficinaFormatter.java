package org.springframework.samples.aerolineasAAAFC.web;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.samples.aerolineasAAAFC.model.Azafato;
import org.springframework.samples.aerolineasAAAFC.model.PersonalControl;
import org.springframework.samples.aerolineasAAAFC.model.PersonalOficina;
import org.springframework.samples.aerolineasAAAFC.service.AzafatoService;
import org.springframework.samples.aerolineasAAAFC.service.PersonalControlService;
import org.springframework.samples.aerolineasAAAFC.service.PersonalOficinaService;
import org.springframework.stereotype.Component;

@Component
public class PersonalOficinaFormatter implements Formatter<PersonalOficina> {

	private final PersonalOficinaService personalOficinaService;

	@Autowired
	public PersonalOficinaFormatter(PersonalOficinaService personalOficinaService) {
		this.personalOficinaService = personalOficinaService;
	}

	@Override
	public String print(PersonalOficina personalOficina, Locale locale) {
		return personalOficina.getNombre() + personalOficina.getApellidos();
	}

	@Override
	public PersonalOficina parse(String text, Locale locale) throws ParseException {
		int id = Integer.valueOf(text.toString());
		PersonalOficina pOficina = this.personalOficinaService.findPersonalOficinaById(Integer.valueOf(text.trim()));

			if (pOficina != null && pOficina.getId().equals(id)) {
				return pOficina;
			}

		throw new ParseException("type not found: " + text, 0);
	}

} 