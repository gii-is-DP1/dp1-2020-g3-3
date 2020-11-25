package org.springframework.samples.aerolineasAAAFC.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.aerolineasAAAFC.model.Azafato;
import org.springframework.samples.aerolineasAAAFC.repository.AzafatoRepository;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AzafatoService {
	
	public AzafatoRepository azafatoRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthoritiesService authoritiesService;
	

	@Autowired
	public AzafatoService(AzafatoRepository azafatoRepository) {
		this.azafatoRepository = azafatoRepository;
	}	
	
	@Transactional(readOnly = true)
	public Azafato findAzafatoById(int id) {
		return azafatoRepository.findById(id).get();
	}
	
	@Transactional
	public void saveAzafato(Azafato azafato) throws DataAccessException {
		azafatoRepository.save(azafato);		
		userService.saveUser(azafato.getUser());
		authoritiesService.saveAuthorities(azafato.getUser().getUsername(), "azafato");
	}		
}
