package org.springframework.samples.aerolineasAAAFC.service;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.aerolineasAAAFC.model.Avion;
import org.springframework.samples.aerolineasAAAFC.model.Vuelo;
import org.springframework.samples.aerolineasAAAFC.repository.AvionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AvionService {
	
	private AvionRepository avionRepository;
	
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
	
	@Transactional
	public Collection<Avion> findAviones(){
		return StreamSupport.stream(avionRepository.findAll().spliterator(), false)
				.collect(Collectors.toList());
	}
	
	@Transactional
	public void eliminarAvion(int id) throws DataAccessException{
		avionRepository.deleteById(id);
	}
}
