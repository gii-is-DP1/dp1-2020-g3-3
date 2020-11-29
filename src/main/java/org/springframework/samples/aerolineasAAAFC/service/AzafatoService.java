package org.springframework.samples.aerolineasAAAFC.service;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.aerolineasAAAFC.model.Azafato;
import org.springframework.samples.aerolineasAAAFC.repository.AzafatoRepository;
import org.springframework.samples.aerolineasAAAFC.service.exceptions.IbanDuplicadoException;
import org.springframework.samples.aerolineasAAAFC.service.exceptions.IdiomasNoSuficientesException;
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
	public void saveAzafato(Azafato azafato) throws DataAccessException, IbanDuplicadoException, 
	NifDuplicadoException, IdiomasNoSuficientesException{ 
		
		Azafato aNif = azafatoRepository.findByNif(azafato.getNif());  //Busca en BDs si hay atributos duplicados
		Azafato aIban = azafatoRepository.findByIban(azafato.getIban());
		if(aNif != null) {
			throw new NifDuplicadoException();
		}else if(aIban != null){
			throw new IbanDuplicadoException();
		}else if(azafato.getIdiomas().size() < 2){
			throw new IdiomasNoSuficientesException("Parece que no ha introducido 2 o más idiomas para este empleado");
		}
		
		else {
			azafatoRepository.save(azafato);		
			userService.saveUser(azafato.getUser());
			authoritiesService.saveAuthorities(azafato.getUser().getUsername(), "azafato");
		}
	}

	public Collection<Azafato> findAzafatos() {
		return StreamSupport.stream(azafatoRepository.findAll().spliterator(), false)
			    .collect(Collectors.toList());
	}
}
