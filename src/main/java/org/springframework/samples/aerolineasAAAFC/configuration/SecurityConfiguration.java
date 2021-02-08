package org.springframework.samples.aerolineasAAAFC.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.samples.aerolineasAAAFC.service.myUserDetailsService;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @author japarejo
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	DataSource dataSource;
	
	@Autowired
	private myUserDetailsService userDetailsService;
	

	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/resources/**","/webjars/**","/h2-console/**").permitAll()
				.antMatchers(HttpMethod.GET, "/","/oups","/exception").permitAll()
				//.antMatchers("/**").permitAll() TODO para probar el sistema mejor dejar esto descomentado
				
				//Crear un cliente y editarlo
				.antMatchers("/user/new").permitAll()
				.antMatchers("clientes/{clienteId}/edit").authenticated()
				.antMatchers("/clientes/new").hasAnyAuthority("admin","personalOficina")
				//Historial compra cliente
				.antMatchers("/clientes/{clienteId}/compras").hasAnyAuthority("admin","personalOficina")
				
				//Compra billetes
				.antMatchers("/billetes/{vueloId}/new","/billetes/{billeteId}/equipajes/new","/billetes/{billeteId}/menus/new").authenticated()
				
				//Permisos para acceder a los perfiles
				.antMatchers("/users/miPerfil").authenticated()
				.antMatchers("/clientes/{clienteId}").authenticated()
				
				.antMatchers("/controladores/{pControlId}","/controladores/{pControlId}/horario","/controladores/{pControlId}/semana")
				.hasAnyAuthority("admin","personalOficina","personalControl")
				
				.antMatchers("/azafatos/{azafatoId}","/azafatos/{azafatoId}/horario","/azafatos/{azafatoId}/semana")
				.hasAnyAuthority("admin","personalOficina","azafato")
				
				.antMatchers("/personalOficina/{pOficinaId}").hasAnyAuthority("admin","personalOficina")
				
				
				//Permisos para acceder a listas generales
				.antMatchers("/aeropuertos","/aviones","/billetes/datos","/clientes","/clientesfind","/vuelos","vuelos/{vueloId}/clientes","/vuelos/historial")
				.hasAnyAuthority("admin","personalOficina","azafato","personalControl")
				
				.antMatchers("/vuelos/ofertas").hasAnyAuthority("admin","personalOficina")
				//Permisos para acceder a listas del personal
				.antMatchers("/controladores","/controladoresfind","/azafatos","/azafatosfind").hasAnyAuthority("admin","personalOficina")
				//Permisos para acceder la lista de los oficinistas, añadirlos, editarlos
				.antMatchers("/personalOficina","/personalOficinafind","personalOficina/new","personalOficina/{pOficinaId}/edit").hasAuthority("admin")
				//Permisos para acceder al estado de los aviones
				.antMatchers("/aviones/estadoAviones").hasAnyAuthority("admin","personalOficina","personalControl")
				//Permisos para acceder a los detalles de diferentes entidades de la empresa
				.antMatchers("/aeropuertos/{aeropuertoId}","/aviones/{avionId}","vuelos/{vueloId}").hasAnyAuthority("admin","personalOficina","azafato","personalControl")
				
				//Eliminar personal
				.antMatchers("azafatos/{azafatoId}/delete","personalOficina/{pOficinaId}/delete","controladores/{pControlId}/delete").hasAuthority("admin")
				
				
				//Permisos para crear y editar diferentes entidades de la empresa
				.antMatchers("/aeropuertos/new","/aviones/new","/vuelos/new","/controladores/new","/azafatos/new").hasAnyAuthority("admin","personalOficina")
				
				.antMatchers("/aeropuertos/{aeropuertoId}/edit","/aviones/{avionId}/edit","/azafatos/{azafatoId}/edit","/controladores/{pControlId}/edit","/vuelos/{vueloId}/edit")
				.hasAnyAuthority("admin","personalOficina")
				
				//Permisos para eliminar diferentes entidades de la empresa
				.antMatchers("/aeropuertos/{aeropuertoId}/delete","/aviones/{avionId}/delete","clientes/{clienteId}/delete").hasAnyAuthority("admin","personalOficina")
				
				
				
				
				
				
				
				.antMatchers("/users/new").permitAll()		
				.antMatchers("/login*").permitAll()
				.anyRequest().denyAll()
				.and()
				 	.formLogin()
				 	.loginPage("/login").permitAll()
				.and()
					.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/");
                // Configuración para que funcione la consola de administración 
                // de la BD H2 (deshabilitar las cabeceras de protección contra
                // ataques de tipo csrf y habilitar los framesets si su contenido
                // se sirve desde esta misma página.
				http.csrf().disable();
                //http.csrf().ignoringAntMatchers("/h2-console/**");
                http.headers().frameOptions().sameOrigin();
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());

	}

	@Bean
	public DaoAuthenticationProvider authProvider() {
	    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	    authProvider.setUserDetailsService(userDetailsService);
	    authProvider.setPasswordEncoder(passwordEncoder());
	    return authProvider;
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {	    
	    return new BCryptPasswordEncoder();
	}
	
}


