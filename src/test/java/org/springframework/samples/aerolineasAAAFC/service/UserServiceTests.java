package org.springframework.samples.aerolineasAAAFC.service;


import static org.assertj.core.api.Assertions.assertThat;
import java.util.Collection;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.aerolineasAAAFC.model.Authorities;
import org.springframework.samples.aerolineasAAAFC.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class UserServiceTests {
	
	@Autowired
	protected UserService userService;
	
	private static final BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();
	
	
	//TEST DE CONSULTA
	@Test
	void getUserSuccessful() {
		User user = this.userService.findUser("01446551N").get();
		
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
	
	
	//TEST DE INSERCIÓN
	@Test
	void shouldInsertUser() {
		User user = new User();
		user.setUsername("28845479Y");
		user.setPassword("C0ntr4s3ñ4T3$t");
		user.setMatchingPassword("C0ntr4s3ñ4T3$t");

		this.userService.saveUser(user);
		
		User usuario = this.userService.findUser("28845479Y").get();
		
		assertThat(usuario).isNotNull();
	}

}
