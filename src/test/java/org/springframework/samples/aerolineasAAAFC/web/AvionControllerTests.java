package org.springframework.samples.aerolineasAAAFC.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.aerolineasAAAFC.model.Avion;
import org.springframework.samples.aerolineasAAAFC.service.AuthoritiesService;
import org.springframework.samples.aerolineasAAAFC.service.AvionService;
import org.springframework.samples.aerolineasAAAFC.service.UserService;
import org.springframework.samples.aerolineasAAAFC.service.VueloService;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;



@WebMvcTest(controllers=AvionController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
public class AvionControllerTests {


	private static final int TEST_AVION_ID = 2;

	@Autowired
	private AvionController avionController;

	@MockBean
	private AvionService avionService;

	@MockBean
	private UserService userService;
	
	@MockBean
	private VueloService vueloService;
	
	@MockBean
	private AuthoritiesService authoritiesService; 
	
	@Autowired
	private MockMvc mockMvc;

	private Avion apache;


	@BeforeEach
	void setup() {
		apache =new Avion();
		apache.setId(TEST_AVION_ID);
//		apache.setAzafatos(new HashSet<>());
		apache.setCapacidadPasajero(100);
		
		apache.setDisponibilidad(false);
		apache.setFechaRevision(LocalDate.of(2019, 12, 8));
		apache.setFechaFabricacion(LocalDate.of(2015, 12, 8));
	
		apache.setHorasAcumuladas(12);
//		apache.setPersonalControl(new HashSet<>());
		apache.setPlazasEconomica(3);
		apache.setPlazasEjecutiva(3);
		apache.setPlazasPrimera(65);
		apache.setTipoAvion("AIRBUS");
		apache.setVuelos(new ArrayList<>());
		given(this.avionService.findAvionById(TEST_AVION_ID)).willReturn(apache);
	}
	
	// TEST DE INSERCIÓN
	@WithMockUser(value = "spring")
	@Test
	void testInitCreationForm() throws Exception{
		mockMvc.perform(get("/aviones/new"))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("avion"))
		.andExpect(view().name("aviones/createOrUpdateAvionForm"));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormSuccess() throws Exception{
		mockMvc.perform(post("/aviones/new")
				.with(csrf())
				.param("tipoAvion", "Airbus 420")
				.param("capacidadPasajero", "200")
				.param("pesoMaximoEquipaje", "25")
				.param("horasAcumuladas", "1000")
				.param("fechaFabricacion", "2018/09/10")
				.param("Disponibilidad", "true")
				.param("fechaRevision", "2020/07/10")
				.param("plazasEconomica", "140")
				.param("plazasEjecutiva", "40")
				.param("plazasPrimera", "20"))
		.andExpect(status().is3xxRedirection());
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormHasErrors() throws Exception{
		mockMvc.perform(post("/aviones/new")
				.with(csrf())
				.param("tipoAvion", "Airbus 420")
				.param("capacidadPasajero", "200")
				.param("pesoMaximoEquipaje", "89302")
				.param("horasAcumuladas", "1000")
				.param("fechaFabricacion", "2018/09/10")
				.param("Disponibilidad", "true")
				.param("fechaRevision", "2020/07/10")
				.param("plazasEconomica", "140")
				.param("plazasEjecutiva", "40")
				.param("plazasPrimera", "20"))
		.andExpect(model().attributeHasErrors("avion"))
		.andExpect(model().attributeHasFieldErrors("avion", "pesoMaximoEquipaje"))
		.andExpect(view().name("aviones/createOrUpdateAvionForm"));
	}
	
	// TEST DE ACTUALIZACIÓN
	@WithMockUser(value = "spring")
	@Test
	void testInitUpdateAvionForm() throws Exception {
		mockMvc.perform(get("/aviones/{avionId}/edit", TEST_AVION_ID)).andExpect(status().isOk())
		.andExpect(model().attributeExists("avion"))
		.andExpect(model().attribute("avion", hasProperty("tipoAvion", is("AIRBUS"))))
		.andExpect(model().attribute("avion", hasProperty("capacidadPasajero", is(100))))
		.andExpect(model().attribute("avion", hasProperty("pesoMaximoEquipaje", is(21))))
		.andExpect(model().attribute("avion", hasProperty("horasAcumuladas", is(12))))
		.andExpect(model().attribute("avion", hasProperty("fechaFabricacion", is(LocalDate.of(2015, 12, 8)))))
		.andExpect(model().attribute("avion", hasProperty("disponibilidad", is(false))))
		.andExpect(model().attribute("avion", hasProperty("fechaRevision", is(LocalDate.of(2019, 12, 8)))))
		.andExpect(model().attribute("avion", hasProperty("plazasEconomica", is(3))))
		.andExpect(model().attribute("avion", hasProperty("plazasEjecutiva", is(3))))
		.andExpect(model().attribute("avion", hasProperty("plazasPrimera", is(65))))
		.andExpect(view().name("aviones/createOrUpdateAvionForm"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdateAvionFormSuccess() throws Exception {
		mockMvc.perform (post("/aviones/{avionId}/edit", TEST_AVION_ID)
				.with(csrf())
				.param("tipoAvion", "TIPE3")
				.param("pesoMaximoEquipaje", "12")
				.param("horasAcumuladas", "0")
				.param("fechaFabricacion","2015/12/28")
				.param("fechaRevision","2020/12/28")
				.param("disponibilidad", "true")
				.param("plazasEconomica", "60")
				.param("plazasEjecutiva", "12")
				.param("plazasPrimera", "5"))
			.andExpect(view().name("redirect:/aviones/{avionId}"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdateAvionFormHasErrors() throws Exception {
		mockMvc.perform(post("/aviones/{avionId}/edit", TEST_AVION_ID)
				.with(csrf())
				.param("fechaRevision","2020/12/28")
				.param("disponibilidad", "")
				.param("plazasEjecutiva", "12"))
				.andExpect(status().isOk())
				.andExpect(model().attributeHasErrors("avion"))
				.andExpect(view().name("aviones/createOrUpdateAvionForm"));
	}

}