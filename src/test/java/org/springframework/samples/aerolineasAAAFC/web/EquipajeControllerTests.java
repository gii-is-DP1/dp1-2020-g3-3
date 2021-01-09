package org.springframework.samples.aerolineasAAAFC.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.aerolineasAAAFC.configuration.SecurityConfiguration;
import org.springframework.samples.aerolineasAAAFC.model.Billete;
import org.springframework.samples.aerolineasAAAFC.model.equipaje.EquipajeBase;
import org.springframework.samples.aerolineasAAAFC.service.BilleteService;
import org.springframework.samples.aerolineasAAAFC.service.EquipajeBaseService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers=EquipajeController.class,
includeFilters = @ComponentScan.Filter(value = EquipajeBaseFormatter.class, type = FilterType.ASSIGNABLE_TYPE),
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
public class EquipajeControllerTests {

	private static final int TEST_BILLETE_ID = 1;

	@MockBean
	private BilleteService billeteService;
	
	@MockBean
	private EquipajeBaseService equipajeBaseService;

	@Autowired
	private MockMvc mockMvc;

	private Billete aSevillaVamos;
	
	private EquipajeBase ebAux;

	@BeforeEach
	void setup() {
		ebAux = new EquipajeBase();
		ebAux.setDimensiones("1x1x1");
		ebAux.setName("Micro");
		ebAux.setPrecio(999.);
		
		given(this.equipajeBaseService.findEquipajesBase()).willReturn(Lists.newArrayList(ebAux));

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
				.param("peso", "22")
				.param("equipajeBase", "Micro"))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/billetes/datos"));
		
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/billetes/{billeteId}/equipajes/new",TEST_BILLETE_ID)
				.with(csrf())
				.param("peso", "22")
				.param("equipajeBase", "Tela de grande"))
		.andExpect(model().attributeHasErrors("equipaje"))
		.andExpect(model().attributeHasFieldErrors("equipaje", "equipajeBase"))
		.andExpect(view().name("billetes/createOrUpdateEquipajeForm"));
	}

}
