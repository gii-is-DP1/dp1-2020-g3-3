package org.springframework.samples.aerolineasAAAFC.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.HashSet;
import java.util.Set;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.aerolineasAAAFC.configuration.SecurityConfiguration;
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
		Martina.setApellidos("Perez");
		Martina.setNombre("Martina");
		Martina.setIban("ES 01225905418408934560815");
		Martina.setNif("11571749N");

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
	}




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
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/azafatos/null")); //OJO, ESTO HAY QUE CAMBIARLO -> en vez de null, ponemos un given que devuelva el id falso
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

}
