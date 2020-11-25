package org.springframework.samples.aerolineasAAAFC.web;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.aerolineasAAAFC.model.Billete;
import org.springframework.samples.aerolineasAAAFC.service.BilleteService;
import org.springframework.samples.aerolineasAAAFC.service.MenuService;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

//Hay que cambiar el mapeado

public class MenuController {
	
	private static final String VIEWS_BILLETE_CREATE_FORM = "billetes/createOrUpdateBilleteForm";
	
	private final BilleteService billeteService;
	private final MenuService menuService;
	
	@Autowired
	public MenuController(MenuService menuService, BilleteService billeteService) {
		this.menuService = menuService;
		this.billeteService = billeteService;
	}
	
	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	@GetMapping(value = "/menus/new")
	public String initCreationForm(Map<String, Object> model) {
		Owner owner = new Owner();
		model.put("owner", owner);
		return VIEWS_BILLETE_CREATE_FORM;
	}

	//Revisar el bloque try catch de menu
//	@PostMapping(value = "/menus/new")
//	public String processCreationForm(@Valid Owner owner, BindingResult result) {
//		if (result.hasErrors()) {
//			return VIEWS_BILLETE_CREATE_FORM;
//		}
//		else {
//			//creating owner, user, and authority
//			this.billeteService.saveBillete(owner);
//			return "redirect:/";
//		}
//	}
	
	
}
