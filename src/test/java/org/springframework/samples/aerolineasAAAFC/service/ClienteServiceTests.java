package org.springframework.samples.aerolineasAAAFC.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.aerolineasAAAFC.model.Cliente;
import org.springframework.samples.aerolineasAAAFC.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class ClienteServiceTests {
	
	@Autowired
	protected ClienteService clienteService;
	
	@Test
	void getNombreClienteSuccessful() {
		Cliente cliente = clienteService.findClienteById(1);
		assertThat(cliente.getNombre()).isNotEmpty();
	}

	@Test
	void getApellidosClienteSuccessful() {
		Cliente cliente = clienteService.findClienteById(1);
		assertThat(cliente.getApellidos()).isNotEmpty();
	}

	@Test
	void getNifClienteSuccessful() {
		Cliente cliente = clienteService.findClienteById(1);
		assertThat(cliente.getNif()).isNotEmpty();
		assertThat(cliente.getNif()).containsPattern("^\\d{8}[a-zA-Z]$");
	}
	
	@Test
	void getDirFacturacionClienteSuccessful() {
		Cliente cliente = clienteService.findClienteById(1);
		assertThat(cliente.getDireccionFacturacion()).isNotEmpty();
	}

	@Test
	void getIbanClienteSuccessful() {
		Cliente cliente = clienteService.findClienteById(1);
		assertThat(cliente.getIban()).isNotEmpty();
		assertThat(cliente.getIban()).containsPattern("^ES\\s\\d{22}$");
	}
	
	@Test
	void getfechaNacimientoAzafatoSuccessful() {
		Cliente cliente = clienteService.findClienteById(1);
		String now = LocalDate.now().toString();
		assertThat(cliente.getFechaNacimiento()).isBefore(now);
		assertThat(cliente.getFechaNacimiento()).hasToString("1995-03-08");
	}
	
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
                
		this.clienteService.saveCliente(cliente);
		assertThat(cliente.getId().longValue()).isNotEqualTo(0);

		clientes = this.clienteService.findClientes();
		assertThat(clientes.size()).isEqualTo(found + 1);
	}

	
}
