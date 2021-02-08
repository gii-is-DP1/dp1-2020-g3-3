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
import org.springframework.samples.aerolineasAAAFC.model.Azafato;
import org.springframework.samples.aerolineasAAAFC.model.Cliente;
import org.springframework.samples.aerolineasAAAFC.model.IdiomaType;
import org.springframework.samples.aerolineasAAAFC.model.PersonalOficina;
import org.springframework.samples.aerolineasAAAFC.model.Vuelo;
import org.springframework.samples.aerolineasAAAFC.service.AzafatoService;
import org.springframework.samples.aerolineasAAAFC.service.exceptions.IdiomasNoSuficientesException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
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
	public String initUpdateAzafatoForm(@PathVariable("azafatoId") int azafatoId, ModelMap model, Authentication authentication) {
		//Evita que otros azafatos editen perfiles de otros
		String usuario = authentication.getName();
		Collection<? extends GrantedAuthority> autoridad  = authentication.getAuthorities();
		String rol = "";
		for(GrantedAuthority auth: autoridad) {
			rol = auth.getAuthority();
		}
		if(rol.equals("azafato")) {
			Azafato azaC = this.azafatoService.findAzafatoByNif(usuario);
			int azaCID = azaC.getId();
			if(azafatoId != azaCID) {
				return "redirect:/azafatos/"+azaCID;
			}
		}
		Azafato azafato = this.azafatoService.findAzafatoById(azafatoId);
		model.addAttribute(azafato);
		return VIEWS_AZAFATO_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/azafatos/{azafatoId}/edit")
	public String processUpdateAzafatoForm(@Valid Azafato azafato, BindingResult result,
			@PathVariable("azafatoId") int azafatoId,ModelMap model, @RequestParam(value = "version", required=false) Integer version, Authentication authentication) {

		Azafato azToUpdate = this.azafatoService.findAzafatoById(azafatoId);

		if(azToUpdate.getVersion() != version) {
			model.put("message","Modificación de azafato ya existente. ¡Prueba de nuevo!");
			return initUpdateAzafatoForm(azafatoId,model,authentication);
		} 

		Azafato azafatoToUpdate = this.azafatoService.findAzafatoById(azafatoId);
		BeanUtils.copyProperties(azafato, azafatoToUpdate, "id","nif","user.username");  
		if(result.hasErrors()) {
			return VIEWS_AZAFATO_CREATE_OR_UPDATE_FORM;
		}
		else {
			azafato.setId(azafatoId);
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


	//CONSULTA
	@GetMapping(value =  "/azafatos" )
	public String showAzafatosList(ModelMap model , @PageableDefault(value=20) Pageable paging) {
		Page<Azafato> pages = azafatoService.findAzafatos(paging);
		if(pages.getContent().isEmpty()) {
			model.addAttribute("number",0);
			model.addAttribute("totalPages", 1);
			model.addAttribute("totalElements", 1);
			model.addAttribute("size", 1);
			model.put("azafatos", pages.getContent());
		}else {
			model.addAttribute("number", pages.getNumber());
			model.addAttribute("totalPages", pages.getTotalPages());
			model.addAttribute("totalElements", pages.getTotalElements());
			model.addAttribute("size", pages.getSize());
			model.put("azafatos", pages.getContent());
		}
		return "azafatos/azafatosList";
	}

	@GetMapping(value = "/azafatosfind")
	public String processFindAzafatoForm(Azafato azafato,Map<String, Object> model, BindingResult result, @RequestParam(value = "nif", required=false) String nif) {

		Azafato resultado = this.azafatoService.findAzafatoByNif(nif);

		if (resultado == null) {
			model.put("number", 0);
			model.put("totalPages", 1);
			model.put("size", 1);
			model.put("msg", "No existe ningún azafato con dicho nif");
			return "azafatos/azafatosList";
		} else {
			return "redirect:/azafatos/" + resultado.getId();
		}

	}

	@GetMapping("/azafatos/{azafatoId}")
	public ModelAndView showAzafato(@PathVariable("azafatoId") int azafatoId, Authentication authentication) {

		//Evita que otros azafatos entren a perfiles de otros
		String usuario = authentication.getName();
		Collection<? extends GrantedAuthority> autoridad  = authentication.getAuthorities();
		String rol = "";
		for(GrantedAuthority auth: autoridad) {
			rol = auth.getAuthority();
		}
		if(rol.equals("azafato")) {
			Azafato azaC = this.azafatoService.findAzafatoByNif(usuario);
			int azaCID = azaC.getId();
			if(azafatoId != azaCID) {
				ModelAndView mav = new ModelAndView("azafatos/azafatoDetails");
				mav.addObject(this.azafatoService.findAzafatoById(azaCID));
				return mav;
			}
		}
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


		if(fecha == null) {
			fecha = LocalDate.now();
		}

		int mes = fecha.getMonthValue();
		int año = fecha.getYear();
		Month mesn = fecha.getMonth();
		int dias = fecha.lengthOfMonth();

		Collection<Vuelo> vuelos = this.azafatoService.horario(azafatoId, mes, año);

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
		return "azafatos/horario";
	}

	// Historia usuario 1:
	@RequestMapping(value = { "/azafatos/{azafatoId}/semana" }, method = RequestMethod.GET)
	public String showHorarioSemanalAzafato(Map<String, Object> model, @PathVariable("azafatoId") int azafatoId,  @RequestParam(name = "fecha", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {

		LocalDate date = LocalDate.now();

		if(fecha != null) {
			date = fecha;
		}

		DayOfWeek diaSemana = date.getDayOfWeek();
		int dia = date.getDayOfYear();
		int anyo = date.getYear();
		model.put("vuelos", this.azafatoService.horarioSemanalConFecha(azafatoId, diaSemana, dia, anyo));
		String diaS = diaSemana.toString();
		model.put("diaS", diaS);
		model.put("diaM", date.getDayOfMonth());
		model.put("mes", date.getMonthValue());
		model.put("anyo", date.getYear());
		return "azafatos/horarioSemanal";
	}
}
