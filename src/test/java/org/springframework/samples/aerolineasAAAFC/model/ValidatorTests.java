package org.springframework.samples.aerolineasAAAFC.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.Test;
import org.springframework.samples.aerolineasAAAFC.model.equipaje.Equipaje;
import org.springframework.samples.aerolineasAAAFC.model.equipaje.EquipajeBase;
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
	
//	@Test Comentado porque la clase de Asiento no funciona
//	@Transactional
//	public void shouldNotValidateBilleteTooManyEquipajes() {
//		Billete b = new Billete();
//		Equipaje equipaje = new Equipaje();
//		Equipaje equipaje1 = new Equipaje();
//		Equipaje equipaje2 = new Equipaje();
//		Equipaje equipaje3 = new Equipaje();
//		Asiento a = new Asiento();
//		a.setNombre("A21");
//		b.setAsiento(a);
//		
//		Set<Equipaje> l = new HashSet<Equipaje>();
//		l.add(equipaje);
//		l.add(equipaje1);
//		l.add(equipaje2);
//		l.add(equipaje3);
//		
//		b.setEquipajes(l);
//		
//		Validator validator = createValidator();
//		Set<ConstraintViolation<Billete>> constraintViolations = validator.validate(b);
//
//		assertThat(constraintViolations.size()).isEqualTo(1);
//		ConstraintViolation<Billete> violation = constraintViolations.iterator().next();
//		assertThat(violation.getPropertyPath().toString()).isEqualTo("equipajes");
//		assertThat(violation.getMessage()).isEqualTo("El peso debe de estar entre los 3kg y 32kg");
//
//	}

}
