package org.springframework.samples.aerolineasAAAFC.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.aerolineasAAAFC.model.PersonalOficina;
import org.springframework.samples.aerolineasAAAFC.repository.PersonalOficinaRepository;
import org.springframework.transaction.annotation.Transactional;

public class PersonalOficinaService {

	private PersonalOficinaRepository pOficinaRepository;
	
	@Autowired
	public PersonalOficinaService(PersonalOficinaRepository pOficinaRepository) {
		this.pOficinaRepository = pOficinaRepository;
	}
	
	@Transactional
	public void savePersonalOficina(PersonalOficina pOficina) throws DataAccessException{
		pOficinaRepository.save(pOficina);
	}
	
	@Transactional(readOnly=true)
	public PersonalOficina findPersonalOficinaById(int id) throws DataAccessException{
		return pOficinaRepository.findById(id);
	}
}
