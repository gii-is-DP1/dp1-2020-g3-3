package org.springframework.samples.aerolineasAAAFC.service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.aerolineasAAAFC.model.Billete;
import org.springframework.samples.aerolineasAAAFC.model.Cliente;
import org.springframework.samples.aerolineasAAAFC.model.Vuelo;
import org.springframework.samples.aerolineasAAAFC.repository.BilleteRepository;
import org.springframework.samples.aerolineasAAAFC.repository.ClienteRepository;
import org.springframework.samples.aerolineasAAAFC.service.exceptions.EquipajePriceException;
import org.springframework.samples.aerolineasAAAFC.service.exceptions.MenuPriceException;
import org.springframework.samples.aerolineasAAAFC.service.exceptions.TooManyItemsBilleteException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

@Service
public class BilleteService {

	private BilleteRepository billeteRepository;

	@Autowired
	private MenuService menuService;

	@Autowired
	private EquipajeService equipajeService;

	@Autowired
	public BilleteService(BilleteRepository billeteRepository) {
		this.billeteRepository = billeteRepository;
	}

	@Transactional
	public void saveBillete(Billete billete) throws DataAccessException, TooManyItemsBilleteException {

		if ((billete.getEquipajes().size() <= 3) || (billete.getMenus().size() <= 3)) {
			billete.getMenus().stream().forEach(menu -> {
				try {
					menuService.saveMenu(menu);
				} catch (MenuPriceException e) {
					e.printStackTrace();
				}
			});

			billete.getEquipajes().stream().forEach(equipaje -> {
				try {
					equipajeService.saveEquipaje(equipaje);
				} catch (EquipajePriceException e) {
					e.printStackTrace();
				}
			});

			billeteRepository.save(billete);
		}

		else if((billete.getMenus().size() > 3)){
			throw new TooManyItemsBilleteException("Parece que ha introducido demasiados menús asociados al billete.","menus");
		}
		
		else if((billete.getEquipajes().size() > 3)){
			throw new TooManyItemsBilleteException("Parece que ha introducido demasiados equipajes asociados al billete.","equipajes");
		}

	}

	@Transactional
	public Collection<Billete> findBilletesPorFecha(LocalDate fecha){
		return StreamSupport.stream(billeteRepository.findAll().spliterator(), false)
				.filter(x->x.getFechaReserva().equals(fecha))
				.collect(Collectors.toList());
	}
	
	@Transactional
	public Collection<Billete> findBilletesPorCliente(Cliente cliente){
		return StreamSupport.stream(billeteRepository.findAll().spliterator(), false)
				.filter(x->x.getCliente().equals(cliente))
				.collect(Collectors.toList());
	}
	
	
	
	@Transactional(readOnly = true)
	public Billete findBilleteById(int id) throws DataAccessException {
		return billeteRepository.findById(id).get();
	}

	@Transactional
	public Collection<Billete> findBilleteConCliente() {
		return StreamSupport.stream(billeteRepository.findAll().spliterator(), false)
				.filter(x->!x.getCliente().equals(null))
				.collect(Collectors.toList());
	}
	
	

}
