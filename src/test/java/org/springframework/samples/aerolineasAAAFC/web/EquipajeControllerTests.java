package org.springframework.samples.aerolineasAAAFC.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.aerolineasAAAFC.configuration.SecurityConfiguration;
import org.springframework.samples.aerolineasAAAFC.model.Billete;
import org.springframework.samples.aerolineasAAAFC.service.BilleteService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers=EquipajeController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
public class EquipajeControllerTests {

	private static final int TEST_BILLETE_ID = 1;


	@MockBean
	private BilleteService billeteService;

	@Autowired
	private MockMvc mockMvc;

	private Billete aSevillaVamos;

	@BeforeEach
	void setup() {

		aSevillaVamos = this.billeteService.findBilleteById(TEST_BILLETE_ID);
		given(this.billeteService.findBilleteById(TEST_BILLETE_ID)).willReturn(aSevillaVamos);
	}

	//TESTS CREACIÓN

	@WithMockUser(value = "spring")
	@Test
	void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/billetes/{billeteId}/equipajes/new",TEST_BILLETE_ID))
		.andExpect(status().isOk())
		.andExpect(view().name("billetes/createOrUpdateEquipajeForm"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/billetes/{billeteId}/equipajes/new",TEST_BILLETE_ID)
				.with(csrf())
				.param("dimensiones", "110x110x78")
				.param("peso", "21")
				.param("precio", "30."))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/billetes/datos"));
		
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/billetes/{billeteId}/equipajes/new",TEST_BILLETE_ID)
				.with(csrf())
				.param("dimensiones", "10x110x78")
				.param("peso", "21")
				.param("precio", "30."))
		.andExpect(status().isOk())
		.andExpect(model().attributeHasErrors("equipaje"))
		.andExpect(model().attributeHasFieldErrors("equipaje", "dimensiones"))
		.andExpect(view().name("billetes/createOrUpdateEquipajeForm"));
	}

}
