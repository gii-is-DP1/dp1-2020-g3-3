package org.springframework.samples.aerolineasAAAFC.web;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.samples.aerolineasAAAFC.model.Aeropuerto;
import org.springframework.samples.aerolineasAAAFC.model.Vuelo;
import org.springframework.samples.aerolineasAAAFC.service.AeropuertoService;
import org.springframework.samples.aerolineasAAAFC.service.AsientoService;
import org.springframework.samples.aerolineasAAAFC.service.AvionService;
import org.springframework.samples.aerolineasAAAFC.service.AzafatoService;
import org.springframework.samples.aerolineasAAAFC.service.BilleteService;
import org.springframework.samples.aerolineasAAAFC.service.PersonalControlService;
import org.springframework.samples.aerolineasAAAFC.service.PersonalOficinaService;
import org.springframework.samples.aerolineasAAAFC.service.VueloService;
import org.springframework.samples.aerolineasAAAFC.service.exceptions.DisponibilidadAvionException;
import org.springframework.samples.aerolineasAAAFC.service.exceptions.HorasImposiblesException;
import org.springframework.samples.aerolineasAAAFC.service.exceptions.HorasMaximasVueloException;
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
public class VueloController {
	private static final String VIEWS_VUELO_CREATE_OR_UPDATE_FORM = "vuelos/createOrUpdateVueloForm";
	
