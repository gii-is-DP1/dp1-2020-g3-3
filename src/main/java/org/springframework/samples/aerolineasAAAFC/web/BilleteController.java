package org.springframework.samples.aerolineasAAAFC.web;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
	public BilleteController(BilleteService billeteService, ClienteService clienteService,VueloService vueloService, AsientoService asientoService) {
		this.billeteService = billeteService;
		this.clienteService=  clienteService;
		this.vueloService= vueloService;
		this.asientoService = asientoService;
	}

//	@ModelAttribute("asientos")
//	public List<Asiento> findAsientosByVuelo(@PathVariable("vueloId") int vueloId) {
//		return this.asientoService.findAsientosSinOcupar(this.vueloService.findVueloById(vueloId));
//	}
//	
//	@ModelAttribute("vuelo")
//	public Vuelo findVuelo(@PathVariable("vueloId") int vueloId) {
//		return this.vueloService.findVueloById(vueloId);
//	}
	
	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	/*
	 * Alta de un nuevo billete
	 */
	@GetMapping(value = "/billetes/{vueloId}/new")
	public String initCreationBilleteForm(@PathVariable("vueloId") int vueloId,
			Map<String, Object> model) {
		if( SecurityContextHolder.getContext().getAuthentication()!=null) {
			Billete billete = new Billete();
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String name = authentication.getName();//name corresponde al nif del usuario
			model.put("billete", billete);
			Vuelo vuelo=this.vueloService.findVueloById(vueloId);
			List<Asiento> asientos=this.asientoService.findAsientosSinOcupar(vuelo);
			model.put("asientos",asientos);
			model.put("vuelo",vuelo);
			Cliente cliente=clienteService.findClienteByNif(name);
			model.put("cliente", cliente);
			return VIEWS_BILLETE_CREATE_OR_UPDATE_FORM;
		}else {
			return "user/createClienteForm.jsp";
		}
	}

	@PostMapping(value = "/billetes/{vueloId}/new")
	public String processCreationBilleteForm(@PathVariable("vueloId") int vueloId,
			@Valid Billete billete, BindingResult result) {
		if (result.hasErrors()) {
			return VIEWS_BILLETE_CREATE_OR_UPDATE_FORM;
		} else {
			this.billeteService.saveBillete(billete);

			return "redirect:/billetes/" + billete.getId();
		}
	}

	/*
	 * Modificacion de un billete
	 */
	@GetMapping(value = "/billetes/{billeteId}/edit")
	public String initUpdateBilleteForm(@PathVariable("billeteId") int billeteId, ModelMap model) {
		Billete billete = this.billeteService.findBilleteById(billeteId);
		model.addAttribute(billete);
		return VIEWS_BILLETE_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/billetes/{billeteId}/edit")
	public String processUpdateBilleteForm(@Valid Billete billete, BindingResult result,
			@PathVariable("billeteId") int billeteId,
			ModelMap model, @RequestParam(value = "version", required=false) Integer version) {
		Billete billeteToUpdate=this.billeteService.findBilleteById(billeteId);

		if(billeteToUpdate.getVersion()!=version) {
			model.put("message","Concurrent modification of billete! Try again!");
			return initUpdateBilleteForm(billeteId,model);
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
	public String ShowDatosBillete(Map<String, Object> model,
			@RequestParam(name = "apellidos", defaultValue = "") String apellidos) {
		if (apellidos.isEmpty()) {
			Collection<Billete> billetes = this.billeteService.findBilleteConCliente();
			model.put("billetes", billetes);
		} else {
			Collection<Billete> billetes = this.billeteService.findBilletePorApellido(apellidos);
			model.put("billetes", billetes);
		}
		return "billetes/billetesDatosList";
	}
}