package org.springframework.samples.aerolineasAAAFC.web;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.aerolineasAAAFC.model.Avion;
import org.springframework.samples.aerolineasAAAFC.model.Cliente;
import org.springframework.samples.aerolineasAAAFC.service.ClienteService;
import org.springframework.samples.aerolineasAAAFC.service.exceptions.NifDuplicadoException;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

public class ClienteController {
	
	private static final String VIEWS_CLIENTE_CREATE_OR_UPDATE_FORM = "clientes/createOrUpdateClienteForm";
	
	private final ClienteService clienteService;
	
	@Autowired
	public ClienteController(ClienteService clienteService) {
		this.clienteService = clienteService;
	}
	
	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	

	/*
	 * ALTA DE UN NUEVO CLIENTE
	 */
	
	@GetMapping(value = "/clientes/new")
	public String initCreationForm(Map<String, Object> model) {
		Cliente cliente = new Cliente();
		model.put("cliente", cliente);
		return VIEWS_CLIENTE_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/clientes/new")
	public String processCreationForm(@Valid Cliente cliente, BindingResult result) {
		if (result.hasErrors()) {
			return VIEWS_CLIENTE_CREATE_OR_UPDATE_FORM;
		}
		else {
			try {
				this.clienteService.saveCliente(cliente);
			} catch (NifDuplicadoException e) {
				 result.rejectValue("nif", "duplicate", "already exists");
				 return VIEWS_CLIENTE_CREATE_OR_UPDATE_FORM;
			}
			
			return "redirect:/clientes/" + cliente.getId();
		}
	}
	
	/*
	 * MODIFICACIÓN DE UN CLIENTE
	 */
	
	@GetMapping(value = "/clientes/{clienteId}/edit")
	public String initUpdateAvionForm(@PathVariable("clienteId") int clienteId, Model model) {
		Cliente cliente = this.clienteService.findClienteById(clienteId);
		model.addAttribute(cliente);
		return VIEWS_CLIENTE_CREATE_OR_UPDATE_FORM;
	}
	
	@PostMapping(value = "/clientes/{clienteId}/edit")
	public String processUpdateAvionForm(@Valid Cliente cliente, BindingResult result, @PathVariable("clienteId") int clienteId) {
		if(result.hasErrors()) {
			return VIEWS_CLIENTE_CREATE_OR_UPDATE_FORM;
		}
		else {
			cliente.setId(clienteId);
			try {
				this.clienteService.saveCliente(cliente);
			} catch (NifDuplicadoException e) {
				 result.rejectValue("nif", "duplicate", "already exists");
				 return VIEWS_CLIENTE_CREATE_OR_UPDATE_FORM;
			}
			
			return "redirect:/clientes/{clienteId}";
		}
	}
	
	/*
	 * BÚSQUEDA CLIENTE/S
	 */
	
	@GetMapping("/clientes/{clienteId}")
	public ModelAndView showCliente(@PathVariable("clienteId") int clienteId) {
		ModelAndView mav = new ModelAndView("clientes/clienteDetails");
		mav.addObject(this.clienteService.findClienteById(clienteId));
		return mav;
	}

}
