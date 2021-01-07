package org.springframework.samples.aerolineasAAAFC.web;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.aerolineasAAAFC.model.Equipaje;
import org.springframework.samples.aerolineasAAAFC.service.BilleteService;
import org.springframework.samples.aerolineasAAAFC.service.exceptions.EquipajePriceException;
import org.springframework.samples.aerolineasAAAFC.service.exceptions.TooManyItemsBilleteException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class EquipajeController {
	
	private static final String VIEWS_EQUIPAJE_CREATE_FORM = "billetes/createOrUpdateEquipajeForm";
	
	private final BilleteService billeteService;
	
	@Autowired
	public EquipajeController(BilleteService billeteService) {
		this.billeteService = billeteService;
	}
	
	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	@GetMapping(value = "/billetes/{billeteId}/equipajes/new")
	public String initCreationForm(@PathVariable("billeteId") int billeteId, Map<String, Object> model) {
		return VIEWS_EQUIPAJE_CREATE_FORM;
	}

	@PostMapping(value = "/billetes/{billeteId}/equipajes/new")
	public String processCreationForm(@Valid Equipaje equipaje, BindingResult result) {
		if (result.hasErrors()) {
			return VIEWS_EQUIPAJE_CREATE_FORM;
		}
		else {
			try {
				this.billeteService.saveEquipaje(equipaje);
			} catch (DataAccessException e) {
				e.printStackTrace();
			} catch (EquipajePriceException e) {
				result.reject(e.getMessage());
				e.printStackTrace();
			} catch (TooManyItemsBilleteException e) {
				result.reject(e.getMessage());
				e.printStackTrace();
			}
			return "redirect:/billetes/datos";
		}
	}
	
}
