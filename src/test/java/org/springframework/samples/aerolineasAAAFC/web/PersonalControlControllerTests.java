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
import org.springframework.samples.aerolineasAAAFC.model.PersonalControl;
import org.springframework.samples.aerolineasAAAFC.model.PersonalOficina;
import org.springframework.samples.aerolineasAAAFC.model.Rol;
import org.springframework.samples.aerolineasAAAFC.model.User;
import org.springframework.samples.aerolineasAAAFC.service.AuthoritiesService;
import org.springframework.samples.aerolineasAAAFC.service.ClienteService;
import org.springframework.samples.aerolineasAAAFC.service.PersonalControlService;
import org.springframework.samples.aerolineasAAAFC.service.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers=PersonalControlController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
public class PersonalControlControllerTests {



	private static final int TEST_PERSONALCONTROL_ID = 2;

	@Autowired
	private PersonalControlController personalControlController;

	@MockBean
	private PersonalControlService personalControlService;

	@MockBean
	private UserService userService;

	@MockBean
	private AuthoritiesService authoritiesService; 

	@Autowired
	private MockMvc mockMvc;

	private PersonalControl Juan;
	
	private User juanUser;

	@BeforeEach
	void setup() {

		Juan = new PersonalControl();
		Juan.setId(TEST_PERSONALCONTROL_ID);
		Juan.setApellidos("Fernandez Romero");
		Juan.setNombre("Juan");
		Juan.setIban("ES 0159480518801639865810");
		Juan.setNif("08493865B");
		Juan.setRol(Rol.COPILOTO);
		Juan.setSalario(2000.);
		
		juanUser = new User();
		juanUser.setUsername("08493865B");
		juanUser.setPassword("juFerRo01");
		juanUser.setMatchingPassword("juFerRo01");
		Juan.setUser(juanUser);

		given(this.personalControlService.findPersonalControlById(TEST_PERSONALCONTROL_ID)).willReturn(Juan);
	}


	//Test de inserción

	@WithMockUser(value = "spring")
	@Test
	void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/controladores/new"))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("personalControl"))
		.andExpect(view().name("controladores/createOrUpdatePersonalControlForm"));
	}


	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/controladores/new")
				.param("nombre", "Daryl")
				.param("apellidos", "Brown")
				.with(csrf())
				.param("nif", "19083474Y")
				.param("iban", "ES 4700815264369772656796")
				.param("rol", "2")
				.param("salario", "2500.")
				.param("user.username", "19083474Y")
				.param("user.password", "asdasd23"))
		.andExpect(status().is3xxRedirection());
	}


	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/controladores/new")
				.with(csrf())
				.param("nombre", "Alessandro")
				.param("apellidos", "Ferrara")
				.param("nif", "297635960K")
				.param("iban", "ES 4433300418403322567002")
				.param("rol", "1")
				.param("salario", "nosalario")
				.param("user.username", "297635960K")
				.param("user.password", "AAAAAAA"))
		.andExpect(status().isOk())
		.andExpect(model().attributeHasErrors("personalControl"))
		.andExpect(model().attributeHasFieldErrors("personalControl", "nif"))
		.andExpect(model().attributeHasFieldErrors("personalControl", "salario"))
		.andExpect(model().attributeHasFieldErrors("personalControl", "user.username"))
		.andExpect(view().name("redirect:/controladores/"));
	}


	//Test de actualización
	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdatePersonalControlFormSuccess() throws Exception {
		mockMvc.perform(post("/controladores/{pOficinaId}/edit", TEST_PERSONALCONTROL_ID)
				.with(csrf())
				.param("nombre", "Juan")
				.param("apellidos", "Fernandez Romero")
				.param("nif", "01582301T")
				.param("iban", "ES 7200813221711748512485")
				.param("rol", "0")
				.param("salario", "4500.")
				.param("user.username", "01582301T")
				.param("user.password", "CCCCCCC"))
		.andExpect(view().name("redirect:/controladores/{pControlId}"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdatePersonalControlFormError() throws Exception {
		mockMvc.perform(post("/controladores/{pControlId}/edit", TEST_PERSONALCONTROL_ID)
				.with(csrf())
				.param("nombre", "Camile")
				.param("apellidos", "Bernard")
				.param("nif", "58963425")
				.param("iban", "yyyyyyy")
				.param("rol", "0")
				.param("salario", "nosalario")
				.param("user.username", "xxxxxx")
				.param("user.password", "AAAAAAA"))
		.andExpect(status().isOk())
		.andExpect(model().attributeHasErrors("personalControl"))
		.andExpect(model().attributeHasFieldErrors("personalControl", "nif"))
		.andExpect(model().attributeHasFieldErrors("personalControl", "iban"))
		.andExpect(model().attributeHasFieldErrors("personalControl", "salario"))
		.andExpect(model().attributeHasFieldErrors("personalControl", "user.username"))
		.andExpect(view().name("controladores/createOrUpdatePersonalControlForm"));
	}












}
