package org.springframework.samples.aerolineasAAAFC.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.aerolineasAAAFC.model.PersonalControl;
import org.springframework.samples.aerolineasAAAFC.repository.PersonalControlRepository;
import org.springframework.transaction.annotation.Transactional;

public class PersonalControlService {

	private PersonalControlRepository pControlRepository;
	
	@Autowired 
	public PersonalControlService(PersonalControlRepository pControlRepository) {
		this.pControlRepository = pControlRepository;
	}
	
	@Transactional
	public void savePersonalControl(PersonalControl pControl) throws DataAccessException{
		pControlRepository.save(pControl);
	}
	
	@Transactional(readOnly=true)
	public PersonalControl findPersonalControlById(int id) throws DataAccessException{
		return pControlRepository.findById(id);
	}
}
