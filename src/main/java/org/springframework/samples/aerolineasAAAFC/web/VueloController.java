package org.springframework.samples.aerolineasAAAFC.web;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.samples.aerolineasAAAFC.model.Aeropuerto;
import org.springframework.samples.aerolineasAAAFC.model.Avion;
import org.springframework.samples.aerolineasAAAFC.model.Azafato;
import org.springframework.samples.aerolineasAAAFC.model.Cliente;
import org.springframework.samples.aerolineasAAAFC.model.PersonalControl;
import org.springframework.samples.aerolineasAAAFC.model.PersonalOficina;
import org.springframework.samples.aerolineasAAAFC.model.Vuelo;
import org.springframework.samples.aerolineasAAAFC.service.AeropuertoService;
import org.springframework.samples.aerolineasAAAFC.service.AuthoritiesService;
import org.springframework.samples.aerolineasAAAFC.service.AvionService;
import org.springframework.samples.aerolineasAAAFC.service.AzafatoService;
import org.springframework.samples.aerolineasAAAFC.service.BilleteService;
import org.springframework.samples.aerolineasAAAFC.service.ClienteService;
import org.springframework.samples.aerolineasAAAFC.service.PersonalControlService;
import org.springframework.samples.aerolineasAAAFC.service.PersonalOficinaService;
import org.springframework.samples.aerolineasAAAFC.service.UserService;
import org.springframework.samples.aerolineasAAAFC.service.VueloService;
import org.springframework.samples.aerolineasAAAFC.service.exceptions.DisponibilidadAvionException;
import org.springframework.samples.aerolineasAAAFC.service.exceptions.HorasImposiblesException;
import org.springframework.samples.aerolineasAAAFC.service.exceptions.HorasMaximasVueloException;
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



@Controller
public class VueloController {
	private static final String VIEWS_VUELO_CREATE_OR_UPDATE_FORM = "vuelos/createOrUpdateVueloForm";
	
	private final VueloService vueloService;
	private final AeropuertoService aeropuertoService;
	private final AvionService avionService;
	private final BilleteService billeteService;
	private final PersonalOficinaService pOficinaService;
	private final PersonalControlService pControlService;
	private final AzafatoService azafatoService;
	
	
	@Autowired
	public VueloController(VueloService vueloService,AeropuertoService aeropuertoService,
			AvionService avionService,BilleteService billeteService,PersonalOficinaService pOficinaService,
			PersonalControlService pControlService, AzafatoService azafatoService) {

		this.vueloService = vueloService;
		this.aeropuertoService = aeropuertoService;
		this.avionService = avionService;
		this.billeteService = billeteService;
		this.pOficinaService = pOficinaService;
		this.pControlService = pControlService;
		this.azafatoService = azafatoService;
	}
	
	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	
//	@ModelAttribute("vuelo")
//	public Vuelo loadVuelo( @PathVariable("vueloId") int vueloId) {
//		Vuelo vuelo=vueloService.findVueloById(vueloId);
//		return vuelo;
//		
//	}
	
	@GetMapping(value = "/vuelos/new") 
	public String initCreationVueloForm(Map<String, Object> model) {
		Vuelo vuelo = new Vuelo();
		model.put("vuelo",vuelo);
		
		List<Aeropuerto> aeropuertos = new ArrayList<>();
		this.aeropuertoService.findAeropuertos().forEach(x->aeropuertos.add(x));
		model.put("aeropuertos",aeropuertos);
		
		List<Avion> aviones = new ArrayList<>();
		this.avionService.findAviones().forEach(x->aviones.add(x));
		model.put("aviones", aviones);
		
		Long billetes=this.vueloService.findVueloById(vuelo.getId()).getAsientos().stream().
		filter(x->!x.getBillete().equals(null)).count();
		model.put("billetes", billetes);
		
		List<PersonalOficina> pOficina = new ArrayList<>();
		this.vueloService.findVueloById(vuelo.getId()).getPersonalOficina().forEach(x->pOficina.add(x));
		model.put("pOficina", pOficina);
		
		List<PersonalControl> pControl = new ArrayList<>();
		this.vueloService.findVueloById(vuelo.getId()).getPersonalControl().forEach(x->pControl.add(x));
		model.put("pControl", pControl);
		
		List<Azafato> azafatos = new ArrayList<>();
		this.vueloService.findVueloById(vuelo.getId()).getAzafatos().forEach(x->azafatos.add(x));
		model.put("azafatos", azafatos);
		
		return VIEWS_VUELO_CREATE_OR_UPDATE_FORM;
	}
	
