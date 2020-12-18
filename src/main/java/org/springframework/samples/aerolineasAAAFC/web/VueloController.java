package org.springframework.samples.aerolineasAAAFC.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.aerolineasAAAFC.model.Aeropuerto;
import org.springframework.samples.aerolineasAAAFC.model.Vuelo;
import org.springframework.samples.aerolineasAAAFC.service.AeropuertoService;
import org.springframework.samples.aerolineasAAAFC.service.VueloService;
import org.springframework.samples.aerolineasAAAFC.service.exceptions.HorasImposiblesException;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author Antonio Javier Sanchez Soria
 */

@Controller
public class VueloController {
	private static final String VIEWS_VUELO_CREATE_OR_UPDATE_FORM = "vuelos/createOrUpdateVueloForm";
	
	private final VueloService vueloService;
	private final AeropuertoService aeropuertoService;
	
	
	@Autowired
	public VueloController(VueloService vueloService,AeropuertoService aeropuertoService) {
		this.vueloService = vueloService;
		this.aeropuertoService=aeropuertoService;
	}
	
	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	
	@ModelAttribute("vuelo")
	public Vuelo loadVuelo( @PathVariable("vueloId") int vueloId) {
		Vuelo vuelo=vueloService.findVueloById(vueloId);
//		vuelo.setId(vueloId);
		return vuelo;
		
	}
	
	
	@GetMapping(value = { "/vuelos" })
	public String showVuelosList(@PathVariable("vueloId") int vueloId,Map<String, Object> model) {
		List<Vuelo> vuelos = new ArrayList<>();
		this.vueloService.findVuelos().forEach(x->vuelos.add(x));
		model.put("vuelos", vuelos);
		return "vuelos/vuelosList";
	}
	
	
	@GetMapping(value = "/vuelos/new") 
	public String initCreationVueloForm(@PathVariable("vueloId") int vueloId,Map<String, Object> model) {
//		Vuelo vuelo = new Vuelo();
//		model.put("vuelo", vuelo);
		List<Aeropuerto> aeropuertos = new ArrayList<>();
		this.aeropuertoService.findAeropuertos().forEach(x->aeropuertos.add(x));
		model.put("aeropuertos", aeropuertos);
		return VIEWS_VUELO_CREATE_OR_UPDATE_FORM;
	}
	
	@PostMapping(value = "/vuelos/new")
	public String processCreationVueloForm(@Valid Vuelo vuelo, BindingResult result) {
		if(result.hasErrors()) {
			return VIEWS_VUELO_CREATE_OR_UPDATE_FORM;
		}
		else {
			try {
				this.vueloService.saveVuelo(vuelo);
			} catch (HorasImposiblesException e) {
				result.rejectValue("horaLlegada", "invalid", "La hora de llegada debe ser posterior a la de salida");
				return VIEWS_VUELO_CREATE_OR_UPDATE_FORM;
			} 
			catch (ConstraintViolationException e) {
				result.rejectValue("aeropuerto", "invalid", "El aeropuerto de salida y destino deben ser distintos");
				return VIEWS_VUELO_CREATE_OR_UPDATE_FORM;
				
			}
			return "redirect:/vuelos";
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
	public String processUpdateVueloForm(@Valid @ModelAttribute("vuelo") Vuelo vuelo, BindingResult result, @PathVariable("vueloId") int vueloId) {
		if(result.hasErrors()) {
			return VIEWS_VUELO_CREATE_OR_UPDATE_FORM;
		}
		else {
			vuelo.setId(vueloId);
			try {
				this.vueloService.saveVuelo(vuelo);
			} catch (HorasImposiblesException e) {
				result.rejectValue("horaLlegada", "invalid", "La hora de llegada debe ser posterior a la de salida");
				return VIEWS_VUELO_CREATE_OR_UPDATE_FORM;
			}
			
			return "redirect:/vuelos";
		}
	}
	
	
	
	
	
	
	
	
}
