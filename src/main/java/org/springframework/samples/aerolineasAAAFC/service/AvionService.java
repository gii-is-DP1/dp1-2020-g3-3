package org.springframework.samples.aerolineasAAAFC.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.aerolineasAAAFC.model.Avion;
import org.springframework.samples.aerolineasAAAFC.repository.AvionRepository;
import org.springframework.transaction.annotation.Transactional;

public class AvionService {
	
	private AvionRepository avionRepository;
	
	// Duda: ¿hay que meter en nuestros servicios 
	//       el UserService y el AuthoritiesService?
	@Autowired
	public AvionService(AvionRepository avionRepository) {
		this.avionRepository = avionRepository;
	}
	
	@Transactional
	public void saveAvion(Avion avion) throws DataAccessException{
		avionRepository.save(avion);
	}
	
	@Transactional(readOnly = true)
	public Avion findAvionById(int id) throws DataAccessException{
		return avionRepository.findById(id).get();
	}
}
