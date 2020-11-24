package org.springframework.samples.aerolineasAAAFC.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.aerolineasAAAFC.model.Equipaje;
import org.springframework.samples.aerolineasAAAFC.repository.EquipajeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EquipajeService {

	private EquipajeRepository equipajeRepository;
	
	@Autowired
	public EquipajeService(EquipajeRepository equipajeRepository) {
		this.equipajeRepository = equipajeRepository;
	}
	
	@Transactional
	public void saveEquipajes(Set<Equipaje> set) throws DataAccessException {
		
		set.stream().forEach(equipaje -> equipajeRepository.save(equipaje));
	
	}
	
}
