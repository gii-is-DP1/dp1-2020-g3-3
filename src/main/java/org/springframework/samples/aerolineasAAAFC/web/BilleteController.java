package org.springframework.samples.aerolineasAAAFC.web;

import java.util.Map;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.aerolineasAAAFC.model.Billete;
import org.springframework.samples.aerolineasAAAFC.service.BilleteService;
import org.springframework.samples.aerolineasAAAFC.service.exceptions.TooManyItemsBilleteException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BilleteController {
	
	private static final String VIEWS_BILLETE_CREATE_OR_UPDATE_FORM = "billetes/createOrUpdateBilleteForm";
	
	private final BilleteService billeteService;
	
	@Autowired
	public BilleteController(BilleteService billeteService) {
		this.billeteService = billeteService;
	}
	
	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	/*
	 *  Alta de un nuevo billete
	 */
	@GetMapping(value = "/billetes/new")
	public String initCreationBilleteForm(Map<String, Object> model) {
		Billete billete = new Billete();
		model.put("billete", billete);
		return VIEWS_BILLETE_CREATE_OR_UPDATE_FORM;
	}
	
	@PostMapping(value = "/billetes/new")
	public String processCreationBilleteForm(@Valid Billete billete, BindingResult result) {
		if(result.hasErrors()) {
			return VIEWS_BILLETE_CREATE_OR_UPDATE_FORM;
		}
		else {
			try {
				this.billeteService.saveBillete(billete);
			} catch (TooManyItemsBilleteException e) {
				 result.rejectValue(e.getCauseF(), "many", "way too many "+e.getCauseF());
					return VIEWS_BILLETE_CREATE_OR_UPDATE_FORM;
			}
			
			return "redirect:/billetes/" + billete.getId();
		}
	}
	
	/*
	 * Modificacion de un billete
	 */
	@GetMapping(value = "/billetes/{billeteId}/edit")
	public String initUpdateBilleteForm(@PathVariable("billeteId") int billeteId, Model model) {
		Billete billete = this.billeteService.findBilleteById(billeteId);
		model.addAttribute(billete);
		return VIEWS_BILLETE_CREATE_OR_UPDATE_FORM;
	}
	
	@PostMapping(value = "/billetes/{billeteId}/edit")
	public String processUpdateBilleteForm(@Valid Billete billete, BindingResult result, @PathVariable("billeteId") int billeteId) {
		if(result.hasErrors()) {
			return VIEWS_BILLETE_CREATE_OR_UPDATE_FORM;
		}
		else {
			billete.setId(billeteId);
			try {
				this.billeteService.saveBillete(billete);
			} catch (TooManyItemsBilleteException e) {
				 result.rejectValue(e.getCauseF(), "many", "way too many "+e.getCauseF());
					return VIEWS_BILLETE_CREATE_OR_UPDATE_FORM;
			}
			
			return "redirect:/billetes/{billeteId}";
		}
	}
	
	
	@GetMapping(value = "/billetes/billetesDatosList/{billeteId}")
	public String ShowDatosBillete(@PathVariable("billeteId") int billeteId, Model model) {
		Billete billete = this.billeteService.findBilleteById(billeteId);
		model.addAttribute(billete);
		return "redirect:/billetes/billetesDatosList/{billeteId}";
	}
	

}
