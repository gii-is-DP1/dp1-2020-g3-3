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
import org.springframework.samples.aerolineasAAAFC.model.Aeropuerto;
import org.springframework.samples.aerolineasAAAFC.service.AeropuertoService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers=AeropuertoController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration = SecurityConfiguration.class)
public class AeropuertoControllerTests {
	
	private static final int TEST_AERO_ID = 3;
	
	@Autowired
	private AeropuertoController aeropuertoController;
	
	@MockBean
	private AeropuertoService aeropuertoService;
	
	@Autowired
	private MockMvc mockMvc;
	private Aeropuerto ejemplo;
	
	@BeforeEach
	void setup() {
		ejemplo = new Aeropuerto();
		ejemplo.setId(TEST_AERO_ID);
		ejemplo.setNombre("Aeropuerto Josep Tarradellas Barcelona-El Prat");
		ejemplo.setLocalizacion("Barcelona, España");
		ejemplo.setCodigoIATA("BCN");
		ejemplo.setTelefono("+34913211000");
		
		given(this.aeropuertoService.findAeropuertoById(TEST_AERO_ID)).willReturn(ejemplo);
	}
	
	//TEST DE INSERCIÓN
	@WithMockUser(value = "spring")
	@Test
	void testInitCreationForm() throws Exception{
		mockMvc.perform(get("/aeropuertos/new"))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("aeropuerto"))
		.andExpect(view().name("aeropuertos/createOrUpdateAeropuertoForm"));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormSuccess() throws Exception{
		mockMvc.perform(post("/aeropuertos/new")
				.param("nombre", "Aeropuerto de Gran Canaria")
				.param("localizacion", "Las Palmas, España")
				.with(csrf())
				.param("codigoIATA", "LPA")
				.param("telefono", "+34928579130"))
		.andExpect(status().is3xxRedirection());
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormHasErrors() throws Exception{
		mockMvc.perform(post("/aeropuertos/new")
				.param("nombre", "Aeropuerto de Gran Canaria")
				.param("localizacion", "Las Palmas, España")
				.with(csrf())
				.param("codigoIATA", "LPA")
				.param("telefono", "923 238 380"))
		.andExpect(status().isOk())
		.andExpect(model().attributeHasErrors("aeropuerto"))
		.andExpect(model().attributeHasFieldErrors("aeropuerto", "telefono"))
		.andExpect(view().name("aeropuertos/createOrUpdateAeropuertoForm"));
	}
	
	//TEST DE ACTUALIZACION
	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdateAeropuertoSuccess() throws Exception{
		mockMvc.perform(post("/aeropuertos/{aeropuertoId}/edit", TEST_AERO_ID)
				.with(csrf())
				.param("nombre", "Aeropuerto Internacional Josep Tarradellas Barcelona-El Prat")
				.param("localizacion", "Barcelona, Cataluña")
				.param("codigoIATA", "BCN")
				.param("telefono", "+34913211000"))
		.andExpect(view().name("redirect:/aeropuertos/{aeropuertoId}"));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdateAeropuertoError() throws Exception{
		mockMvc.perform(post("/aeropuertos/{aeropuertoId}/edit", TEST_AERO_ID)
				.with(csrf())
				.param("nombre", "Aeropuerto Josep Tarradellas Barcelona-El Prat")
				.param("localizacion", "Barcelona, España")
				.param("codigoIATA", "BCN")
				.param("telefono", "123 467 898"))
		.andExpect(status().isOk())
		.andExpect(model().attributeHasErrors("aeropuerto"))
		.andExpect(model().attributeHasFieldErrors("aeropuerto", "telefono"))
		.andExpect(view().name("aeropuertos/createOrUpdateAeropuertoForm"));
	}
}
