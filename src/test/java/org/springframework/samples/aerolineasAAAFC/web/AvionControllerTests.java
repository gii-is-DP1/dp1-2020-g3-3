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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.aerolineasAAAFC.model.Avion;
import org.springframework.samples.aerolineasAAAFC.service.AvionService;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(controllers=AvionController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
public class AvionControllerTests {


	private static final int TEST_AVION_ID = 2;

	@Autowired
	private AvionController avionController;

	@MockBean
	@Autowired
	private AvionService avionService;
	
    @MockBean
	private UserService userService;
    
	@MockBean
	private AuthoritiesService authoritiesService; 

	@Autowired
	private MockMvc mockMvc;

	private Avion apache;


	@BeforeEach
	void setup() {
		apache =new Avion();
		apache.setId(TEST_AVION_ID);
		apache.setAzafatos(new HashSet<>());
		apache.setCapacidadPasajero(100);
		apache.setDisponibilidad(true);
		try {
			apache.setFechaRevision(new SimpleDateFormat("yyyy/MM/dd").parse("2019/12/08"));
			apache.setFechaFabricacion(new SimpleDateFormat("yyyy/MM/dd").parse("2015/12/28"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		apache.setHorasAcumuladas(12);
		apache.setPersonalControl(new HashSet<>());
		apache.setPesoMaximoEquipaje(21);
		apache.setPlazasEconomica(3);
		apache.setPlazasEjecutiva(3);
		apache.setPlazasPrimera(65);
		apache.setTipoAvion("AIRBUS");
		apache.setVuelos(new HashSet<>());
		given(this.avionService.findAvionById(TEST_AVION_ID)).willReturn(apache);


	}
	@WithMockUser(value = "spring")
	@Test
	void testInitUpdateAvionForm() throws Exception {
		mockMvc.perform(get("/aviones/{avionId}/edit", TEST_AVION_ID)).andExpect(status().isOk())
		.andExpect(model().attributeExists("avion"))
		.andExpect(model().attribute("avion", hasProperty("tipoAvion", is("AIRBUS"))))
		.andExpect(model().attribute("avion", hasProperty("capacidadPasajero", is(100))))
		.andExpect(model().attribute("avion", hasProperty("pesoMaximoEquipaje", is(21))))
		.andExpect(model().attribute("avion", hasProperty("horasAcumuladas", is(12))))
		.andExpect(model().attribute("avion", hasProperty("fechaFabricacion", is(new SimpleDateFormat("yyyy/MM/dd").parse("2015/12/28")))))
		.andExpect(model().attribute("avion", hasProperty("disponibilidad", is(true))))
		.andExpect(model().attribute("avion", hasProperty("fechaRevision", is(new SimpleDateFormat("yyyy/MM/dd").parse("2019/12/08")))))
		.andExpect(model().attribute("avion", hasProperty("plazasEconomica", is(3))))
		.andExpect(model().attribute("avion", hasProperty("plazasEjecutiva", is(3))))
		.andExpect(model().attribute("avion", hasProperty("plazasPrimera", is(65))))
		.andExpect(view().name("aviones/createOrUpdateAvionForm"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdateOwnerFormSuccess() throws Exception {
		mockMvc.perform((RequestBuilder) ((ResultActions) post("/aviones/{avionId}/edit", TEST_AVION_ID)
				.with(csrf())
				.param("tipoAvion", "TIPE3")
				.param("pesoMaximoEquipaje", "12")
				.param("horasAcumuladas", "0")
				.param("fechaFabricacion","2015/12/28")
				.param("fechaResion","2020/12/28")
				.param("disponibilidad", "true")
				.param("plazasEconomica", "60")
				.param("plazasEjecutiva", "12")
				.param("plazasPrimera", "5"))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/aviones/{avionId}")));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdateOwnerFormHasErrors() throws Exception {
		mockMvc.perform((RequestBuilder) ((ResultActions) post("/aviones/{avionId}/edit", TEST_AVION_ID)
				.with(csrf())
				.param("fechaResion","2020/12/28")
				.param("disponibilidad", "false")
				.param("plazasEconomica", "ocho")
				.param("plazasEjecutiva", "12"))
		.andExpect(status().isOk())
		.andExpect(model().attributeHasErrors("avion"))
		.andExpect(model().attributeHasFieldErrors("avion", "plazasEconomica"))
		.andExpect(view().name("aviones/createOrUpdateAvionForm")));
	}




}








