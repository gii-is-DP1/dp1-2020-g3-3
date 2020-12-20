package org.springframework.samples.aerolineasAAAFC.web;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.aerolineasAAAFC.model.Aeropuerto;
import org.springframework.samples.aerolineasAAAFC.model.Avion;
import org.springframework.samples.aerolineasAAAFC.model.Billete;
import org.springframework.samples.aerolineasAAAFC.model.Clase;
import org.springframework.samples.aerolineasAAAFC.model.Vuelo;
import org.springframework.samples.aerolineasAAAFC.service.AeropuertoService;
import org.springframework.samples.aerolineasAAAFC.service.AvionService;
import org.springframework.samples.aerolineasAAAFC.service.BilleteService;
import org.springframework.samples.aerolineasAAAFC.service.PersonalOficinaService;
import org.springframework.samples.aerolineasAAAFC.service.VueloService;
import org.springframework.samples.aerolineasAAAFC.service.exceptions.HorasImposiblesException;
import org.springframework.samples.aerolineasAAAFC.service.exceptions.TooManyItemsBilleteException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
public class VueloController {
	private static final String VIEWS_VUELO_CREATE_OR_UPDATE_FORM = "vuelos/createOrUpdateVueloForm";
	
	private final VueloService vueloService;
	private final AeropuertoService aeropuertoService;
	private final AvionService avionService;
	private final BilleteService billeteService;
	private final PersonalOficinaService personalOService;
	
	
	@Autowired
	public VueloController(VueloService vueloService,AeropuertoService aeropuertoService,
			AvionService avionService,BilleteService billeteService,PersonalOficinaService personalOService) {
		this.vueloService = vueloService;
		this.aeropuertoService=aeropuertoService;
		this.avionService= avionService;
		this.billeteService=billeteService;
		this.personalOService=personalOService;
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
		
		Integer billetes=0;
		model.put("billetes", billetes);
		
		return VIEWS_VUELO_CREATE_OR_UPDATE_FORM;
	}
	
	@PostMapping(value = "/vuelos/new")
	public String processCreationVueloForm(@Valid Vuelo vuelo, BindingResult result) {
		if(result.hasErrors()) {
			return VIEWS_VUELO_CREATE_OR_UPDATE_FORM;
		}
		else {
			try {
				String asiento="A";
				for(int i=0;i<vuelo.getBilletes().size();i++) 
				{
					Billete billete=new Billete();
					billete.setAsiento(asiento+String.valueOf(i));
					billete.setFechaReserva(LocalDate.now());
					billete.setClase(Clase.ECONOMICA);
					try {
						this.billeteService.saveBillete(billete);
					} catch (DataAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (TooManyItemsBilleteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				this.vueloService.saveVuelo(vuelo);
			} catch (HorasImposiblesException e) {
				result.rejectValue("horaLlegada", "invalid", "La hora de llegada debe ser posterior a la de salida");
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
	public String initUpdateVueloForm(@PathVariable("vueloId") int vueloId, Model model) {
		
		List<Aeropuerto> aeropuertos = new ArrayList<>();
		this.aeropuertoService.findAeropuertos().forEach(x->aeropuertos.add(x));
		model.addAttribute("aeropuertos", aeropuertos);
		
		
		Vuelo vuelo = this.vueloService.findVueloById(vueloId);
		model.addAttribute("vuelo",vuelo);
		
		List<Avion> aviones = new ArrayList<>();
		this.avionService.findAviones().forEach(x->aviones.add(x));
		model.addAttribute("aviones",aviones);
		
		Integer billetes=0;
		this.vueloService.findVueloById(vuelo.getId()).getBilletes().size();
		model.addAttribute("billetes", billetes);
		
		return VIEWS_VUELO_CREATE_OR_UPDATE_FORM;
	}
	
	@PostMapping(value = "/vuelos/{vueloId}/edit")
	public String processUpdateVueloForm(@Valid Vuelo vuelo, BindingResult result, 
			@PathVariable("vueloId") int vueloId) {
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
			}catch (ConstraintViolationException e) {
				result.rejectValue("aeropuerto", "invalid", "El aeropuerto de salida y destino deben ser distintos");
				return VIEWS_VUELO_CREATE_OR_UPDATE_FORM;
				
			}
			
			return "redirect:/vuelos";
		}
	}
	
	@GetMapping(value = { "/vuelos" })
	public String showVuelosList(Map<String, Object> model) {
		List<Vuelo> vuelos = new ArrayList<>();
		this.vueloService.findVuelos().forEach(x->vuelos.add(x));
		model.put("vuelos", vuelos);
		return "vuelos/vuelosList";
	}
	
	
	
	
	
	
	
}
