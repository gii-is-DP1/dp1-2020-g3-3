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
import java.time.Month;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.assertj.core.util.Lists;
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
import org.springframework.samples.aerolineasAAAFC.model.Azafato;
import org.springframework.samples.aerolineasAAAFC.model.IdiomaType;
import org.springframework.samples.aerolineasAAAFC.model.User;
import org.springframework.samples.aerolineasAAAFC.service.AuthoritiesService;
import org.springframework.samples.aerolineasAAAFC.service.AzafatoService;
import org.springframework.samples.aerolineasAAAFC.service.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers=AzafatoController.class,
includeFilters = @ComponentScan.Filter(value = IdiomaTypeFormatter.class, type = FilterType.ASSIGNABLE_TYPE),
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
public class AzafatoControllerTests {



	private static final int TEST_AZAFATO_ID = 2;

	@Autowired
	private AzafatoController azafatoController;

	@MockBean
	private AzafatoService azafatoService;

	@MockBean
	private UserService userService;

	@MockBean
	private AuthoritiesService authoritiesService; 

	@Autowired
	private MockMvc mockMvc;

	private Azafato Martina;

	private User martinaUser;

	@BeforeEach
	void setup() {

		Martina = new Azafato();
		Martina.setId(TEST_AZAFATO_ID);
		Martina.setNombre("Martina");
		Martina.setApellidos("Perez");
		Martina.setNif("11571749N");
		Martina.setIban("ES 01225905418408934560815");
		

		IdiomaType lng = new IdiomaType();
		lng.setIdioma("FR");
		IdiomaType lng2 = new IdiomaType();
		lng2.setIdioma("ES");
		
		given(this.azafatoService.findIdiomaTypes()).willReturn(Lists.newArrayList(lng,lng2));

		Martina.setSalario(1200.);
		
		Set<IdiomaType> s = new HashSet<IdiomaType>();
		s.add(lng);
		s.add(lng2);
		Martina.setIdiomas(s);
		
		martinaUser = new User();
		martinaUser.setUsername("11571749N");
		martinaUser.setPassword("EEEEE");
		Martina.setUser(martinaUser);

		given(this.azafatoService.findAzafatoById(TEST_AZAFATO_ID)).willReturn(Martina);
		
		List<Azafato> lista = new ArrayList<Azafato>();
		lista.add(Martina);
		Page pagina = new PageImpl<Azafato>(lista);
		Pageable paging = PageRequest.of(0, 20);
		
		given(this.azafatoService.findAzafatos(paging)).willReturn(pagina);
	}



