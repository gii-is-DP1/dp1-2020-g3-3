package org.springframework.samples.aerolineasAAAFC.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.aerolineasAAAFC.model.Menu;
import org.springframework.samples.aerolineasAAAFC.repository.MenuRepository;
import org.springframework.samples.aerolineasAAAFC.service.exceptions.MenuPriceException;
import org.springframework.samples.aerolineasAAAFC.utils.menuUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MenuService {

	private MenuRepository menuRepository;

	@Autowired
	public MenuService(MenuRepository menuRepository) {
		this.menuRepository = menuRepository;
	}

	@Transactional(rollbackFor = MenuPriceException.class)
	public void saveMenu(Menu menu) throws DataAccessException, MenuPriceException {

		if (!menuUtils.validaPrecio(menu)) {
			throw new MenuPriceException("El precio recibido no se corresponde con el estipulado en web.");
		}

		else {
			menuRepository.save(menu);
		}

	}

	@Transactional
	public void deleteMenu(int id) throws DataAccessException {
		menuRepository.deleteById(id);
	}

	@Transactional (readOnly = true)
	public Menu findMenuById(int id) throws DataAccessException {
		
		Menu menu = menuRepository.findById(id).orElseGet(null);
		
		return menu;
	}

}
