package org.springframework.samples.aerolineasAAAFC.web;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.samples.aerolineasAAAFC.model.Azafato;
import org.springframework.samples.aerolineasAAAFC.model.Cliente;
import org.springframework.samples.aerolineasAAAFC.service.AzafatoService;
import org.springframework.samples.aerolineasAAAFC.service.exceptions.IbanDuplicadoException;
import org.springframework.samples.aerolineasAAAFC.service.exceptions.IdiomasNoSuficientesException;
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
public class AzafatoController {

	private static final String VIEWS_AZAFATO_CREATE_OR_UPDATE_FORM = "azafatos/createOrUpdateAzafatoForm";
	
	private final AzafatoService azafatoService;
	
	@Autowired
	public AzafatoController(AzafatoService azafatoService) {
		this.azafatoService = azafatoService;
	}
	
	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	/*
	 * Alta de un nuevo azafato
	 */
	@GetMapping(value = "/azafatos/new")
	public String initCreationAzafatoForm(Map<String, Object> model) {
		Azafato azafato = new Azafato();
		model.put("azafato", azafato);
		return VIEWS_AZAFATO_CREATE_OR_UPDATE_FORM;
	}
	
	@PostMapping(value = "/azafatos/new")
	public String processCreationPersonalOficinaForm(@Valid Azafato azafato, BindingResult result) {
		if(result.hasErrors()) {
			return VIEWS_AZAFATO_CREATE_OR_UPDATE_FORM;
		}
		else {
			try {
				this.azafatoService.saveAzafato(azafato);
			} catch (DataIntegrityViolationException e) {
				result.rejectValue("nif", "duplicate", "already exists");
				return VIEWS_AZAFATO_CREATE_OR_UPDATE_FORM;
			} catch (IbanDuplicadoException e) {
				result.rejectValue("iban", "duplicate", "already exists");
				return VIEWS_AZAFATO_CREATE_OR_UPDATE_FORM;
			} catch (IdiomasNoSuficientesException e) {
				result.rejectValue("idiomas", "not enough", "not enough languages");
				return VIEWS_AZAFATO_CREATE_OR_UPDATE_FORM;
			}
			
			return "redirect:/azafatos/" + azafato.getId();
		}
	}
	
	/*
	 *  Update sobre un azafato
	 */
	@GetMapping(value = "/azafatos/{azafatoId}/edit")
	public String initUpdateAzafatoForm(@PathVariable("azafatoId") int azafatoId, Model model) {
		Azafato azafato = this.azafatoService.findAzafatoById(azafatoId);
		model.addAttribute(azafato);
		return VIEWS_AZAFATO_CREATE_OR_UPDATE_FORM;
	}
	
	@PostMapping(value = "/azafatos/{azafatoId}/edit")
	public String processUpdateAzafatoForm(@Valid Azafato azafato, BindingResult result, @PathVariable("azafatoId") int azafatoId) {
		if(result.hasErrors()) {
			return VIEWS_AZAFATO_CREATE_OR_UPDATE_FORM;
		}
		else {
			azafato.setId(azafatoId);
			Azafato azafatoToUpdate = this.azafatoService.findAzafatoById(azafatoId);
			BeanUtils.copyProperties(azafato, azafatoToUpdate, "id","nif","username");  
			try {
				this.azafatoService.saveAzafato(azafato);
			} catch (DataIntegrityViolationException e) {
				result.rejectValue("nif", "duplicate", "already exists");
				return VIEWS_AZAFATO_CREATE_OR_UPDATE_FORM;
			} catch (IbanDuplicadoException e) {
				result.rejectValue("iban", "duplicate", "already exists");
				return VIEWS_AZAFATO_CREATE_OR_UPDATE_FORM;
			} catch (IdiomasNoSuficientesException e) {
				result.rejectValue("idiomas", "not enough", "not enough languages");
				return VIEWS_AZAFATO_CREATE_OR_UPDATE_FORM;
			}
			
			return "redirect:/azafatos/{azafatoId}";
		}
	}
}
