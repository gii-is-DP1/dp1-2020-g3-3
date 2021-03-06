package org.springframework.samples.aerolineasAAAFC.web;

import java.util.Collection;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.samples.aerolineasAAAFC.model.PersonalOficina;
import org.springframework.samples.aerolineasAAAFC.service.PersonalOficinaService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class PersonalOficinaController {

	private static final String VIEWS_PERSONALOFICINA_CREATE_OR_UPDATE_FORM = "personalOficina/createOrUpdatePersonalOficinaForm";
	
	private final PersonalOficinaService pOficinaService;
	
	@Autowired
	public PersonalOficinaController(PersonalOficinaService pOficinaService) {
		this.pOficinaService = pOficinaService;
	}
	
	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	
	// Alta de un nuevo oficinista
	
	@GetMapping(value = "/personalOficina/new")
	public String initCreationPersonalOficinaForm(Map<String, Object> model) {
		PersonalOficina pOficina = new PersonalOficina();
		model.put("personalOficina", pOficina);
		return VIEWS_PERSONALOFICINA_CREATE_OR_UPDATE_FORM;
	}
	
	@PostMapping(value = "/personalOficina/new")
	public String processCreationPersonalOficinaForm(@Valid PersonalOficina pOficina, BindingResult result) {
		if(result.hasErrors()) {
			return VIEWS_PERSONALOFICINA_CREATE_OR_UPDATE_FORM;
		}
		else {
			try {
				this.pOficinaService.savePersonalOficina(pOficina);
			} catch (DataIntegrityViolationException e) {
				result.rejectValue("nif", "duplicate", "already exists");
				return VIEWS_PERSONALOFICINA_CREATE_OR_UPDATE_FORM;
			}
			
			return "redirect:/personalOficina/" + pOficina.getId() ;
		}
	}
	
	
	// Update sobre un oficinista
	
	@GetMapping(value = "/personalOficina/{pOficinaId}/edit")
	public String initUpdatePersonalOficinaForm(@PathVariable("pOficinaId") int pOficinaId, ModelMap model, Authentication authentication) {
		//Evita que otros oficinistas editen perfiles de otros
		String usuario = authentication.getName();
		Collection<? extends GrantedAuthority> autoridad  = authentication.getAuthorities();
		String rol = "";
		for(GrantedAuthority auth: autoridad) {
			rol = auth.getAuthority();
		}
		if(rol.equals("personalOficina")) {
			PersonalOficina pOfiC = this.pOficinaService.findPersonalOficinaByNif(usuario);
			int pOfiCID = pOfiC.getId();
			if(pOficinaId != pOfiCID) {
				log.warn("El oficinista {} está intentado editar el oficinista {}", pOfiCID, pOficinaId);
				return "redirect:/personalOficina/"+pOfiCID;
			}
		}
		PersonalOficina personalOficina = this.pOficinaService.findPersonalOficinaById(pOficinaId);
		model.addAttribute(personalOficina);
		return VIEWS_PERSONALOFICINA_CREATE_OR_UPDATE_FORM;
	}
	
	
	@PostMapping(value = "/personalOficina/{pOficinaId}/edit")
	public String processUpdatePersonalOficinaForm(@Valid PersonalOficina personalOficina, BindingResult result, @PathVariable("pOficinaId") int pOficinaId,
			ModelMap model, @RequestParam(value = "version", required=false) Integer version, Authentication authentication) {

		
		PersonalOficina personalOficinaToUpdate = this.pOficinaService.findPersonalOficinaById(pOficinaId);

		if(personalOficinaToUpdate.getVersion()!=version) {
			model.put("message","Modificación de personal de oficina ya existente. ¡Prueba de nuevo!");
			return initUpdatePersonalOficinaForm(pOficinaId,model,authentication);
			}

		BeanUtils.copyProperties(personalOficina, personalOficinaToUpdate, "id","nif","user.username");
		if(result.hasErrors()) {
			return VIEWS_PERSONALOFICINA_CREATE_OR_UPDATE_FORM;
		}
		else {
			personalOficina.setId(pOficinaId);
			try {
				this.pOficinaService.savePersonalOficina(personalOficina);
			} catch (DataIntegrityViolationException e) {
				e.printStackTrace();
			}

			
			return "redirect:/personalOficina/{pOficinaId}";
		}
	}
	
	
	//ELIMINACIÓN
	@GetMapping(value = "/personalOficina/{pOficinaId}/delete")
	public String deleteCliente(@PathVariable("pOficinaId") int id) {
		this.pOficinaService.deletePersonalOficinaById(id);
		return "redirect:/personalOficina";
	}
	
	
	// Vistas de consulta
	@GetMapping(value = "/personalOficina")
	public String showPersonalOficinaList(ModelMap model, @PageableDefault(value=20) Pageable paging) {
		Page<PersonalOficina> pages = this.pOficinaService.findPersonal(paging);
		model.addAttribute("number", pages.getNumber());
		model.addAttribute("totalPages", pages.getTotalPages());
		model.addAttribute("totalElements", pages.getTotalElements());
		model.addAttribute("size", pages.getSize());
		model.addAttribute("personalOficina",pages.getContent());
		
		return "personalOficina/personalOficinaList";
	}
	
	@GetMapping(value = "/personalOficinafind")
	public String processFindPersonalOficinaForm(PersonalOficina pOficina, Map<String, Object> model, BindingResult result, @RequestParam(value = "nif", required=false) String nif) {

		PersonalOficina resultado = this.pOficinaService.findPersonalOficinaByNif(nif);

		if (resultado == null) {
			model.put("number", 0);
			model.put("totalPages", 1);
			model.put("size", 1);
			model.put("msg", "No existe ningún oficinista con dicho nif");
			return "personalOficina/personalOficinaList";
		} else {
			return "redirect:/personalOficina/" + resultado.getId();
		}

	}
	
	@GetMapping("/personalOficina/{pOficinaId}")
	public ModelAndView showAvion(@PathVariable("pOficinaId") int pOficinaId, Authentication authentication) {
		
		//Evita que otros oficinistas entren a perfiles de otros
		String usuario = authentication.getName();
		Collection<? extends GrantedAuthority> autoridad  = authentication.getAuthorities();
		String rol = "";
		for(GrantedAuthority auth: autoridad) {
			rol = auth.getAuthority();
		}
		if(rol.equals("personalOficina")) {
			PersonalOficina pOfiC = this.pOficinaService.findPersonalOficinaByNif(usuario);
			int pOfiCID = pOfiC.getId();
			if(pOficinaId != pOfiCID) {
				log.warn("El oficinista {} está intentado entrar en el perfil del oficinista {}", pOfiCID, pOficinaId);
				ModelAndView mav = new ModelAndView("personalOficina/oficinistaDetails");
				mav.addObject(this.pOficinaService.findPersonalOficinaById(pOfiCID));
				return mav;
			}
		}
		ModelAndView mav = new ModelAndView("personalOficina/oficinistaDetails");
		mav.addObject(this.pOficinaService.findPersonalOficinaById(pOficinaId));
		return mav;
	}
}
