package org.springframework.samples.aerolineasAAAFC.web;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.samples.aerolineasAAAFC.model.Cliente;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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
	 * ALTA NUEVO CONTROLADOR
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
	 *  UPDATE CONTROLADOR
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
	
	
	/*
	 * BUSCAR CONTROLADOR
	 */

	@GetMapping(value =  "/personalControlList" )
	public String showPersonalControlList(Map<String, Object> model) {
		List<PersonalControl> pControl = new ArrayList<>();
		this.pControlService.findPersonalControl().forEach(x->pControl.add(x));
		model.put("pControl", pControl);
		return "controladores/personalControlList";
	}

	@GetMapping(value = "/controladores/find")
	public String initFindPersonalControlForm(Map<String, Object> model) {
		model.put("pControl", new PersonalControl());
		return "controladores/findPersonalControl";
	}

	@GetMapping(value = "/controladores")
	public String processFindPersonalControlForm(PersonalControl pControl, BindingResult result, Map<String, Object> model) {

		if (pControl.getNif() == null) {
			pControl.setNif(""); 
		}

		PersonalControl resultado = this.pControlService.findPersonalControlByNif(pControl.getNif());

		if (resultado == null) {
			result.rejectValue("nif", "notFound", "nif no encontrado");
			return "controladores/findPersonalControl";
		} else {
			return "redirect:/controladores/" + resultado.getId();
		}

	}

	@GetMapping("/controladores/{pControlId}")
	public ModelAndView showCliente(@PathVariable("pControlId") int pControlId) {
		ModelAndView mav = new ModelAndView("controladores/controladorDetails");
		mav.addObject(this.pControlService.findPersonalControlById(pControlId));
		return mav;
	}

	/*
	 *  ELIMINAR CONTROLADOR
	 */

	@GetMapping(value = "/controladores/{pControlId}/delete")
	public String deletePersonalControl(@PathVariable("pControlId") int pControlId) {
		this.pControlService.deletePersonalControlById(pControlId);
		return "redirect:/personalControlList";
	}
	
	@RequestMapping(value = { "/controladores/{pControlId}/horario" }, method = RequestMethod.GET)
	public String showVuelosList(Map<String, Object> model, @PathVariable("pControlId") int pControlId,  @RequestParam(name = "fecha", defaultValue = "") String fecha) {

		if(fecha.isEmpty()) {
			model.put("vuelos", this.pControlService.horario(pControlId));
		}else {
			fecha += "-01";
			LocalDate date = LocalDate.parse(fecha, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			int mes = date.getMonthValue();
			int año = date.getYear();
			model.put("vuelos", this.pControlService.findVuelosByDate(pControlId, mes, año));
		}
		return "controladores/horario";
	}
	
}
