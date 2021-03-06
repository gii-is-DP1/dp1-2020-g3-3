package org.springframework.samples.aerolineasAAAFC.web;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.samples.aerolineasAAAFC.configuration.SecurityConfiguration;
import org.springframework.samples.aerolineasAAAFC.model.Cliente;
import org.springframework.samples.aerolineasAAAFC.model.PersonalOficina;
import org.springframework.samples.aerolineasAAAFC.model.Rol;
import org.springframework.samples.aerolineasAAAFC.model.User;
import org.springframework.samples.aerolineasAAAFC.service.AuthoritiesService;
import org.springframework.samples.aerolineasAAAFC.service.ClienteService;
import org.springframework.samples.aerolineasAAAFC.service.PersonalOficinaService;
import org.springframework.samples.aerolineasAAAFC.service.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebMvcTest(controllers=PersonalOficinaController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
public class PersonalOficinaControllerTests {



	private static final int TEST_PERSONALOFICINA_ID = 1;



	@MockBean
	private PersonalOficinaService personalOficinaService;

	@MockBean
	private UserService userService;

	@MockBean
	private AuthoritiesService authoritiesService; 

	@Autowired
	private MockMvc mockMvc;

	private PersonalOficina carlos;

	private User carlosUser;

	@BeforeEach
	void setup() {

		carlos = new PersonalOficina();
		carlos.setId(TEST_PERSONALOFICINA_ID);
		carlos.setNombre("Carlos");
		carlos.setApellidos("Santana Hidalgo");
		carlos.setNif("70292959Z");
		carlos.setIban("ES 1804875866011781392136");
		carlos.setSalario(1000.);
		carlos.setVersion(1);
		
		carlosUser = new User();
		carlosUser.setUsername("70292959Z");
		carlosUser.setPassword("CaSanHi70");
		carlos.setUser(carlosUser);

		given(this.personalOficinaService.findPersonalOficinaById(TEST_PERSONALOFICINA_ID)).willReturn(carlos);
	
		List<PersonalOficina> lista = new ArrayList<PersonalOficina>();
		lista.add(carlos);
		Page pagina = new PageImpl<PersonalOficina>(lista);
		Pageable paging = PageRequest.of(0, 20);
		
		given(this.personalOficinaService.findPersonal(paging)).willReturn(pagina);
	}


	//TEST DE CREACIÓN


	@WithMockUser(value = "spring")
	@Test
	void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/personalOficina/new"))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("personalOficina"))
		.andExpect(view().name("personalOficina/createOrUpdatePersonalOficinaForm"));
	}


	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/personalOficina/new")
				.param("nombre", "Kenneth")
				.param("apellidos", "Downing Jr")
				.with(csrf())
				.param("nif", "89628343B")
				.param("iban", "ES 6721007638981977233634")
				.param("salario", "1432.")
				.param("user.username", "89628343B")
				.param("user.password", "AAAAAAA"))
		.andExpect(status().is3xxRedirection());
	}


	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/personalOficina/new")
				.with(csrf())
				.param("nombre", "Kenneth")
				.param("apellidos", "Downing Jr")
				.param("nif", "29763330H")
				.param("iban", "ES 4433300418403322567812")
				.param("salario", "London")
				.param("user.username", "29763330H")
				.param("user.password", "AAAAAAA"))
		.andExpect(status().isOk())
		.andExpect(model().attributeHasErrors("personalOficina"))
		.andExpect(model().attributeHasFieldErrors("personalOficina", "salario"))
		.andExpect(view().name("personalOficina/createOrUpdatePersonalOficinaForm"));
	}



	//TEST ACTUALIZACIÓN
	@WithMockUser(value = "spring")
	@Test
	void testInitUpdateForm() throws Exception{
		mockMvc.perform(get("/personalOficina/{pOficinaId}/edit", TEST_PERSONALOFICINA_ID))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("personalOficina"))
		.andExpect(model().attribute("personalOficina", hasProperty("nombre", is("Carlos"))))
		.andExpect(model().attribute("personalOficina", hasProperty("apellidos", is("Santana Hidalgo"))))
		.andExpect(model().attribute("personalOficina", hasProperty("nif", is("70292959Z"))))
		.andExpect(model().attribute("personalOficina", hasProperty("iban", is("ES 1804875866011781392136"))))
		.andExpect(model().attribute("personalOficina", hasProperty("salario", is(1000.))))
		.andExpect(view().name("personalOficina/createOrUpdatePersonalOficinaForm"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdatePersonalOficinaFormSuccess() throws Exception {
		mockMvc.perform(post("/personalOficina/{pOficinaId}/edit", TEST_PERSONALOFICINA_ID)
				.with(csrf())
				.param("version", "1")
				.param("nombre", "Carlos")
				.param("apellidos", "Bloggs")
				.param("nif", "70292959Z")
				.param("iban", "ES 1804875866011781392136")
				.param("salario", "4312.0")
				.param("user.username", "70292959Z")
				.param("user.password", "AAAAAAA"))
		.andExpect(view().name("redirect:/personalOficina/{pOficinaId}"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdatePersonalOficinaFormError() throws Exception {
		mockMvc.perform(post("/personalOficina/{pOficinaId}/edit", TEST_PERSONALOFICINA_ID)
				.with(csrf())
				.param("version", "1")
				.param("nombre", "Joe")
				.param("apellidos", "Bloggs")
				.param("nif", "uvedoblexdxdxd")
				.param("iban", "nosoyuniban")
				.param("salario", "soyunString")
				.param("user.username", "uvedoblexdxdxd")
				.param("user.password", "AAAAAAA"))
		.andExpect(status().isOk())
		.andExpect(model().attributeHasErrors("personalOficina"))
		.andExpect(model().attributeHasFieldErrors("personalOficina", "nif"))
		.andExpect(model().attributeHasFieldErrors("personalOficina", "iban"))
		.andExpect(model().attributeHasFieldErrors("personalOficina", "salario"))
		.andExpect(view().name("personalOficina/createOrUpdatePersonalOficinaForm"));
	}

	
	//TEST DE ELIMINACIÓN
	@WithMockUser(value = "spring")
	@Test
	void testDeletePersonalOficina() throws Exception{
		mockMvc.perform(get("/personalOficina/{pOficinaId}/delete", TEST_PERSONALOFICINA_ID))
		.andExpect(status().isFound())
		.andExpect(model().attributeDoesNotExist("personalOficina"))
		.andExpect(view().name("redirect:/personalOficina"));
	}
	
	
	//OTROS
	@WithMockUser(value = "spring")
	@Test
	void testShowPersonalOificinaList() throws Exception{
		mockMvc.perform(get("/personalOficina"))
		.andExpect(model().attributeExists("personalOficina"))
		.andExpect(view().name("personalOficina/personalOficinaList"));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testShowPersonalControl() throws Exception {
		mockMvc.perform(get("/personalOficina/{pOficinaId}", TEST_PERSONALOFICINA_ID))
		.andExpect(model().attributeExists("personalOficina"))
		.andExpect(model().attribute("personalOficina", hasProperty("nombre", is("Carlos"))))
		.andExpect(model().attribute("personalOficina", hasProperty("apellidos", is("Santana Hidalgo"))))
		.andExpect(model().attribute("personalOficina", hasProperty("nif", is("70292959Z"))))
		.andExpect(model().attribute("personalOficina", hasProperty("iban", is("ES 1804875866011781392136"))))
		.andExpect(model().attribute("personalOficina", hasProperty("salario", is(1000.))))
		.andExpect(view().name("personalOficina/oficinistaDetails"));
	}


	@WithMockUser(value = "spring")
	@Test
	void testProcessFindFormByNif() throws Exception {
		given(this.personalOficinaService.findPersonalOficinaByNif(carlos.getNif())).willReturn(carlos);
		log.info("Carlos: " + carlos);

		mockMvc.perform(get("/personalOficinafind")
				.param("nif", "70292959Z"))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/personalOficina/" + TEST_PERSONALOFICINA_ID));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessFindFormNoPersonalControlFound() throws Exception {
		mockMvc.perform(get("/personalOficinafind")
				.param("nif", ""))
		.andExpect(status().is2xxSuccessful())
		.andExpect(view().name("personalOficina/personalOficinaList"));
	}








}
