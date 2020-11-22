package org.springframework.samples.aerolineasAAAFC.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.aerolineasAAAFC.repository.EquipajeRepository;
import org.springframework.stereotype.Service;

@Service
public class EquipajeService {

	private EquipajeRepository equipajeRepository;
	
	@Autowired
	public EquipajeService(EquipajeRepository equipajeRepository) {
		this.equipajeRepository = equipajeRepository;
	}
	
}
