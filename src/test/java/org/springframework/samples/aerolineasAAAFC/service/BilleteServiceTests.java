package org.springframework.samples.aerolineasAAAFC.service;

import static org.assertj.core.api.Assertions.assertThat;


import java.text.ParseException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.samples.aerolineasAAAFC.model.Asiento;
import org.springframework.samples.aerolineasAAAFC.model.Billete;
import org.springframework.samples.aerolineasAAAFC.model.Clase;
import org.springframework.samples.aerolineasAAAFC.model.Cliente;
import org.springframework.samples.aerolineasAAAFC.model.equipaje.Equipaje;
import org.springframework.samples.aerolineasAAAFC.model.equipaje.EquipajeBase;
import org.springframework.samples.aerolineasAAAFC.model.Vuelo;
import org.springframework.samples.aerolineasAAAFC.model.menu.Menu;
import org.springframework.samples.aerolineasAAAFC.model.menu.Plato;
import org.springframework.samples.aerolineasAAAFC.service.exceptions.DisponibilidadAvionException;
import org.springframework.samples.aerolineasAAAFC.service.exceptions.HorasMaximasVueloException;
import org.springframework.samples.aerolineasAAAFC.service.exceptions.PlatosNoValidosException;
import org.springframework.samples.aerolineasAAAFC.service.exceptions.TooManyItemsBilleteException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class BilleteServiceTests {
	
	@Autowired
	protected BilleteService billeteService;
	
	@Autowired
	protected AsientoService asientoService;

	@Autowired
	protected VueloService vueloService;
	
	@Autowired
	protected PlatoBaseService platoBaseService;
	
	@Autowired
	protected EquipajeBaseService equipajeBaseService;
	
	@Autowired
	protected ClienteService clienteService;

	@Test
	@Transactional
	public void shouldInsertBilleteIntoDatabaseAndGenerateId() throws ParseException, HorasMaximasVueloException, DisponibilidadAvionException {

		int idVuelo = 2;
		Vuelo vuelo = this.vueloService.findVueloById(idVuelo);
		
		List<Asiento> asientos = this.asientoService.findAsientosSinOcupar(vuelo);
		Asiento asiento = asientos.size()==0?null : asientos.get(0);
		
		int nBilletes = this.billeteService.findBilletesByVuelo(idVuelo).size();

		Billete billete = new Billete();

		billete.setAsiento(asiento);

		billete.setCoste(12);
		LocalDate reserva = LocalDate.parse("2010-05-16", DateTimeFormatter.ISO_DATE);
		billete.setFechaReserva(reserva);
		
		this.billeteService.saveBillete(billete);
		
		int nBilletes2 = this.billeteService.findBilletesByVuelo(idVuelo).size();
		
		assertThat(nBilletes2).isEqualTo(nBilletes + 1);
		//Probamos que el precio se actualiza
		assertThat(billete.getCoste()).isEqualTo(vuelo.getCoste()*1.75);
	}
	
	@Test
	@Transactional
	public void shouldInsertBilleteIntoDatabaseAndGenerateBilleteDescuento() throws ParseException, HorasMaximasVueloException, DisponibilidadAvionException {

		int idVuelo = 2;
		Vuelo vuelo = this.vueloService.findVueloById(idVuelo);

		vuelo.setFechaSalida(LocalDateTime.now().plusDays(5)); //Hacemos que el vuelo tenga descuento
		vuelo.setFechaLlegada(LocalDateTime.now().plusDays(5).plusHours(2));
		
		List<Asiento> asientos = this.asientoService.findAsientosSinOcupar(vuelo);
		Asiento asiento = asientos.size()==0?null : asientos.get(0);
		
		int nBilletes = this.billeteService.findBilletesByVuelo(idVuelo).size();

		Billete billete = new Billete();

		billete.setAsiento(asiento);
		
		double oldCoste = vuelo.getCoste();
		
		if(asiento.getClase() == Clase.EJECUTIVA) {
			oldCoste = oldCoste * 1.25;
		}
		
		else if(asiento.getClase() == Clase.PRIMERACLASE) {
			oldCoste = oldCoste * 1.75;
		}

		billete.setCoste(12);
		LocalDate reserva = LocalDate.parse("2010-05-16", DateTimeFormatter.ISO_DATE);
		billete.setFechaReserva(reserva);
		
		this.billeteService.saveBillete(billete);
		
		int nBilletes2 = this.billeteService.findBilletesByVuelo(idVuelo).size();
		
		assertThat(nBilletes2).isEqualTo(nBilletes + 1);
		//Probamos que el precio se actualiza
		assertThat(oldCoste * 0.75).isEqualTo(billete.getCoste()); 
		//Comprobamos que realmente hemos descontado un 25% sobre el precio del billete
	}
	
	@Test
	@Transactional
	public void shouldInsertMenuIntoDatabaseAndGenerateId() {
		Collection<Menu> menus = this.billeteService.findMenus();
		int found = menus.size();
		
		Billete b = this.billeteService.findBilleteById(3);
		double costeAntiguo = b.getCoste();
		
		Plato p1 = new Plato();
		p1.setPlatoBase(this.platoBaseService.findPlatoBaseByName("Sopa de miso"));
		
		Plato p2 = new Plato();
		p2.setPlatoBase(this.platoBaseService.findPlatoBaseByName("Arroz con ternera al curry"));
		
		Plato p3 = new Plato();
		p3.setPlatoBase(this.platoBaseService.findPlatoBaseByName("Manzana"));
		
		Menu m = new Menu();
		m.setPlato1(p1);
		m.setPlato2(p2);
		m.setPlato3(p3);
		m.setBillete(b);
		
		log.info("Introducimos platosBase: {} de id {}", p1.getPlatoBase().getTipoPlato().getName(), p1.getPlatoBase().getId());
		log.info("Introducimos platosBase: {} de id {}", p2.getPlatoBase().getTipoPlato().getName(), p2.getPlatoBase().getId());
		log.info("Introducimos platosBase: {} de id {}", p3.getPlatoBase().getTipoPlato().getName(), p3.getPlatoBase().getId());
		
		try {
			this.billeteService.saveMenu(m);
		} catch (DataAccessException | TooManyItemsBilleteException | PlatosNoValidosException e) {
			e.getMessage();
		}
		menus = this.billeteService.findMenus();
		
		assertThat(menus.size()).isEqualTo(found + 1);
		//Probamos que el precio se actualiza y como el billete es de primera clase, el menú es gratis
		assertThat(costeAntiguo).isEqualTo(b.getCoste());
	
	}
	
	@Test
	@Transactional
	public void shouldInsertMenuIntoDatabaseAndGenerateIdForECONOMICA() {
		Collection<Menu> menus = this.billeteService.findMenus();
		int found = menus.size();
		
		Billete b = this.billeteService.findBilleteById(4);
		double costeAntiguo = b.getCoste();
		
		Plato p1 = new Plato();
		p1.setPlatoBase(this.platoBaseService.findPlatoBaseByName("Sopa de miso"));
		
		Plato p2 = new Plato();
		p2.setPlatoBase(this.platoBaseService.findPlatoBaseByName("Arroz con ternera al curry"));
		
		Plato p3 = new Plato();
		p3.setPlatoBase(this.platoBaseService.findPlatoBaseByName("Manzana"));
		
		Menu m = new Menu();
		m.setPlato1(p1);
		m.setPlato2(p2);
		m.setPlato3(p3);
		m.setBillete(b);
		
		log.info("Introducimos platosBase: {} de id {}", p1.getPlatoBase().getTipoPlato().getName(), p1.getPlatoBase().getId());
		log.info("Introducimos platosBase: {} de id {}", p2.getPlatoBase().getTipoPlato().getName(), p2.getPlatoBase().getId());
		log.info("Introducimos platosBase: {} de id {}", p3.getPlatoBase().getTipoPlato().getName(), p3.getPlatoBase().getId());
		
		try {
			this.billeteService.saveMenu(m);
		} catch (DataAccessException | TooManyItemsBilleteException | PlatosNoValidosException e) {
			e.getMessage();
		}
		menus = this.billeteService.findMenus();
		
		assertThat(menus.size()).isEqualTo(found + 1);
		//Probamos que el precio se actualiza y como el billete es de primera clase, el menú es gratis
		assertThat(costeAntiguo + p3.getPlatoBase().getPrecio() + p2.getPlatoBase().getPrecio() +
				p1.getPlatoBase().getPrecio()).isEqualTo(b.getCoste());
	
	}
	
	@Test
	@Transactional
	public void shouldNotInsertTooManyMenus() {
		
		Billete b = this.billeteService.findBilleteById(3);
		int found = b.getMenus().size();
		
		Plato p1 = new Plato();
		p1.setPlatoBase(this.platoBaseService.findPlatoBaseByName("Sopa de miso"));
		
		Plato p2 = new Plato();
		p2.setPlatoBase(this.platoBaseService.findPlatoBaseByName("Arroz con ternera al curry"));
		
		Plato p3 = new Plato();
		p3.setPlatoBase(this.platoBaseService.findPlatoBaseByName("Manzana"));
		
		Menu m = new Menu();
		m.setPlato1(p1);
		m.setPlato2(p2);
		m.setPlato3(p3);
		m.setBillete(b);
		Menu m1 = new Menu();
		m1.setPlato1(p1);
		m1.setPlato2(p2);
		m1.setPlato3(p3);
		m1.setBillete(b);
		Menu m2 = new Menu();
		m2.setPlato1(p1);
		m2.setPlato2(p2);
		m2.setPlato3(p3);
		m2.setBillete(b);
		Menu m3 = new Menu();
		m3.setPlato1(p1);
		m3.setPlato2(p2);
		m3.setPlato3(p3);
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
	
		int menusNew = b.getMenus().size();
		
		assertThat(menusNew).isEqualTo(found + 3); //+3 dado que deja insertar los 3 primeros
		
	}
	
	@Test
	@Transactional
	public void shouldNotInsertSamePlatosTypes() {
		
		Billete b = this.billeteService.findBilleteById(3);
		Plato p1 = new Plato();
		p1.setPlatoBase(this.platoBaseService.findPlatoBaseByName("Sopa de miso"));
		
		Plato p2 = new Plato();
		p2.setPlatoBase(this.platoBaseService.findPlatoBaseByName("Manzana"));
		
		Plato p3 = new Plato();
		p3.setPlatoBase(this.platoBaseService.findPlatoBaseByName("Manzana"));

		Set<Plato> s = new HashSet<Plato>();
		s.add(p1);
		s.add(p2);
		s.add(p3);
		
		Menu m = new Menu();
		m.setPlato1(p1);
		m.setPlato2(p2);
		m.setPlato3(p3);
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
		double costeAntiguo = b.getCoste();
		EquipajeBase eb = this.equipajeBaseService.findEquipajeBaseByName("Grande");
		
		Equipaje e = new Equipaje();
		e.setBillete(b);
		e.setPeso(21);
		e.setEquipajeBase(eb);
			
		try {
			this.billeteService.saveEquipaje(e);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		equipajes = this.billeteService.findEquipajes();
		assertThat(equipajes.size()).isEqualTo(found + 1);
		assertThat(e.getId()).isNotNull();
		//Probamos que el precio se actualiza
		assertThat(costeAntiguo + e.getEquipajeBase().getPrecio()).isEqualTo(b.getCoste());
	}
	
	@Test
	@Transactional
	public void shouldNotInsertTooManyEquipajesIntoDatabase() {
		Billete b = this.billeteService.findBilleteById(1);
		
		EquipajeBase eb = this.equipajeBaseService.findEquipajeBaseByName("Grande");
		
		Equipaje e1 = new Equipaje();
		e1.setBillete(b);
		e1.setPeso(21);
		e1.setEquipajeBase(eb);
		Equipaje e2 = new Equipaje();
		e2.setBillete(b);
		e2.setPeso(21);
		e2.setEquipajeBase(eb);
		Equipaje e3 = new Equipaje();
		e3.setBillete(b);
		e3.setPeso(21);	
		e3.setEquipajeBase(eb);
			
		try {
			this.billeteService.saveEquipaje(e1);
			this.billeteService.saveEquipaje(e2);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		Assertions.assertThrows(TooManyItemsBilleteException.class, () ->{
			this.billeteService.saveEquipaje(e2);});
	}
	
	@Test
	@Transactional
	public void shouldNotInsertEquipajeWithWrongWeight() {
		Billete b = this.billeteService.findBilleteById(1);
		EquipajeBase eb = this.equipajeBaseService.findEquipajeBaseByName("Grande");
		
		Equipaje e = new Equipaje();
		e.setBillete(b);
		e.setPeso(2);
		e.setEquipajeBase(eb);
			
		Assertions.assertThrows(ConstraintViolationException.class, () ->{
		this.billeteService.saveEquipaje(e);});
	}
	
	@Test
	void shouldFindBilletesApellido() {
		Pageable page = PageRequest.of(0, 20);
		Page<Billete> billetes = this.billeteService.findBilletePorApellido("Soto Ramirez",page);
		
		assertThat(billetes.getContent()).isNotEmpty();
	}	
	
	@Test
	void shouldFindBilletesCliente() {
		Collection<Billete> billetes = this.billeteService.findBilletesPorCliente(this.clienteService.findClienteById(2));
		
		assertThat(billetes).isNotEmpty();
	}
	
	@Test
	void shouldFindBilletesFecha() {
		Collection<Billete> billetes = this.billeteService.findBilletesPorFecha(LocalDate.of(2020, 4, 6));
		
		assertThat(billetes).isNotEmpty();
	}
	
	@Test
	void shouldFindMenusByVuelo() {
		Set<Menu> menus = this.billeteService.findMenusByVuelo(2);
		
		Pageable page = PageRequest.of(0, 20);
		Page<Billete> billetes = this.billeteService.findBilletes(page);
		Set<Menu> s = billetes.getContent().stream().filter(x -> x.getAsiento().getVuelo().getId().equals(2))
		.flatMap(x -> x.getMenus().stream()).collect(Collectors.toSet());
		
		assertThat(menus).isNotEmpty();
		assertThat(menus.size()).isEqualTo(s.size());
	}
	
	@Test
	void shouldFindBilletesByVuelo() {
		List<Billete> billetes1 = this.billeteService.findBilletesByVuelo(2);
		
		Pageable page = PageRequest.of(0, 20);
		Page<Billete> billetes2 = this.billeteService.findBilletes(page);
		List<Billete> l = billetes2.getContent();
		
		assertThat(billetes1).isNotEmpty();
		assertThat(billetes1.size()).isEqualTo(l.size());
	}
	
	@Test
	void shouldFindClientesBilletesByVuelo() {
		Set<Cliente> clientes = this.billeteService.findClientesBilletesByVuelo(2);
		
		Pageable page = PageRequest.of(0, 20);
		Page<Billete> billetes2 = this.billeteService.findBilletes(page);
		Set<Cliente> s = billetes2.getContent().stream().filter(x -> x.getAsiento().getVuelo().getId().equals(2))
				.map(x -> x.getCliente()).collect(Collectors.toSet());;
		
		for(Cliente c : clientes) {
			System.out.println(c.getApellidos());
		}
		
		assertThat(clientes).isNotEmpty();
		assertThat(clientes.size()).isEqualTo(s.size());
	}
	
	@Test
	void shouldFindNumBilletesByVuelo() {
		Set<Cliente> clientes = this.billeteService.findClientesBilletesByVuelo(2);
		
		Pageable page = PageRequest.of(0, 20);
		Page<Billete> billetes2 = this.billeteService.findBilletes(page);
		Set<Cliente> s = billetes2.getContent().stream().filter(x -> x.getAsiento().getVuelo().getId().equals(2))
				.map(x -> x.getCliente()).collect(Collectors.toSet());;
		
		for(Cliente c : clientes) {
			System.out.println(c.getApellidos());
		}
		
		assertThat(clientes).isNotEmpty();
		assertThat(clientes.size()).isEqualTo(s.size());
	}
}
