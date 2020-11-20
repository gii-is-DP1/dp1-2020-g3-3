package org.springframework.samples.aerolineasAAAFC.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.aerolineasAAAFC.model.Cliente;
import org.springframework.samples.aerolineasAAAFC.service.ClienteService;
import org.springframework.web.bind.annotation.GetMapping;

public class ClienteController {
	
	private static final String VIEWS_CLIENTE_CREATE_OR_UPDATE_FORM = "clientes/createOrUpdateClienteForm";
	
	private final ClienteService clienteService;
	
	@Autowired
	public ClienteController(ClienteService clienteService) {
		this.clienteService = clienteService;
	}
	
	/*
	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}*/
	
	@GetMapping(value = "/clientes/find")
	public String initFindForm(Map<String, Object> model) {
		model.put("cliente", new Cliente());
		return "clientes/findClientes";
	}

}
