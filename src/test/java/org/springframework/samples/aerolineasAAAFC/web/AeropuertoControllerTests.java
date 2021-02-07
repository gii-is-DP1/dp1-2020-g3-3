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

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.beans.HasProperty;
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
import org.springframework.samples.aerolineasAAAFC.model.Aeropuerto;
import org.springframework.samples.aerolineasAAAFC.service.AeropuertoService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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
		ejemplo.setVersion(1);
		
		given(this.aeropuertoService.findAeropuertoById(TEST_AERO_ID)).willReturn(ejemplo);
		
		List<Aeropuerto> lista = new ArrayList<Aeropuerto>();
		lista.add(ejemplo);
		Page pagina = new PageImpl<Aeropuerto>(lista);
		Pageable paging = PageRequest.of(0, 20);
		
		given(this.aeropuertoService.findAeropuertos(paging)).willReturn(pagina);
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
	void testInitUpdateForm() throws Exception{
		mockMvc.perform(get("/aeropuertos/{aeropuertoId}/edit", TEST_AERO_ID))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("aeropuerto"))
		.andExpect(model().attribute("aeropuerto", hasProperty("nombre", is("Aeropuerto Josep Tarradellas Barcelona-El Prat"))))
		.andExpect(model().attribute("aeropuerto", hasProperty("localizacion", is("Barcelona, España"))))
		.andExpect(model().attribute("aeropuerto", hasProperty("codigoIATA", is("BCN"))))
		.andExpect(model().attribute("aeropuerto", hasProperty("telefono", is("+34913211000"))))
		.andExpect(view().name("aeropuertos/createOrUpdateAeropuertoForm"));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdateAeropuertoSuccess() throws Exception{
		mockMvc.perform(post("/aeropuertos/{aeropuertoId}/edit", TEST_AERO_ID)
				.with(csrf())
				.param("nombre", "Aeropuerto Internacional Josep Tarradellas Barcelona-El Prat")
				.param("localizacion", "Barcelona, Cataluña")
				.param("codigoIATA", "BCN")
				.param("telefono", "+34913211000"))
		.andExpect(view().name("redirect:/aeropuertos"));
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
	
	
	//TEST DE ELIMINACIÓN
	@WithMockUser(value = "spring")
	@Test
	void testProcessDeleteAeropuerto() throws Exception{
		mockMvc.perform(get("/aeropuertos/{aeropuertoId}/delete", TEST_AERO_ID))
		.andExpect(status().isFound())
		.andExpect(model().attributeDoesNotExist("aeropuerto"))
		.andExpect(view().name("redirect:/aeropuertos"));
	}
	
	
	//OTROS
	@WithMockUser(value = "spring")
	@Test
	void testShowAeropuerto() throws Exception {
		mockMvc.perform(get("/aeropuertos/{aeropuertoId}", TEST_AERO_ID))
		.andExpect(model().attribute("aeropuerto", hasProperty("nombre", is("Aeropuerto Josep Tarradellas Barcelona-El Prat"))))
		.andExpect(model().attribute("aeropuerto", hasProperty("localizacion", is("Barcelona, España"))))
		.andExpect(model().attribute("aeropuerto", hasProperty("codigoIATA", is("BCN"))))
		.andExpect(model().attribute("aeropuerto", hasProperty("telefono", is("+34913211000"))))
		.andExpect(view().name("aeropuertos/aeropuertoDetails"));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testShowAeropuertoList() throws Exception {
		mockMvc.perform(get("/aeropuertos"))
		.andExpect(view().name("aeropuertos/aeropuertosList"));
	}
	
}
