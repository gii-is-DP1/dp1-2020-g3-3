package org.springframework.samples.aerolineasAAAFC.web;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.samples.aerolineasAAAFC.model.Aeropuerto;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.samples.aerolineasAAAFC.model.Azafato;
import org.springframework.samples.aerolineasAAAFC.model.IdiomaType;
import org.springframework.samples.aerolineasAAAFC.model.Vuelo;
import org.springframework.samples.aerolineasAAAFC.service.AzafatoService;
import org.springframework.samples.aerolineasAAAFC.service.exceptions.IbanDuplicadoException;
import org.springframework.samples.aerolineasAAAFC.service.exceptions.IdiomasNoSuficientesException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AzafatoController {

	private static final String VIEWS_AZAFATO_CREATE_OR_UPDATE_FORM = "azafatos/createOrUpdateAzafatoForm";
	
	private final AzafatoService azafatoService;
	
	@Autowired
	public AzafatoController(AzafatoService azafatoService) {
		this.azafatoService = azafatoService;
	}
	
	@ModelAttribute("idioma_types")
	public Collection<IdiomaType> populateIdiomaTypes() {
		return this.azafatoService.findIdiomaTypes();
	}
	
	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	/*
	 * Alta de un nuevo azafato
	 */
	@GetMapping(value = "/azafatos/new")
	public String initCreationAzafatoForm(Map<String, Object> model) {
		Azafato azafato = new Azafato();
		model.put("azafato", azafato);
		return VIEWS_AZAFATO_CREATE_OR_UPDATE_FORM;
	}
	
	@PostMapping(value = "/azafatos/new")
	public String processCreationAzafatoForm(@Valid Azafato azafato, BindingResult result) {
		
		if(result.hasErrors()) {
			return VIEWS_AZAFATO_CREATE_OR_UPDATE_FORM;
		}
		else {
			try {
				this.azafatoService.saveAzafato(azafato);
			} catch (DataIntegrityViolationException e) {
				result.rejectValue("nif", "duplicate", "already exists");
				return VIEWS_AZAFATO_CREATE_OR_UPDATE_FORM;
			} catch (IdiomasNoSuficientesException e) {
				result.rejectValue("idiomas", "not enough", "not enough languages");
				return VIEWS_AZAFATO_CREATE_OR_UPDATE_FORM;
			}
			
			return "redirect:/azafatos/" + azafato.getId();
		}
		
	}
	
	/*
	 *  Update sobre un azafato
	 */
	@GetMapping(value = "/azafatos/{azafatoId}/edit")
	public String initUpdateAzafatoForm(@PathVariable("azafatoId") int azafatoId, ModelMap model) {
		
		Azafato azafato = this.azafatoService.findAzafatoById(azafatoId);
		model.addAttribute(azafato);
		return VIEWS_AZAFATO_CREATE_OR_UPDATE_FORM;
	}
	
	@PostMapping(value = "/azafatos/{azafatoId}/edit")
	public String processUpdateAzafatoForm(@Valid Azafato azafato, BindingResult result, @PathVariable("azafatoId") int azafatoId,
											ModelMap model, @RequestParam(value = "version", required=false) Integer version) {
		
		Azafato azToUpdate = this.azafatoService.findAzafatoById(azafatoId);

//		if(azToUpdate.getVersion() != version) {
//			model.put("message","Concurrent modification of azafato! Try again!");
//			return initUpdateAzafatoForm(azafatoId,model);
//			} 
		
		
		if(result.hasErrors()) {
			return VIEWS_AZAFATO_CREATE_OR_UPDATE_FORM;
		}
		else {
			azafato.setId(azafatoId);
			Azafato azafatoToUpdate = this.azafatoService.findAzafatoById(azafatoId);
			BeanUtils.copyProperties(azafato, azafatoToUpdate, "id","nif","username");  
			try {
				this.azafatoService.saveAzafato(azafato);
			} catch (DataIntegrityViolationException e) {
				if(e.getMessage().contains("PUBLIC.AZAFATO(IBAN)")) {
					result.rejectValue("iban", "duplicate", "already exists");
				}else{
					result.rejectValue("nif", "duplicate", "already exists");
				}
				return VIEWS_AZAFATO_CREATE_OR_UPDATE_FORM;
			} catch (IdiomasNoSuficientesException e) {
				result.rejectValue("idiomas", "not enough", "not enough languages");
				return VIEWS_AZAFATO_CREATE_OR_UPDATE_FORM;
			}
			
			return "redirect:/azafatos/{azafatoId}";
		}
		
	}
	
	//ELIMINACIÓN
	@GetMapping(value = "/azafatos/{azafatoId}/delete")
	public String deleteAzafato(@PathVariable("azafatoId") int azafatoId) {
		this.azafatoService.eliminarAzafato(azafatoId);
		return "redirect:/azafatosList";
	}
	
	@GetMapping(value =  "/azafatosList" )
	public String showAzafatosList(Map<String, Object> model) {
		List<Azafato> azafatos = new ArrayList<>();
		this.azafatoService.findAzafatos().forEach(x->azafatos.add(x));
		model.put("azafatos", azafatos);
		return "azafatos/azafatosList";
	}
	
	@GetMapping("/azafatos/{azafatoId}")
	public ModelAndView showAzafato(@PathVariable("azafatoId") int azafatoId) {
		ModelAndView mav = new ModelAndView("azafatos/azafatoDetails");
		mav.addObject(this.azafatoService.findAzafatoById(azafatoId));
		return mav;
	}
	
	/*
	 *  Horario de un azafato
	 */
	
	@RequestMapping(value = { "/azafatos/{azafatoId}/horario" }, method = RequestMethod.GET)
	public String showVuelosList(Map<String, Object> model, @PathVariable("azafatoId") int azafatoId,  
								@RequestParam(name = "fecha", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {


			int mes = 0;
			int año = 0;
			Month mesn = null;
			int dias = 0;
			
			if(fecha == null) {
				fecha = LocalDate.now();
				mes = fecha.getMonthValue();
				año = fecha.getYear();
				mesn = fecha.getMonth();
				dias = fecha.lengthOfMonth();
			}else {
				mes = fecha.getMonthValue();
				año = fecha.getYear();
				mesn = fecha.getMonth();
				dias = fecha.lengthOfMonth();
			}	
			
			Collection<Vuelo> vuelos = this.azafatoService.horario(azafatoId, mes, año);
			
			List<Integer> diasV = new ArrayList<>();
			for(Vuelo v: vuelos) {
				diasV.add(v.getFechaSalida().getDayOfMonth());
			}
			
			model.put("vuelos", vuelos);
			model.put("dias", dias);
			model.put("mes", mesn);
			model.put("año", año);
			model.put("diasV", diasV);

		return "azafatos/horario";
	}
}
