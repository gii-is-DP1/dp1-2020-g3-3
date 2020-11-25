package org.springframework.samples.aerolineasAAAFC.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
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
	@Transactional
	public void shouldInsertCliente() throws ParseException {
		Collection<Cliente> clientes = this.clienteService.findClientes();
		int found = clientes.size();

		Cliente cliente = new Cliente();
		cliente.setNombre("Juan Jesús");
		cliente.setApellidos("Ferrero Gutiérrez");
		cliente.setNif("28976897W");
		cliente.setDireccionFacturacion("Calle Carbón, 35 - 41007 Sevilla");
		cliente.setIban("ES 6621000418401234567893");
		Date fecha = new SimpleDateFormat("yyyy/MM/dd").parse("1997/06/03");  
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
