package org.springframework.samples.aerolineasAAAFC.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.aerolineasAAAFC.configuration.SecurityConfiguration;
import org.springframework.samples.aerolineasAAAFC.model.Cliente;
import org.springframework.samples.aerolineasAAAFC.model.User;
import org.springframework.samples.aerolineasAAAFC.service.AuthoritiesService;
import org.springframework.samples.aerolineasAAAFC.service.ClienteService;
import org.springframework.samples.aerolineasAAAFC.service.UserService;
import org.springframework.samples.aerolineasAAAFC.service.VueloService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers=ClienteController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
public class ClienteControllerTests {

	private static final int TEST_CLIENTE_ID = 2;

	@Autowired
	private ClienteController clienteController;

	@MockBean
	private ClienteService clienteService;

	@MockBean
	private UserService userService;

	@MockBean
	private AuthoritiesService authoritiesService; 
	
	@MockBean
	private VueloService vueloService;

	@Autowired
	private MockMvc mockMvc;

	private Cliente dolores;

	private User doloresUser;

	@BeforeEach
	void setup() {

		dolores = new Cliente();
		dolores.setId(TEST_CLIENTE_ID);
		dolores.setNombre("Dolores");
		dolores.setApellidos("Ramos Ceballos");
		dolores.setNif("29565800A");
		dolores.setDireccionFacturacion("Calle Parera ,15 5ºA - 41011 Sevilla");
		dolores.setIban("ES 4422000418403334567812");
		dolores.setFechaNacimiento(LocalDate.of(1989, 12, 03));

		doloresUser = new User();
		doloresUser.setUsername("29565800A");
		doloresUser.setPassword("EEEEE");
		doloresUser.setMatchingPassword("EEEEE");
		dolores.setUser(doloresUser);


		given(this.clienteService.findClienteById(TEST_CLIENTE_ID)).willReturn(dolores);
	}

	//TESTS CREACIÓN

	@WithMockUser(value = "spring")
	@Test
	void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/clientes/new"))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("cliente"))
		.andExpect(view().name("clientes/createOrUpdateClienteForm"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/clientes/new")
				.param("nombre", "Juan Jesús")
				.param("apellidos", "Ferrero Gutiérrez")
				.with(csrf())
				.param("nif", "28976897W")
				.param("direccionFacturacion", "Calle Carbón, 35 4ºB Sevilla")
				.param("iban", "ES 6621000418401234567893")
				.param("fechaNacimiento", "1995-03-08")
				.param("email", "juanjeferrero@outlook.com")
				.param("user.username", "28976897W")
				.param("user.password", "AAAAAAA")
				.param("user.matchingPassword", "AAAAAAA"))
		.andExpect(status().is3xxRedirection());
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/clientes/new")
				.with(csrf())
				.param("nombre", "Juan Jesús")
				.param("apellidos", "Ferrero Gutiérrez")
				.param("nif", "")
				.param("direccionFacturacion", "Calle Carbón, 35 4ºB Sevilla")
				.param("iban", "ES 6621000418401234567893")
				.param("fechaNacimiento", "2013-03-08")
				.param("email", "juanjeferrero@outlook.com")
				.param("user.username", "28976897W")
				.param("user.password", "AAAAAAA"))
		.andExpect(status().isOk())
		.andExpect(model().attributeHasErrors("cliente"))
		.andExpect(model().attributeHasFieldErrors("cliente", "nif"))
		.andExpect(model().attributeHasFieldErrors("cliente", "fechaNacimiento"))
		.andExpect(view().name("clientes/createOrUpdateClienteForm"));
	}

	//TESTS BÚSQUEDA

	@WithMockUser(value = "spring")
	@Test
	void testInitFindForm() throws Exception {
		mockMvc.perform(get("/clientes/find")).andExpect(status().isOk()).andExpect(model().attributeExists("cliente"))
		.andExpect(view().name("clientes/findClientes"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessFindFormByNif() throws Exception {
		given(this.clienteService.findClienteByNif(dolores.getNif())).willReturn(dolores);
		Logger.getLogger(ClienteControllerTests.class.getName()).log(Level.INFO, "Dolores: " + dolores);

		mockMvc.perform(get("/clientes").param("nif", "29565800A")).andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/clientes/" + TEST_CLIENTE_ID));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessFindFormNoClientesFound() throws Exception {
		mockMvc.perform(get("/clientes").param("nif", "29565800A")).andExpect(status().isOk())
		.andExpect(model().attributeHasFieldErrors("cliente", "nif"))
		.andExpect(model().attributeHasFieldErrorCode("cliente", "nif", "notFound"))
		.andExpect(view().name("clientes/findClientes"));
	}

	//TESTS ACTUALIZACIÓN

	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdateClienteSuccess() throws Exception {
		mockMvc.perform(post("/clientes/{clienteId}/edit",TEST_CLIENTE_ID)
				.with(csrf())
				.param("nombre", "Dolores")
				.param("apellidos", "Ramos Ceballos")
				.param("nif", "86437755Y")
				.param("direccionFacturacion", "Calle Parera, 23 1ºD - 41011 Sevilla")
				.param("iban", "ES 6621000418401234567893")
				.param("fechaNacimiento", "1997-06-03")
				.param("email", "juanjeferrero@outlook.com")
				.param("user.username", "86437755Y")
				.param("user.password", "AAAAAAA"))
		.andExpect(view().name("redirect:/clientes/{clienteId}"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdateClienteError() throws Exception {
		mockMvc.perform(post("/clientes/{clienteId}/edit",TEST_CLIENTE_ID)
				.with(csrf())
				.param("nombre", "Dolores")
				.param("apellidos", "Ramos Ceballos")
				.param("nif", "29565800A")
				.param("direccionFacturacion", "")
				.param("iban", "")
				.param("fechaNacimiento", "1997-06-03")
				.param("email", "juanjeferrero@outlook.com")
				.param("user.username", "29565800A")
				.param("user.password", "AAAAAAA")
				.param("user.password", "AAAAAAA"))
		.andExpect(status().isOk())
		.andExpect(model().attributeHasErrors("cliente"))
		.andExpect(model().attributeHasFieldErrors("cliente", "direccionFacturacion"))
		.andExpect(model().attributeHasFieldErrors("cliente", "iban"))
		.andExpect(view().name("clientes/createOrUpdateClienteForm"));
	}

}
