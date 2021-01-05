package org.springframework.samples.aerolineasAAAFC.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.Test;
import org.springframework.samples.aerolineasAAAFC.deprecated.Menu;
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
	public void shouldNotValidateMenuPlato1NoValido() {

		Menu menu = new Menu();

		menu.setPrecio(1.21 + 5.54 + 0.19);
		menu.setPrimerPlato("EnsaladaMaliciosa");
		menu.setSegundoPlato("PatoPekinesa");
		menu.setPostre("Pera");
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Menu>> constraintViolations = validator.validate(menu);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Menu> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("primerPlato");
		assertThat(violation.getMessage()).isEqualTo("El primer plato no está disponible");

	}
	
	@Test
	@Transactional
	public void shouldNotValidateMenuPlato2NoValido() {

		Menu menu = new Menu();

		menu.setPrecio(1.21 + 5.54 + 0.19);
		menu.setPrimerPlato("EnsaladaCésar");
		menu.setSegundoPlato("Pat");
		menu.setPostre("Pera");
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Menu>> constraintViolations = validator.validate(menu);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Menu> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("segundoPlato");
		assertThat(violation.getMessage()).isEqualTo("El segundo plato no está disponible");

	}
	
	@Test
	@Transactional
	public void shouldNotValidateMenuPostreNoValido() {

		Menu menu = new Menu();

		menu.setPrecio(1.21 + 5.54 + 0.19);
		menu.setPrimerPlato("EnsaladaCésar");
		menu.setSegundoPlato("PatoPekinesa");
		menu.setPostre("PeraMaliciosa");
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Menu>> constraintViolations = validator.validate(menu);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Menu> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("postre");
		assertThat(violation.getMessage()).isEqualTo("El postre no está disponible");

	}
	
	@Test
	@Transactional
	public void shouldNotValidateEquipajeMedidasNoValido() {

		Equipaje equipaje = new Equipaje();

		equipaje.setDimensiones("11x110x78");
		equipaje.setPeso(24);
		equipaje.setPrecio(30.);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Equipaje>> constraintViolations = validator.validate(equipaje);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Equipaje> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("dimensiones");
		assertThat(violation.getMessage()).isEqualTo("Las medidas del equipaje deben ser las definidas por la página");

	}
	
	@Test
	@Transactional
	public void shouldNotValidateEquipajePesoNoValido() {

		Equipaje equipaje = new Equipaje();

		equipaje.setDimensiones("110x110x78");
		equipaje.setPeso(2);
		equipaje.setPrecio(30.);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Equipaje>> constraintViolations = validator.validate(equipaje);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Equipaje> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("peso");
		assertThat(violation.getMessage()).isEqualTo("El peso debe de estar entre los 3kg y 32kg");

	}

}
