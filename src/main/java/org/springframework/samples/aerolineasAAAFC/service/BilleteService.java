package org.springframework.samples.aerolineasAAAFC.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.samples.aerolineasAAAFC.deprecated.EquipajeService;
import org.springframework.samples.aerolineasAAAFC.deprecated.MenuService;
import org.springframework.samples.aerolineasAAAFC.model.Asiento;
import org.springframework.samples.aerolineasAAAFC.model.Billete;
import org.springframework.samples.aerolineasAAAFC.model.Clase;
import org.springframework.samples.aerolineasAAAFC.model.Cliente;
import org.springframework.samples.aerolineasAAAFC.model.equipaje.Equipaje;
import org.springframework.samples.aerolineasAAAFC.model.menu.Menu;
import org.springframework.samples.aerolineasAAAFC.model.menu.Plato;
import org.springframework.samples.aerolineasAAAFC.model.menu.PlatoBase;
import org.springframework.samples.aerolineasAAAFC.repository.BilleteRepository;
import org.springframework.samples.aerolineasAAAFC.service.exceptions.EquipajePriceException;
import org.springframework.samples.aerolineasAAAFC.service.exceptions.PlatosNoValidosException;
import org.springframework.samples.aerolineasAAAFC.service.exceptions.TooManyItemsBilleteException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

@Service
public class BilleteService {

	private BilleteRepository billeteRepository;

	private MenuService menuService;

	private EquipajeService equipajeService;

	private PlatoBaseService platoBaseService;

	@Autowired
	public BilleteService(BilleteRepository billeteRepository, MenuService menuService, 
			EquipajeService equipajeService, PlatoBaseService platoBaseService) {
		this.billeteRepository = billeteRepository;
		this.menuService= menuService;
		this.equipajeService = equipajeService;
		this.platoBaseService = platoBaseService;
	}

	@Transactional
	public void saveBillete(Billete billete) throws DataAccessException {
		Asiento a = billete.getAsiento();

		if (a.getClase().equals(Clase.ECONOMICA)) {
			billete.setCoste(a.getVuelo().getCoste());
		}

		else if (a.getClase().equals(Clase.EJECUTIVA)) {
			billete.setCoste(1.25 * a.getVuelo().getCoste());
		}

		else if (a.getClase().equals(Clase.PRIMERACLASE)) {
			billete.setCoste(1.75 * a.getVuelo().getCoste());
		}

		DateTimeFormatter d = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		LocalDate today = LocalDate.now();
		String aux = today.format(d);
		today = LocalDate.parse(aux, d);
		billete.setFechaReserva(today);

		billeteRepository.save(billete);
		//Ocupamos el asiento
		a.setLibre(false);
	}

	@Transactional
	public void saveMenu(Menu menu) throws DataAccessException, TooManyItemsBilleteException, PlatosNoValidosException {

		if (menu.getPlato1() == null || menu.getPlato2() == null || menu.getPlato3() == null) {
			throw new PlatosNoValidosException(1);
		}

		else {
			int cont1 = 0;
			int cont2 = 0;
			int cont3 = 0;

			Plato p1 = menu.getPlato1();
			Plato p2 = menu.getPlato2();
			Plato p3 = menu.getPlato3();
			PlatoBase aux1 = platoBaseService.findPlatoBaseByName(p1.getPlatoBase().getName());
			PlatoBase aux2 = platoBaseService.findPlatoBaseByName(p2.getPlatoBase().getName());
			PlatoBase aux3 = platoBaseService.findPlatoBaseByName(p3.getPlatoBase().getName());

			if (aux1 == null || aux2 == null || aux3 == null)
				throw new PlatosNoValidosException(2);

			else { // Esta comprobacion es solo necesaria si usamos el service directamente, se
					// puede eliminar
				if (aux1.getTipoPlato().getName().equals("primerPlato"))
					cont1 = 1;

				if (aux2.getTipoPlato().getName().equals("segundoPlato"))
					cont2 = 1;

				if (aux3.getTipoPlato().getName().equals("postre"))
					cont3 = 1;

				if (cont1 != 1 || cont2 != 1 || cont3 != 1)
					throw new PlatosNoValidosException(3);

				else {

					if (menu.getBillete().getMenus().equals(null)) {
						menuService.saveMenu(menu);
						Set<Menu> newSetMenus = new HashSet<Menu>();
						newSetMenus.add(menu);
						menu.getBillete().setMenus(newSetMenus);
					} else {
						if (menu.getBillete().getMenus().size() >= 3) {
							throw new TooManyItemsBilleteException("Ya ha introducido el máximo de menús permitido.");
						} else {
							menuService.saveMenu(menu);
							menu.getBillete().getMenus().add(menu);
							menu.getBillete().setCoste(menu.getBillete().getCoste() + aux1.getPrecio()
									+ aux2.getPrecio() + aux3.getPrecio());
							// Le añadimos el precio del menu
						}
					}
				}
			}

		}

	}

	@Transactional
	public void saveEquipaje(Equipaje equipaje) throws DataAccessException, TooManyItemsBilleteException, EquipajePriceException {

		if (equipaje.getBillete().getEquipajes().equals(null)) {
			equipajeService.saveEquipaje(equipaje);
			Set<Equipaje> newSetEquipajes = new HashSet<Equipaje>();
			newSetEquipajes.add(equipaje);
			equipaje.getBillete().setEquipajes(newSetEquipajes);
		}

		else {
			if (equipaje.getBillete().getEquipajes().size() >= 3) {
				throw new TooManyItemsBilleteException("Ya ha introducido el máximo de equipajes permitido.");
			}

			else {
				equipajeService.saveEquipaje(equipaje);
				equipaje.getBillete().getEquipajes().add(equipaje);
				equipaje.getBillete()
						.setCoste(equipaje.getBillete().getCoste() + equipaje.getEquipajeBase().getPrecio());
			}
		}

	}

	@Transactional
	public Collection<Billete> findBilletesPorFecha(LocalDate fecha) {
		return billeteRepository.findByFecha(fecha);
	}

	@Transactional
	public Collection<Billete> findBilletesPorCliente(Cliente cliente) {
		return billeteRepository.findByCliente(cliente);
	}

	@Transactional(readOnly = true)
	public Billete findBilleteById(int id) throws DataAccessException {
		return billeteRepository.findById(id).get();
	}

	public Page<Billete> findBilletePorApellido(String apellidos, Pageable page) {
		return billeteRepository.findByApellido(apellidos.toUpperCase(), page);
	}

	@Transactional(readOnly = true)
	public Set<Cliente> findClientesBilletesByVuelo(int id) {
		return billeteRepository.findClientesBilletesByVuelo(id);
	}

	@Transactional(readOnly = true)
	public List<Billete> findBilletesByVuelo(int id) {
		return billeteRepository.findBilletesByVuelo(id);
	}

	@Transactional(readOnly = true)
	public Set<Menu> findMenusByVuelo(int id) {
		return billeteRepository.findMenusByVuelo(id);
	}

	@Transactional(readOnly = true)
	public Collection<Menu> findMenus() {
		return menuService.findMenus();
	}

	@Transactional(readOnly = true)
	public Collection<Equipaje> findEquipajes() {
		return equipajeService.findEquipajes();
	}

	public Page<Billete> findBilletes(Pageable page) {
		return billeteRepository.findAll(page);
	}

}