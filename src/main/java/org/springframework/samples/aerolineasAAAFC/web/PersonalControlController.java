package org.springframework.samples.aerolineasAAAFC.web;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.log4j2.Log4J2LoggingSystem;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.samples.aerolineasAAAFC.model.Aeropuerto;
import org.springframework.samples.aerolineasAAAFC.model.Billete;
import org.springframework.samples.aerolineasAAAFC.model.Cliente;
import org.springframework.samples.aerolineasAAAFC.model.PersonalControl;
import org.springframework.samples.aerolineasAAAFC.model.PersonalOficina;
import org.springframework.samples.aerolineasAAAFC.model.Rol;
import org.springframework.samples.aerolineasAAAFC.model.Vuelo;
import org.springframework.samples.aerolineasAAAFC.service.PersonalControlService;
import org.springframework.samples.aerolineasAAAFC.service.exceptions.IbanDuplicadoException;
import org.springframework.samples.aerolineasAAAFC.service.exceptions.NifDuplicadoException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

import net.bytebuddy.asm.Advice.Local;

@Controller
public class PersonalControlController {

	private static final String VIEWS_PERSONALCONTROL_CREATE_OR_UPDATE_FORM = "controladores/createOrUpdatePersonalControlForm";
	
	private final PersonalControlService pControlService;
	
	@Autowired
	public PersonalControlController(PersonalControlService pControlService) {
		this.pControlService = pControlService;
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
				result.rejectValue("nif", "duplicate", "already exists");
				e.printStackTrace();
			} catch (IbanDuplicadoException e) {
				result.rejectValue("iban", "duplicate", "already exists");
				return VIEWS_PERSONALCONTROL_CREATE_OR_UPDATE_FORM;
			}
			
			return "redirect:/controladores/" + pControl.getId();
		}
	}
	
	/*
	 *  UPDATE CONTROLADOR
	 */
	@GetMapping(value = "/controladores/{pControlId}/edit")
	public String initUpdatePersonalControlForm(@PathVariable("pControlId") int pControlId, ModelMap model, 
			Map<String, Object> roles) {
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
			ModelMap model, @RequestParam(value = "version", required=false) Integer version) {
		PersonalControl PersonalControlToUpdate=this.pControlService.findPersonalControlById(pControlId);

		if(PersonalControlToUpdate.getVersion()!=version) {
			model.put("message","Concurrent modification of Controller Try again!");
			return initUpdatePersonalControlForm(pControlId,model);
			}
		if(result.hasErrors()) {
			return VIEWS_PERSONALCONTROL_CREATE_OR_UPDATE_FORM;
		}
		else {
			pControl.setId(pControlId);
			PersonalControl pControlToUpdate = this.pControlService.findPersonalControlById(pControlId);
			BeanUtils.copyProperties(pControl, pControlToUpdate, "id","nif","username");
			try {
				this.pControlService.updatePersonalControl(pControl);
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
	
	
	private String initUpdatePersonalControlForm(int pControlId, ModelMap model) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * BUSCAR CONTROLADOR
	 */
//
//	@GetMapping(value =  "/personalControlList" )
//	public String showPersonalControlList2(Map<String, Object> model) {
//		List<PersonalControl> pControl = new ArrayList<>();
//		this.pControlService.findPersonalControl().forEach(x->pControl.add(x));
//		model.put("pControl", pControl);
//		return "controladores/personalControlList";

	@GetMapping(value = "/controladores/find")
	public String initFindPersonalControlForm(Map<String, Object> model) {
		model.put("pControl", new PersonalControl());
		return "controladores/findPersonalControl";
	}

	@GetMapping(value = "/controladores")
	public String processFindPersonalControlForm(PersonalControl pControl, BindingResult result, Map<String, Object> model) {

		if (pControl.getNif() == null) {
			pControl.setNif(""); 
		}

		PersonalControl resultado = this.pControlService.findPersonalControlByNif(pControl.getNif());

		if (resultado == null) {
			result.rejectValue("nif", "notFound", "nif no encontrado");
			return "controladores/findPersonalControl";
		} else {
			return "redirect:/controladores/" + resultado.getId();
		}

	}

	/*
	 * Vistas de consulta de controladores
	 */
	
	@GetMapping(value = "/controladoresList")
	public String showPersonalControlList(Map<String, Object> model) {
		List<PersonalControl> controladores = new ArrayList<PersonalControl>();
		controladores.addAll(this.pControlService.findPersonalControl());
		model.put("personalControl", controladores);
		
		return "controladores/personalControlList";
	}
	
	
	@GetMapping("/controladores/{pControlId}")
	public ModelAndView showControlador(@PathVariable("pControlId") int pControlId) {
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
		return "redirect:/personalControlList";
	}
	
	//Horario del controlador
	
	@RequestMapping(value = { "/controladores/{pControlId}/horario" }, method = RequestMethod.GET)
	public String showVuelosList(Map<String, Object> model, @PathVariable("pControlId") int pControlId,  
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
		model.put("diasV", diasV);
		return "controladores/horario";
	}
	
	
	//consulta de vuelos para conocer ruta (H9) ??
	
	@RequestMapping(value = { "/controladores/{pControlId}/" }, method = RequestMethod.GET)
	public String consultaVuelosList(Map<String, Object> model, @PathVariable("pControlId") int pControlId,  @RequestParam(name = "aeropuertoOrigen", defaultValue = "") String aeropuertoOrigen) {

		if(aeropuertoOrigen.isEmpty()) {
			model.put("vuelos", this.pControlService.rutaVuelos(pControlId));
		}else {
			Aeropuerto aeropuertoDestino; 
			model.put("vuelos", this.pControlService.rutaVuelos(pControlId));
		}
		return "controladores/rutaVuelos";
	}
	
	// Historia usuario 1:
	@RequestMapping(value = { "/controladores/{pControlId}/semana" }, method = RequestMethod.GET)
	public String showHorarioSemanalPersonalControl(Map<String, Object> model, @PathVariable("pControlId") int pControlId,  @RequestParam(name = "fecha", defaultValue = "") String fecha) {

		if(fecha.isEmpty()) {
			model.put("vuelos", this.pControlService.horarioSemanalSinFecha(pControlId));
			List<String> date = this.pControlService.sacaFecha();
			model.put("diaS", date.get(0));
			model.put("diaM", date.get(1));
			model.put("mes", date.get(2));
			model.put("anyo", date.get(3));
		}else {
			LocalDate date = LocalDate.parse(fecha, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			DayOfWeek diaSemana = date.getDayOfWeek();
			int dia = date.getDayOfYear();
			int anyo = date.getYear();
			model.put("vuelos", this.pControlService.horarioSemanalConFecha(pControlId, diaSemana, dia, anyo));
			String diaS = diaSemana.toString();
			model.put("diaS", diaS);
			String diaM = String.valueOf(date.getDayOfMonth());
			model.put("diaM", diaM);
			String mes = String.valueOf(date.getMonthValue());
			model.put("mes", mes);
			String anyoS = String.valueOf(date.getYear());
			model.put("anyo", anyoS);
		}
		return "controladores/horarioSemanal";
	}
	
}
