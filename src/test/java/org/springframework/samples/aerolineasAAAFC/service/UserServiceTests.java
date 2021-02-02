package org.springframework.samples.aerolineasAAAFC.service;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collection;
import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.aerolineasAAAFC.model.Authorities;
import org.springframework.samples.aerolineasAAAFC.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class UserServiceTests {
	
	@Autowired
	protected UserService userService;
	
	private static final BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();
	
	@Test
	void getUserSuccessful() {
		User user = userService.findUser("01446551N").get();
		
		//USUARIO
		assertThat(user.getUsername()).isEqualTo("01446551N");
		//CONTRASEÑA
		assertThat(bCrypt.matches("Fly_High14&", user.getPassword()));
		//CONFIRMAR CONTRASEÑA
		assertThat(user.getPassword()).isEqualTo(user.getMatchingPassword());
		//AUTORIDADES
		String rol = "";
		Collection<Authorities> autoridad = user.getAuth();
		for(Authorities auth: autoridad) {
			rol = auth.getAuthority();
		}
		assertThat(rol).isEqualTo("cliente");
	}
	
	@Test
	void shouldInsertUser() {
		User user = new User();
		user.setUsername("28845479Y");
		user.setPassword("C0ntr4s3ñ4T3$t");
		user.setMatchingPassword("C0ntr4s3ñ4T3$t");

		userService.saveUser(user);
		
		User usuario = userService.findUser("28845479Y").get();
		
		assertThat(usuario).isNotNull();
	}
	
	@Test
	void shouldNotInsertUser() {
		User user = new User();
		user.setUsername("28845479Y");
		user.setPassword("C0ntr4s3ñ4T3$t");

		assertThrows( ConstraintViolationException.class, () -> { userService.saveUser(user); } );
	}

}
