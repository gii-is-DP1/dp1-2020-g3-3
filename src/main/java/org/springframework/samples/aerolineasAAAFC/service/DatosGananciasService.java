package org.springframework.samples.aerolineasAAAFC.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.aerolineasAAAFC.model.Billete;
import org.springframework.samples.aerolineasAAAFC.model.DatosGanancias;
import org.springframework.samples.aerolineasAAAFC.repository.DatosGananciasRepository;
import org.springframework.samples.aerolineasAAAFC.service.exceptions.TelefonoErroneoException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DatosGananciasService {

	private DatosGananciasRepository datosGananciasRepository;

	@Autowired
	public DatosGananciasService(DatosGananciasRepository datosGananciasRepository) {
		this.datosGananciasRepository = datosGananciasRepository;
	}	

	@Transactional
	public void saveDatosGanancias(DatosGanancias datosGanancias) throws DataAccessException {
		this.datosGananciasRepository.save(datosGanancias);
	}

	@Transactional(readOnly = true)
	public DatosGanancias findDatosGananciasById(int id) throws DataAccessException{
		return datosGananciasRepository.findById(id).get();
	}
	
	@Transactional(readOnly = true)
	public List<Billete> findGanancias(LocalDate fecha) throws DataAccessException{
		return datosGananciasRepository.findByFecha(fecha);
	}
}
