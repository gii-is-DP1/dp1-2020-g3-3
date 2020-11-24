package org.springframework.samples.aerolineasAAAFC.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.aerolineasAAAFC.model.Billete;
import org.springframework.samples.aerolineasAAAFC.service.BilleteService;
import org.springframework.samples.aerolineasAAAFC.service.MenuService;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

//Hay que cambiar el mapeado

public class MenuController {
	
	private static final String VIEWS_BILLETE_CREATE_OR_UPDATE_FORM = "billetes/createOrUpdateBilleteForm";
	
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
	
	//revisar
	@ModelAttribute("billete")
	public Billete findBillete(@PathVariable("billeteId") int billeteId) {
		return this.billeteService.findBilleteById(billeteId).get();
	}
	
	//queda por continuar
	
	
}
