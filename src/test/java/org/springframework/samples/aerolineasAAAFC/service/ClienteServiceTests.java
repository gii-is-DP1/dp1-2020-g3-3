package org.springframework.samples.aerolineasAAAFC.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.validation.ConstraintViolationException;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.samples.aerolineasAAAFC.model.Avion;
import org.springframework.samples.aerolineasAAAFC.model.Cliente;
import org.springframework.samples.aerolineasAAAFC.model.User;
import org.springframework.samples.aerolineasAAAFC.service.exceptions.NifDuplicadoException;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedPetNameException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class ClienteServiceTests {
	
	@Autowired
	protected ClienteService clienteService;
	
	
	//Tests Consultar
	
	@Test
	void getNifClienteSuccessful() {
		Cliente cliente = clienteService.findClienteById(1);
		assertThat(cliente.getNif())
		.isNotEmpty()
		.containsPattern("^\\d{8}[a-zA-Z]$");
	}

	@Test
	void getIbanClienteSuccessful() {
		Cliente cliente = clienteService.findClienteById(1);
		assertThat(cliente.getIban())
		.isNotEmpty()
		.containsPattern("^ES\\s\\d{22}$");
	}
	
	@Test
	void getfechaNacimientoClienteSuccessful() {
		Cliente cliente = clienteService.findClienteById(1);
		String now = LocalDate.now().toString();
		assertThat(cliente.getFechaNacimiento())
		.isBefore(now)
		.hasToString("1995-03-08")
		.isBefore(LocalDate.now().minusYears(18));
	}
	
	//Tests Añadir
	
	@Test
	@Transactional
	public void shouldInsertCliente(){
		Collection<Cliente> clientes = this.clienteService.findClientes();
		int found = clientes.size();

		Cliente cliente = new Cliente();
		cliente.setNombre("Juan Jesús");
		cliente.setApellidos("Ferrero Gutiérrez");
		cliente.setNif("28976897W");
		cliente.setDireccionFacturacion("Calle Carbón, 35 4ºB Sevilla");
		cliente.setIban("ES 6621000418401234567893");
		LocalDate fecha = LocalDate.parse("1997-06-03", DateTimeFormatter.ISO_DATE);
		cliente.setFechaNacimiento(fecha);
                User user = new User();
                user.setUsername("28976897W");
                user.setPassword("*Fly_High14&");
                user.setEnabled(true);
                cliente.setUser(user);                
                
		try {
			this.clienteService.saveCliente(cliente);
		} catch (NifDuplicadoException ex) {
			 Logger.getLogger(ClienteServiceTests.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		assertThat(cliente.getId().longValue()).isNotEqualTo(0);

		clientes = this.clienteService.findClientes();
		assertThat(clientes.size()).isEqualTo(found + 1);
	}
	
	@Test
	@Transactional
	public void shouldNotInsertClienteRepetido(){
		Collection<Cliente> clientes = this.clienteService.findClientes();
		int found = clientes.size();
		Logger.getLogger(ClienteServiceTests.class.getName()).log(Level.INFO, "Clientes encontrados: " + found);
		
		Cliente cliente = new Cliente();
		cliente.setNombre("María");
		cliente.setApellidos("Soto Ramírez");
		cliente.setNif("01446551N");
		cliente.setDireccionFacturacion("C/Enladrillada,2 1ºB Sevilla");
		cliente.setIban("ES 7771056418401234567893");
		LocalDate fecha = LocalDate.parse("1995-03-08", DateTimeFormatter.ISO_DATE);
		cliente.setFechaNacimiento(fecha);
                
			User user = new User();
            user.setUsername("01446551Z");
            user.setPassword("*Fly_High14&");
            user.setEnabled(true);
            cliente.setUser(user);                    

        
        Assertions.assertThrows(NifDuplicadoException.class, ()->{ this.clienteService.saveCliente(cliente); });
		clientes = this.clienteService.findClientes();
		assertThat(clientes.size()).isEqualTo(found);
	}
	
	//Tests Actualizar
	
	@Test
	void shouldUpdateDirFacturacionSuccessful() {
		Cliente cliente = clienteService.findClienteById(1);
		
		//Logger.getLogger(ClienteServiceTests.class.getName()).log(Level.INFO, "Cambiando dirección de Facturación de cliente de: " + cliente.getDireccionFacturacion() + " a " + "Calle Almira, 17 3ºC Segovia");
		
		cliente.setDireccionFacturacion("Calle Almira, 17 3ºC Segovia");
		
		assertThat(cliente.getDireccionFacturacion())
		.isNotEmpty()
		.isEqualTo("Calle Almira, 17 3ºC Segovia");
	}
	
	//Tests Eliminar
	
	@Test
	@Transactional
	public void shouldDeleteCliente() {
		
	}

}
