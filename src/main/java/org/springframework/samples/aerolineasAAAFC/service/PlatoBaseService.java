package org.springframework.samples.aerolineasAAAFC.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.aerolineasAAAFC.model.menu.PlatoBase;
import org.springframework.samples.aerolineasAAAFC.repository.PlatoBaseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlatoBaseService {

	private PlatoBaseRepository platoBaseRepository;

	@Autowired
	public PlatoBaseService(PlatoBaseRepository platoBaseRepository) {
		this.platoBaseRepository = platoBaseRepository;
	}

	@Transactional(readOnly = true)
	public PlatoBase findPlatoBaseByName(String name) {
		return this.platoBaseRepository.findByName(name);
	}

}
