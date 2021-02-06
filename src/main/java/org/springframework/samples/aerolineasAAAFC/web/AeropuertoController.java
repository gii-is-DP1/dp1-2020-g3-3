package org.springframework.samples.aerolineasAAAFC.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.samples.aerolineasAAAFC.model.Aeropuerto;
import org.springframework.samples.aerolineasAAAFC.model.Cliente;
import org.springframework.samples.aerolineasAAAFC.service.AeropuertoService;
import org.springframework.samples.aerolineasAAAFC.service.exceptions.TelefonoErroneoException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

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


	//INSERCIÓN
	@GetMapping(value = "/aeropuertos/new")
	public String initCreationAeropuertoForm(Map<String, Object> model) {
		Aeropuerto aeropuerto = new Aeropuerto();
		model.put("aeropuerto", aeropuerto);
		return VIEWS_AEROPUERTO_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/aeropuertos/new")
	public String processCreationVueloForm(@Valid Aeropuerto aeropuerto, BindingResult result) {
		if (result.hasErrors()) {
			return VIEWS_AEROPUERTO_CREATE_OR_UPDATE_FORM;
		} else {
			try {
				this.aeropuertoService.saveAeropuerto(aeropuerto);
			} catch (TelefonoErroneoException e) {
				result.rejectValue("telefono", "invalid", "invalid phone number");
				return VIEWS_AEROPUERTO_CREATE_OR_UPDATE_FORM;
			}

			return "redirect:/aeropuertos";

		}
	}

	// MODIFICACION
	@GetMapping(value = "/aeropuertos/{aeropuertoId}/edit")
	public String initUpdateAeropuertoForm(@PathVariable("aeropuertoId") int aeropuertoId, ModelMap model) {
		Aeropuerto aeropuerto = this.aeropuertoService.findAeropuertoById(aeropuertoId);
		model.addAttribute(aeropuerto);
		return VIEWS_AEROPUERTO_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/aeropuertos/{aeropuertoId}/edit")
	public String processUpdateAeropuertoForm(@Valid Aeropuerto aeropuerto, BindingResult result,
			@PathVariable("aeropuertoId") int aeropuertoId, ModelMap model,
			@RequestParam(value = "version", required = false) Integer version) {
//		Aeropuerto aeropuertoToUpdate = this.aeropuertoService.findAeropuertoById(aeropuertoId);

		if (result.hasErrors()) {
			return VIEWS_AEROPUERTO_CREATE_OR_UPDATE_FORM;
		} else {
//			if (aeropuertoToUpdate.getVersion() != version) {
//				model.put("message", "Concurrent modification of airport! Try again!");
//				return initUpdateAeropuertoForm(aeropuertoId, model);
//			}
//			aeropuerto.incrementVersion();
			aeropuerto.setId(aeropuertoId);
			try {
				this.aeropuertoService.saveAeropuerto(aeropuerto);
			} catch (TelefonoErroneoException e) {
				result.rejectValue("telefono", "invalid", "invalid phone number");
				return VIEWS_AEROPUERTO_CREATE_OR_UPDATE_FORM;
			}

			return "redirect:/aeropuertos";
		}
	}

	
	//ELIMINACIÓN
	@GetMapping(value = "/aeropuertos/{aeropuertoId}/delete")
	public String deleteAeropuerto(@PathVariable("aeropuertoId") int aeropuertoId) {
		this.aeropuertoService.eliminarAeropuerto(aeropuertoId);
		return "redirect:/aeropuertos";
	}

	
	//CONSULTA
	@GetMapping(value = { "/aeropuertos" })
	public String showAeropuertosList(ModelMap model, @PageableDefault(value=20) Pageable paging) {
		Page<Aeropuerto> pages = aeropuertoService.findAeropuertos(paging);
		model.put("number", pages.getNumber());
		model.put("totalPages", pages.getTotalPages());
		model.put("totalElements", pages.getTotalElements());
		model.put("size", pages.getSize());
		model.put("aeropuertos",pages.getContent());
		return "aeropuertos/aeropuertosList";
	}
	
	@GetMapping("/aeropuertos/{aeropuertoId}")
	public ModelAndView showAeropuerto(@PathVariable("aeropuertoId") int id) {
		ModelAndView mav = new ModelAndView("aeropuertos/aeropuertoDetails");
		mav.addObject(this.aeropuertoService.findAeropuertoById(id));
		return mav;
	}

}
