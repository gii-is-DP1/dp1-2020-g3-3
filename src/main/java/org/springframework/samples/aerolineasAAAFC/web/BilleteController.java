package org.springframework.samples.aerolineasAAAFC.web;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.samples.aerolineasAAAFC.model.Asiento;
import org.springframework.samples.aerolineasAAAFC.model.Billete;
import org.springframework.samples.aerolineasAAAFC.model.Cliente;
import org.springframework.samples.aerolineasAAAFC.model.Vuelo;
import org.springframework.samples.aerolineasAAAFC.service.AsientoService;
import org.springframework.samples.aerolineasAAAFC.service.BilleteService;
import org.springframework.samples.aerolineasAAAFC.service.ClienteService;
import org.springframework.samples.aerolineasAAAFC.service.VueloService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

@Controller
public class BilleteController {

	private static final String VIEWS_BILLETE_CREATE_OR_UPDATE_FORM = "billetes/createOrUpdateBilleteForm";

	private final BilleteService billeteService;
	private final ClienteService clienteService;
	private final VueloService vueloService;
	private final AsientoService asientoService;

	@Autowired
	public BilleteController(BilleteService billeteService, ClienteService clienteService, VueloService vueloService,
			AsientoService asientoService) {
		this.billeteService = billeteService;
		this.clienteService = clienteService;
		this.vueloService = vueloService;
		this.asientoService = asientoService;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	/*
	 * Alta de un nuevo billete
	 */
	@GetMapping(value = "/billetes/{vueloId}/new")
	public String initCreationBilleteForm(@PathVariable("vueloId") int vueloId, Map<String, Object> model) {
		if (SecurityContextHolder.getContext().getAuthentication() != null) {
			Billete billete = new Billete();
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String name = authentication.getName(); // name corresponde al nif del usuario
			model.put("billete", billete);
			Vuelo vuelo = this.vueloService.findVueloById(vueloId);
			List<Asiento> asientos = this.asientoService.findAsientosSinOcupar(vuelo);
			model.put("asientos", asientos);
			model.put("nAsientos", asientos.size());
			model.put("vuelo", vuelo);
			Cliente cliente = clienteService.findClienteByNif(name);
			model.put("cliente", cliente);
			DateTimeFormatter d = DateTimeFormatter.ofPattern("yyyy/MM/dd");
			LocalDate today = LocalDate.now();
			String aux = today.format(d);
			model.put("fechaReserva", aux);
			return VIEWS_BILLETE_CREATE_OR_UPDATE_FORM;
		} else {
			return "user/createClienteForm.jsp";
		}
	}

	@PostMapping(value = "/billetes/{vueloId}/new")
	public String processCreationBilleteForm(@PathVariable("vueloId") int vueloId, @Valid Billete billete,
			BindingResult result, Map<String, Object> model) {
		if (result.hasErrors()) {
			return VIEWS_BILLETE_CREATE_OR_UPDATE_FORM;
		} else {
			this.billeteService.saveBillete(billete);

			return "redirect:/billetes/" + billete.getId();
		}
	}

	// Vista que muestra el billete comprado
	@GetMapping(value = "/billetes/{billeteId}")
	public String previewBillete(@PathVariable("billeteId") int billeteId, Map<String, Object> model) {
		// Apartado de validacion

		Billete billete = this.billeteService.findBilleteById(billeteId);

		if (billete == null) {
			return "/oups"; // Vista de error
		}

		else if (SecurityContextHolder.getContext().getAuthentication() != null) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String name = authentication.getName(); // name corresponde al nif del usuario

			if (name.equals(billete.getCliente().getNif())) {
				model.put("billete", billete);
				Cliente cliente = billete.getCliente();
				model.put("cliente", cliente);
			}
			
			else {
				return "user/createClienteForm.jsp";
			}
			
		} else {
			return "user/createClienteForm.jsp";
		}

		return "billetes/billetePreview";
	}

	/*
	 * Modificacion de un billete
	 */
	@GetMapping(value = "/billetes/{billeteId}/edit") //DEPRECATED
	public String initUpdateBilleteForm(@PathVariable("billeteId") int billeteId, ModelMap model) {
		Billete billete = this.billeteService.findBilleteById(billeteId);
		model.addAttribute(billete);
		return VIEWS_BILLETE_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/billetes/{billeteId}/edit") //DEPRECATED
	public String processUpdateBilleteForm(@Valid Billete billete, BindingResult result,
			@PathVariable("billeteId") int billeteId, ModelMap model,
			@RequestParam(value = "version", required = false) Integer version) {
		Billete billeteToUpdate = this.billeteService.findBilleteById(billeteId);

		if (billeteToUpdate.getVersion() != version) {
			model.put("message", "Concurrent modification of billete! Try again!");
			return initUpdateBilleteForm(billeteId, model);
		}
		if (result.hasErrors()) {
			return VIEWS_BILLETE_CREATE_OR_UPDATE_FORM;
		} else {
			billete.setId(billeteId);
			this.billeteService.saveBillete(billete);

			return "redirect:/billetes/" + billete.getId();
		}
	}

	@RequestMapping(value = { "/billetes/datos" }, method = RequestMethod.GET)
	public String ShowDatosBillete(ModelMap model,
			@RequestParam(name = "apellidos", defaultValue = "") String apellidos,
			@PageableDefault(value = 20) Pageable paging) {

		if (apellidos.trim().isEmpty()) {
			Page<Billete> pages = this.billeteService.findBilletes(paging);
			model.addAttribute("number", pages.getNumber());
			model.addAttribute("totalPages", pages.getTotalPages());
			model.addAttribute("totalElements", pages.getTotalElements());
			model.addAttribute("size", pages.getSize());
			model.addAttribute("billetes", pages.getContent());
		} else {
			Page<Billete> pages = this.billeteService.findBilletePorApellido(apellidos.toUpperCase(), paging);

			if (pages.getContent().isEmpty()) {
				model.put("msg", "No hay billetes para el cliente buscado.");
				model.addAttribute("number", 0);
				model.addAttribute("totalPages", 1);
				model.addAttribute("totalElements", 1);
				model.addAttribute("size", 1);
			} else {
				model.addAttribute("number", pages.getNumber());
				model.addAttribute("totalPages", pages.getTotalPages());
				model.addAttribute("totalElements", pages.getTotalElements());
				model.addAttribute("size", pages.getSize());
				model.addAttribute("billetes", pages.getContent());
			}
		}
		return "billetes/billetesDatosList";
	}
}