package org.springframework.samples.aerolineasAAAFC.web;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.samples.aerolineasAAAFC.model.PersonalControl;
import org.springframework.samples.aerolineasAAAFC.model.Rol;
import org.springframework.samples.aerolineasAAAFC.model.Vuelo;
import org.springframework.samples.aerolineasAAAFC.service.PersonalControlService;
import org.springframework.samples.aerolineasAAAFC.service.VueloService;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class PersonalControlController {

	private static final String VIEWS_PERSONALCONTROL_CREATE_OR_UPDATE_FORM = "controladores/createOrUpdatePersonalControlForm";
	
	private final PersonalControlService pControlService;
	
	private final VueloService vueloService;
	
	@Autowired
	public PersonalControlController(PersonalControlService pControlService, VueloService vueloService) {
		this.pControlService = pControlService;
		this.vueloService = vueloService;
	}
	
	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	/*
	 * ALTA NUEVO CONTROLADOR
	 */
	@GetMapping(value = "/controladores/new")
	public String initCreationPersonalControlForm(Map<String, Object> model) {
		PersonalControl pControl = new PersonalControl();
		
		List<Rol> rol = new ArrayList<Rol>();
	    rol.add(Rol.PILOTO);
	    rol.add(Rol.COPILOTO);
	    rol.add(Rol.INGENIERO_DE_VUELO);
	    
	    model.put("personalControl", pControl);
	    model.put("roles", rol);
		return VIEWS_PERSONALCONTROL_CREATE_OR_UPDATE_FORM;
	}
	
	@PostMapping(value = "/controladores/new")
	public String processCreationPersonalControlForm(@Valid PersonalControl pControl, BindingResult result) {
		if(result.hasErrors()) {
			return VIEWS_PERSONALCONTROL_CREATE_OR_UPDATE_FORM;
		}
		else {
			try {
				this.pControlService.savePersonalControl(pControl);
			} catch (DataIntegrityViolationException e) {
				if(e.getMessage().contains("PUBLIC.PERSONAL_OFICINA(IBAN)")) {
					result.rejectValue("iban", "duplicate", "already exists");
				}else{
					result.rejectValue("nif", "duplicate", "already exists");
				}
				return VIEWS_PERSONALCONTROL_CREATE_OR_UPDATE_FORM;
			}
			
			return "redirect:/controladores/" + pControl.getId();
		}
	}
	
	/*
	 *  UPDATE CONTROLADOR
	 */
	@GetMapping(value = "/controladores/{pControlId}/edit")
	public String initUpdatePersonalControlForm(@PathVariable("pControlId") int pControlId, ModelMap model, Map<String, Object> roles, Authentication authentication) {
		
		//Evita que otros controladores editen perfiles de otros
				String usuario = authentication.getName();
				Collection<? extends GrantedAuthority> autoridad  = authentication.getAuthorities();
				String rolc = "";
				for(GrantedAuthority auth: autoridad) {
					rolc = auth.getAuthority();
				}
				if(rolc.equals("personalControl")) {
					PersonalControl pControlC = this.pControlService.findPersonalControlByNif(usuario);
					int pControlCID = pControlC.getId();
					if(pControlId != pControlCID) {
						log.warn("El controlador {} está intentado editar el controlador {}", pControlCID, pControlId);
						return "redirect:/controladores" + pControlCID;
					}
				}
		
		PersonalControl pControl = this.pControlService.findPersonalControlById(pControlId);
		model.addAttribute(pControl);
		
		List<Rol> rol = new ArrayList<Rol>();
	    rol.add(Rol.PILOTO);
	    rol.add(Rol.COPILOTO);
	    rol.add(Rol.INGENIERO_DE_VUELO);
	    roles.put("roles", rol);
	    
		return VIEWS_PERSONALCONTROL_CREATE_OR_UPDATE_FORM;
	}
	
	@PostMapping(value = "/controladores/{pControlId}/edit")
	public String processUpdatePersonalControlForm(@Valid PersonalControl pControl, BindingResult result, @PathVariable("pControlId") int pControlId,
			ModelMap model, Map<String, Object> roles, @RequestParam(value = "version", required=false) Integer version, Authentication authentication) {
		
		PersonalControl pControlToUpdate = this.pControlService.findPersonalControlById(pControlId);
		if(pControlToUpdate.getVersion()!=version) {
			model.put("message","Modificación de personal de control ya existente. ¡Prueba de nuevo!");
			return initUpdatePersonalControlForm(pControlId,model, roles, authentication);
			}
 
		
		List<Rol> rol = new ArrayList<Rol>();
	    rol.add(Rol.PILOTO);
	    rol.add(Rol.COPILOTO);
	    rol.add(Rol.INGENIERO_DE_VUELO);
	    roles.put("roles", rol);
	    BeanUtils.copyProperties(pControl, pControlToUpdate, "id","nif","user.username");
		if(result.hasErrors()) {
			return VIEWS_PERSONALCONTROL_CREATE_OR_UPDATE_FORM;
		}
		else {
			pControl.setId(pControlId);
			try {
				this.pControlService.savePersonalControl(pControl);
			} catch (DataIntegrityViolationException e) {
				if(e.getMessage().contains("PUBLIC.PERSONAL_OFICINA(IBAN)")) {
					result.rejectValue("iban", "duplicate", "already exists");
				}else{
					result.rejectValue("nif", "duplicate", "already exists");
				}
				return VIEWS_PERSONALCONTROL_CREATE_OR_UPDATE_FORM;
			}
			return "redirect:/controladores/{pControlId}";
		}
	}

	/*
	 * BUSCAR CONTROLADOR
	 */



	@GetMapping(value = "/controladoresfind")
	public String processFindPersonalControlForm(PersonalControl pControl, BindingResult result, Map<String, Object> model, @RequestParam(value = "nif", required=false) String nif) {

		PersonalControl resultado = this.pControlService.findPersonalControlByNif(pControl.getNif());

		if (resultado == null) {
			model.put("number", 0);
			model.put("totalPages", 1);
			model.put("size", 1);
			model.put("msg", "No existe ningún controlador con dicho nif");
			return "controladores/personalControlList";
		} else {
			return "redirect:/controladores/" + resultado.getId();
		}

	}

	/*
	 * Vistas de consulta de controladores
	 */
	
	@GetMapping(value = "/controladores")
	public String showPersonalControlList(ModelMap model, @PageableDefault(value=20) Pageable paging) {
    
		Page<PersonalControl> pages = this.pControlService.findPersonalControl(paging);
		model.addAttribute("number", pages.getNumber());
		model.addAttribute("totalPages", pages.getTotalPages());
		model.addAttribute("totalElements", pages.getTotalElements());
		model.addAttribute("size", pages.getSize());
		model.addAttribute("personalControl",pages.getContent());

		return "controladores/personalControlList";
	}
	
	
	@GetMapping("/controladores/{pControlId}")
	public ModelAndView showControlador(@PathVariable("pControlId") int pControlId, Authentication authentication) {
		
		//Evita que otros controladores entren a perfiles de otros
		String usuario = authentication.getName();
		Collection<? extends GrantedAuthority> autoridad  = authentication.getAuthorities();
		String rol = "";
		for(GrantedAuthority auth: autoridad) {
			rol = auth.getAuthority();
		}
		if(rol.equals("personalControl")) {
			PersonalControl pControlC = this.pControlService.findPersonalControlByNif(usuario);
			int pControlCID = pControlC.getId();
			if(pControlId != pControlCID) {
				log.warn("El controlador {} está intentado entrar en el perfil del controlador {}", pControlCID, pControlId);
				ModelAndView mav = new ModelAndView("controladores/controladorDetails");
				mav.addObject(this.pControlService.findPersonalControlById(pControlCID));
				return mav;
			}
		}
		ModelAndView mav = new ModelAndView("controladores/controladorDetails");
		mav.addObject(this.pControlService.findPersonalControlById(pControlId));
		return mav;
	}

	/*
	 *  ELIMINAR CONTROLADOR
	 */

	@GetMapping(value = "/controladores/{pControlId}/delete")
	public String deletePersonalControl(@PathVariable("pControlId") int pControlId) {
		this.pControlService.deletePersonalControlById(pControlId);
		return "redirect:/controladoresList";
	}
	
	//Horario del controlador
	
	@RequestMapping(value = { "/controladores/{pControlId}/horario" }, method = RequestMethod.GET)
	public String showHorario(Map<String, Object> model, @PathVariable("pControlId") int pControlId,  
								@RequestParam(name = "fecha", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {


		if(fecha == null) {
			fecha = LocalDate.now();
		}
		
		int mes = fecha.getMonthValue();
		int año = fecha.getYear();
		Month mesn = fecha.getMonth();
		int dias = fecha.lengthOfMonth();
		
		Collection<Vuelo> vuelos = this.pControlService.horario(pControlId, mes, año);
		
		List<Integer> diasV = new ArrayList<>();
		for(Vuelo v: vuelos) {
			diasV.add(v.getFechaSalida().getDayOfMonth());
		}
		
		model.put("vuelos", vuelos);
		model.put("dias", dias);
		model.put("mes", mesn);
		model.put("año", año);
		if(mes>=10) {
			model.put("mesN", mes);
		}else {
			model.put("mesN", "0"+mes);
		}
		model.put("diasV", diasV);
		return "controladores/horario";
	}
	
	
	//H9: Esta vista no se usa, se puede consultar en controladorDetails	
//	@GetMapping(value = { "/controladores/vuelos" })
//	public String consultaVuelosList(ModelMap model) {
//
//		List<Vuelo> vuelos = this.vueloService.findVuelos();
//		model.put("vuelos", vuelos);
//		
//		return "controladores/rutaVuelos";
//	}
	
	// Historia usuario 1:
	@RequestMapping(value = { "/controladores/{pControlId}/semana" }, method = RequestMethod.GET)
	public String showHorarioSemanalPersonalControl(Map<String, Object> model, @PathVariable("pControlId") int pControlId,  @RequestParam(name = "fecha", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {

		LocalDate date = LocalDate.now();
		
		if(fecha != null) {
			date = fecha;
		}
		
		DayOfWeek diaSemana = date.getDayOfWeek();
		int dia = date.getDayOfYear();
		int anyo = date.getYear();
		model.put("vuelos", this.pControlService.horarioSemanalConFecha(pControlId, diaSemana, dia, anyo));
		String diaS = diaSemana.toString();
		model.put("diaS", diaS);
		model.put("diaM", date.getDayOfMonth());
		model.put("mes", date.getMonthValue());
		model.put("anyo", date.getYear());
		return "controladores/horarioSemanal";
	}
	
}
