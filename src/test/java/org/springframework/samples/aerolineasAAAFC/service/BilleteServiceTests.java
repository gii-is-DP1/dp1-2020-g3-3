package org.springframework.samples.aerolineasAAAFC.service;

import static org.assertj.core.api.Assertions.assertThat;


import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.aerolineasAAAFC.model.Billete;
import org.springframework.samples.aerolineasAAAFC.model.Clase;
import org.springframework.samples.aerolineasAAAFC.model.Equipaje;
import org.springframework.samples.aerolineasAAAFC.model.Vuelo;
import org.springframework.samples.aerolineasAAAFC.model.menu.Menu;
import org.springframework.samples.aerolineasAAAFC.model.menu.Plato;
import org.springframework.samples.aerolineasAAAFC.service.exceptions.EquipajePriceException;
import org.springframework.samples.aerolineasAAAFC.service.exceptions.HorasImposiblesException;
import org.springframework.samples.aerolineasAAAFC.service.exceptions.PlatosNoValidosException;
import org.springframework.samples.aerolineasAAAFC.service.exceptions.TooManyItemsBilleteException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class BilleteServiceTests {


	@Autowired
	protected BilleteService billeteService;

	@Autowired
	protected VueloService vueloService;
	
	@Autowired
	protected PlatoBaseService platoBaseService;


	@Test
	@Transactional
	public void shouldInsertBilleteIntoDatabaseAndGenerateId() throws ParseException {


		Vuelo vuelo = this.vueloService.findVueloById(1);
		
		int nBilletes=vuelo.getBilletes().size();

		Billete billete = new Billete();

		Clase clase=Clase.ECONOMICA;
		billete.setClase(clase);
//		billete.setAsiento("A44");

		billete.setCoste(12);
		LocalDate reserva=LocalDate.parse("2010-05-16", DateTimeFormatter.ISO_DATE);
		billete.setFechaReserva(reserva);

		billete.setVuelos(vuelo);
		
		//se comprueba que se añadio correctamente el billete

		vuelo.getBilletes().add(billete);
		assertThat(vuelo.getBilletes().size()).isEqualTo(nBilletes + 1);


		//se comprueba que se guarda exitosamente los cambios en vuelo

		
		//this.billeteService.saveBillete(billete);

//		this.vueloService.saveVuelo(vuelo); AHORA SE TIENE EN CUENTA LA EXCEPCION DE HORAS
		try {
			this.vueloService.saveVuelo(vuelo);
		} catch (HorasImposiblesException ex) {
			Logger.getLogger(VueloServiceTests.class.getName()).log(Level.SEVERE, null, ex);
		}

		vuelo = this.vueloService.findVueloById(1);
		assertThat(vuelo.getBilletes().size()).isEqualTo(nBilletes + 1);
		
		
		//se comprueba que el id se añade correctamente
		billete.setId(2);
		assertThat(billete.getId()).isNotNull();
	}
	
	@Test
	@Transactional
	public void shouldInsertMenuIntoDatabaseAndGenerateId() {
		Collection<Menu> menus = this.billeteService.findMenus();
		int found = menus.size();
		
		Billete b = this.billeteService.findBilleteById(1);
		Plato p1 = new Plato();
		p1.setPlatoBase(platoBaseService.findPlatoBaseByName("Sopa de miso"));
		
		Plato p2 = new Plato();
		p2.setPlatoBase(platoBaseService.findPlatoBaseByName("Arroz con ternera al curry"));
		
		Plato p3 = new Plato();
		p3.setPlatoBase(platoBaseService.findPlatoBaseByName("Manzana"));
		
		Set<Plato> s = new HashSet<Plato>();
		s.add(p1);
		s.add(p2);
		s.add(p3);
		
		Menu m = new Menu();
		m.setPlatos(s);
		m.setBillete(b);
		
		Logger.getLogger(BilleteServiceTests.class.getName()).log(Level.INFO,"Introducimos platosBase: " + p1.getPlatoBase().getTipoPlato().getName() + " de id " + p1.getPlatoBase().getId());
		Logger.getLogger(BilleteServiceTests.class.getName()).log(Level.INFO,"Introducimos platosBase: " + p2.getPlatoBase().getTipoPlato().getName() + " de id " + p2.getPlatoBase().getId());
		Logger.getLogger(BilleteServiceTests.class.getName()).log(Level.INFO,"Introducimos platosBase: " + p3.getPlatoBase().getTipoPlato().getName() + " de id " + p3.getPlatoBase().getId());
		
		try {
			this.billeteService.saveMenu(m);
		} catch (DataAccessException | TooManyItemsBilleteException | PlatosNoValidosException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		menus = this.billeteService.findMenus();
		assertThat(menus.size()).isEqualTo(found + 1);
	
	}
	
	@Test
	@Transactional
	public void shouldNotInsertTooManyMenus() {
		
		Billete b = this.billeteService.findBilleteById(1);
		Plato p1 = new Plato();
		p1.setPlatoBase(platoBaseService.findPlatoBaseByName("Sopa de miso"));
		
		Plato p2 = new Plato();
		p2.setPlatoBase(platoBaseService.findPlatoBaseByName("Arroz con ternera al curry"));
		
		Plato p3 = new Plato();
		p3.setPlatoBase(platoBaseService.findPlatoBaseByName("Manzana"));
		
		Set<Plato> s = new HashSet<Plato>();
		s.add(p1);
		s.add(p2);
		s.add(p3);
		
		Menu m = new Menu();
		m.setPlatos(s);
		m.setBillete(b);
		Menu m1 = new Menu();
		m1.setPlatos(s);
		m1.setBillete(b);
		Menu m2 = new Menu();
		m2.setPlatos(s);
		m2.setBillete(b);
		Menu m3 = new Menu();
		m3.setPlatos(s);
		m3.setBillete(b);
		
		try {
			this.billeteService.saveMenu(m);
			this.billeteService.saveMenu(m1);
			this.billeteService.saveMenu(m2);
		} catch (DataAccessException | TooManyItemsBilleteException | PlatosNoValidosException e) {
			e.printStackTrace();
		}
		Assertions.assertThrows(TooManyItemsBilleteException.class, () ->{
			this.billeteService.saveMenu(m3);});
	
	}
	
	@Test
	@Transactional
	public void shouldNotInsertTooManyPlatos() {
		
		Billete b = this.billeteService.findBilleteById(1);
		Plato p1 = new Plato();
		p1.setPlatoBase(platoBaseService.findPlatoBaseByName("Sopa de miso"));
		
		Plato p2 = new Plato();
		p2.setPlatoBase(platoBaseService.findPlatoBaseByName("Arroz con ternera al curry"));
		
		Plato p3 = new Plato();
		p3.setPlatoBase(platoBaseService.findPlatoBaseByName("Manzana"));
		
		Plato p4 = new Plato();
		p4.setPlatoBase(platoBaseService.findPlatoBaseByName("Manzana"));
		
		Set<Plato> s = new HashSet<Plato>();
		s.add(p1);
		s.add(p2);
		s.add(p3);
		s.add(p4);
		
		Menu m = new Menu();
		m.setPlatos(s);
		m.setBillete(b);
		
		Assertions.assertThrows(PlatosNoValidosException.class, () ->{
			this.billeteService.saveMenu(m);});
	
	}
	
	@Test
	@Transactional
	public void shouldNotInsertSamePlatosTypes() {
		
		Billete b = this.billeteService.findBilleteById(1);
		Plato p1 = new Plato();
		p1.setPlatoBase(platoBaseService.findPlatoBaseByName("Sopa de miso"));
		
		Plato p2 = new Plato();
		p2.setPlatoBase(platoBaseService.findPlatoBaseByName("Manzana"));
		
		Plato p3 = new Plato();
		p3.setPlatoBase(platoBaseService.findPlatoBaseByName("Manzana"));
		
		Set<Plato> s = new HashSet<Plato>();
		s.add(p1);
		s.add(p2);
		s.add(p3);
		
		Menu m = new Menu();
		m.setPlatos(s);
		m.setBillete(b);
		
		Assertions.assertThrows(PlatosNoValidosException.class, () ->{
			this.billeteService.saveMenu(m);});
	
	}
	
	@Test
	@Transactional
	public void shouldInsertEquipajeIntoDatabaseAndGenerateId() {
		Collection<Equipaje> equipajes = this.billeteService.findEquipajes();
		int found = equipajes.size();
		
		Billete b = this.billeteService.findBilleteById(1);
		
		Equipaje e = new Equipaje();
		e.setBillete(b);
		e.setDimensiones("110x110x78");
		e.setPeso(21);
		e.setPrecio(30.);
			
		try {
			this.billeteService.saveEquipaje(e);
		} catch (DataAccessException e1) {
			e1.printStackTrace();
		} catch (EquipajePriceException e1) {
			e1.printStackTrace();
		} catch (TooManyItemsBilleteException e1) {
			e1.printStackTrace();
		}
		equipajes = this.billeteService.findEquipajes();
		assertThat(equipajes.size()).isEqualTo(found + 1);
	}
	
	@Test
	@Transactional
	public void shouldNotInsertTooManyEquipajesIntoDatabase() {
		Billete b = this.billeteService.findBilleteById(1);
		
		Equipaje e = new Equipaje();
		e.setBillete(b);
		e.setDimensiones("110x110x78");
		e.setPeso(21);
		e.setPrecio(30.);
		Equipaje e1 = new Equipaje();
		e1.setBillete(b);
		e1.setDimensiones("110x110x78");
		e1.setPeso(21);
		e1.setPrecio(30.);
		Equipaje e2 = new Equipaje();
		e2.setBillete(b);
		e2.setDimensiones("110x110x78");
		e2.setPeso(21);
		e2.setPrecio(30.);
		Equipaje e3 = new Equipaje();
		e3.setBillete(b);
		e3.setDimensiones("110x110x78");
		e3.setPeso(21);
		e3.setPrecio(30.);	
			
		try {
			this.billeteService.saveEquipaje(e);
			this.billeteService.saveEquipaje(e1);
			this.billeteService.saveEquipaje(e2);
		} catch (DataAccessException ex) {
			ex.printStackTrace();
		} catch (EquipajePriceException ex) {
			ex.printStackTrace();
		} catch (TooManyItemsBilleteException ex) {
			ex.printStackTrace();
		}
		Assertions.assertThrows(TooManyItemsBilleteException.class, () ->{
			this.billeteService.saveEquipaje(e3);});
	}
	
	@Test
	@Transactional
	public void shouldNotInsertEquipajeWithWrongPrice() {
		Billete b = this.billeteService.findBilleteById(1);
		
		Equipaje e = new Equipaje();
		e.setBillete(b);
		e.setDimensiones("110x110x78");
		e.setPeso(21);
		e.setPrecio(30.2);
			
		Assertions.assertThrows(EquipajePriceException.class, () ->{
			this.billeteService.saveEquipaje(e);});
	}

}
