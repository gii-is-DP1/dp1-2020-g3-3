package org.springframework.samples.aerolineasAAAFC.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
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

}