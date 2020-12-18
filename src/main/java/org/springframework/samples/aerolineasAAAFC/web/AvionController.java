package org.springframework.samples.aerolineasAAAFC.web;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.aerolineasAAAFC.model.Avion;
import org.springframework.samples.aerolineasAAAFC.service.AvionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AvionController {
	
	private static final String VIEWS_AVION_CREATE_OR_UPDATE_FORM = "aviones/createOrUpdateAvionForm";
	
	private final AvionService avionService;
	
	@Autowired
	public AvionController(AvionService avionService) {
		this.avionService = avionService;
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
		if(result.hasErrors()) {
			return VIEWS_AVION_CREATE_OR_UPDATE_FORM;
		}
		else {
			this.avionService.saveAvion(avion);

			return "redirect:/aviones/" + avion.getId();
		}
	}
	
	/*
	 * MODIFICACIÓN DE UN AVIÓN
	 */
	@GetMapping(value = "/aviones/{avionId}/edit")
	public String initUpdateAvionForm(@PathVariable("avionId") int avionId, Model model) {
		Avion avion = this.avionService.findAvionById(avionId);
		model.addAttribute(avion);
		
		return VIEWS_AVION_CREATE_OR_UPDATE_FORM;
	}
	
	@PostMapping(value = "/aviones/{avionId}/edit")
	public String processUpdateAvionForm(@Valid Avion avion, BindingResult result, 
			@PathVariable("avionId") int avionId) {
		if(result.hasErrors()) {
			return VIEWS_AVION_CREATE_OR_UPDATE_FORM;
		}
		else {
			avion.setId(avionId);
			this.avionService.saveAvion(avion);
			
			return "redirect:aviones/"+avionId;
		}
	}
	
	
	@GetMapping("/aviones/{avionId}")
	public ModelAndView showAvion(@PathVariable("avionId") int avionId) {
		ModelAndView mav = new ModelAndView("aviones/avionDetails");
		mav.addObject(this.avionService.findAvionById(avionId));
		return mav;
	}

}
