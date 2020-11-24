package org.springframework.samples.aerolineasAAAFC.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.aerolineasAAAFC.model.Billete;
import org.springframework.samples.aerolineasAAAFC.repository.BilleteRepository;
import org.springframework.transaction.annotation.Transactional;

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
	public void saveBillete(Billete billete) throws DataAccessException{
		
		billeteRepository.save(billete);
		
		menuService.saveMenus(billete.getMenus());
		
		equipajeService.saveEquipajes(billete.getEquipajes());
	}
	
	@Transactional(readOnly = true)
	public Optional<Billete> findBilleteById(int id) throws DataAccessException{
		return billeteRepository.findById(id);
	}

}
