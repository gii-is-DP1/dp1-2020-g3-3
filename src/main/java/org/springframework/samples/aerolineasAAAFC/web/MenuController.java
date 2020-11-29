package org.springframework.samples.aerolineasAAAFC.web;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.aerolineasAAAFC.model.Billete;
import org.springframework.samples.aerolineasAAAFC.service.BilleteService;
import org.springframework.samples.aerolineasAAAFC.service.MenuService;
import org.springframework.samples.aerolineasAAAFC.service.exceptions.BadRequestException;
import org.springframework.samples.aerolineasAAAFC.service.exceptions.TooManyItemsBilleteException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

//Checkear mapeado
@Controller
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
		Billete billete = new Billete();
		model.put("billete", billete);
		return VIEWS_BILLETE_CREATE_FORM;
	}

	@PostMapping(value = "/menus/new")
	public String processCreationForm(@Valid Billete billete, BindingResult result) {
		if (result.hasErrors()) {
			return VIEWS_BILLETE_CREATE_FORM;
		}
		else {
			
			try {
				this.billeteService.saveBillete(billete);
			} catch (TooManyItemsBilleteException e) {
				 result.rejectValue(e.getCauseF(), "many", "way too many "+e.getCauseF());
					return VIEWS_BILLETE_CREATE_FORM;
			}
			return "redirect:/";
		}
	}
	
//	@DeleteMapping("/menus/{id}")
//	public void delete(@PathVariable("id") int id) throws BadRequestException {
//
//		if (menuService.findMenuById(id) == null) {
//			throw new BadRequestException("Oops! Parece que no se ha encontrado el menu");
//		} else {
//			menuService.deleteMenu(id);
//		}
//	}
	
	
}
