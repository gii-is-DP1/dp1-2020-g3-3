package org.springframework.samples.aerolineasAAAFC.web;

import java.util.Collection;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.samples.aerolineasAAAFC.model.Azafato;
import org.springframework.samples.aerolineasAAAFC.model.Cliente;
import org.springframework.samples.aerolineasAAAFC.model.PersonalControl;
import org.springframework.samples.aerolineasAAAFC.model.PersonalOficina;
import org.springframework.samples.aerolineasAAAFC.service.AzafatoService;
import org.springframework.samples.aerolineasAAAFC.service.ClienteService;
import org.springframework.samples.aerolineasAAAFC.service.PersonalControlService;
import org.springframework.samples.aerolineasAAAFC.service.PersonalOficinaService;
import org.springframework.samples.aerolineasAAAFC.service.UserService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class UserController {
	
	private static final String VIEWS_CLIENTE_CREATE_FORM = "users/createClienteForm";
	
	private final UserService userService;
	private final AzafatoService azafatoService;
	private final ClienteService clienteService;
	private final PersonalControlService personalControlService;
	private final PersonalOficinaService personalOficinaService;
	

	@Autowired
	public UserController(UserService userService, AzafatoService azafatoService, ClienteService clienteService, 
						PersonalControlService personalControlService, PersonalOficinaService personalOficinaService) {
		this.userService = userService;
		this.azafatoService = azafatoService;
		this.clienteService = clienteService;
		this.personalControlService = personalControlService;
		this.personalOficinaService = personalOficinaService;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}	
	
	@GetMapping(value = "/user/new")
	public String initCreationClienteForm(Map<String, Object> model) {
		Cliente cliente = new Cliente();
		model.put("cliente", cliente);
		return VIEWS_CLIENTE_CREATE_FORM;
	}

	@PostMapping(value = "/user/new")
	public String processCreationClienteForm(@Valid Cliente cliente, BindingResult result) {
		if (result.hasErrors()) {
			return VIEWS_CLIENTE_CREATE_FORM;
		}
		else {
			try {
				this.clienteService.saveCliente(cliente);
			} catch (DataIntegrityViolationException e) {
				result.rejectValue("nif", "duplicate", "already exists");
				return VIEWS_CLIENTE_CREATE_FORM;
			} 

			return "redirect:/clientes/" + cliente.getId();
		}
	}

	
	@GetMapping(value = "/users/miPerfil")
	public String getMiPerfil(Authentication authentication) {
		
		String usuario = authentication.getName();
		Collection<? extends GrantedAuthority> autoridad  = authentication.getAuthorities();
		String rol = "";
		for(GrantedAuthority auth: autoridad) {
			rol = auth.getAuthority();
		}


		if(rol.equals("cliente")) {
			Cliente resultado = this.clienteService.findClienteByNif(usuario);
			return "redirect:/clientes/" + resultado.getId();
		}else if(rol.equals("azafato")) {
			Azafato resultado = this.azafatoService.findAzafatoByNif(usuario);
			return "redirect:/azafatos/" + resultado.getId();
		}else if(rol.equals("personalControl")) {
			PersonalControl resultado = this.personalControlService.findPersonalControlByNif(usuario);
			return "redirect:/controladores/" + resultado.getId();
		}else if(rol.equals("personalOficina")) {
			PersonalOficina resultado = this.personalOficinaService.findPersonalControlByNif(usuario);
			return "redirect:/personalOficina/" + resultado.getId();
		}
		
		return "welcome";
	}
}
