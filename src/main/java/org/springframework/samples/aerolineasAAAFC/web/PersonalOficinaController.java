package org.springframework.samples.aerolineasAAAFC.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.samples.aerolineasAAAFC.model.Avion;
import org.springframework.samples.aerolineasAAAFC.model.PersonalControl;
import org.springframework.samples.aerolineasAAAFC.model.PersonalOficina;
import org.springframework.samples.aerolineasAAAFC.service.PersonalOficinaService;
import org.springframework.samples.aerolineasAAAFC.service.exceptions.IbanDuplicadoException;
import org.springframework.samples.aerolineasAAAFC.service.exceptions.NifDuplicadoException;
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
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PersonalOficinaController {

	private static final String VIEWS_PERSONALOFICINA_CREATE_OR_UPDATE_FORM = "oficinistas/createOrUpdatePersonalOficinaForm";
	
	private final PersonalOficinaService pOficinaService;
	
	@Autowired
	public PersonalOficinaController(PersonalOficinaService pOficinaService) {
		this.pOficinaService = pOficinaService;
	}
	
	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	
//	@ModelAttribute("pOficina")
//	public PersonalOficina loadPersonalOficinaWithVisit() {
//		PersonalOficina pOficina = new PersonalOficina();
//		return pOficina;
//	}
	
	/*
	 * Alta de un nuevo oficinista
	 */
	@GetMapping(value = "/oficinistas/new")
	public String initCreationPersonalOficinaForm(Map<String, Object> model) {
		PersonalOficina pOficina = new PersonalOficina();
		model.put("pOficina", pOficina);
		return VIEWS_PERSONALOFICINA_CREATE_OR_UPDATE_FORM;
	}
	
	@PostMapping(value = "/oficinistas/new")
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
			} catch (IbanDuplicadoException e) {
				result.rejectValue("iban", "duplicate", "already exists");
				return VIEWS_PERSONALOFICINA_CREATE_OR_UPDATE_FORM;
			}
			
			return "redirect:/oficinistas/" + pOficina.getId() ;
		}
	}
	
	/*
	 *  Update sobre un oficinista
	 */
	@GetMapping(value = "/oficinistas/{pOficinaId}/edit")
	public String initUpdatePersonalOficinaForm(@PathVariable("pOficinaId") int pOficinaId, Model model) {
		PersonalOficina pOficina = this.pOficinaService.findPersonalOficinaById(pOficinaId);
		model.addAttribute(pOficina);
		return VIEWS_PERSONALOFICINA_CREATE_OR_UPDATE_FORM;
	}
	
	@PostMapping(value = "/oficinistas/{pOficinaId}/edit")
	public String processUpdatePersonalOficinaForm(@Valid PersonalOficina pOficina, BindingResult result, @PathVariable("pOficinaId") int pOficinaId) {
		if(result.hasErrors()) {
			return VIEWS_PERSONALOFICINA_CREATE_OR_UPDATE_FORM;
		}
		else {
			pOficina.setId(pOficinaId);
			PersonalOficina pOficinaToUpdate = this.pOficinaService.findPersonalOficinaById(pOficinaId);
			BeanUtils.copyProperties(pOficina, pOficinaToUpdate, "id","nif","username");
			try {
				this.pOficinaService.savePersonalOficina(pOficina);
			} catch (DataIntegrityViolationException e) {
				result.rejectValue("nif", "duplicate", "already exists");
				return VIEWS_PERSONALOFICINA_CREATE_OR_UPDATE_FORM;
			} catch (IbanDuplicadoException e) {
				result.rejectValue("iban", "duplicate", "already exists");
				return VIEWS_PERSONALOFICINA_CREATE_OR_UPDATE_FORM;
			}
			
			return "redirect:/oficinistas/{pOficinaId}";
		}
	}
	
	/*
	 * Vistas de consulta
	 */
	
	@GetMapping(value = "/oficinistas")
	public String showPersonalOficinaList(Map<String, Object> model) {
		List<PersonalOficina> oficinistas = new ArrayList<PersonalOficina>();
		oficinistas.addAll(this.pOficinaService.findPersonal());
		model.put("oficinistas", oficinistas);
		
		return "oficinistas/personalOficinaList";
	}
	
	@GetMapping("/oficinistas/{pOficinaId}")
	public ModelAndView showAvion(@PathVariable("pOficinaId") int pOficinaId) {
		ModelAndView mav = new ModelAndView("oficinistas/oficinistaDetails");
		mav.addObject(this.pOficinaService.findPersonalOficinaById(pOficinaId));
		return mav;
	}
}
