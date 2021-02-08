package org.springframework.samples.aerolineasAAAFC.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.Test;
import org.springframework.samples.aerolineasAAAFC.model.equipaje.Equipaje;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

class ValidatorTests {

	private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}
	
	// Aeropuerto
	@Test
	@Transactional
	public void shouldNotValidateAeropuertoNoValido() {
		Aeropuerto aero = new Aeropuerto();
		aero.setNombre("Aeropuerto de Sevilla");
		aero.setLocalizacion("Sevilla, España");
		aero.setCodigoIATA("SVQ");
		aero.setTelefono("911");
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Aeropuerto>> constraintViolations = validator.validate(aero);
		
		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Aeropuerto> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("telefono");
		assertThat(violation.getMessage()).isEqualTo("El número de teléfono asociado a este aeropuerto no es correcto.");
	}
	
	// Avión
	@Test
	@Transactional
	public void shouldNotValidateAvionNoValido() {
		Avion avion = new Avion();
		avion.setCapacidadPasajero(9999);
		avion.setPlazasEconomica(1);
		avion.setPlazasEjecutiva(1);
		avion.setPlazasPrimera(1);
		avion.setHorasAcumuladas(0);
		
		LocalDate today = LocalDate.parse("2022-12-12");
		
		avion.setFechaFabricacion(today);
		avion.setFechaRevision(today);
		avion.setDisponibilidad(true);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Avion>> constraintViolations = validator.validate(avion);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Avion> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("");
		assertThat(violation.getMessage()).isEqualTo("Las plazas por clase del avión, deben corresponder a la capacidad del mismo.");
	}
	
	// Asiento
	@Test
	@Transactional
	public void shouldNotValidateAsientoNoValido() {
		Asiento a = new Asiento();
		a.setNombre("A");
		a.setClase(Clase.ECONOMICA);

		Validator validator = createValidator();
		Set<ConstraintViolation<Asiento>> constraintViolations = validator.validate(a);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Asiento> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("nombre");
		assertThat(violation.getMessage()).isEqualTo("El nombre de este asiento no tiene un formato válido.");
	}
	
	// Azafato
	@Test
	@Transactional
	public void shouldNotValidateAzafatoNoValido() {
		Azafato azafato = new Azafato();
		azafato.setNombre("Prueba");
		azafato.setApellidos("Prueba Prueba");
		azafato.setNif("17530830T");
		azafato.setIban("ES 6400811574415378856369");
		azafato.setSalario(300.);
		
		IdiomaType i1 = new IdiomaType();
		i1.setIdioma("ES");
		IdiomaType i2 = new IdiomaType();
		i2.setIdioma("EN");
		Set<IdiomaType> idiomas = new HashSet<IdiomaType>();
		idiomas.add(i1);
		idiomas.add(i2);
		azafato.setIdiomas(idiomas);
		
		User user = new User();
		user.setUsername("17530830T");
		user.setPassword("*pepe_csfay");
		azafato.setUser(user);  

		Validator validator = createValidator();
		Set<ConstraintViolation<Azafato>> constraintViolations = validator.validate(azafato);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Azafato> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("salario");
		assertThat(violation.getMessage()).isEqualTo("El salario debe ser mayor o igual a 1000.0 euros.");
	}
	
	// Cliente
	@Test
	@Transactional
	public void shouldNotValidateClienteNoValido() {
		Cliente cliente = new Cliente();
		cliente.setNombre("Prueba");
		cliente.setApellidos("Prueba Prueba");
		cliente.setNif("15268646G");
		cliente.setIban("ES 9221006777563139943572");
		cliente.setEmail("prueba@gmail.com");
		cliente.setDireccionFacturacion("Calle Prueba");
		cliente.setFechaNacimiento(LocalDate.of(2021, 1, 1));
				
		User user = new User();
		user.setUsername("15268646G");
		user.setPassword("*pepe_csfay");
		cliente.setUser(user);  

		Validator validator = createValidator();
		Set<ConstraintViolation<Cliente>> constraintViolations = validator.validate(cliente);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Cliente> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("fechaNacimiento");
		assertThat(violation.getMessage()).isEqualTo("El cliente debe de ser mayor de edad");
	}
	
	// IdiomaType
	@Test
	@Transactional
	public void shouldNotValidateIdiomaTypeNoValido() {
		IdiomaType idioma = new IdiomaType();
		idioma.setIdioma("Español");	

		Validator validator = createValidator();
		Set<ConstraintViolation<IdiomaType>> constraintViolations = validator.validate(idioma);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<IdiomaType> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("idioma");
		assertThat(violation.getMessage()).isEqualTo("El nombre del idioma no se adapta a los códigos ISO 639-1");
	}
	
	// Person (usando un cliente para validar el nif y el iban, pero se aplica a todas las clases que heredan Person)
	@Test
	@Transactional
	public void shouldNotValidatePersonNoValido1() {
		Cliente cliente = new Cliente();
		cliente.setNombre("Prueba");
		cliente.setApellidos("Prueba Prueba");
		cliente.setNif("12345678A");
		cliente.setIban("ES 1331902781315481161314");
		cliente.setEmail("prueba@gmail.com");
		cliente.setDireccionFacturacion("Calle Prueba");
		cliente.setFechaNacimiento(LocalDate.of(1995, 1, 1));
				
		User user = new User();
		user.setUsername("12345678A");
		user.setPassword("*pepe_csfay");
		cliente.setUser(user);  
	

		Validator validator = createValidator();
		Set<ConstraintViolation<Cliente>> constraintViolations = validator.validate(cliente);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Cliente> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("nif");
		assertThat(violation.getMessage()).isEqualTo("Formato de Nif incorrecto");
	}
	
	@Test
	@Transactional
	public void shouldNotValidatePersonNoValido2() {
		Cliente cliente = new Cliente();
		cliente.setNombre("Prueba");
		cliente.setApellidos("Prueba Prueba");
		cliente.setNif("96976840P");
		cliente.setIban("ES1331902781315481161314");
		cliente.setEmail("prueba@gmail.com");
		cliente.setDireccionFacturacion("Calle Prueba");
		cliente.setFechaNacimiento(LocalDate.of(1995, 1, 1));
				
		User user = new User();
		user.setUsername("96976840P");
		user.setPassword("*pepe_csfay");
		cliente.setUser(user);  
	

		Validator validator = createValidator();
		Set<ConstraintViolation<Cliente>> constraintViolations = validator.validate(cliente);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Cliente> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("iban");
		assertThat(violation.getMessage()).isEqualTo("Debe corresponder al formato: ES, un espacio en blanco y 22 dígitos");
	}
	
	// PersonalControl o PersonalOficina
	@Test
	@Transactional
	public void shouldNotValidatePersonalControlNoValido() {
		PersonalControl control = new PersonalControl();
		control.setNombre("Prueba");
		control.setApellidos("Prueba Prueba");
		control.setNif("96168201A");
		control.setIban("ES 7904875761411792374146");
		control.setSalario(350.);
		control.setRol(Rol.PILOTO);
		
		User user = new User();
		user.setUsername("96168201A");
		user.setPassword("*pepe_csfay");
		control.setUser(user);  

		Validator validator = createValidator();
		Set<ConstraintViolation<PersonalControl>> constraintViolations = validator.validate(control);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<PersonalControl> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("Salario");
		assertThat(violation.getMessage()).isEqualTo("El salario debe ser mayor o igual a 1000.0 euros.");
	}
	
	
	// Equipaje
	@Test
	@Transactional
	public void shouldNotValidateEquipajeMedidasNoValido() {

		Equipaje equipaje = new Equipaje();
		equipaje.setPeso(34);

		Validator validator = createValidator();
		Set<ConstraintViolation<Equipaje>> constraintViolations = validator.validate(equipaje);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Equipaje> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("peso");
		assertThat(violation.getMessage()).isEqualTo("El peso debe de estar entre los 3kg y 32kg");

	}

}