package org.springframework.samples.aerolineasAAAFC.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.samples.aerolineasAAAFC.model.Avion;
import org.springframework.samples.aerolineasAAAFC.model.Billete;
import org.springframework.samples.aerolineasAAAFC.model.Cliente;
import org.springframework.samples.aerolineasAAAFC.model.PersonalControl;
import org.springframework.samples.aerolineasAAAFC.model.PersonalOficina;
import org.springframework.samples.aerolineasAAAFC.service.PersonalOficinaService;
import org.springframework.samples.aerolineasAAAFC.service.exceptions.IbanDuplicadoException;
import org.springframework.samples.aerolineasAAAFC.service.exceptions.NifDuplicadoException;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PersonalOficinaController {

	private static final String VIEWS_PERSONALOFICINA_CREATE_OR_UPDATE_FORM = "personalOficina/createOrUpdatePersonalOficinaForm";
	
	private final PersonalOficinaService pOficinaService;
	
	@Autowired
	public PersonalOficinaController(PersonalOficinaService pOficinaService) {
		this.pOficinaService = pOficinaService;
	}
	
	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	
	// Alta de un nuevo oficinista
	
	@GetMapping(value = "/personalOficina/new")
	public String initCreationPersonalOficinaForm(Map<String, Object> model) {
		PersonalOficina pOficina = new PersonalOficina();
		model.put("personalOficina", pOficina);
		return VIEWS_PERSONALOFICINA_CREATE_OR_UPDATE_FORM;
	}
	
	@PostMapping(value = "/personalOficina/new")
	public String processCreationPersonalOficinaForm(@Valid PersonalOficina pOficina, BindingResult result) {
		if(result.hasErrors()) {
			return VIEWS_PERSONALOFICINA_CREATE_OR_UPDATE_FORM;
		}
		else {
			try {
				this.pOficinaService.savePersonalOficina(pOficina);
			} catch (DataIntegrityViolationException e) {
				result.rejectValue("nif", "duplicate", "already exists");
				return VIEWS_PERSONALOFICINA_CREATE_OR_UPDATE_FORM;
			}
			
			return "redirect:/personalOficina/" + pOficina.getId() ;
		}
	}
	
	
	// Update sobre un oficinista
	
	@GetMapping(value = "/personalOficina/{pOficinaId}/edit")
	public String initUpdatePersonalOficinaForm(@PathVariable("pOficinaId") int pOficinaId, ModelMap model) {
		PersonalOficina personalOficina = this.pOficinaService.findPersonalOficinaById(pOficinaId);
		model.addAttribute(personalOficina);
		return VIEWS_PERSONALOFICINA_CREATE_OR_UPDATE_FORM;
	}
	
	
	@PostMapping(value = "/personalOficina/{pOficinaId}/edit")
	public String processUpdatePersonalOficinaForm(@Valid PersonalOficina personalOficina, BindingResult result, @PathVariable("pOficinaId") int pOficinaId,
			ModelMap model, @RequestParam(value = "version", required=false) Integer version) {
		
//		PersonalOficina personalOficinaToUpdate=this.pOficinaService.findPersonalOficinaById(pOficinaId);

//		if(personalOficinaToUpdate.getVersion()!=version) {
//			model.put("message","Concurrent modification of Personal Oficina! Try again!");
//			return initUpdatePersonalOficinaForm(pOficinaId,model);
//			}
		if(result.hasErrors()) {
			return VIEWS_PERSONALOFICINA_CREATE_OR_UPDATE_FORM;
		}
		else {
			personalOficina.setId(pOficinaId);
			PersonalOficina personalOficinaToUpdate2 = this.pOficinaService.findPersonalOficinaById(pOficinaId);
			BeanUtils.copyProperties(personalOficina, personalOficinaToUpdate2, "id","nif","username");

			try {
				this.pOficinaService.savePersonalOficina(personalOficina);
			} catch (DataIntegrityViolationException e) {
				e.printStackTrace();
			}

			
			return "redirect:/personalOficina/{pOficinaId}";
		}
	}
	
	
	//ELIMINACIÓN
	@GetMapping(value = "/personalOficina/{pOficinaId}/delete")
	public String deleteCliente(@PathVariable("pOficinaId") int id) {
		this.pOficinaService.deletePersonalOficinaById(id);
		return "redirect:/personalOficina";
	}
	
	
	// Vistas de consulta
	@GetMapping(value = "/personalOficina")
	public String showPersonalOficinaList(ModelMap model, @PageableDefault(value=20) Pageable paging) {
		Page<PersonalOficina> pages = this.pOficinaService.findPersonal(paging);
		model.addAttribute("number", pages.getNumber());
		model.addAttribute("totalPages", pages.getTotalPages());
		model.addAttribute("totalElements", pages.getTotalElements());
		model.addAttribute("size", pages.getSize());
		model.addAttribute("personalOficina",pages.getContent());
		
		return "personalOficina/personalOficinaList";
	}
	
	@GetMapping(value = "/personalOficinafind")
	public String processFindPersonalOficinaForm(PersonalOficina pOficina, Map<String, Object> model, BindingResult result, @RequestParam(value = "nif", required=false) String nif) {

		PersonalOficina resultado = this.pOficinaService.findPersonalOficinaByNif(nif);

		if (resultado == null) {
			model.put("number", 0);
			model.put("totalPages", 1);
			model.put("size", 1);
			model.put("msg", "No existe ningún oficinista con dicho nif");
			return "personalOficina/personalOficinaList";
		} else {
			return "redirect:/personalOficina/" + resultado.getId();
		}

	}
	
	@GetMapping("/personalOficina/{pOficinaId}")
	public ModelAndView showAvion(@PathVariable("pOficinaId") int pOficinaId) {
		ModelAndView mav = new ModelAndView("personalOficina/oficinistaDetails");
		mav.addObject(this.pOficinaService.findPersonalOficinaById(pOficinaId));
		return mav;
	}
}
