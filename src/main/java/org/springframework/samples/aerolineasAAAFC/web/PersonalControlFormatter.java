package org.springframework.samples.aerolineasAAAFC.web;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.samples.aerolineasAAAFC.model.Azafato;
import org.springframework.samples.aerolineasAAAFC.model.PersonalControl;
import org.springframework.samples.aerolineasAAAFC.service.AzafatoService;
import org.springframework.samples.aerolineasAAAFC.service.PersonalControlService;
import org.springframework.stereotype.Component;

@Component
public class PersonalControlFormatter implements Formatter<PersonalControl> {

	private final PersonalControlService personalControlService;

	@Autowired
	public PersonalControlFormatter(PersonalControlService personalControlService) {
		this.personalControlService = personalControlService;
	}

	@Override
	public String print(PersonalControl personalControl, Locale locale) {
		return personalControl.getNombre();
	}

	@Override
	public PersonalControl parse(String text, Locale locale) throws ParseException {
		int id = Integer.valueOf(text.toString());
		PersonalControl pControl = this.personalControlService.findPersonalControlById(Integer.valueOf(text.trim()));

			if (pControl != null && pControl.getId().equals(id)) {
				return pControl;
			}

		throw new ParseException("type not found: " + text, 0);
	}

} 