	private final VueloService vueloService;
	private final AeropuertoService aeropuertoService;
	private final AvionService avionService;
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
		this.pOficinaService = pOficinaService;
		this.pControlService = pControlService;
		this.azafatoService = azafatoService;
	}
	
	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	@GetMapping(value = "/vuelos/new") 
	public String initCreationVueloForm(Map<String, Object> model) {
		Vuelo vuelo = new Vuelo();
		model.put("vuelo",vuelo);
		
		model.put("aeropuertos",this.aeropuertoService.findAeropuertos());
		model.put("aviones", this.avionService.findAviones());

		model.put("pOficina", this.pOficinaService.findPersonal());
		model.put("pControl", this.pControlService.findPersonalControl());
		model.put("azafatos", this.azafatoService.findAzafatos());
		
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
		
		model.addAttribute("aeropuertos", this.aeropuertoService.findAeropuertos());
		
//		List<Billete> billetes = new ArrayList<>();
//		this.billeteService.findBilletes().forEach(x->aeropuertos.add(x));
//		model.addAttribute("aeropuertos", aeropuertos);
		
		model.addAttribute("vuelo",this.vueloService.findVueloById(vueloId));
		model.addAttribute("aviones",this.avionService.findAviones());
		model.addAttribute("pOficina", this.vueloService.findVueloById(vueloId).getPersonalOficina());
		model.addAttribute("todoPersonal",this.pOficinaService.findPersonal());
		model.addAttribute("todoControl", this.pControlService.findPersonalControl());
		model.addAttribute("todoAzafato", this.azafatoService.findAzafatos());
		
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
	
	@GetMapping(value = "/vuelos/{vueloId}/showMenusByVuelo")
	public String showMenusByVuelo(Map<String, Object> model, @PathVariable("vueloId") int vueloId) {
		Vuelo vuelo = this.vueloService.findVueloById(vueloId);
		Map<String, Long> numeroPlatosInVuelo = this.vueloService.findMenusPorVuelo(vuelo);
		Integer numMenus = this.vueloService.countMenusInVuelo(numeroPlatosInVuelo);
		model.put("vuelo", vuelo);
		model.put("numMenus", numMenus);
		model.put("numeroPlatosInVuelo", numeroPlatosInVuelo);
		
		return "vuelos/URLAMIJSP";
	}
	
	@GetMapping(value = "/vuelos/historial")
	public String showPersonalOficinaList(Map<String, Object> model) {
		List<Vuelo> vuelos = new ArrayList<Vuelo>();
		vuelos.addAll(this.vueloService.findVuelosOrdered());
		model.put("vuelos", vuelos);
		
		return "vuelos/vuelosHistorial";
	}
	
	// Página de vuelos (HOME)
	@RequestMapping(value = { "/" ,"/home" }, method = RequestMethod.GET)
	public String showHome(Map<String,Object> model, @RequestParam(name = "fecha", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
			@RequestParam(name = "precio", required = false) Double precio, @RequestParam(name = "origen", required = false) String origen,
			@RequestParam(name = "destino", required = false) String destino) {
		log.info("fecha: {}, precio: {}, origen: {}, destino: {}", fecha,precio,origen,destino);
		LocalDateTime ahora = LocalDateTime.now();
		LocalDate ahoraA = LocalDate.now();
		model.put("hoy", ahoraA);
		if((fecha == null && precio == null && origen == null && destino == null) || (fecha.equals(ahoraA) && precio == 9999.0 && origen == "" && destino == "")) {							//sin filtro
			log.info("soy el filtro nulo");
			model.put("vuelos",this.vueloService.findVuelosConFecha(ahora));
		}else if(!fecha.equals(ahoraA) && precio == 9999.0 && origen == "" && destino == "") {			//filtro por fecha 1
			log.info("soy el filtro por fecha");
			LocalDateTime horas = LocalDateTime.of(fecha, LocalTime.MIN);
			model.put("vuelos",this.vueloService.findVuelosConFecha(horas));
		}else if(fecha.equals(ahoraA) && precio != 9999.0 && origen == "" && destino == "") {			//filtro por precio 2
			log.info("soy el filtro por precio");
			model.put("vuelos",this.vueloService.findVuelosConFechaYPrecio(ahora,precio));
		}else if(fecha.equals(ahoraA) && precio == 9999.0 && origen != "" && destino == "") {			//filtro por aeropuerto de salida 3
			log.info("soy el filtro aeropuerto de salida");
			model.put("vuelos",this.vueloService.findVuelosConFechaYOrigen(ahora,origen));
		}else if(fecha.equals(ahoraA) && precio == 9999.0 && origen == "" && destino != "") {			//filtro por aeropuerto de destino 4
			log.info("soy el filtro aeropuerto de destino");
			model.put("vuelos",this.vueloService.findVuelosConFechaYDestino(ahora,destino));
		}else if(!fecha.equals(ahoraA) && precio != 9999.0 && origen == "" && destino == "") {			//filtro por fecha+precio 1,2
			log.info("soy el filtro fecha+precio");
			LocalDateTime horas = LocalDateTime.of(fecha, LocalTime.MIN);
			model.put("vuelos",this.vueloService.findVuelosConFechaYPrecio(horas, precio));
		}else if(!fecha.equals(ahoraA) && precio == 9999.0 && origen != "" && destino == "") {			//filtro por fecha+salida 1,3
			log.info("soy el filtro por fecha+salida");
			LocalDateTime horas = LocalDateTime.of(fecha, LocalTime.MIN);
			model.put("vuelos",this.vueloService.findVuelosConFechaYOrigen(horas, origen));
		}else if(!fecha.equals(ahoraA) && precio == 9999.0 && origen == "" && destino != "") {			//filtro por fecha+destino 1,4
			log.info("soy el filtro fecha+destino");
			LocalDateTime horas = LocalDateTime.of(fecha, LocalTime.MIN);
			model.put("vuelos",this.vueloService.findVuelosConFechaYDestino(horas, destino));
		}else if(fecha.equals(ahoraA) && precio != 9999.0 && origen != "" && destino == "") {			//filtro por precio+salida 2,3
			log.info("soy el filtro precio+salida");
			model.put("vuelos",this.vueloService.findVuelosConFechaPrecioYOrigen(ahora, precio, origen));
		}else if(fecha.equals(ahoraA) && precio != 9999.0 && origen == "" && destino != "") {			//filtro por precio+destino 2,4		
			log.info("soy el filtro precio+destino ");
			model.put("vuelos",this.vueloService.findVuelosConFechaPrecioYDestino(ahora, precio, destino));
		}else if(fecha.equals(ahoraA) && precio == 9999.0 && origen != "" && destino != "") {			//filtro por salida+destino 3,4
			log.info("soy el filtro salida+destino");
			model.put("vuelos",this.vueloService.findVuelosConFechaOrigenYDestino(ahora, origen, destino));
		}else if(!fecha.equals(ahoraA) && precio != 9999.0 && origen != "" && destino == "") {			//filtro fecha+precio+salida 1,2,3
			log.info("soy el filtro filtro fecha+precio+salida");
			LocalDateTime horas = LocalDateTime.of(fecha, LocalTime.MIN);
			model.put("vuelos",this.vueloService.findVuelosConFechaPrecioYOrigen(horas, precio, origen));
		}else if(!fecha.equals(ahoraA) && precio != 9999.0 && origen == "" && destino != "") {			//filtro fecha+precio+destino 1,2,4
			log.info("soy el filtro fecha+precio+destino");
			LocalDateTime horas = LocalDateTime.of(fecha, LocalTime.MIN);
			model.put("vuelos",this.vueloService.findVuelosConFechaPrecioYDestino(horas, precio, destino));
		}else if(fecha.equals(ahoraA) && precio == 9999.0 && origen != "" && destino != "") {			//filtro fecha+salida+destino 1,3,4
			log.info("soy el filtro fecha+salida+destino");
			LocalDateTime horas = LocalDateTime.of(fecha, LocalTime.MIN);
			model.put("vuelos",this.vueloService.findVuelosConFechaOrigenYDestino(horas, origen, destino));
		}else if(fecha.equals(ahoraA) && precio != 9999.0 && origen != "" && destino != "") {			//filtro precio+salida+destino 2,3,4
			log.info("soy el filtro precio+salida+destino");
			model.put("vuelos",this.vueloService.findVuelosConTodo(ahora, precio, origen, destino));
		}else{																							//filtro total 1,2,3,4
			log.info("soy el filtro total");
			LocalDateTime horas = LocalDateTime.of(fecha, LocalTime.MIN);
			model.put("vuelos",this.vueloService.findVuelosConTodo(horas, precio, origen, destino));
		}
		
		Collection<Aeropuerto> aeropuertos = this.aeropuertoService.findAeropuertos();
		List<String> codigos = new ArrayList<String>();
		for(Aeropuerto a : aeropuertos) {
			codigos.add(a.getCodigoIATA());
		}
		model.put("codigos", codigos);
		
		return "home";
	}

	
}