	@PostMapping(value = "/vuelos/new")
	public String processCreationVueloForm(@Valid Vuelo vuelo, BindingResult result) {
		if(result.hasErrors()) {
			return VIEWS_VUELO_CREATE_OR_UPDATE_FORM;
		}
		else {
			try {
				this.vueloService.saveVuelo(vuelo);
			} catch (HorasImposiblesException e) {
				result.rejectValue("horaLlegada", "invalid", "La hora de llegada debe ser posterior a la de salida");
				return VIEWS_VUELO_CREATE_OR_UPDATE_FORM;
			} 
			catch (HorasMaximasVueloException e) {
				result.rejectValue("horaLlegada", "invalid", "Ningún avión puede superar el límite de 14 horas seguidas en vuelo");
				return VIEWS_VUELO_CREATE_OR_UPDATE_FORM;
				
			}
			catch (DisponibilidadAvionException e) {
				result.rejectValue("avion", "invalid", "El avión no está disponible porque debe pasar una revisión");
				return VIEWS_VUELO_CREATE_OR_UPDATE_FORM;
			}
			catch (ConstraintViolationException e) {
				result.rejectValue("aeropuerto", "invalid", "El aeropuerto de salida y destino deben ser distintos");
				return VIEWS_VUELO_CREATE_OR_UPDATE_FORM;
				
			}
			return "redirect:/vuelos";
		}
	}
	
	//MODIFICACION
	
	@GetMapping(value = "/vuelos/{vueloId}/edit")
	public String initUpdateVueloForm(@PathVariable("vueloId") int vueloId, ModelMap model) {
		
		List<Aeropuerto> aeropuertos = new ArrayList<>();
		this.aeropuertoService.findAeropuertos().forEach(x->aeropuertos.add(x));
		model.addAttribute("aeropuertos", aeropuertos);
		
//		List<Billete> billetes = new ArrayList<>();
//		this.billeteService.findBilletes().forEach(x->aeropuertos.add(x));
//		model.addAttribute("aeropuertos", aeropuertos);
		
		Vuelo vuelo = this.vueloService.findVueloById(vueloId);
		model.addAttribute("vuelo",vuelo);
		
		
		
		List<Avion> aviones = new ArrayList<>();
		this.avionService.findAviones().forEach(x->aviones.add(x));
		model.addAttribute("aviones",aviones);
		
		List<PersonalOficina> pOficina = new ArrayList<>();
		this.vueloService.findVueloById(vuelo.getId()).getPersonalOficina().forEach(x->pOficina.add(x));
		model.addAttribute("pOficina", pOficina);
		
		List<PersonalOficina> todoPersonal = new ArrayList<>();
		this.pOficinaService.findPersonal().forEach(x->todoPersonal.add(x));
		model.addAttribute("todoPersonal",todoPersonal);
		
		List<PersonalControl> pControl = new ArrayList<>();
		this.pControlService.findPersonalControl().forEach(x->pControl.add(x));
		model.addAttribute("todoControl", pControl);
		
		List<Azafato> azafatos = new ArrayList<>();
		this.azafatoService.findAzafatos().forEach(x->azafatos.add(x));
		model.addAttribute("todoAzafato", azafatos);
		
		return VIEWS_VUELO_CREATE_OR_UPDATE_FORM;
	}
	
