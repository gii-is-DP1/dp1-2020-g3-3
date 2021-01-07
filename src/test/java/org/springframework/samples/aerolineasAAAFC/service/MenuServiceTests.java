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
import org.springframework.samples.aerolineasAAAFC.model.menu.Menu;
import org.springframework.samples.aerolineasAAAFC.deprecated.MenuPriceException;
import org.springframework.samples.aerolineasAAAFC.deprecated.MenuService;
import org.springframework.samples.aerolineasAAAFC.model.Billete;
import org.springframework.samples.aerolineasAAAFC.service.exceptions.TooManyItemsBilleteException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class MenuServiceTests {

	@Autowired
	protected MenuService menuService;

	@Autowired
	protected BilleteService billeteService;

//	@Test
//	@Transactional
//	public void shouldInsertMenuAndGenerateId() {
//
//		Billete billete1 = this.billeteService.findBilleteById(1);
//		int found = billete1.getMenus().size();
//
//		Menu menu = new Menu();
//
//		// Correspondientes a ensalada césar, pato a la pekinesa y pera
//		menu.setPrecio(1.21 + 5.54 + 0.19);
//		menu.setPrimerPlato("EnsaladaCésar");
//		menu.setSegundoPlato("PatoPekinesa");
//		menu.setPostre("Pera");
//
//		Set<Menu> oldMenus = billete1.getMenus();
//		oldMenus.add(menu);
//		billete1.setMenus(oldMenus);
//		assertThat(billete1.getMenus().size()).isEqualTo(found + 1);
//
//		menu.setBillete(billete1);
//		Logger.getLogger(MenuServiceTests.class.getName()).log(Level.INFO,
//				"Existe billete con menús: " + billete1.toString());
//
//		try {
//			Logger.getLogger(MenuServiceTests.class.getName()).log(Level.INFO,
//					"Introducimos el menú: " + menu.toString());
//
//			menuService.saveMenu(menu);
//		} catch (MenuPriceException ex) {
//			// Parece que se han introducido precios incorrectos
//			Logger.getLogger(MenuServiceTests.class.getName()).log(Level.SEVERE,
//					"Parece que ha introducido un precio incorrecto.", ex);
//		}
//
//		try {
//			this.billeteService.saveBillete(billete1);
//		} catch (TooManyItemsBilleteException e) {
//			 e.printStackTrace();
//		}
//		
//		Logger.getLogger(MenuServiceTests.class.getName()).log(Level.INFO, "El menú tiene id: " + menu.getId());
//		assertThat(billete1.getMenus().size()).isEqualTo(found + 1);
//		// checks that id has been generated
//		assertThat(menu.getId()).isNotNull();
//
//	}

//	@Test
//	@Transactional
//	public void shouldNotInsertMenuPrecioNoValido() {
//
//		Billete billete1 = this.billeteService.findBilleteById(1);
//		Collection<Menu> menus = this.menuService.findMenus();
//		int found = menus.size();
//
//		Menu menu = new Menu();
//
//		//Insertamos precios imposibles
//		menu.setPrecio(1.00 + 5.54 + 0.19);
//		menu.setPrimerPlato("EnsaladaCésar");
//		menu.setSegundoPlato("PatoPekinesa");
//		menu.setPostre("Pera");
//
//		menu.setBillete(billete1);
//		Logger.getLogger(MenuServiceTests.class.getName()).log(Level.INFO,
//				"Existe billete con menús: " + billete1.toString());
//
//		Logger.getLogger(MenuServiceTests.class.getName()).log(Level.INFO, "Introducimos el menú: " + menu.toString());
//		
//		Assertions.assertThrows(MenuPriceException.class, () -> {menuService.saveMenu(menu);});
//
//		menus = this.menuService.findMenus();
//		assertThat(menus.size()).isEqualTo(found);
//
//	}

}
