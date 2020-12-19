package org.springframework.samples.aerolineasAAAFC.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;
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
import org.springframework.samples.aerolineasAAAFC.model.Cliente;
import org.springframework.samples.aerolineasAAAFC.model.PersonalOficina;
import org.springframework.samples.aerolineasAAAFC.service.AuthoritiesService;
import org.springframework.samples.aerolineasAAAFC.service.ClienteService;
import org.springframework.samples.aerolineasAAAFC.service.PersonalOficinaService;
import org.springframework.samples.aerolineasAAAFC.service.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers=PersonalOficinaController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
public class PersonalOficinaControllerTests {



	private static final int TEST_PERSONALOFICINA_ID = 2;

	@Autowired
	private PersonalOficinaController personalOficinaController;

	@MockBean
	private PersonalOficinaService personalOficinaService;

	@MockBean
	private UserService userService;

	@MockBean
	private AuthoritiesService authoritiesService; 

	@Autowired
	private MockMvc mockMvc;

	private PersonalOficina carlos;



	@BeforeEach
	void setup() {

		carlos = new PersonalOficina();
		carlos.setId(TEST_PERSONALOFICINA_ID);
		carlos.setApellidos("Santana");
		carlos.setNombre("Carlos");
		carlos.setIban("ES 4422000418403334567812");
		carlos.setNif("29765800A");
		carlos.setSalario(1000.);

		given(this.personalOficinaService.findPersonalOficinaById(TEST_PERSONALOFICINA_ID)).willReturn(carlos);
	}




	@WithMockUser(value = "spring")
	@Test
	void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/oficinistas/new")).andExpect(status().isOk())
		.andExpect(model().attributeExists("pOficina"))
		.andExpect(view().name("oficinistas/createOrUpdatePersonalOficinaForm"));
	}


	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/oficinistas/new")
				.param("nombre", "Kenneth")
				.param("apellidos", "Downing Jr")
				.with(csrf())
				.param("nif", "29763330H")
				.param("iban", "ES 4433300418403322567812")
				.param("salario", "1432."))
//		.andExpect(status().isOk())
		.andExpect(view().name("redirect:/oficinistas/{pOficinaId}"));
	}


	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/oficinistas/new")
				.param("nombre", "Kenneth")
				.param("apellidos", "Downing Jr")
				.with(csrf())
				.param("salario", "London"))
		.andExpect(status().isOk())
		.andExpect(model().attributeHasErrors("pOficina"))
		.andExpect(model().attributeHasFieldErrors("oficinista", "salario"))
		.andExpect(view().name("redirect:/oficinistas/"));
	}





	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdatePersonalOficinaFormSuccess() throws Exception {
		mockMvc.perform(post("/oficinistas/{pOficinaId}/edit", TEST_PERSONALOFICINA_ID)
				.with(csrf())
				.param("nombre", "Joe")
				.param("apellidos", "Bloggs")
				.param("nif", "29763330X")
				.param("iban", "ES 4433300418454322567812")
				.param("salario", "4312"))
//		.andExpect(status().isOk())
		.andExpect(view().name("redirect:/oficinistas/{pOficinaId}"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdatePersonalOficinaFormError() throws Exception {
		mockMvc.perform(post("/oficinistas/{pOficinaId}/edit", TEST_PERSONALOFICINA_ID)
				.with(csrf())
				.param("nombre", "Joe")
				.param("apellidos", "Bloggs")
				.param("nif", "uvedoblexdxdxd")
				.param("iban", "nosoyuniban")
				.param("salario", "soyunString"))
		.andExpect(status().isOk())
		.andExpect(model().attributeHasErrors("pOficina"))
		.andExpect(model().attributeHasFieldErrors("pOficina", "nif"))
		.andExpect(model().attributeHasFieldErrors("pOficina", "iban"))
		.andExpect(model().attributeHasFieldErrors("pOficina", "salario"))
		.andExpect(view().name("oficinistas/createOrUpdatePersonalOficinaForm"));
	}












}
