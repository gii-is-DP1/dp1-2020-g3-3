package org.springframework.samples.aerolineasAAAFC.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.aerolineasAAAFC.model.Aeropuerto;
import org.springframework.samples.aerolineasAAAFC.repository.AeropuertoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AeropuertoService {

	private AeropuertoRepository aeropuertoRepository;

	@Autowired
	public AeropuertoService(AeropuertoRepository aeropuertoRepository) {
		this.aeropuertoRepository = aeropuertoRepository;
	}	

	@Transactional
	public void saveAeropuerto(Aeropuerto aeropuerto) throws DataAccessException{
		aeropuertoRepository.save(aeropuerto);
	}


	@Transactional(readOnly = true)
	public Aeropuerto findAeropuertoById(int id) throws DataAccessException{
		return aeropuertoRepository.findById(id).get();
	}


	public void eliminarAeropuerto(int id) throws DataAccessException {
		aeropuertoRepository.deleteById(id);
	}
}
