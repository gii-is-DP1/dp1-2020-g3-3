package org.springframework.samples.aerolineasAAAFC.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.samples.aerolineasAAAFC.model.Authorities;
import org.springframework.samples.aerolineasAAAFC.model.User;
import org.springframework.samples.aerolineasAAAFC.service.AuthoritiesService;
import org.springframework.samples.aerolineasAAAFC.service.UserService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

public class UserControllerTests {

	private static final String TEST_USUARIOCLIENTE_ID = "01446551N";
	
	@Autowired
	private UserController userController;
	
	@MockBean
	private UserService userService;
	
	@MockBean
	private AuthoritiesService authoritiesService; 
	
	@Autowired
	private MockMvc mockMvc;
	
	
	
	@WithMockUser(value = "spring")
	@Test
	void testgetMiPerfilCliente() throws Exception {
		User defaultUserC = this.userService.findUser(TEST_USUARIOCLIENTE_ID).get();

		given(this.userService.findUser(defaultUserC.getUsername()).get()).willReturn(defaultUserC);
		Logger.getLogger(ClienteControllerTests.class.getName()).log(Level.INFO, "defaultUser: " + defaultUserC);

		mockMvc.perform(get("/users/miPerfil").param("username", "TEST_USUARIO_ID")).andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/clientes/" + 1));
	}
	
}
