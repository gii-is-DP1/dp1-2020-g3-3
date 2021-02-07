package org.springframework.samples.aerolineasAAAFC.web;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.samples.aerolineasAAAFC.model.Aeropuerto;
import org.springframework.samples.aerolineasAAAFC.model.Cliente;
import org.springframework.samples.aerolineasAAAFC.model.Vuelo;
import org.springframework.samples.aerolineasAAAFC.service.AeropuertoService;
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
		
		model.put("aeropuertos",this.aeropuertoService.findAeropuertosNoPageable());
		model.put("aviones", this.avionService.findAvionesNoPageable());

		model.put("pOficina", this.pOficinaService.findPersonalNoPageable());
		model.put("pControl", this.pControlService.findPersonalControlNoPageable());
		model.put("azafatos", this.azafatoService.findAzafatosNoPageable());
		
		return VIEWS_VUELO_CREATE_OR_UPDATE_FORM;
	}
	
	@PostMapping(value = "/vuelos/new")
	public String processCreationVueloForm(@Valid Vuelo vuelo, BindingResult result, Map<String, Object> model) {
		
		model.put("aeropuertos",this.aeropuertoService.findAeropuertosNoPageable());
		model.put("aviones", this.avionService.findAvionesNoPageable());

		model.put("pOficina", this.pOficinaService.findPersonalNoPageable());
		model.put("pControl", this.pControlService.findPersonalControlNoPageable());
		model.put("azafatos", this.azafatoService.findAzafatosNoPageable());
		
		if(result.hasErrors()) {
			log.info("vuelo tiene errores");
			return VIEWS_VUELO_CREATE_OR_UPDATE_FORM;
		}else {
			try {
				this.vueloService.saveVuelo(vuelo);
			} catch (HorasImposiblesException e) {
				result.rejectValue("horaLlegada", "invalid", "La hora de llegada debe ser posterior a la de salida");
				return VIEWS_VUELO_CREATE_OR_UPDATE_FORM;
			} catch (HorasMaximasVueloException e) {
				result.rejectValue("horaLlegada", "invalid", "Ningún avión puede superar el límite de 14 horas seguidas en vuelo");
				return VIEWS_VUELO_CREATE_OR_UPDATE_FORM;	
			} catch (DisponibilidadAvionException e) {
				result.rejectValue("avion", "invalid", "El avión no está disponible porque debe pasar una revisión");
				return VIEWS_VUELO_CREATE_OR_UPDATE_FORM;
			} catch (ConstraintViolationException e) {
				result.rejectValue("aeropuerto", "invalid", "El aeropuerto de salida y destino deben ser distintos");
				return VIEWS_VUELO_CREATE_OR_UPDATE_FORM;
			}
			
			return "redirect:/vuelos";
		}
	}
	
	//MODIFICACION
	
	@GetMapping(value = "/vuelos/{vueloId}/edit")
	public String initUpdateVueloForm(@PathVariable("vueloId") int vueloId, ModelMap model) {
		
		model.addAttribute("aeropuertos", this.aeropuertoService.findAeropuertosNoPageable());
		
//		List<Billete> billetes = new ArrayList<>();
//		this.billeteService.findBilletes().forEach(x->aeropuertos.add(x));
//		model.addAttribute("aeropuertos", aeropuertos);
		
		model.addAttribute("vuelo",this.vueloService.findVueloById(vueloId));
		model.addAttribute("aviones",this.avionService.findAvionesNoPageable());
		model.addAttribute("pOficina", this.vueloService.findVueloById(vueloId).getPersonalOficina());
		model.addAttribute("todoPersonal",this.pOficinaService.findPersonalNoPageable());
		model.addAttribute("todoControl", this.pControlService.findPersonalControlNoPageable());
		model.addAttribute("todoAzafato", this.azafatoService.findAzafatosNoPageable());
		
		return VIEWS_VUELO_CREATE_OR_UPDATE_FORM;
	}
	
	@PostMapping(value = "/vuelos/{vueloId}/edit")
	public String processUpdateVueloForm(@Valid Vuelo vuelo, BindingResult result, 
			@PathVariable("vueloId") int vueloId, ModelMap model, @RequestParam(value = "version", required=false) Integer version) {
		Vuelo vueloToUpdate=this.vueloService.findVueloById(vueloId);

		if(vueloToUpdate.getVersion()!=version) {
			model.put("message","Modificación de vuelo ya existente. ¡Prueba de nuevo!");
			return initUpdateVueloForm(vueloId,model);
		}
		
		if(result.hasErrors()) {
			return VIEWS_VUELO_CREATE_OR_UPDATE_FORM;
		}
		else {
			vuelo.incrementVersion();
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
	public String showVuelosList(ModelMap model,  @RequestParam(name = "fecha", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha, 
								@PageableDefault(value=20) Pageable paging) {

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
		
		Page<Vuelo> pages =  this.vueloService.findVuelosByMes(mes, año, paging);
		if(pages.getContent().isEmpty()) {
			model.addAttribute("number",0);
			model.addAttribute("totalPages", 1);
			model.addAttribute("totalElements", 1);
			model.addAttribute("size", 1);
			model.put("vuelos", pages.getContent());
		}else {
			model.addAttribute("number", pages.getNumber());
			model.addAttribute("totalPages", pages.getTotalPages());
			model.addAttribute("totalElements", pages.getTotalElements());
			model.addAttribute("size", pages.getSize());
			model.put("vuelos", pages.getContent());
		}
		
		
		return "vuelos/vuelosList";
	}
	
	
	@RequestMapping(value = { "/vuelos/ofertas" }, method = RequestMethod.GET)
	public String showVuelosOfertadosList(ModelMap model,  @RequestParam(name = "fecha", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha, 
										 @PageableDefault(value=20) Pageable paging) {

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
		
		
		Page<Vuelo> pages = this.vueloService.findVuelosOfertadosByMes(mes, año, paging);
		
		if(pages.getContent().isEmpty()) {
			model.addAttribute("number",0);
			model.addAttribute("totalPages", 1);
			model.addAttribute("totalElements", 1);
			model.addAttribute("size", 1);
			model.put("vuelosOferta", pages.getContent());
		}else {
			model.addAttribute("number", pages.getNumber());
			model.addAttribute("totalPages", pages.getTotalPages());
			model.addAttribute("totalElements", pages.getTotalElements());
			model.addAttribute("size", pages.getSize());
			model.put("vuelosOferta", pages.getContent());
		}
		
		return "vuelos/vuelosOfertadosList";
	}
	
	
	@RequestMapping(value = { "/vuelos/{vueloId}/clientes" }, method = RequestMethod.GET)
	public String showClientes(ModelMap model,@PathVariable("vueloId") int vueloId, @PageableDefault(value=20) Pageable paging) {
		
		Vuelo vuelo = this.vueloService.findVueloById(vueloId);
		Page<Cliente> pages = this.vueloService.findClientesPorVuelo(vuelo,paging);
		if(pages.getContent().isEmpty()) {
			model.addAttribute("number",0);
			model.addAttribute("totalPages", 1);
			model.addAttribute("totalElements", 1);
			model.addAttribute("size", 1);
			model.put("clientes", pages.getContent());
		}else {
			model.addAttribute("number", pages.getNumber());
			model.addAttribute("totalPages", pages.getTotalPages());
			model.addAttribute("totalElements", pages.getTotalElements());
			model.addAttribute("size", pages.getSize());
			model.put("clientes", pages.getContent());
		}
		
		
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
		
		return "vuelos/menusByVuelo";
	}
	
	@GetMapping(value = "/vuelos/historial")
	public String showHistorial(ModelMap model, @PageableDefault(value=20) Pageable paging) {
		Page<Vuelo> pages = this.vueloService.findVuelosOrdered(paging);
		
		if(pages.getContent().isEmpty()) {
			model.addAttribute("number",0);
			model.addAttribute("totalPages", 1);
			model.addAttribute("totalElements", 1);
			model.addAttribute("size", 1);
			model.put("vuelos", pages.getContent());
		}else {
			model.addAttribute("number", pages.getNumber());
			model.addAttribute("totalPages", pages.getTotalPages());
			model.addAttribute("totalElements", pages.getTotalElements());
			model.addAttribute("size", pages.getSize());
			model.put("vuelos", pages.getContent());
		}
		
		return "vuelos/vuelosHistorial";
	}
	
	// Página de vuelos (HOME)
	@RequestMapping(value = { "/" ,"/home" }, method = RequestMethod.GET)
	public String showHome(ModelMap model, @RequestParam(name = "fecha", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
			@RequestParam(name = "precio", required = false) Double precio, @RequestParam(name = "origen", required = false) String origen,
			@RequestParam(name = "destino", required = false) String destino, @PageableDefault(value=20) Pageable paging) {
		
		log.info("fecha: {}, precio: {}, origen: {}, destino: {}", fecha,precio,origen,destino);
		LocalDateTime ahora = LocalDateTime.now();
		LocalDate ahoraA = LocalDate.now();
		model.addAttribute("hoy", ahoraA);
		Page<Vuelo> pages = new PageImpl<Vuelo>(new ArrayList<Vuelo>());
		if((fecha == null && precio == null && origen == null && destino == null) || (fecha.equals(ahoraA) && precio == 9999.0 && origen == "" && destino == "")) {							//sin filtro
			
			log.info("se ha accedido al filtro nulo");
			pages = this.vueloService.findVuelosConFecha(ahora,paging);
					
		}else if(!fecha.equals(ahoraA) && precio == 9999.0 && origen == "" && destino == "") {			//filtro por fecha 1
			
			log.info("se ha accedido al filtro por fecha");
			LocalDateTime horas = LocalDateTime.of(fecha, LocalTime.MIN);
			pages = this.vueloService.findVuelosConFecha(horas,paging);
			
		}else if(fecha.equals(ahoraA) && precio != 9999.0 && origen == "" && destino == "") {			//filtro por precio 2
			
			log.info("se ha accedido al filtro por precio");
			pages = this.vueloService.findVuelosConFechaYPrecio(ahora,precio, paging);
			
		}else if(fecha.equals(ahoraA) && precio == 9999.0 && origen != "" && destino == "") {			//filtro por aeropuerto de salida 3
			
			log.info("se ha accedido al filtro aeropuerto de salida");
			pages = this.vueloService.findVuelosConFechaYOrigen(ahora,origen, paging);
			
		}else if(fecha.equals(ahoraA) && precio == 9999.0 && origen == "" && destino != "") {			//filtro por aeropuerto de destino 4
			
			log.info("se ha accedido al filtro aeropuerto de destino");
			pages = this.vueloService.findVuelosConFechaYDestino(ahora,destino, paging);
			
		}else if(!fecha.equals(ahoraA) && precio != 9999.0 && origen == "" && destino == "") {			//filtro por fecha+precio 1,2
			
			log.info("se ha accedido al filtro fecha+precio");
			LocalDateTime horas = LocalDateTime.of(fecha, LocalTime.MIN);
			pages = this.vueloService.findVuelosConFechaYPrecio(horas, precio, paging);
			
		}else if(!fecha.equals(ahoraA) && precio == 9999.0 && origen != "" && destino == "") {			//filtro por fecha+salida 1,3
			
			log.info("se ha accedido al filtro por fecha+salida");
			LocalDateTime horas = LocalDateTime.of(fecha, LocalTime.MIN);
			pages = this.vueloService.findVuelosConFechaYOrigen(horas, origen, paging);
			
		}else if(!fecha.equals(ahoraA) && precio == 9999.0 && origen == "" && destino != "") {			//filtro por fecha+destino 1,4
			
			log.info("se ha accedido al filtro fecha+destino");
			LocalDateTime horas = LocalDateTime.of(fecha, LocalTime.MIN);
			pages = this.vueloService.findVuelosConFechaYDestino(horas, destino, paging);
			
		}else if(fecha.equals(ahoraA) && precio != 9999.0 && origen != "" && destino == "") {			//filtro por precio+salida 2,3
			
			log.info("se ha accedido al filtro precio+salida");
			pages = this.vueloService.findVuelosConFechaPrecioYOrigen(ahora, precio, origen, paging);
			
		}else if(fecha.equals(ahoraA) && precio != 9999.0 && origen == "" && destino != "") {			//filtro por precio+destino 2,4		
			
			log.info("se ha accedido al filtro precio+destino ");
			pages = this.vueloService.findVuelosConFechaPrecioYDestino(ahora, precio, destino, paging);
			
		}else if(fecha.equals(ahoraA) && precio == 9999.0 && origen != "" && destino != "") {			//filtro por salida+destino 3,4
			
			log.info("se ha accedido al filtro salida+destino");
			pages = this.vueloService.findVuelosConFechaOrigenYDestino(ahora, origen, destino, paging);
			
		}else if(!fecha.equals(ahoraA) && precio != 9999.0 && origen != "" && destino == "") {			//filtro fecha+precio+salida 1,2,3
			
			log.info("se ha accedido al filtro filtro fecha+precio+salida");
			LocalDateTime horas = LocalDateTime.of(fecha, LocalTime.MIN);
			pages = this.vueloService.findVuelosConFechaPrecioYOrigen(horas, precio, origen, paging);
			
		}else if(!fecha.equals(ahoraA) && precio != 9999.0 && origen == "" && destino != "") {			//filtro fecha+precio+destino 1,2,4
			
			log.info("se ha accedido al filtro fecha+precio+destino");
			LocalDateTime horas = LocalDateTime.of(fecha, LocalTime.MIN);
			pages = this.vueloService.findVuelosConFechaPrecioYDestino(horas, precio, destino, paging);
			
		}else if(fecha.equals(ahoraA) && precio == 9999.0 && origen != "" && destino != "") {			//filtro fecha+salida+destino 1,3,4
			
			log.info("se ha accedido al filtro fecha+salida+destino");
			LocalDateTime horas = LocalDateTime.of(fecha, LocalTime.MIN);
			pages = this.vueloService.findVuelosConFechaOrigenYDestino(horas, origen, destino, paging);
			
		}else if(fecha.equals(ahoraA) && precio != 9999.0 && origen != "" && destino != "") {			//filtro precio+salida+destino 2,3,4
			
			log.info("se ha accedido al filtro precio+salida+destino");
			pages = this.vueloService.findVuelosConTodo(ahora, precio, origen, destino, paging);
			
		}else{																							//filtro total 1,2,3,4
			
			log.info("se ha accedido al filtro total");
			LocalDateTime horas = LocalDateTime.of(fecha, LocalTime.MIN);
			pages = this.vueloService.findVuelosConTodo(horas, precio, origen, destino, paging);
			
		}
		
		Collection<Aeropuerto> aeropuertos = this.aeropuertoService.findAeropuertosNoPageable();
		List<String> codigos = new ArrayList<String>();
		for(Aeropuerto a : aeropuertos) {
			codigos.add(a.getCodigoIATA());
		}
		model.addAttribute("codigos", codigos);
		
		if(pages.getContent().isEmpty()) {
			model.addAttribute("number",0);
			model.addAttribute("totalPages", 1);
			model.addAttribute("totalElements", 1);
			model.addAttribute("size", 1);
			model.put("vuelos", pages.getContent());
		}else {
			model.addAttribute("number", pages.getNumber());
			model.addAttribute("totalPages", pages.getTotalPages());
			model.addAttribute("totalElements", pages.getTotalElements());
			model.addAttribute("size", pages.getSize());
			model.put("vuelos", pages.getContent());
		}
		
		return "home";
	}

	
}
