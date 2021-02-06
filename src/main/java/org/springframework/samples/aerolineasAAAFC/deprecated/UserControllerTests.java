//package org.springframework.samples.aerolineasAAAFC.deprecated;
//
//import static org.mockito.BDDMockito.given;
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
//
//import java.util.Optional;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.samples.aerolineasAAAFC.model.Authorities;
//import org.springframework.samples.aerolineasAAAFC.model.User;
//import org.springframework.samples.aerolineasAAAFC.service.AuthoritiesService;
//import org.springframework.samples.aerolineasAAAFC.service.ClienteService;
//import org.springframework.samples.aerolineasAAAFC.service.UserService;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.web.servlet.MockMvc;
//
//public class UserControllerTests {
//
//	private static final String TEST_USUARIOCLIENTE_ID = "01446551N";
//	
//	@Autowired
//	private UserController userController;
//	
//	@MockBean
//	private UserService userService;
//	
//	@MockBean
//	private AuthoritiesService authoritiesService;
//	
//	@MockBean
//	private ClienteService clienteService;
//	
//	@Autowired
//	private MockMvc mockMvc;
//
//	
//	//INSERCIÓN
//	@WithMockUser(value = "spring")
//	@Test
//	void testInitCreationForm() throws Exception {
//		mockMvc.perform(get("/user/new"))
//		.andExpect(status().isOk())
//		.andExpect(model().attributeExists("cliente"))
//		.andExpect(view().name("users/createClienteForm"));
//	}
//	
//	@WithMockUser(value = "spring")
//	@Test
//	void testProcessCreationFormSuccess() throws Exception{
//		mockMvc.perform(post("/user/new")
//				.param("nombre", "Pepe")
//				.param("apellidos", "Pepin")
//				.param("nif", "33083215S")
//				.param("direccionFacturacion", "Calle Pepe")
//				.param("iban", "ES 8514658879073713989322")
//				.with(csrf())
//				.param("fechaNacimiento", "1995-03-08")
//				.param("email", "pepe@gmail.com")
//				.param("user.username", "33083215S")
//				.param("user.password", "Pepe"))
//		.andExpect(status().is3xxRedirection());
//	}
//	
//	@WithMockUser(value = "spring")
//	@Test
//	void testProcessCreationFormHasErrors() throws Exception{
//		mockMvc.perform(post("/user/new")
//				.param("nombre", "Pepe")
//				.param("apellidos", "Pepin")
//				.param("nif", "33083215S")
//				.param("direccionFacturacion", "Calle Pepe")
//				.param("iban", "")
//				.with(csrf())
//				.param("fechaNacimiento", "1995-03-08")
//				.param("email", "pepe@gmail.com")
//				.param("user.username", "holahola")
//				.param("user.password", "Pepe"))
//		.andExpect(status().isOk())
//		.andExpect(model().attributeHasErrors("cliente"))
//		.andExpect(model().attributeHasFieldErrors("cliente", "iban"))
//		.andExpect(model().attributeHasFieldErrors("cliente", "user.username"))
//		.andExpect(view().name("clientes/createOrUpdateClienteForm"));
//	}
//	
//	@WithMockUser(value = "spring")
//	@Test
//	void testgetMiPerfilCliente() throws Exception {
//		User defaultUserC = this.userService.findUser(TEST_USUARIOCLIENTE_ID).get();
//		Logger.getLogger(ClienteControllerTests.class.getName()).log(Level.INFO, "defaultUser: " + defaultUserC);
//
//		mockMvc.perform(get("/users/miPerfil").param("username", "TEST_USUARIO_ID")).andExpect(status().is3xxRedirection())
//		.andExpect(view().name("redirect:/clientes/" + 1));
//	}
//	
//}
