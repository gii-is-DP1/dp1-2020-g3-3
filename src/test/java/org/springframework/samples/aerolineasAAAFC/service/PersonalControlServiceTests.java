package org.springframework.samples.aerolineasAAAFC.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.samples.aerolineasAAAFC.model.PersonalControl;
import org.springframework.samples.aerolineasAAAFC.model.Rol;
import org.springframework.samples.aerolineasAAAFC.model.User;
import org.springframework.samples.aerolineasAAAFC.model.Vuelo;
import org.springframework.samples.aerolineasAAAFC.service.exceptions.IbanDuplicadoException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class PersonalControlServiceTests {

	@Autowired
	protected PersonalControlService personalControlService;

	
	//TESTS DE CONSULTA
	@Test
	void getPersonalSuccessful() {
		PersonalControl personal = this.personalControlService.findPersonalControlById(1);
		
		//NOMBRE
		assertThat(personal.getNombre())
		.isNotEmpty();
		
		//APELLIDOS
		assertThat(personal.getApellidos())
		.isNotEmpty();
		
		//NIF
		assertThat(personal.getNif())
		.isNotEmpty()
		.containsPattern("^\\d{8}[a-zA-Z]$");
		
		//IBAN
		assertThat(personal.getIban())
		.isNotEmpty()
		.containsPattern("^ES\\s\\d{22}$");
		
		//ROL
		assertThat(personal.getRol())
		.isNotNull()
		.isEqualTo(Rol.PILOTO);
		
		//SALARIO
		assertThat(personal.getSalario())
		.isNotNull()
		.isGreaterThan(100);
	}

	@Test
	void getPersonalByNif() {
		PersonalControl pc = this.personalControlService.findPersonalControlByNif("17579447H");
		assertThat(pc).isNotNull();
	}
	
	@Test
	void getPersonalByIBAN() {
		PersonalControl pc = this.personalControlService.findPersonalControlByIban("ES 6621000418401234567892");
		assertThat(pc).isNotNull();
	}
	
	@Test
	void getVuelosByDateSuccessful() {

		Collection<Vuelo> vuelos = this.personalControlService.horario(1, 01, 2015);
		
		int found = vuelos.size();
		
		assertThat(found).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource ({
		"2021-01-11, 1",
		"2021-01-05, 2",
		"2015-01-21, 1",
		"2021-01-07, 2",
		"2021-01-08, 2",
		"2021-02-06, 1",
		"2021-01-10, 2"
	})
	void getVuelosSemana(String fecha, int total) {
		LocalDate date = LocalDate.parse(fecha, DateTimeFormatter.ISO_DATE);
		Collection<Vuelo> vuelos = this.personalControlService.horarioSemanalConFecha(1, date.getDayOfWeek(), date.getDayOfYear(),date.getYear());
		
		int found = vuelos.size();
		
		assertThat(found).isEqualTo(total);
	}
	
	
	//TESTS DE INSERCIÓN
	@Test
	@Transactional
	void shouldInsertPersonal(){
		Collection<PersonalControl> pcs = this.personalControlService.findPersonalControlNoPageable();
		int found = pcs.size();
		
		PersonalControl pc = new PersonalControl();
		pc.setNombre("Juan");
		pc.setApellidos("Fernandez Romero");
		pc.setNif("08493865B");
		pc.setIban("ES 0159480518801639865810");
		pc.setRol(Rol.COPILOTO);
		pc.setSalario(2000.);
		
		User pcUser = new User();
		pcUser.setUsername("08493865B");
		pcUser.setPassword("juFerRo01");
		pc.setUser(pcUser);
		
		this.personalControlService.savePersonalControl(pc);
		
		assertThat(pc.getId()).isNotNull();
		
		pcs = this.personalControlService.findPersonalControlNoPageable();
		assertThat(pcs.size()).isEqualTo(found + 1);
	}
	
	@Test
	@Transactional
	void shouldNotInsertPersonalIbanDupl() throws IbanDuplicadoException {
		
		PersonalControl pc = new PersonalControl();
		pc.setNombre("Juan");
		pc.setApellidos("Fernandez Romero");
		pc.setNif("08493865B");
		pc.setIban("ES 1221089893201234561111");
		pc.setRol(Rol.COPILOTO);
		pc.setSalario(2000.);
		
		User pcUser = new User();
		pcUser.setUsername("08493865B");
		pcUser.setPassword("juFerRo01");
		pc.setUser(pcUser);
		
		assertThrows(DataIntegrityViolationException.class, () -> { this.personalControlService.savePersonalControl(pc); });
	}
	
	
	//TEST DE ACTUALIZACIÓN
	@Test
	void shouldUpdateSuccessful() {
		PersonalControl pc = this.personalControlService.findPersonalControlById(1);

		pc.setSalario(2300.0);
		
		this.personalControlService.savePersonalControl(pc);
		
		pc = this.personalControlService.findPersonalControlById(1);
		assertThat(pc.getSalario())
		.isEqualTo(2300.0);
	}
	
	
	//TEST DE ELIMINACIÓN
	@Test
	@Transactional
	void deletePersonalById() {
		Collection<PersonalControl> pc = this.personalControlService.findPersonalControlNoPageable();
		int found = pc.size();
		
		this.personalControlService.deletePersonalControlById(1);
		
		pc = this.personalControlService.findPersonalControlNoPageable();
		assertThat(pc.size()).isEqualTo(found-1);
	}
	
}










