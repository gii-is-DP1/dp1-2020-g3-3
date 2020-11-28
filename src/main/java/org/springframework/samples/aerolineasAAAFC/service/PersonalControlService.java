package org.springframework.samples.aerolineasAAAFC.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.aerolineasAAAFC.model.Azafato;
import org.springframework.samples.aerolineasAAAFC.model.PersonalControl;
import org.springframework.samples.aerolineasAAAFC.model.PersonalOficina;
import org.springframework.samples.aerolineasAAAFC.repository.PersonalControlRepository;
import org.springframework.samples.aerolineasAAAFC.repository.PersonalOficinaRepository;
import org.springframework.samples.aerolineasAAAFC.service.exceptions.IbanDuplicadoException;
import org.springframework.samples.aerolineasAAAFC.service.exceptions.NifDuplicadoException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PersonalControlService {


	private PersonalControlRepository pControlRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthoritiesService authoritiesService;
	
	@Autowired
	public PersonalControlService(PersonalControlRepository pControlRepository) {
		this.pControlRepository = pControlRepository;
	}
	
	public PersonalControl findPersonalControlByNif(String nif) {
		return pControlRepository.findByNif(nif);
	}
	
	public PersonalControl findPersonalControlByIban(String iban) {
		return pControlRepository.findByIban(iban);
	}
	
	@Transactional
	public void savePersonalControl(PersonalControl pControl) throws DataAccessException, NifDuplicadoException, IbanDuplicadoException{
		PersonalControl pNif = pControlRepository.findByNif(pControl.getNif());
		PersonalControl pIban = pControlRepository.findByIban(pControl.getIban());
		if(pNif != null) {
			throw new NifDuplicadoException();
		}else if(pIban != null){
			throw new IbanDuplicadoException();
		}else {
			pControlRepository.save(pControl);
			userService.saveUser(pControl.getUser());
			authoritiesService.saveAuthorities(pControl.getUser().getUsername(), "personal");
		}
	}
	
	@Transactional(readOnly=true)
	public PersonalControl findPersonalControlById(int id) throws DataAccessException{
		return pControlRepository.findById(id).get();
	}
	
}
