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

	private PersonalOficina Juan;



	@BeforeEach
	void setup() {

		Juan = new PersonalControl();
		Juan.setId(TEST_PERSONALCONTROL_ID);
		Juan.setApellidos("Jones");
		Juan.setNombre("Marie");
		Juan.setIban("ES 0159480518801639865810");
		Juan.setNif("01582301T");
		Juan.setRol("copiloto");
		Juan.setSalario(2000.);

		given(this.personalControlService.findPersonalControlById(TEST_PERSONALCONTROL_ID)).willReturn(Juan);
	}




	@WithMockUser(value = "spring")
	@Test
	void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/controladores/new")).andExpect(status().isOk()).andExpect(model().attributeExists("PersonalControlador"))
		.andExpect(view().name("controladores/createOrUpdatePersonalControlForm"));
	}


	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/controladores/new")
				.param("nombre", "Daryl")
				.param("apellidos", "Brown")
				.with(csrf())
				.param("nif", "85765119M")
				.param("iban", "ES 7800230514501459995863")
				.param("rol", "piloto")
				.param("salario", "2500."))
		.andExpect(status().isOk());
	}


	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/controladores/new")
				.param("nombre", "Alessandro")
				.param("apellidos", "Ferrara")
				.with(csrf())
				.param("rol", "piloto")
				.param("salario", "nosalario"))
		.andExpect(status().isOk())
		.andExpect(model().attributeHasErrors("controladores"))
		//.andExpect(model().attributeHasFieldErrors("controladores", "salario"))
		.andExpect(view().name("redirect:/controladores/"));
	}





	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdatePersonalControlFormSuccess() throws Exception {
		mockMvc.perform(post("/oficinistas/{pOficinaId}/edit", TEST_PERSONALCONTROL_ID)
				.with(csrf())
				.param("nombre", "Carlo")
				.param("apellidos", "Santoro")
				.param("nif", "01565890H")
				.param("iban", "ES 5723389618452562008819")
				.param("rol", "piloto")
				.param("salario", "4500"))
		.andExpect(status().isOk())
		.andExpect(view().name("redirect:/controladores/{pControlId}"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdatePersonalControlFormError() throws Exception {
		mockMvc.perform(post("/oficinistas/{pOficinaId}/edit", TEST_PERSONALCONTROL_ID)
				.with(csrf())
				.param("nombre", "Camile")
				.param("apellidos", "Bernard ")
				.param("nif", "58963425")
				.param("iban", "XXXXXXXX")
				.param("rol", "0")
				.param("salario", "nosalario"))
		.andExpect(status().isOk())
		.andExpect(model().attributeHasErrors("pControl"))
		.andExpect(model().attributeHasFieldErrors("pControl", "nif"))
		.andExpect(model().attributeHasFieldErrors("pControl", "iban"))
		.andExpect(model().attributeHasFieldErrors("pControl", "salario"))
		.andExpect(view().name("oficinistas/createOrUpdatePersonalControlForm"));
	}












}
