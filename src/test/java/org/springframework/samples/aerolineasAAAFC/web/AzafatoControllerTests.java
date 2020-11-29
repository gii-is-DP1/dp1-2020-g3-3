package org.springframework.samples.aerolineasAAAFC.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.aerolineasAAAFC.configuration.SecurityConfiguration;
import org.springframework.samples.aerolineasAAAFC.model.Azafato;
import org.springframework.samples.aerolineasAAAFC.model.Cliente;
import org.springframework.samples.aerolineasAAAFC.model.Idioma;
import org.springframework.samples.aerolineasAAAFC.model.PersonalOficina;
import org.springframework.samples.aerolineasAAAFC.service.AuthoritiesService;
import org.springframework.samples.aerolineasAAAFC.service.AzafatoService;
import org.springframework.samples.aerolineasAAAFC.service.ClienteService;
import org.springframework.samples.aerolineasAAAFC.service.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers=AzafatoController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
public class AzafatoControllerTests {



	private static final int TEST_AZAFATO_ID = 2;

	@Autowired
	private AzafatoController azafatoController;

	@MockBean
	private AzafatoService azafatoService;

	@MockBean
	private UserService userService;

	@MockBean
	private AuthoritiesService authoritiesService; 

	@Autowired
	private MockMvc mockMvc;

	private Azafato Martina;



	@BeforeEach
	void setup() {

		Martina = new Azafato();
		Martina.setId(TEST_AZAFATO_ID);
		Martina.setApellidos("Perez");
		Martina.setNombre("Martina");
		Martina.setIban("ES 01225905418408934560815");
		Martina.setNif("89565804G");

		Idioma lng = new Idioma();
		lng.setIdioma("Francés");
    Idioma lng2 = new Idioma();
		lng.setIdioma("Castellano");
		Set<Idioma> lngs = new HashSet<Idioma>();
		lngs.add(lng);
    lngs.add(lng2);
		Martina.setIdiomas(lngs);

		Martina.setSalario(1200.);

		given(this.azafatoService.findAzafatoById(TEST_AZAFATO_ID)).willReturn(Martina);
	}




	@WithMockUser(value = "spring")
	@Test
	void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/azafatos/new")).andExpect(status().isOk()).andExpect(model().attributeExists("azafato"))
		.andExpect(view().name("azafatos/createOrUpdateAzafatoForm"));
	}


	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/azafatos/new")
				.param("nombre", "Gonzalo")
				.param("apellidos", "Gonzalez")
				.with(csrf())
				.param("nif", "589614237D")
				.param("iban", "ES 3332020458401202067812")
				.param("salario", "1400."))
		.andExpect(status().isOk());
	}


	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/azafatos/new")
				.param("nombre", "Luis")
				.param("apellidos", "Soler")
				.with(csrf())
				.param("salario", "nosalario"))
		.andExpect(status().isOk())
		.andExpect(model().attributeHasErrors("azafato"))
		.andExpect(view().name("redirect:/azafatos/azafatoId"));
	}





	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdateAzafatosFormSuccess() throws Exception {
		mockMvc.perform(post("/azafatos/{azafatoId}/edit", TEST_AZAFATO_ID)
				.with(csrf())
				.param("nombre", "Amelie")
				.param("apellidos", "Meyer")
				.param("nif", "58961253K")
				.param("iban", "ES 1563382454044322567800")
				.param("salario", "1600"))
		.andExpect(status().isOk())
		.andExpect(view().name("redirect:/azafatos/{azafatoId}"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdateAzafatoFormError() throws Exception {
		mockMvc.perform(post("/azafatos/{azafatoId}/edit", TEST_AZAFATO_ID)
				.with(csrf())
				.param("nombre", "Erik")
				.param("apellidos", "Fischer")
				.param("nif", "XXX")
				.param("iban", "XXX")
				.param("salario", "XXX"))
		.andExpect(status().isOk())
		.andExpect(model().attributeHasErrors("azafato"))
		.andExpect(model().attributeHasFieldErrors("azafato", "nif"))
		.andExpect(model().attributeHasFieldErrors("azafato", "iban"))
		.andExpect(model().attributeHasFieldErrors("azafato", "salario"))
		.andExpect(view().name("azafatos/createOrUpdateAzafatoForm"));
	}












}
