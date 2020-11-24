package org.springframework.samples.aerolineasAAAFC.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.aerolineasAAAFC.model.Menu;
import org.springframework.samples.aerolineasAAAFC.repository.MenuRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MenuService {

	private MenuRepository menuRepository;
	
	@Autowired
	public MenuService(MenuRepository menuRepository) {
		this.menuRepository = menuRepository;
	}

	
	//abajo debo añadir la excepcion de precio (pasar este metodo a individual)
	//saveMenu -> y el cambio se hace en billete service
	@Transactional 
	public void saveMenus(Set<Menu> set) throws DataAccessException {

		set.stream().forEach(menu -> menuRepository.save(menu));
		
	}
	
}
