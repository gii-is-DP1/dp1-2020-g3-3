package org.springframework.samples.aerolineasAAAFC.web;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.aerolineasAAAFC.model.Aeropuerto;
import org.springframework.samples.aerolineasAAAFC.service.AeropuertoService;
import org.springframework.samples.aerolineasAAAFC.service.exceptions.TelefonoErroneoException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AeropuertoController {
	private static final String VIEWS_AEROPUERTO_CREATE_OR_UPDATE_FORM = "aeropuertos/createOrUpdateAeropuertoForm";

	private final AeropuertoService aeropuertoService;
	
	@Autowired
	public AeropuertoController(AeropuertoService aeropuertoService) {
		this.aeropuertoService = aeropuertoService;
	}
	
	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	
	@GetMapping(value = "/aeropuertos/new") 
	public String initCreationAeropuertoForm(Map<String, Object> model) {
		Aeropuerto aeropuerto = new Aeropuerto();
		model.put("aeropuerto", aeropuerto);
		return VIEWS_AEROPUERTO_CREATE_OR_UPDATE_FORM;
	}
	
	@PostMapping(value = "/aeropuertos/new")
	public String processCreationVueloForm(@Valid Aeropuerto aeropuerto, BindingResult result) {
		if(result.hasErrors()) {
			return VIEWS_AEROPUERTO_CREATE_OR_UPDATE_FORM;
		}
		else {
			try {
				this.aeropuertoService.saveAeropuerto(aeropuerto);
			} catch (TelefonoErroneoException e) {
				 result.rejectValue("telefono", "invalid", "invalid phone number");
				 return VIEWS_AEROPUERTO_CREATE_OR_UPDATE_FORM;
			}
			
			return "redirect:/aeropuertos/" + aeropuerto.getId();
		}
	}
	
	//MODIFICACION
	
	@GetMapping(value = "/aeropuertos/{aeropuertoId}/edit")
	public String initUpdateAeropuertoForm(@PathVariable("aeropuertoId") int aeropuertoId, Model model) {
		Aeropuerto aeropuerto = this.aeropuertoService.findAeropuertoById(aeropuertoId);
		model.addAttribute(aeropuerto);
		return VIEWS_AEROPUERTO_CREATE_OR_UPDATE_FORM;
	}
	
	@PostMapping(value = "/aeropuertos/{aeropuertoId}/edit")
	public String processUpdateAeropuertoForm(@Valid Aeropuerto aeropuerto, BindingResult result, @PathVariable("aeropuertoId") int aeropuertoId) {
		if(result.hasErrors()) {
			return VIEWS_AEROPUERTO_CREATE_OR_UPDATE_FORM;
		}
		else {
			aeropuerto.setId(aeropuertoId);
			try {
				this.aeropuertoService.saveAeropuerto(aeropuerto);
			} catch (TelefonoErroneoException e) {
				 result.rejectValue("telefono", "invalid", "invalid phone number");
				 return VIEWS_AEROPUERTO_CREATE_OR_UPDATE_FORM;
			}
			
		
			return "redirect:/aeropuertos/{aeropuertoId}";
		}
	}

}