	//TEST CREACIÓN
	@WithMockUser(value = "spring")
	@Test
	void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/azafatos/new"))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("azafato"))
		.andExpect(view().name("azafatos/createOrUpdateAzafatoForm"));
	}


	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/azafatos/new")
				.param("nombre", "Gonzalo")
				.param("apellidos", "Gonzalez")
				.with(csrf())
				.param("nif", "11571749N")
				.param("iban", "ES 3332020458401202067812")
				.param("idiomas", "FR","ES")
				.param("salario", "1400")
				.param("user.username", "11571749N")
				.param("user.password", "AAAAAAA"))
		.andExpect(status().is3xxRedirection());
	}


	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/azafatos/new")
				.param("nombre", "Gonzalo")
				.param("apellidos", "Gonzalez")
				.with(csrf())
				.param("nif", "11571749N")
				.param("iban", "ES 3332020458401202067812")
				.param("idiomas", "E")
				.param("salario", "nosalario")
				.param("user.username", "11571749N")
				.param("user.password", "AAAAAAA"))
		.andExpect(status().isOk())
		.andExpect(model().attributeHasErrors("azafato"))
		.andExpect(model().attributeHasFieldErrors("azafato", "salario"))
		.andExpect(model().attributeHasFieldErrors("azafato", "idiomas"))
		.andExpect(view().name("azafatos/createOrUpdateAzafatoForm"));
	}

	
	//TEST ACTUALIZACIÓN
    @WithMockUser(value = "spring")
	@Test
	void testInitUpdateForm() throws Exception {
		mockMvc.perform(get("/azafatos/{azafatoId}/edit", TEST_AZAFATO_ID))
				.andExpect(status().isOk()).andExpect(model().attributeExists("azafato"))
				.andExpect(view().name("azafatos/createOrUpdateAzafatoForm"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdateAzafatosFormSuccess() throws Exception {
		mockMvc.perform(post("/azafatos/{azafatoId}/edit", TEST_AZAFATO_ID)
				.with(csrf())
				.param("nombre", "Amelie")
				.param("apellidos", "Meyer")
				.param("nif", "11571749N")
				.param("iban", "ES 3332020458401202067812")
				.param("idiomas", "FR","ES")
				.param("salario", "1600.")
				.param("user.username", "11571749N")
				.param("user.password", "AAAAAAA"))
		.andExpect(view().name("redirect:/azafatos/{azafatoId}"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdateAzafatoFormError() throws Exception {
		mockMvc.perform(post("/azafatos/{azafatoId}/edit", TEST_AZAFATO_ID)
				.with(csrf())
				.param("nombre", "Erik")
				.param("apellidos", "Fischer")
				.param("nif", "XXX")
				.param("iban", "XXX")
				.param("idiomas", "Frrr")
				.param("salario", "XXX")
				.param("user.username", "11571749N")
				.param("user.password", "AAAAAAA"))
		.andExpect(status().isOk())
		.andExpect(model().attributeHasErrors("azafato"))
		.andExpect(model().attributeHasFieldErrors("azafato", "nif"))
		.andExpect(model().attributeHasFieldErrors("azafato", "iban"))
		.andExpect(model().attributeHasFieldErrors("azafato", "idiomas"))
		.andExpect(model().attributeHasFieldErrors("azafato", "salario"))
		.andExpect(view().name("azafatos/createOrUpdateAzafatoForm"));
	}
	
	//TEST DE ELIMINACIÓN
	@WithMockUser(value = "spring")
	@Test
	void testDeleteCliente() throws Exception{
		mockMvc.perform(get("/azafatos/{azafatoId}/delete", TEST_AZAFATO_ID))
		.andExpect(status().isFound())
		.andExpect(model().attributeDoesNotExist("azafato"))
		.andExpect(view().name("redirect:/azafatosList"));
	}
	
	//OTROS
	@WithMockUser(value = "spring")
	@Test
	void testShowAzafatosList() throws Exception{
		mockMvc.perform(get("/azafatos"))
		.andExpect(model().attributeExists("azafatos"))
		.andExpect(view().name("azafatos/azafatosList"));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testShowAzafato() throws Exception {
		mockMvc.perform(get("/azafatos/{azafatoId}", TEST_AZAFATO_ID))
		.andExpect(model().attribute("azafato", hasProperty("nombre", is("Martina"))))
		.andExpect(model().attribute("azafato", hasProperty("apellidos", is("Perez"))))
		.andExpect(model().attribute("azafato", hasProperty("nif", is("11571749N"))))
		.andExpect(model().attribute("azafato", hasProperty("iban", is("ES 01225905418408934560815"))))
		.andExpect(model().attribute("azafato", hasProperty("salario", is(1200.))))
		.andExpect(model().attributeExists("idioma_types"))
		.andExpect(view().name("azafatos/azafatoDetails"));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testshowVuelosList() throws Exception {
		mockMvc.perform(get("/azafatos/{azafatoId}/horario", 1)
				.param("fecha", LocalDate.of(2021, 01, 01).toString()))
		.andExpect(model().attributeExists("vuelos"))
		.andExpect(model().attribute("dias", 31))
		.andExpect(model().attribute("mes", Month.JANUARY))
		.andExpect(model().attribute("año", 2021))
		.andExpect(model().attributeExists("diasV"))
		.andExpect(view().name("azafatos/horario"));
	}

}
