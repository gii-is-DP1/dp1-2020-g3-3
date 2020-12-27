package org.springframework.samples.aerolineasAAAFC.web;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.samples.aerolineasAAAFC.model.PersonalControl;
import org.springframework.samples.aerolineasAAAFC.service.PersonalControlService;
import org.springframework.samples.aerolineasAAAFC.service.exceptions.IbanDuplicadoException;
import org.springframework.samples.aerolineasAAAFC.service.exceptions.NifDuplicadoException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PersonalControlController {

	private static final String VIEWS_PERSONALCONTROL_CREATE_OR_UPDATE_FORM = "controladores/createOrUpdatePersonalControlForm";
	
	private final PersonalControlService pControlService;
	
	@Autowired
	public PersonalControlController(PersonalControlService pControlService) {
		this.pControlService = pControlService;
	}
	
	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	/*
	 * Alta de un nuevo controlador
	 */
	@GetMapping(value = "/controladores/new")
	public String initCreationPersonalControlForm(Map<String, Object> model) {
		PersonalControl pControl = new PersonalControl();
		model.put("personalControl", pControl);
		return VIEWS_PERSONALCONTROL_CREATE_OR_UPDATE_FORM;
	}
	
	@PostMapping(value = "/controladores/new")
	public String processCreationPersonalControlForm(@Valid PersonalControl pControl, BindingResult result) {
		if(result.hasErrors()) {
			return VIEWS_PERSONALCONTROL_CREATE_OR_UPDATE_FORM;
		}
		else {
			try {
				this.pControlService.savePersonalControl(pControl);
			} catch (DataIntegrityViolationException e) {
				result.rejectValue("nif", "duplicate", "already exists");
				e.printStackTrace();
			} catch (IbanDuplicadoException e) {
				result.rejectValue("iban", "duplicate", "already exists");
				return VIEWS_PERSONALCONTROL_CREATE_OR_UPDATE_FORM;
			}
			
			return "redirect:/controladores/" + pControl.getId();
		}
	}
	
	/*
	 *  Update sobre un controlador
	 */
	@GetMapping(value = "/controladores/{pControlId}/edit")
	public String initUpdatePersonalControlForm(@PathVariable("pControlId") int pControlId, Model model) {
		PersonalControl pControl = this.pControlService.findPersonalControlById(pControlId);
		model.addAttribute(pControl);
		return VIEWS_PERSONALCONTROL_CREATE_OR_UPDATE_FORM;
	}
	
	@PostMapping(value = "/controladores/{pControlId}/edit")
	public String processUpdatePersonalControlForm(@Valid PersonalControl pControl, BindingResult result, @PathVariable("pControlId") int pControlId) {
		if(result.hasErrors()) {
			return VIEWS_PERSONALCONTROL_CREATE_OR_UPDATE_FORM;
		}
		else {
			pControl.setId(pControlId);
			PersonalControl pControlToUpdate = this.pControlService.findPersonalControlById(pControlId);
			BeanUtils.copyProperties(pControl, pControlToUpdate, "id","nif","username");
			try {
				this.pControlService.savePersonalControl(pControl);
			} catch (DataIntegrityViolationException e) {
				result.rejectValue("nif", "duplicate", "already exists");
				return VIEWS_PERSONALCONTROL_CREATE_OR_UPDATE_FORM;
			} catch (IbanDuplicadoException e) {
				result.rejectValue("iban", "duplicate", "already exists");
				return VIEWS_PERSONALCONTROL_CREATE_OR_UPDATE_FORM;
			}
			
			return "redirect:/controladores/{pControlId}";
		}
	}
}
