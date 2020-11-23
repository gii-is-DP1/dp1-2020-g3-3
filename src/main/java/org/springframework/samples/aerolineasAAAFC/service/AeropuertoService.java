package org.springframework.samples.aerolineasAAAFC.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.aerolineasAAAFC.model.Aeropuerto;
import org.springframework.samples.aerolineasAAAFC.model.Vuelo;
import org.springframework.samples.aerolineasAAAFC.repository.AeropuertoRepository;
import org.springframework.samples.aerolineasAAAFC.repository.VueloRepository;
import org.springframework.transaction.annotation.Transactional;

public class AeropuertoService {

	 private AeropuertoRepository aeropuertoRepository;
	 
	 @Transactional
		public void saveAeropuerto(Aeropuerto aeropuerto) throws DataAccessException{
		 aeropuertoRepository.save(aeropuerto);
		}
	 
	
	 @Transactional(readOnly = true)
		public Aeropuerto findAeropuertoById(int id) throws DataAccessException{
			return aeropuertoRepository.findById(id);
		}
	 
	 
	 @Autowired
		public AeropuertoService(AeropuertoRepository aeropuertoRepository) {
			this.aeropuertoRepository = aeropuertoRepository;
		}	
	
}
