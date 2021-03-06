package org.springframework.samples.aerolineasAAAFC.web;

import java.util.Map;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.samples.aerolineasAAAFC.model.Avion;
import org.springframework.samples.aerolineasAAAFC.service.AvionService;
import org.springframework.samples.aerolineasAAAFC.service.VueloService;
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

@Controller
public class AvionController {

	private static final String VIEWS_AVION_CREATE_OR_UPDATE_FORM = "aviones/createOrUpdateAvionForm";

	private final AvionService avionService;

	private final VueloService vueloService;

	@Autowired
	public AvionController(AvionService avionService, VueloService vueloService) {
		this.avionService = avionService;
		this.vueloService = vueloService;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	/*
	 * ALTA DE UN NUEVO AVIÓN
	 */
	@GetMapping(value = "/aviones/new")
	public String initCreationAvionForm(Map<String, Object> model) {
		Avion avion = new Avion();
		model.put("avion", avion);
		return VIEWS_AVION_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/aviones/new")
	public String processCreationAvionForm(@Valid Avion avion, BindingResult result) {
		if (result.hasErrors()) {
			return VIEWS_AVION_CREATE_OR_UPDATE_FORM;
		} else {
			this.avionService.saveAvion(avion);
			return "redirect:/aviones/" + avion.getId();
		}
	}

	/*
	 * MODIFICACIÓN DE UN AVIÓN
	 */
	@GetMapping(value = "/aviones/{avionId}/edit")
	public String initUpdateAvionForm(@PathVariable("avionId") int avionId, ModelMap model) {
		Avion avion = this.avionService.findAvionById(avionId);
		model.addAttribute(avion);

		return VIEWS_AVION_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/aviones/{avionId}/edit")
	public String processUpdateAvionForm(@Valid Avion avion, BindingResult result, @PathVariable("avionId") int avionId,
			ModelMap model, @RequestParam(value = "version", required = false) Integer version) {
		Avion avionBD = this.avionService.findAvionById(avionId);

		if (result.hasErrors()) {
			return VIEWS_AVION_CREATE_OR_UPDATE_FORM;
		} else {
			if (avionBD.getVersion() != version) {
				model.put("message", "Modificación de avión ya existente. ¡Prueba de nuevo!");
				return initUpdateAvionForm(avionId, model);
			}
			avion.setId(avionId);
			this.avionService.saveAvion(avion);

			return "redirect:/aviones/{avionId}";
		}
	}
	
	//ELIMINACIÓN
	@GetMapping(value = "/aviones/{avionId}/delete")
	public String deleteCliente(@PathVariable("avionId") int avionId) {
		this.avionService.eliminarAvion(avionId);
		return "redirect:/aviones";
	}
	
	//CONSULTA
	@GetMapping(value = "/aviones")
	public String showAvionesList(ModelMap model, @PageableDefault(value=20) Pageable paging) {
		Page<Avion> pages = avionService.findAviones(paging);
		model.addAttribute("number", pages.getNumber());
		model.addAttribute("totalPages", pages.getTotalPages());
		model.addAttribute("totalElements", pages.getTotalElements());
		model.addAttribute("size", pages.getSize());
		model.addAttribute("aviones",pages.getContent());
		return "aviones/avionesList";
	}

	@GetMapping("/aviones/{avionId}")
	public ModelAndView showAvion(@PathVariable("avionId") int avionId) {
		ModelAndView mav = new ModelAndView("aviones/avionDetails");
		mav.addObject(this.avionService.findAvionById(avionId));
		return mav;
	}
	
	@GetMapping(value = "/aviones/estadoAviones")
	public String showAvionesListPersonal(Map<String, Object> model, @PageableDefault(value=20) Pageable paging) {
		
		Page<Avion> pages = avionService.findAviones(paging);
		model.put("number", pages.getNumber());
		model.put("totalPages", pages.getTotalPages());
		model.put("totalElements", pages.getTotalElements());
		model.put("size", pages.getSize());
		model.put("aviones",pages.getContent());

		return "aviones/estadoAviones";
	}
	
	

}
