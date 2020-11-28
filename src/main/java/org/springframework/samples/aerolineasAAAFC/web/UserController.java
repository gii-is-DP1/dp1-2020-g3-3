package org.springframework.samples.aerolineasAAAFC.web;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.aerolineasAAAFC.model.Cliente;
import org.springframework.samples.aerolineasAAAFC.service.ClienteService;
import org.springframework.samples.aerolineasAAAFC.service.PersonalOficinaService;
import org.springframework.samples.aerolineasAAAFC.service.exceptions.NifDuplicadoException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;

public class UserController {
	
	private static final String VIEWS_CLIENTE_CREATE_FORM = "users/createClienteForm";
	
	private final ClienteService clienteService;
	

	@Autowired
	public UserController(ClienteService clienteService) {
		this.clienteService = clienteService;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@GetMapping(value = "/users/new")
	public String initCreationForm(Map<String, Object> model) {
		Cliente cliente= new Cliente();
		model.put("cliente", cliente);
		return VIEWS_CLIENTE_CREATE_FORM;
	}

	@PostMapping(value = "/users/new")
	public String processCreationForm(@Valid Cliente cliente, BindingResult result) {
		if (result.hasErrors()) {
			return VIEWS_CLIENTE_CREATE_FORM;
		}
		else {
			//creating cliente, user, and authority
			try {
				this.clienteService.saveCliente(cliente);
			} catch (NifDuplicadoException e) {
				 result.rejectValue("nif", "duplicate", "already exists");
				 return VIEWS_CLIENTE_CREATE_FORM;
			}
			return "redirect:/";
		}
	}
}