	@PostMapping(value = "/vuelos/{vueloId}/edit")
	public String processUpdateVueloForm(@Valid Vuelo vuelo, BindingResult result, 
			@PathVariable("vueloId") int vueloId, ModelMap model, @RequestParam(value = "version", required=false) Integer version) {
		Vuelo vueloToUpdate=this.vueloService.findVueloById(vueloId);

		if(vueloToUpdate.getVersion()!=version) {
			model.put("message","Concurrent modification of Vuelo! Try again!");
			return initUpdateVueloForm(vueloId,model);
			}
		if(result.hasErrors()) {
			return VIEWS_VUELO_CREATE_OR_UPDATE_FORM;
		}
		else {
			vuelo.setId(vueloId);
			try {
				this.vueloService.saveVuelo(vuelo);
			} catch (HorasImposiblesException e) {
				result.rejectValue("horaLlegada", "invalid", "La hora de llegada debe ser posterior a la de salida");
				return VIEWS_VUELO_CREATE_OR_UPDATE_FORM;
			} catch (HorasMaximasVueloException e) {
				result.rejectValue("horaLlegada", "invalid", "Ningún avión puede superar el límite de 14 horas seguidas en vuelo");
				return VIEWS_VUELO_CREATE_OR_UPDATE_FORM;
			}catch (DisponibilidadAvionException e) {
					result.rejectValue("avion", "invalid", "El avión no está disponible porque debe pasar una revisión");
					return VIEWS_VUELO_CREATE_OR_UPDATE_FORM;
			}catch (ConstraintViolationException e) {
				result.rejectValue("aeropuerto", "invalid", "El aeropuerto de salida y destino deben ser distintos");
				return VIEWS_VUELO_CREATE_OR_UPDATE_FORM;
				
			}
			
			return "redirect:/vuelos";
		}
	}
	
	@RequestMapping(value = { "/vuelos" }, method = RequestMethod.GET)
	public String showVuelosList(Map<String, Object> model,  @RequestParam(name = "fecha", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {

		int mes = 0;
		int año = 0;
		
		if(fecha == null) {
			fecha = LocalDate.now();
			mes = fecha.getMonthValue();
			año = fecha.getYear();
		}else {
			mes = fecha.getMonthValue();
			año = fecha.getYear();	
		}
		
		model.put("vuelos", this.vueloService.findVuelosByMes(mes, año));
		
		return "vuelos/vuelosList";
	}
	
	
	@RequestMapping(value = { "/vuelos/ofertas" }, method = RequestMethod.GET)
	public String showVuelosOfertadosList(Map<String, Object> model,  @RequestParam(name = "fecha", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {

		int mes = 0;
		int año = 0;
		
		if(fecha == null) {
			fecha = LocalDate.now();
			mes = fecha.getMonthValue();
			año = fecha.getYear();
		}else {
			mes = fecha.getMonthValue();
			año = fecha.getYear();	
		}			
		
		model.put("vuelosOferta", this.vueloService.findVuelosOfertadosByMes(mes, año));
		
		return "vuelos/vuelosOfertadosList";
	}
	
	
	@RequestMapping(value = { "/vuelos/{vueloId}/clientes" }, method = RequestMethod.GET)
	public String showClientes(Map<String, Object> model,@PathVariable("vueloId") int vueloId) {
		
		Vuelo vuelo=this.vueloService.findVueloById(vueloId);
		model.put("clientes",this.vueloService.findClientesPorVuelo(vuelo));
		
		return "vuelos/clientesList";
	}
	
	
	@GetMapping("/vuelos/{vueloId}")
	public ModelAndView showVuelo(@PathVariable("vueloId") int vueloId) {
		ModelAndView mav = new ModelAndView("vuelos/vueloDetails");
		mav.addObject(this.vueloService.findVueloById(vueloId));
		return mav;
	}

	
}
