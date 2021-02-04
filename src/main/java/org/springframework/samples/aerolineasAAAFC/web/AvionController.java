package org.springframework.samples.aerolineasAAAFC.web;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.aerolineasAAAFC.model.Aeropuerto;
import org.springframework.samples.aerolineasAAAFC.model.Avion;
import org.springframework.samples.aerolineasAAAFC.model.Azafato;
import org.springframework.samples.aerolineasAAAFC.model.Vuelo;
import org.springframework.samples.aerolineasAAAFC.service.AvionService;
import org.springframework.samples.aerolineasAAAFC.service.VueloService;
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

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

@Controller
public class AvionController {

	private static final String VIEWS_AVION_CREATE_OR_UPDATE_FORM = "aviones/createOrUpdateAvionForm";

	private final AvionService avionService;

	private final VueloService vueloService;

	@Autowired
	public AvionController(AvionService avionService, VueloService vueloService) {
		this.avionService = avionService;
		this.vueloService = vueloService;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	/*
	 * ALTA DE UN NUEVO AVIÓN
	 */
	@GetMapping(value = "/aviones/new")
	public String initCreationAvionForm(Map<String, Object> model) {
		Avion avion = new Avion();
		model.put("avion", avion);
		return VIEWS_AVION_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/aviones/new")
	public String processCreationAvionForm(@Valid Avion avion, BindingResult result) {
		if (result.hasErrors()) {
			return VIEWS_AVION_CREATE_OR_UPDATE_FORM;
		} else {
			this.avionService.saveAvion(avion);

			return "redirect:/aviones/" + avion.getId();
		}
	}

	/*
	 * MODIFICACIÓN DE UN AVIÓN
	 */
	@GetMapping(value = "/aviones/{avionId}/edit")
	public String initUpdateAvionForm(@PathVariable("avionId") int avionId, ModelMap model) {
		Avion avion = this.avionService.findAvionById(avionId);
		model.addAttribute(avion);

		return VIEWS_AVION_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/aviones/{avionId}/edit")
	public String processUpdateAvionForm(@Valid Avion avion, BindingResult result, @PathVariable("avionId") int avionId,
			ModelMap model, @RequestParam(value = "version", required = false) Integer version) {

		if (result.hasErrors()) {
			return VIEWS_AVION_CREATE_OR_UPDATE_FORM;
		} else {
			Avion avionBD = this.avionService.findAvionById(avionId);
			if (avionBD.getVersion() != version) {
				model.put("message", "Concurrent modification of avion! Try again!");
				return VIEWS_AVION_CREATE_OR_UPDATE_FORM;
			}
			avion.setId(avionId);
			avion.incrementVersion();
			this.avionService.saveAvion(avion);

			return "redirect:/aviones/{avionId}";
		}
	}

	@GetMapping(value = "/aviones")
	public String showAvionesList(Map<String, Object> model) {
		List<Avion> aviones = new ArrayList<Avion>();
		aviones.addAll(this.avionService.findAviones());
		model.put("aviones", aviones);

		return "aviones/avionesList";
	}

	@GetMapping("/aviones/{avionId}")
	public ModelAndView showAvion(@PathVariable("avionId") int avionId) {
		ModelAndView mav = new ModelAndView("aviones/avionDetails");
		mav.addObject(this.avionService.findAvionById(avionId));
		return mav;
	}

	// estadoAviones (H4)

				//	@RequestMapping(value = { "/controladores/{pControlId}/estadoAviones" }, method = RequestMethod.GET)
				//	public String showVuelosList(Map<String, Object> model, @PathVariable("pControlId") int pControlId,
				//			@RequestParam(name = "fecha", defaultValue = "") String fecha) {
				//
				//		if (fecha.isEmpty()) {
				//			model.put("vuelos", this.avionService.estadoAviones(pControlId));
				//		} else {
				//			fecha += "-01";
				//			LocalDate date = LocalDate.parse(fecha, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
				//			int mes = date.getMonthValue();
				//			int año = date.getYear();
				//			Boolean disponibilidad; // ??
				//			Integer horasAcumuladas; // ??
				//			LocalDate fechaFabricacion; // ??
				//			model.put("vuelos", this.avionService.findAviones());
				//		}
				//		return "controladores/estadoAviones";
				//	}

	@GetMapping(value = "/controladores/rutaAviones")
	public String showAvionesListPersonal(Map<String, Object> model) {
		
		Aeropuerto aeropuerto = null; //Inicializa aeropuerto
		List<Avion> aviones = this.avionService.findAviones(); //Almacena todos los aviones de la compañía
		Collection<Vuelo> todosVuelos = this.vueloService.findVuelos(); //Almacena todos los vuelos de la compañía
		List<Aeropuerto> aeropuertosDestino = new ArrayList<Aeropuerto>(); //Inicializa aeropuertosDestino
		
		//Recorre los vuelos y recoge su avión, si dicho avión es de la compañía establece como fechaFinal la fecha de llegada
		for (Vuelo v : todosVuelos) {
			LocalDateTime fechaFinal = LocalDateTime.of(1900, Month.JULY, 29, 19, 30, 40);
			Avion avion = v.getAvion();
			
			for (int i = 0; i <= aviones.size()-1; i++) {
				if (avion.equals(aviones.get(i))) {

					LocalDateTime fechaLlegada = v.getFechaLlegada();
					if (fechaLlegada.isAfter(fechaFinal)) {
						fechaFinal = fechaLlegada;
					}
				}
			}

			//aeropuerto = this.vueloService.findVueloByFechaLLegada(fechaFinal).; //Coge el aeropuerto
			aeropuertosDestino.add(aeropuerto); //lo añade a la lista
		}

		model.put("aviones", aviones);
		model.put("aeropuertosDestino", aeropuertosDestino);

		return "controladores/rutaAviones";
	}
	
	

}
