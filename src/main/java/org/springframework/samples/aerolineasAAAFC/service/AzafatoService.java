package org.springframework.samples.aerolineasAAAFC.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.aerolineasAAAFC.model.Azafato;
import org.springframework.samples.aerolineasAAAFC.model.Cliente;
import org.springframework.samples.aerolineasAAAFC.repository.AzafatoRepository;
import org.springframework.samples.aerolineasAAAFC.service.exceptions.IbanDuplicadoException;
import org.springframework.samples.aerolineasAAAFC.service.exceptions.NifDuplicadoException;
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
	
	public Azafato findAzafatoByNif(String nif) {
		return azafatoRepository.findByNif(nif);
	}
	
	public Azafato findAzafatoByIban(String iban) {
		return azafatoRepository.findByIban(iban);
	}
	
	@Transactional
	public void saveAzafato(Azafato azafato) throws DataAccessException, IbanDuplicadoException, NifDuplicadoException{ 
		Azafato aNif = azafatoRepository.findByNif(azafato.getNif());
		Azafato aIban = azafatoRepository.findByIban(azafato.getIban());
		if(aNif != null) {
			throw new NifDuplicadoException();
		}else if(aIban != null){
			throw new IbanDuplicadoException();
		}else {
			azafatoRepository.save(azafato);		
			userService.saveUser(azafato.getUser());
			authoritiesService.saveAuthorities(azafato.getUser().getUsername(), "azafato");
		}
	}		
}
