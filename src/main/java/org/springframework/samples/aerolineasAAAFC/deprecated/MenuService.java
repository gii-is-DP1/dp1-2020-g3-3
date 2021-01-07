package org.springframework.samples.aerolineasAAAFC.deprecated;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.aerolineasAAAFC.model.menu.Menu;
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

	@Transactional(rollbackFor = MenuPriceException.class)
	public void saveMenu(Menu menu) throws DataAccessException {

		menuRepository.save(menu);

	}

	@Transactional
	public void deleteMenu(int id) throws DataAccessException {
		menuRepository.deleteById(id);
	}

	@Transactional(readOnly = true)
	public Menu findMenuById(int id) throws DataAccessException {

		Menu menu = menuRepository.findById(id).orElseGet(null);

		return menu;
	}

	public Collection<Menu> findMenus() {
		return StreamSupport.stream(menuRepository.findAll().spliterator(), false).collect(Collectors.toList());
	}

}
