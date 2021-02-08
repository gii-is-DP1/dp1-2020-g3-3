package org.springframework.samples.aerolineasAAAFC.service;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.samples.aerolineasAAAFC.model.PersonalOficina;
import org.springframework.samples.aerolineasAAAFC.repository.PersonalOficinaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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

	
	public PersonalOficina findPersonalOficinaByNif(String nif) {
		return pOficinaRepository.findByNif(nif);
	}

	@Transactional(readOnly = true)
	public PersonalOficina findPersonalOficinaByIban(String iban) {
		return pOficinaRepository.findByIban(iban);
	}

	@Transactional
	public void savePersonalOficina(PersonalOficina pOficina) throws DataIntegrityViolationException{
		if(pOficina.getId() == null) {
			pOficina.setVersion(1);
			pOficinaRepository.save(pOficina);
			log.info("Personal de Oficina {} creado.", pOficina.getId());
			userService.saveUser(pOficina.getUser());
			authoritiesService.saveAuthorities(pOficina.getUser().getUsername(), "personalOficina");
		}else {
			pOficina.setVersion(pOficina.getVersion()+1);
			pOficinaRepository.save(pOficina);
			log.info("Personal de Oficina {} actualizado.", pOficina.getId());
		}
	}
	
	@Transactional(readOnly = true)
	public PersonalOficina findPersonalOficinaById(int id) throws DataAccessException{
		return pOficinaRepository.findById(id).get();
	}

	@Transactional(readOnly = true)
	public Collection<PersonalOficina> findPersonalNoPageable(){
		return StreamSupport.stream(pOficinaRepository.findAll().spliterator(), false)
				.collect(Collectors.toList());
	}
	
	@Transactional(readOnly = true)
	public Page<PersonalOficina> findPersonal(Pageable pageable){
		return pOficinaRepository.findAll(pageable);
	}

	@Transactional
	public void deletePersonalOficinaById(int id) throws DataAccessException {
		log.info("Personal de Oficina {} eliminado.", id);
		pOficinaRepository.deleteById(id);
	}
}
