package org.springframework.samples.aerolineasAAAFC.web;

import java.util.Collection;
import java.util.Map;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.aerolineasAAAFC.model.Billete;
import org.springframework.samples.aerolineasAAAFC.model.menu.Menu;
import org.springframework.samples.aerolineasAAAFC.model.menu.PlatoBase;
import org.springframework.samples.aerolineasAAAFC.service.BilleteService;
import org.springframework.samples.aerolineasAAAFC.service.PlatoBaseService;
import org.springframework.samples.aerolineasAAAFC.service.exceptions.TooManyItemsBilleteException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
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
	public Collection<PlatoBase> populatePlatosPrimeros() {
		return this.platoBaseService.findPlatosPorTipo("primerPlato");
	}
	
	@ModelAttribute("platos_segundos")
	public Collection<PlatoBase> populatePlatosSegundos() {
		return this.platoBaseService.findPlatosPorTipo("segundoPlato");
	}
	
	@ModelAttribute("postres")
	public Collection<PlatoBase> populatePostres() {
		return this.platoBaseService.findPlatosPorTipo("postre");
	}
	
	@GetMapping(value = "/billetes/{billeteId}/menus/new")
	public String initCreationForm(@PathVariable("billeteId") int billeteId, Map<String, Object> model) {

		Billete billete = this.billeteService.findBilleteById(billeteId);

		if (billete == null) {
			return "redirect:/exception"; // Vista de error
		}

		else if (SecurityContextHolder.getContext().getAuthentication() != null) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String name = authentication.getName(); // name corresponde al nif del usuario

			if (name.equals(billete.getCliente().getNif())) {
				Menu menu = new Menu();
				model.put("menu", menu);
			}

			else {
				return "redirect:/exception";
			}

		} else {
			return "login";
		}

		return VIEWS_MENU_CREATE_FORM;		
	}

	@PostMapping(value = "/billetes/{billeteId}/menus/new")
	public String processCreationForm(@PathVariable("billeteId") int billeteId, @Valid Menu menu, BindingResult result) {
		
		Billete billete = this.billeteService.findBilleteById(billeteId);

		if (billete == null) {
			return "redirect:/exception"; // Vista de error
		}

		else if (SecurityContextHolder.getContext().getAuthentication() != null) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String name = authentication.getName(); // name corresponde al nif del usuario

			if (name.equals(billete.getCliente().getNif())) {
				if (result.hasErrors()) {
					return VIEWS_MENU_CREATE_FORM;
				}

				else {
					try {
						menu.setBillete(this.billeteService.findBilleteById(billeteId));
						this.billeteService.saveMenu(menu);
					} catch (TooManyItemsBilleteException | DataAccessException e) {
						result.reject(e.getMessage());
						e.printStackTrace();
						return VIEWS_MENU_CREATE_FORM;
					} catch (Exception e) {
						result.reject(e.getMessage());
						e.printStackTrace();
						return "redirect:/exception";
					}
				}
			}

			else {
				return "redirect:/exception";
			}
		}
		return "redirect:/billetes/"+billete.getId();
	}
	
}
