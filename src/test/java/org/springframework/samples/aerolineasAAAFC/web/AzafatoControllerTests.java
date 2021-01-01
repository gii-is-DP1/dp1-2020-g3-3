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
		Martina.setNif("89565804G");

		IdiomaType lng = new IdiomaType();
		lng.setIdioma("FR");
		IdiomaType lng2 = new IdiomaType();
		lng.setIdioma("ES");
		Set<IdiomaType> lngs = new HashSet<IdiomaType>();
		lngs.add(lng);
		lngs.add(lng2);
		Martina.setIdiomas(lngs);

		Martina.setSalario(1200.);
		
		martinaUser = new User();
		martinaUser.setEnabled(true);
		martinaUser.setUsername("89565804G");
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
				.param("nif", "589614237D")
				.param("iban", "ES 3332020458401202067812")
				.param("idiomas", "[frances,español]")
				.param("salario", "1400")
				.param("user.username", "589614237D")
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
				.param("salario", "nosalario"))
		.andExpect(status().isOk())
		.andExpect(model().attributeHasErrors("azafato"))
		.andExpect(model().attributeHasFieldErrors("cliente", "salario"))
		.andExpect(view().name("azafatos/createOrUpdateAzafatoForm"));
	}





	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdateAzafatosFormSuccess() throws Exception {
		mockMvc.perform(post("/azafatos/{azafatoId}/edit", TEST_AZAFATO_ID)
				.with(csrf())
				.param("nombre", "Amelie")
				.param("apellidos", "Meyer")
				.param("nif", "89565804G")
				.param("iban", "ES 3332020448401202067812")
				.param("salario", "1600."))
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
				.param("salario", "XXX"))
		.andExpect(status().isOk())
		.andExpect(model().attributeHasErrors("azafato"))
		.andExpect(model().attributeHasFieldErrors("azafato", "nif"))
		.andExpect(model().attributeHasFieldErrors("azafato", "iban"))
		.andExpect(model().attributeHasFieldErrors("azafato", "salario"))
		.andExpect(view().name("azafatos/createOrUpdateAzafatoForm"));
	}












}
