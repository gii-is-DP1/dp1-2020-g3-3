package org.springframework.samples.aerolineasAAAFC.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.aerolineasAAAFC.model.Billete;
import org.springframework.samples.aerolineasAAAFC.model.Equipaje;
import org.springframework.samples.aerolineasAAAFC.service.exceptions.EquipajePriceException;
import org.springframework.samples.aerolineasAAAFC.service.exceptions.TooManyItemsBilleteException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class EquipajeServiceTests {

	@Autowired
	protected EquipajeService equipajeService;

	@Autowired
	protected BilleteService billeteService;

	@Test
	@Transactional
	public void shouldInsertEquipajeAndGenerateId() {

		Billete billete1 = this.billeteService.findBilleteById(1);
		int found = billete1.getEquipajes().size();

		Equipaje equipaje = new Equipaje();

		equipaje.setDimensiones("110x110x78");
		equipaje.setPeso(24);
		equipaje.setPrecio(30.);

		Set<Equipaje> oldEquipajes = billete1.getEquipajes();
		oldEquipajes.add(equipaje);
		billete1.setEquipajes(oldEquipajes);
		assertThat(billete1.getEquipajes().size()).isEqualTo(found + 1);

		equipaje.setBillete(billete1);
		Logger.getLogger(MenuServiceTests.class.getName()).log(Level.INFO,
				"Existe billete con equipajes: " + billete1.toString());

		Logger.getLogger(MenuServiceTests.class.getName()).log(Level.INFO,
				"Introducimos el equipaje: " + equipaje.toString());

		try {
			equipajeService.saveEquipaje(equipaje);
		} catch (EquipajePriceException e) {
			Logger.getLogger(MenuServiceTests.class.getName()).log(Level.SEVERE,
					"Parece que ha introducido un precio incorrecto.", e);
		}

		try {
			this.billeteService.saveBillete(billete1);
		} catch (TooManyItemsBilleteException e) {
			 e.printStackTrace();
		}
		
		Logger.getLogger(MenuServiceTests.class.getName()).log(Level.INFO, "El equipaje tiene id: " + equipaje.getId());
		assertThat(billete1.getEquipajes().size()).isEqualTo(found + 1);
		// checks that id has been generated
		assertThat(equipaje.getId()).isNotNull();

	}
	
	@Test
	@Transactional
	public void shouldNotInsertEquipajePrecioNoValido() {

		Billete billete1 = this.billeteService.findBilleteById(1);
		Collection<Equipaje> equipajes = this.equipajeService.findEquipajes();
		int found = equipajes.size();

		Equipaje equipaje = new Equipaje();

		//Insertamos precios imposibles
		equipaje.setDimensiones("110x110x78");
		equipaje.setPeso(24);
		equipaje.setPrecio(35.);

		equipaje.setBillete(billete1);
		Logger.getLogger(EquipajeServiceTests.class.getName()).log(Level.INFO,
				"Existe billete con equipajes: " + billete1.toString());

		Logger.getLogger(MenuServiceTests.class.getName()).log(Level.INFO, "Introducimos el equipaje: " + equipaje.toString());
		
		Assertions.assertThrows(EquipajePriceException.class, () -> {equipajeService.saveEquipaje(equipaje);});

		equipajes = this.equipajeService.findEquipajes();
		assertThat(equipajes.size()).isEqualTo(found);

	}
	

}
