package org.springframework.samples.aerolineasAAAFC.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.aerolineasAAAFC.model.Azafato;
import org.springframework.samples.aerolineasAAAFC.repository.AzafatoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AzafatoService {
	
	public AzafatoRepository azafatoRepository;

	@Autowired
	public AzafatoService(AzafatoRepository azafatoRepository) {
		this.azafatoRepository = azafatoRepository;
	}	
	
	@Transactional(readOnly = true)
	public Optional<Azafato> findAzafatoById(int id) {
		return azafatoRepository.findById(id);
	}
}
