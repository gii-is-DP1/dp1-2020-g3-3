package org.springframework.samples.aerolineasAAAFC.web;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
public class UserControllerTests {

	@Autowired
	private WebApplicationContext context;    

	private MockMvc mockMvc;

	private int TEST_USER_ID = 1;



	@BeforeEach
	public void setup() {
		mockMvc = MockMvcBuilders
				.webAppContextSetup(context)
				.apply(SecurityMockMvcConfigurers.springSecurity())
				.build();
	}


	//PERFILES
	@WithMockUser(username ="01446551N",authorities = {"cliente"})
	@Test
	void successfullTestGetPerfilCliente() throws Exception {
		mockMvc.perform(get("/users/miPerfil"))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/clientes/"+TEST_USER_ID));
	}

	@WithMockUser(username ="21333214R",authorities = {"azafato"})
	@Test
	void successfullTestGetPerfilAzafato() throws Exception {
		mockMvc.perform(get("/users/miPerfil"))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/azafatos/"+TEST_USER_ID));
	}

	@WithMockUser(username ="17579447H",authorities = {"personalControl"})
	@Test
	void successfullTestGetPerfilPControl() throws Exception {
		mockMvc.perform(get("/users/miPerfil"))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/controladores/"+TEST_USER_ID));
	}

	@WithMockUser(username ="76188332G",authorities = {"personalOficina"})
	@Test
	void successfullTestGetPerfilPOficina() throws Exception {
		mockMvc.perform(get("/users/miPerfil"))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/personalOficina/"+TEST_USER_ID));
	}

	@WithMockUser(username ="29565800A",authorities = {"cliente"})
	@Test
	void unsuccessfullTestGetPerfilCliente() throws Exception {
		mockMvc.perform(get("/clientes/"+TEST_USER_ID))
		.andExpect(status().isOk())
		.andExpect(view().name("clientes/clienteDetails"))
		.andExpect(model().attributeExists("cliente"))
		.andExpect(model().attribute("cliente", hasProperty("id",is(2))));
	}

	@WithMockUser(username ="65519676J",authorities = {"azafato"})
	@Test
	void unsuccessfullTestGetPerfilAzafato() throws Exception {
		mockMvc.perform(get("/azafatos/"+TEST_USER_ID))
		.andExpect(status().isOk())
		.andExpect(view().name("azafatos/azafatoDetails"))
		.andExpect(model().attributeExists("azafato"))
		.andExpect(model().attribute("azafato", hasProperty("id",is(2))));
	}

	@WithMockUser(username ="12355435L",authorities = {"personalControl"})
	@Test
	void unsuccessfullTestGetPerfilPControl() throws Exception {
		mockMvc.perform(get("/controladores/"+TEST_USER_ID))
		.andExpect(status().isOk())
		.andExpect(view().name("controladores/controladorDetails"))
		.andExpect(model().attributeExists("personalControl"))
		.andExpect(model().attribute("personalControl", hasProperty("id",is(2))));
	}


	@WithMockUser(username ="39658948W",authorities = {"personalOficina"})
	@Test
	void unsuccessfullTestGetPerfilPOficina() throws Exception {
		mockMvc.perform(get("/personalOficina/"+TEST_USER_ID))
		.andExpect(status().isOk())
		.andExpect(view().name("personalOficina/oficinistaDetails"))
		.andExpect(model().attributeExists("personalOficina"))
		.andExpect(model().attribute("personalOficina", hasProperty("id",is(2))));
	}	

	//ACCESO
	@WithMockUser(username ="01446551N",authorities = {"cliente"})
	@Test
	void successfullTestAccesoCliente() throws Exception {
		mockMvc.perform(get("/vuelos"))
		.andExpect(status().is(HttpStatus.FORBIDDEN.value()));
		mockMvc.perform(get("/aviones"))
		.andExpect(status().is(HttpStatus.FORBIDDEN.value()));
  		mockMvc.perform(get("/aeropuertos"))
		.andExpect(status().is(HttpStatus.FORBIDDEN.value()));
  		mockMvc.perform(get("/clientes"))
		.andExpect(status().is(HttpStatus.FORBIDDEN.value()));
	}

	@WithMockUser(username ="39658948W",authorities = {"personalOficina"})
	@Test
	void successfullTestAccesoOficinista() throws Exception {
		mockMvc.perform(get("/personalOficina"))
		.andExpect(status().is(HttpStatus.FORBIDDEN.value()));
		mockMvc.perform(get("/clientes"))
		.andExpect(status().isOk())
		.andExpect(view().name("clientes/clientesList"));
		mockMvc.perform(get("/aeropuertos"))
		.andExpect(status().isOk())
		.andExpect(view().name("aeropuertos/aeropuertosList"));
	}

	@WithMockUser(username ="17579447H",authorities = {"personalControl"})
	@Test
	void successfullTestAccesoPcontrol() throws Exception {
		mockMvc.perform(get("/personalControl"))
		.andExpect(status().is(HttpStatus.FORBIDDEN.value()));
		mockMvc.perform(get("/clientes"))
		.andExpect(status().isOk())
		.andExpect(view().name("clientes/clientesList"));
		mockMvc.perform(get("/aviones"))
		.andExpect(status().isOk())
		.andExpect(view().name("aviones/avionesList"));
	}

	@WithMockUser(username ="21333214R",authorities = {"azafato"})
	@Test
	void successfullTestAccesoAzafato() throws Exception {
		mockMvc.perform(get("/azafato"))
		.andExpect(status().is(HttpStatus.FORBIDDEN.value()));
		mockMvc.perform(get("/clientes"))
		.andExpect(status().isOk())
		.andExpect(view().name("clientes/clientesList"));
		mockMvc.perform(get("/vuelos"))
		.andExpect(status().isOk())
		.andExpect(view().name("vuelos/vuelosList"));
	}
}
