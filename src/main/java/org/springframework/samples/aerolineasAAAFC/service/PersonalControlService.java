<<<<<<< HEAD
package org.springframework.samples.aerolineasAAAFC.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.aerolineasAAAFC.model.PersonalControl;
import org.springframework.samples.aerolineasAAAFC.model.PersonalOficina;
import org.springframework.samples.aerolineasAAAFC.repository.PersonalControlRepository;
import org.springframework.samples.aerolineasAAAFC.repository.PersonalOficinaRepository;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.transaction.annotation.Transactional;

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
	
	@Transactional
	public void savePersonalControl(PersonalControl pControl) throws DataAccessException{
		pControlRepository.save(pControl);
		userService.saveUser(pControl.getUser());
		authoritiesService.saveAuthorities(pControl.getUser().getUsername(), "personal");
	}
	
	@Transactional(readOnly=true)
	public PersonalControl findPersonalControlById(int id) throws DataAccessException{
		return pControlRepository.findById(id);
	}
	
}
=======
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
>>>>>>> branch 'master' of https://github.com/gii-is-DP1/dp1-2020-g3-3.git
