package org.springframework.samples.aerolineasAAAFC.web;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.aerolineasAAAFC.model.Vuelo;
import org.springframework.samples.aerolineasAAAFC.service.VueloService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author Antonio Javier Sanchez Soria
 */

@Controller
public class VueloController {
	private static final String VIEWS_VUELO_CREATE_OR_UPDATE_FORM = "vuelos/createOrUpdateVueloForm";
	
	private final VueloService vueloService;
	
	@Autowired
	public VueloController(VueloService vueloService) {
		this.vueloService = vueloService;
	}
	
	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	
	@GetMapping(value = "/vuelos/new") 
	public String initCreationVueloForm(Map<String, Object> model) {
		Vuelo vuelo = new Vuelo();
		model.put("vuelo", vuelo);
		return VIEWS_VUELO_CREATE_OR_UPDATE_FORM;
	}
	
	@PostMapping(value = "/vuelos/new")
	public String processCreationVueloForm(@Valid Vuelo vuelo, BindingResult result) {
		if(result.hasErrors()) {
			return VIEWS_VUELO_CREATE_OR_UPDATE_FORM;
		}
		else {
			this.vueloService.saveVuelo(vuelo);
			
			// Ahora mismo no debería devolver a ningún sitio,
			// pero dejamos esto como placeholder para cuando
			// hagamos el listado o consulta de aviones
			return "redirect:/vuelos/" + vuelo.getId();
		}
	}
	
	//MODIFICACION
	
	@GetMapping(value = "/vuelos/{vueloId}/edit")
	public String initUpdateVueloForm(@PathVariable("vueloId") int vueloId, Model model) {
		Vuelo vuelo = this.vueloService.findVueloById(vueloId);
		model.addAttribute(vuelo);
		return VIEWS_VUELO_CREATE_OR_UPDATE_FORM;
	}
	
	@PostMapping(value = "/vuelos/{vueloId}/edit")
	public String processUpdateVueloForm(@Valid Vuelo vuelo, BindingResult result, @PathVariable("vueloId") int vueloId) {
		if(result.hasErrors()) {
			return VIEWS_VUELO_CREATE_OR_UPDATE_FORM;
		}
		else {
			vuelo.setId(vueloId);
			this.vueloService.saveVuelo(vuelo);
			
			// Igual que en el alta de aviones
			return "redirect:/vuelos/{vueloId}";
		}
	}
	
	
	
	
	
	
	
	
}
