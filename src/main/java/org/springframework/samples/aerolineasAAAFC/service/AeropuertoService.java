package org.springframework.samples.aerolineasAAAFC.service;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.samples.aerolineasAAAFC.model.Aeropuerto;
import org.springframework.samples.aerolineasAAAFC.repository.AeropuertoRepository;
import org.springframework.samples.aerolineasAAAFC.service.exceptions.TelefonoErroneoException;
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
	public void saveAeropuerto(Aeropuerto aeropuerto) throws DataAccessException, TelefonoErroneoException{
		String aux = aeropuerto.getTelefono();
		if(!(aux.matches("^(\\+|\\d)[0-9]{7,16}$"))) {
			throw new TelefonoErroneoException("");
		} else {
			if(aeropuerto.getId()==null)
				aeropuerto.setVersion(1);
			
			aeropuertoRepository.save(aeropuerto);
		}
	}


	@Transactional(readOnly = true)
	public Aeropuerto findAeropuertoById(int id) throws DataAccessException{
		return aeropuertoRepository.findById(id).get();
	}
	
	@Transactional
	public Page<Aeropuerto> findAeropuertos(Pageable pageable){
		return aeropuertoRepository.findAll(pageable);
	}
	
	

	public void eliminarAeropuerto(int id) throws DataAccessException {
		aeropuertoRepository.deleteById(id);
	}

	public Collection<Aeropuerto> findAeropuertosNoPageable() {
		return StreamSupport.stream(aeropuertoRepository.findAll().spliterator(), false)
	    .collect(Collectors.toList());
	}
}
