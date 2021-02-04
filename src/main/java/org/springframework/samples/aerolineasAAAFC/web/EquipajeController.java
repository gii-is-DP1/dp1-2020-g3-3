package org.springframework.samples.aerolineasAAAFC.web;

import java.util.Collection;
import java.util.Map;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.aerolineasAAAFC.model.equipaje.Equipaje;
import org.springframework.samples.aerolineasAAAFC.model.equipaje.EquipajeBase;
import org.springframework.samples.aerolineasAAAFC.service.BilleteService;
import org.springframework.samples.aerolineasAAAFC.service.EquipajeBaseService;
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
public class EquipajeController {
	
	private static final String VIEWS_EQUIPAJE_CREATE_FORM = "billetes/createOrUpdateEquipajeForm";
	
	private final BilleteService billeteService;
	
	private final EquipajeBaseService equipajeBaseService;
	
	@Autowired
	public EquipajeController(BilleteService billeteService, EquipajeBaseService equipajeBaseService) {
		this.billeteService = billeteService;
		this.equipajeBaseService = equipajeBaseService;
	}
	
	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	@ModelAttribute("equipajes")
	public Collection<EquipajeBase> populateEquipajeTypes() {
		return this.equipajeBaseService.findEquipajesBase();
	}
	
	@GetMapping(value = "/billetes/{billeteId}/equipajes/new")
	public String initCreationForm(@PathVariable("billeteId") int billeteId, Map<String, Object> model) {
		Equipaje equipaje = new Equipaje();
		model.put("equipaje", equipaje);
		return VIEWS_EQUIPAJE_CREATE_FORM;
	}

	@PostMapping(value = "/billetes/{billeteId}/equipajes/new")
	public String processCreationForm(@PathVariable("billeteId") int billeteId, @Valid Equipaje equipaje, BindingResult result) {
		if (result.hasErrors()) {
			return VIEWS_EQUIPAJE_CREATE_FORM;
		}
		else {
			try {
				equipaje.setBillete(this.billeteService.findBilleteById(billeteId));
				this.billeteService.saveEquipaje(equipaje);
			} catch (TooManyItemsBilleteException e) {
				result.reject(e.getMessage());
				e.printStackTrace();
				return VIEWS_EQUIPAJE_CREATE_FORM;
			} catch (DataAccessException e) {
				result.reject(e.getMessage());
				e.printStackTrace();
				return VIEWS_EQUIPAJE_CREATE_FORM;
			} catch (Exception e) {
				result.reject(e.getMessage());
				e.printStackTrace();
				return "oups";
			}
			
			return "redirect:/billetes/datos";
		}
	}
	
}
