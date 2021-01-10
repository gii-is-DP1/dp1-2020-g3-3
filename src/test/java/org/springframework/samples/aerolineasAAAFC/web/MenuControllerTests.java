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
import org.springframework.samples.aerolineasAAAFC.model.menu.PlatoBase;
import org.springframework.samples.aerolineasAAAFC.model.menu.PlatoType;
import org.springframework.samples.aerolineasAAAFC.service.BilleteService;
import org.springframework.samples.aerolineasAAAFC.service.PlatoBaseService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers=MenuController.class,
includeFilters = @ComponentScan.Filter(value = PlatoFormatter.class, type = FilterType.ASSIGNABLE_TYPE),
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
public class MenuControllerTests {

	private static final int TEST_BILLETE_ID = 1;
	
	@MockBean
	private BilleteService billeteService;
	
	@MockBean
	private PlatoBaseService platoBaseService;

	@Autowired
	private MockMvc mockMvc;

	private Billete aSevillaVamos;
	
	private PlatoBase p1;
	private PlatoBase p2;
	private PlatoBase p3;
	
	private PlatoType pt1;
	private PlatoType pt2;
	private PlatoType pt3;

	@BeforeEach
	void setup() {
		p1 = new PlatoBase();
		p2 = new PlatoBase();
		p3 = new PlatoBase();
		
		pt1 = new PlatoType();
		pt2 = new PlatoType();
		pt3 = new PlatoType();
		
		pt1.setName("primerPlato");
		pt2.setName("segundoPlato");
		pt3.setName("primerPlato");
		
		p1.setName("Sopa de miso");
		p2.setName("Risotto vegetal");
		p3.setName("Manzana");
		p1.setPrecio(1.21);
		p2.setPrecio(1.21);
		p3.setPrecio(1.21);
		p1.setTipoPlato(pt1);
		p2.setTipoPlato(pt2);
		p3.setTipoPlato(pt3);
		
		given(this.platoBaseService.findPlatosBase()).willReturn(Lists.newArrayList(p1,p2,p3));
		
		aSevillaVamos = this.billeteService.findBilleteById(TEST_BILLETE_ID);
		given(this.billeteService.findBilleteById(TEST_BILLETE_ID)).willReturn(aSevillaVamos);
	}

	//TESTS CREACIÓN

	@WithMockUser(value = "spring")
	@Test
	void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/billetes/{billeteId}/menus/new",TEST_BILLETE_ID))
		.andExpect(status().isOk())
		.andExpect(view().name("billetes/createOrUpdateMenuForm"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/billetes/{billeteId}/menus/new",TEST_BILLETE_ID)
				.with(csrf())
				.param("plato1", "Sopa de miso")
				.param("plato2", "Risotto vegetal")
				.param("plato3", "Manzana"))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/billetes/datos"));
		
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/billetes/{billeteId}/menus/new",TEST_BILLETE_ID)
				.with(csrf())
				.param("plato1", "Sopa de miso")
				.param("plato2", "Eaaaaa")
				.param("plato3", "Esta"))
		.andExpect(model().attributeHasErrors("menu"))
		.andExpect(model().attributeHasFieldErrors("menu", "plato2"))
		.andExpect(model().attributeHasFieldErrors("menu", "plato3"))
		.andExpect(view().name("billetes/createOrUpdateMenuForm"));
	}

}
