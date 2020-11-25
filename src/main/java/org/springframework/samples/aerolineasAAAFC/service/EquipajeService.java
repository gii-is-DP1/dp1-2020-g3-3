package org.springframework.samples.aerolineasAAAFC.service;

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
	public void saveEquipaje(Equipaje equipaje) throws DataAccessException {
		
		equipajeRepository.save(equipaje);
	
	}
	
}
