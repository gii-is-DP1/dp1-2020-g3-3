package org.springframework.samples.aerolineasAAAFC.web;

import java.util.Collection;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.aerolineasAAAFC.model.menu.Menu;
import org.springframework.samples.aerolineasAAAFC.service.BilleteService;
import org.springframework.samples.aerolineasAAAFC.service.PlatoBaseService;
import org.springframework.samples.aerolineasAAAFC.service.exceptions.PlatosNoValidosException;
import org.springframework.samples.aerolineasAAAFC.service.exceptions.TooManyItemsBilleteException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MenuController {
	
	private static final String VIEWS_MENU_CREATE_FORM = "billetes/createOrUpdateMenuForm";
	
	private final BilleteService billeteService;
	
	private final PlatoBaseService platoBaseService;
	
	@Autowired
	public MenuController(BilleteService billeteService, PlatoBaseService platoBaseService) {
		this.billeteService = billeteService;
		this.platoBaseService = platoBaseService;
	}
	
	@ModelAttribute("platos_primeros")
	public Collection<String> populatePlatosPrimeros() {
		return this.platoBaseService.findPlatosPorTipo("primerPlato");
	}
	
	@ModelAttribute("platos_segundos")
	public Collection<String> populatePlatosSegundos() {
		return this.platoBaseService.findPlatosPorTipo("segundoPlato");
	}
	
	@ModelAttribute("postres")
	public Collection<String> populatePostres() {
		return this.platoBaseService.findPlatosPorTipo("postre");
	}
	
	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	@GetMapping(value = "/billetes/{billeteId}/menus/new")
	public String initCreationForm(@PathVariable("billeteId") int billeteId, Map<String, Object> model) {
		Menu menu = new Menu();
		model.put("menu", menu);
		return VIEWS_MENU_CREATE_FORM;
	}

	@PostMapping(value = "/billetes/{billeteId}/menus/new")
	public String processCreationForm(@PathVariable("billeteId") int billeteId, @Valid Menu menu, BindingResult result) {
		if (result.hasErrors()) {
			return VIEWS_MENU_CREATE_FORM;
		}
		else {
			try {
				menu.setBillete(this.billeteService.findBilleteById(billeteId));
				this.billeteService.saveMenu(menu);
			} catch (DataAccessException e) {
				e.printStackTrace();
				return VIEWS_MENU_CREATE_FORM;
			} catch (TooManyItemsBilleteException e) {
				result.reject(e.getMessage());
				e.printStackTrace();
				return VIEWS_MENU_CREATE_FORM;
			} catch (PlatosNoValidosException e) {
				result.reject(e.getMessage());
				e.printStackTrace();
				return VIEWS_MENU_CREATE_FORM;
			}
			return "redirect:/billetes/datos";
		}
	}
	
}
