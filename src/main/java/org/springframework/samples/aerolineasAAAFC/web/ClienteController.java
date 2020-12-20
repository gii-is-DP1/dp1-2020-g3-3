package org.springframework.samples.aerolineasAAAFC.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.samples.aerolineasAAAFC.model.Cliente;
import org.springframework.samples.aerolineasAAAFC.service.AuthoritiesService;
import org.springframework.samples.aerolineasAAAFC.service.ClienteService;
import org.springframework.samples.aerolineasAAAFC.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class ClienteController {

	private static final String VIEWS_CLIENTE_CREATE_OR_UPDATE_FORM = "clientes/createOrUpdateClienteForm";

	private final ClienteService clienteService;

	@Autowired
	public ClienteController(ClienteService clienteService, UserService userService, AuthoritiesService authoritiesService) {
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
	public String initCreationClienteForm(Map<String, Object> model) {
		Cliente cliente = new Cliente();
		model.put("cliente", cliente);
		return VIEWS_CLIENTE_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/clientes/new")
	public String processCreationClienteForm(@Valid Cliente cliente, BindingResult result) {
		if (result.hasErrors()) {
			return VIEWS_CLIENTE_CREATE_OR_UPDATE_FORM;
		}
		else {
			try {
				this.clienteService.saveCliente(cliente);
			} catch (DataIntegrityViolationException e) {
				result.rejectValue("nif", "duplicate", "already exists");
				return VIEWS_CLIENTE_CREATE_OR_UPDATE_FORM;
			}

			return "redirect:/clientes/" + cliente.getId();
		}
	}

	/*
	 * MODIFICACIÓN CLIENTE
	 */

	@GetMapping(value = "/clientes/{clienteId}/edit")
	public String initUpdateClienteForm(@PathVariable("clienteId") int clienteId, Model model) {
		Cliente cliente = this.clienteService.findClienteById(clienteId);
		model.addAttribute(cliente);
		return VIEWS_CLIENTE_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/clientes/{clienteId}/edit")
	public String processUpdateClienteForm(@Valid Cliente cliente, BindingResult result, 
			@PathVariable("clienteId") int clienteId) {
		if(result.hasErrors()) {
			return VIEWS_CLIENTE_CREATE_OR_UPDATE_FORM;
		}
		else {
			cliente.setId(clienteId);
			Cliente clienteToUpdate = this.clienteService.findClienteById(clienteId);
			BeanUtils.copyProperties(cliente, clienteToUpdate, "id","nif","username");    
			try {
				this.clienteService.saveCliente(cliente);
			} catch (DataIntegrityViolationException e) {
				result.rejectValue("nif", "duplicate", "already exists");
				return VIEWS_CLIENTE_CREATE_OR_UPDATE_FORM;
			}
			return "redirect:/clientes/{clienteId}";
		}
	}

	/*
	 * BÚSQUEDA CLIENTE/S
	 */

	@GetMapping(value =  "/clientesList" )
	public String showClientesList(Map<String, Object> model) {
		List<Cliente> clientes = new ArrayList<>();
		this.clienteService.findClientes().forEach(x->clientes.add(x));
		model.put("clientes", clientes);
		return "clientes/clientesList";
	}

	@GetMapping(value = "/clientes/find")
	public String initFindClienteForm(Map<String, Object> model) {
		model.put("cliente", new Cliente());
		return "clientes/findClientes";
	}

	@GetMapping(value = "/clientes")
	public String processFindClienteForm(Cliente cliente, BindingResult result, Map<String, Object> model) {

		if (cliente.getNif() == null) {
			cliente.setNif(""); 
		}

		Cliente resultado = this.clienteService.findClienteByNif(cliente.getNif());

		if (resultado == null) {
			result.rejectValue("nif", "notFound", "nif no encontrado");
			return "clientes/findClientes";
		} else {
			return "redirect:/clientes/" + resultado.getId();
		}

	}

	@GetMapping("/clientes/{clienteId}")
	public ModelAndView showCliente(@PathVariable("clienteId") int clienteId) {
		ModelAndView mav = new ModelAndView("clientes/clienteDetails");
		mav.addObject(this.clienteService.findClienteById(clienteId));
		return mav;
	}

	/*
	 *  ELIMINAR CLIENTE
	 */

	@GetMapping(value = "/clientes/{clienteId}/delete")
	public String deleteCliente(@PathVariable("clienteId") int clienteId) {
		this.clienteService.deleteClienteById(clienteId);
		return "redirect:/clientesList";
	}


}
