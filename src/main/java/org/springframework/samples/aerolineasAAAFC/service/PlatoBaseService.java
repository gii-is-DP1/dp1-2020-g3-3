package org.springframework.samples.aerolineasAAAFC.service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
	
	@Transactional(readOnly = true)
	public Collection<PlatoBase> findPlatosBase() throws DataAccessException {
		return platoBaseRepository.findPlatosBase();
	}
	
	@Transactional(readOnly = true)
	public Collection<PlatoBase> findPlatosPorTipo(String tipoPlato) throws DataAccessException {
		return platoBaseRepository.findPlatosBase().stream().filter(x -> x.getTipoPlato().getName().equals(tipoPlato))
				.collect(Collectors.toList());
	}

}
