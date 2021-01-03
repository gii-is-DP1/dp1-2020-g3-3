package org.springframework.samples.aerolineasAAAFC.service;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.samples.aerolineasAAAFC.model.PersonalControl;
import org.springframework.samples.aerolineasAAAFC.model.PersonalOficina;
import org.springframework.samples.aerolineasAAAFC.model.Vuelo;
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
	public void savePersonalOficina(PersonalOficina pOficina) throws DataAccessException, DataIntegrityViolationException, IbanDuplicadoException{
		PersonalOficina pIban = pOficinaRepository.findByIban(pOficina.getIban());
//		PersonalOficina pNif = pOficinaRepository.findByNif(pOficina.getNif());
		
		if(pIban != null && !pIban.getId().equals(pOficina.getId())){
			throw new IbanDuplicadoException("");
//		}else if(pNif != null && !pNif.getId().equals(pOficina.getId())){
//			throw new DataIntegrityViolationException("");
		}else {
			pOficinaRepository.save(pOficina);
			userService.saveUser(pOficina.getUser());
			authoritiesService.saveAuthorities(pOficina.getUser().getUsername(), "personalOficina");
		}
	}
	
	@Transactional(readOnly=true)
	public PersonalOficina findPersonalOficinaById(int id) throws DataAccessException{
		return pOficinaRepository.findById(id).get();
	}
	
	@Transactional
	public Collection<PersonalOficina> findPersonal(){
		return StreamSupport.stream(pOficinaRepository.findAll().spliterator(), false)
				.collect(Collectors.toList());
	}
	
	public void eliminarPersonalOficina(int id) throws DataAccessException {
		pOficinaRepository.deleteById(id);
	}
}
