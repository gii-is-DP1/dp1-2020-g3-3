package org.springframework.samples.aerolineasAAAFC.web;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
			LocalDateTime hoy = LocalDateTime.now();

			if (vuelo.getFechaSalida().isAfter(hoy)) {
				List<Asiento> asientos = this.asientoService.findAsientosSinOcupar(vuelo);
				model.put("asientos", asientos);
				model.put("nAsientos", asientos.size());
				model.put("vuelo", vuelo);
				Cliente cliente = clienteService.findClienteByNif(name);
				model.put("cliente", cliente);

				LocalDate today = LocalDate.now();
				DateTimeFormatter d = DateTimeFormatter.ofPattern("yyyy/MM/dd");
				String aux = today.format(d);
				model.put("fechaReserva", aux);
				
				//Comprobamos si el vuelo tiene descuento
				long dif = (Duration.between(hoy, vuelo.getFechaSalida()).toDays());
				boolean descuento = dif <= 7? true : false;
				model.put("descuento", descuento); //Que haya descuento implica un cambio en todos los precios del jsp
				
			}

			else {
				return "redirect:/exception";
			}

		} else {
			return "user/createClienteForm.jsp";
		}
		return VIEWS_BILLETE_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/billetes/{vueloId}/new")
	public String processCreationBilleteForm(@PathVariable("vueloId") int vueloId, @Valid Billete billete,
			BindingResult result, Map<String, Object> model) {
		if (SecurityContextHolder.getContext().getAuthentication() != null) {
			if (result.hasErrors()) {
				return VIEWS_BILLETE_CREATE_OR_UPDATE_FORM;
			} else {
				LocalDateTime hoy = LocalDateTime.now();
				Vuelo vuelo = this.vueloService.findVueloById(vueloId);
				// Ya que el id de vuelo en asiento se puede cambiar, comprobamos que ambos
				// coinciden
				int vueloIdDeAsiento = billete.getAsiento().getVuelo().getId();
				int vueloIdDeVuelo = vuelo.getId();
				
				//Comprobamos que los IDs sean iguales, si se intenta hacer 
				//POST hacking, se comprará un billete si el asiento formateado existe
				//es análogo a acceder a la compra de forma legal, solo que cambiando url y value
				if (vuelo.getFechaSalida().isAfter(hoy) && (vueloIdDeAsiento == vueloIdDeVuelo)) {
					//Recordemos que la fecha reserva y coste nos dan igual, ya que se establecen a nivel service
					this.billeteService.saveBillete(billete);
				} else {
					return "redirect:/exception";
				}

			}
		} else {
			return "redirect:/exception";
		}
		return "redirect:/billetes/" + billete.getId();
	}

	// Vista que muestra el billete comprado
	@GetMapping(value = "/billetes/{billeteId}")
	public String previewBillete(@PathVariable("billeteId") int billeteId, Map<String, Object> model) {

		Billete billete = this.billeteService.findBilleteById(billeteId);

		if (billete == null) {
			return "redirect:/exception"; // Vista de error
		}

		else if (SecurityContextHolder.getContext().getAuthentication() != null) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String name = authentication.getName(); // name corresponde al nif del usuario

			if (name.equals(billete.getCliente().getNif())) {
				model.put("billete", billete);
				Cliente cliente = billete.getCliente();
				model.put("cliente", cliente);
			} else {
				return "redirect:/exception";
			}

		} else {
			return "login";
		}

		return "billetes/billetePreview";
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