package org.springframework.samples.aerolineasAAAFC.web;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.aerolineasAAAFC.model.Aeropuerto;
import org.springframework.samples.aerolineasAAAFC.model.Vuelo;
import org.springframework.samples.aerolineasAAAFC.service.AeropuertoService;
import org.springframework.samples.aerolineasAAAFC.service.VueloService;
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
	private static final String VIEWS_AEROPUERTO_CREATE_OR_UPDATE_FORM = "aeropuerto/createOrUpdateAeropuertoForm";

	private final AeropuertoService aeropuertoService;
	
	@Autowired
	public AeropuertoController(AeropuertoService aeropuertoService) {
		this.aeropuertoService = aeropuertoService;
	}
	
	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	
	@GetMapping(value = "/aeropuerto/new") 
	public String initCreationAeropuertoForm(Map<String, Object> model) {
		Aeropuerto aeropuerto = new Aeropuerto();
		model.put("aeropuerto", aeropuerto);
		return VIEWS_AEROPUERTO_CREATE_OR_UPDATE_FORM;
	}
	
	@PostMapping(value = "/aeropuerto/new")
	public String processCreationVueloForm(@Valid Aeropuerto aeropuerto, BindingResult result) {
		if(result.hasErrors()) {
			return VIEWS_AEROPUERTO_CREATE_OR_UPDATE_FORM;
		}
		else {
			this.aeropuertoService.saveAeropuerto(aeropuerto);
			
			// Ahora mismo no debería devolver a ningún sitio,
			// pero dejamos esto como placeholder para cuando
			// hagamos el listado o consulta de aviones
			return "redirect:/aeropuertos/" + aeropuerto.getId();
		}
	}
	
	//MODIFICACION
	
	@GetMapping(value = "/aeropuerto/{aeropuertoId}/edit")
	public String initUpdateAeropuertoForm(@PathVariable("aeropuertoId") int aeropuertoId, Model model) {
		Aeropuerto aeropuerto = this.aeropuertoService.findAeropuertoById(aeropuertoId);
		model.addAttribute(aeropuerto);
		return VIEWS_AEROPUERTO_CREATE_OR_UPDATE_FORM;
	}
	
	@PostMapping(value = "/aeropuertos/{aeropuertoId}/edit")
	public String processUpdateVueloForm(@Valid Aeropuerto aeropuerto, BindingResult result, @PathVariable("vueloId") int aeropuertoId) {
		if(result.hasErrors()) {
			return VIEWS_AEROPUERTO_CREATE_OR_UPDATE_FORM;
		}
		else {
			aeropuerto.setId(aeropuertoId);
			this.aeropuertoService.saveAeropuerto(aeropuerto);
			
			// Igual que en el alta de aviones
			return "redirect:/aeropuertos/{aeropuertoId}";
		}
	}
	
	
	

}
