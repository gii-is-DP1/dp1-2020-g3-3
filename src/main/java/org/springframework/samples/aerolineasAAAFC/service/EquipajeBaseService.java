package org.springframework.samples.aerolineasAAAFC.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.aerolineasAAAFC.model.equipaje.EquipajeBase;
import org.springframework.samples.aerolineasAAAFC.repository.EquipajeBaseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EquipajeBaseService {

	private EquipajeBaseRepository equipajeBaseRepository;

	@Autowired
	public EquipajeBaseService(EquipajeBaseRepository equipajeBaseRepository) {
		this.equipajeBaseRepository = equipajeBaseRepository;
	}

	@Transactional(readOnly = true)
	public EquipajeBase findEquipajeBaseByName(String name) {
		return this.equipajeBaseRepository.findByName(name);
	}
	
	@Transactional(readOnly = true)
	public Collection<EquipajeBase> findEquipajesBase() throws DataAccessException {
		return equipajeBaseRepository.findEquipajesBase();
	}
}
