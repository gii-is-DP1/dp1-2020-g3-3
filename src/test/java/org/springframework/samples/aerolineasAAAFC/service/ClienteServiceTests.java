package org.springframework.samples.aerolineasAAAFC.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.samples.aerolineasAAAFC.model.Billete;
import org.springframework.samples.aerolineasAAAFC.model.Clase;
import org.springframework.samples.aerolineasAAAFC.model.Cliente;
import org.springframework.samples.aerolineasAAAFC.model.User;
import org.springframework.samples.aerolineasAAAFC.model.Vuelo;
import org.springframework.samples.aerolineasAAAFC.service.exceptions.NifDuplicadoException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class ClienteServiceTests {

	@Autowired
	protected ClienteService clienteService;


	//Tests Consultar

	@Test
	void getClienteSuccessful() {
		Cliente cliente = clienteService.findClienteById(1);
		//NOMBRE
		assertThat(cliente.getNombre())
		.isNotEmpty();
		//APELLIDOS
		assertThat(cliente.getApellidos())
		.isNotEmpty();
		//IBAN
		assertThat(cliente.getIban())
		.isNotEmpty()
		.containsPattern("^ES\\s\\d{22}$");
		//NIF
		assertThat(cliente.getNif())
		.isNotEmpty()
		.containsPattern("^\\d{8}[a-zA-Z]$");
		//FECHA DE NACIMIENTO
		assertThat(cliente.getFechaNacimiento())
		.isBefore(LocalDate.now().minusYears(18));
		//EMAIL
		assertThat(cliente.getEmail())
		.isNotEmpty();
		//BILLETES
		assertThat(cliente.getBilletes())
		.isNotEmpty();
	}

	@Test
	void shouldAddBillete() {
		Billete billete = new Billete();
		Cliente cliente = clienteService.findClienteById(1);
		int found = cliente.getBilletes().size();
		
		billete.setId(2);
		billete.setCoste(80);
//		billete.setAsiento("F5");
		billete.setFechaReserva(LocalDate.of(2020, 04, 06));
		billete.setClase(Clase.ECONOMICA);
		cliente.getBilletes().add(billete);
		
		assertThat(cliente.getBilletes().size())
		.isEqualTo(found+1);

	}
	
	@Test
	void shouldGetClienteByNif() {
		Cliente cliente = clienteService.findClienteByNif("01446551N");
		assertThat(cliente.getId());
	}

	//Tests Añadir

	@Test
	@Transactional
	public void shouldInsertCliente() throws NifDuplicadoException{
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
		cliente.setEmail("juanjeferrero@outlook.com");

		User user = new User();
		user.setUsername("juanjiniero");
		user.setPassword("*Fly_High14&");
		user.setEnabled(true);
		cliente.setUser(user);                

		this.clienteService.saveCliente(cliente);

		assertThat(cliente.getId().longValue()).isNotEqualTo(0);

		clientes = this.clienteService.findClientes();
		assertThat(clientes.size()).isEqualTo(found + 1);
	}

	@Test
	@Transactional
	public void shouldNotInsertClienteRepetido(){

		Cliente cliente = new Cliente();
		cliente.setNombre("María");
		cliente.setApellidos("Soto Ramírez");
		cliente.setNif("01446551N");
		cliente.setDireccionFacturacion("C/Enladrillada,2 1ºB Sevilla");
		cliente.setIban("ES 7771056418401234567893");
		LocalDate fecha = LocalDate.parse("1995-03-08", DateTimeFormatter.ISO_DATE);
		cliente.setFechaNacimiento(fecha);     
		cliente.setEmail("marisotoram@hotmail.com");
		
		User user = new User();
		user.setUsername("marSoRa");
		user.setPassword("*Fly_Low14&");
		user.setEnabled(true);
		cliente.setUser(user);
		
		Assertions.assertThrows(DataIntegrityViolationException.class, () -> {this.clienteService.saveCliente(cliente);});
		
	}

	//Tests Actualizar

	@Test
	void shouldUpdateDirFacturacionSuccessful() {
		Cliente cliente = clienteService.findClienteById(1);

		cliente.setDireccionFacturacion("Calle Almira, 17 3ºC Segovia");

		assertThat(cliente.getDireccionFacturacion())
		.isNotEmpty()
		.isEqualTo("Calle Almira, 17 3ºC Segovia");
	}
	
	//Tests Eliminar

	@Test
	@Transactional
	public void shouldDeleteClienteById() {
		Collection<Cliente> clientes = this.clienteService.findClientes();
		int found = clientes.size();

		clienteService.deleteClienteById(1);

		clientes = this.clienteService.findClientes();
		assertThat(clientes.size()).isEqualTo(found - 1);
	}

//
//	@Test
//	@Transactional
//	public void shouldFindClientesByVuelo() {
//		
//		Collection<Cliente> clientes=this.clienteService.findClientesPorVuelo(vuelo);
//		assertThat(!clientes.isEmpty());
//	}
	
	
}
