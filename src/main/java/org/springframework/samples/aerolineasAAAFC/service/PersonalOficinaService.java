package org.springframework.samples.aerolineasAAAFC.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.aerolineasAAAFC.model.PersonalControl;
import org.springframework.samples.aerolineasAAAFC.model.PersonalOficina;
import org.springframework.samples.aerolineasAAAFC.repository.PersonalOficinaRepository;
import org.springframework.samples.aerolineasAAAFC.service.exceptions.IbanDuplicadoException;
import org.springframework.samples.aerolineasAAAFC.service.exceptions.NifDuplicadoException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PersonalOficinaService {

	private PersonalOficinaRepository pOficinaRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthoritiesService authoritiesService;
	
	@Autowired
	public PersonalOficinaService(PersonalOficinaRepository pOficinaRepository) {
		this.pOficinaRepository = pOficinaRepository;
	}
	
	public PersonalOficina findPersonalControlByNif(String nif) {
		return pOficinaRepository.findByNif(nif);
	}
	
	public PersonalOficina findPersonalControlByIban(String iban) {
		return pOficinaRepository.findByIban(iban);
	}
	
	@Transactional
	public void savePersonalOficina(PersonalOficina pOficina) throws DataAccessException, NifDuplicadoException, IbanDuplicadoException{
		PersonalOficina pNif = pOficinaRepository.findByNif(pOficina.getNif());
		PersonalOficina pIban = pOficinaRepository.findByIban(pOficina.getIban());
		if(pNif != null) {
			throw new NifDuplicadoException();
		}else if(pIban != null){
			throw new IbanDuplicadoException();
		}else {
			pOficinaRepository.save(pOficina);
			userService.saveUser(pOficina.getUser());
			authoritiesService.saveAuthorities(pOficina.getUser().getUsername(), "personal");
		}
	}
	
	@Transactional(readOnly=true)
	public PersonalOficina findPersonalOficinaById(int id) throws DataAccessException{
		return pOficinaRepository.findById(id).get();
	}
}
