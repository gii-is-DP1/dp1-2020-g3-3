package org.springframework.samples.aerolineasAAAFC.service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.aerolineasAAAFC.model.Billete;
import org.springframework.samples.aerolineasAAAFC.model.Cliente;
import org.springframework.samples.aerolineasAAAFC.model.equipaje.Equipaje;
import org.springframework.samples.aerolineasAAAFC.model.menu.Menu;
import org.springframework.samples.aerolineasAAAFC.model.menu.Plato;
import org.springframework.samples.aerolineasAAAFC.model.menu.PlatoBase;
import org.springframework.samples.aerolineasAAAFC.repository.BilleteRepository;
import org.springframework.samples.aerolineasAAAFC.repository.EquipajeRepository;
import org.springframework.samples.aerolineasAAAFC.repository.MenuRepository;
import org.springframework.samples.aerolineasAAAFC.service.exceptions.PlatosNoValidosException;
import org.springframework.samples.aerolineasAAAFC.service.exceptions.TooManyItemsBilleteException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

@Service
public class BilleteService {

	private BilleteRepository billeteRepository;

	private MenuRepository menuRepository;

	private EquipajeRepository equipajeRepository;

	@Autowired
	private PlatoBaseService platoBaseService;

	@Autowired
	public BilleteService(BilleteRepository billeteRepository, MenuRepository menuRepository,
			EquipajeRepository equipajeRepository) {
		this.billeteRepository = billeteRepository;
		this.menuRepository = menuRepository;
		this.equipajeRepository = equipajeRepository;
	}
	
	@Transactional
	public void saveBillete(Billete billete) throws DataAccessException {
		
		billeteRepository.save(billete);
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
						menuRepository.save(menu);
						Set<Menu> newSetMenus = new HashSet<Menu>();
						newSetMenus.add(menu);
						menu.getBillete().setMenus(newSetMenus);
					} else {
						if (menu.getBillete().getMenus().size() >= 3) {
							throw new TooManyItemsBilleteException("Ya ha introducido el máximo de menús permitido.");
						} else {
							menuRepository.save(menu);
							menu.getBillete().getMenus().add(menu);
						}
					}
				}
			}

		}

	}

	@Transactional
	public void saveEquipaje(Equipaje equipaje) throws DataAccessException, TooManyItemsBilleteException {

		if (equipaje.getBillete().getEquipajes().equals(null)) {
			equipajeRepository.save(equipaje);
			Set<Equipaje> newSetEquipajes = new HashSet<Equipaje>();
			newSetEquipajes.add(equipaje);
			equipaje.getBillete().setEquipajes(newSetEquipajes);
		}

		else {
			if (equipaje.getBillete().getEquipajes().size() >= 3) {
				throw new TooManyItemsBilleteException("Ya ha introducido el máximo de equipajes permitido.");
			}

			else {
				equipajeRepository.save(equipaje);
				equipaje.getBillete().getEquipajes().add(equipaje);
			}
		}

	}

	@Transactional
	public Collection<Billete> findBilletesPorFecha(LocalDate fecha) {
		return StreamSupport.stream(billeteRepository.findAll().spliterator(), false)
				.filter(x -> x.getFechaReserva().equals(fecha)).collect(Collectors.toList());
	}

	@Transactional
	public Collection<Billete> findBilletesPorCliente(Cliente cliente) {
		return StreamSupport.stream(billeteRepository.findAll().spliterator(), false)
				.filter(x -> x.getCliente().equals(cliente)).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public Billete findBilleteById(int id) throws DataAccessException {
		Optional<Billete> b = billeteRepository.findById(id);
		return b.get();
	}

	@Transactional
	public Collection<Billete> findBilleteConCliente() {
		return StreamSupport.stream(billeteRepository.findAll().spliterator(), false)
				.filter(x -> !x.getCliente().equals(null)).collect(Collectors.toList());
	}

	public Collection<Billete> findBilletePorApellido(String apellidos) {
		return StreamSupport.stream(billeteRepository.findAll().spliterator(), false)
				.filter(x -> x.getCliente().getApellidos().equals(apellidos)).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public Set<Cliente> findClientesBilletesByVuelo(int id) {
		return StreamSupport.stream(billeteRepository.findAll().spliterator(), false)
				.filter(x -> x.getAsiento().getVuelo().getId().equals(id))
				.map(x -> x.getCliente())
				.collect(Collectors.toSet());
	}
	
	@Transactional(readOnly = true)
	public Long findNumBilletesByVuelo(int id) {
		return StreamSupport.stream(billeteRepository.findAll().spliterator(), false)
				.filter(x -> x.getAsiento().getVuelo().getId().equals(id))
				.collect(Collectors.counting());
	}
	
	@Transactional(readOnly = true)
	public List<Billete> findBilletesByVuelo(int id) {
		return StreamSupport.stream(billeteRepository.findAll().spliterator(), false)
				.filter(x -> x.getAsiento().getVuelo().getId().equals(id))
				.collect(Collectors.toList());
	}
	
	@Transactional(readOnly = true)
	public Set<Menu> findMenusByVuelo(int id) {
		return StreamSupport.stream(billeteRepository.findAll().spliterator(), false)
				.filter(x -> x.getAsiento().getVuelo().getId().equals(id))
				.flatMap(x -> x.getMenus().stream())
				.collect(Collectors.toSet());
	}
	
	@Transactional(readOnly = true)
	public Collection<Menu> findMenus() {
		return StreamSupport.stream(menuRepository.findAll().spliterator(), false).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public Collection<Equipaje> findEquipajes() {
		return StreamSupport.stream(equipajeRepository.findAll().spliterator(), false).collect(Collectors.toList());
	}

}