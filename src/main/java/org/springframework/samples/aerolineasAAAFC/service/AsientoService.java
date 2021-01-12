package org.springframework.samples.aerolineasAAAFC.service;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.aerolineasAAAFC.model.Asiento;
import org.springframework.samples.aerolineasAAAFC.model.Avion;
import org.springframework.samples.aerolineasAAAFC.model.Vuelo;
import org.springframework.samples.aerolineasAAAFC.repository.AsientoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AsientoService {

	private AsientoRepository asientoRepository;
	
	@Autowired
	public AsientoService(AsientoRepository asientoRepository) {
		this.asientoRepository = asientoRepository;
	}
	
	@Transactional
	public void saveAsiento(Asiento asiento) throws DataAccessException{
		asientoRepository.save(asiento);
	}
	
	@Transactional(readOnly = true)
	public Asiento findAsientoById(int id) throws DataAccessException{
		return asientoRepository.findById(id).get();
	}
	
	@Transactional
	public Collection<Asiento> findAsientos(){
		return StreamSupport.stream(asientoRepository.findAll().spliterator(), false)
				.collect(Collectors.toList());
	}
	
	@Transactional
	public void eliminarAsiento(int id) throws DataAccessException{
		asientoRepository.deleteById(id);
	}
	
//	@Transactional
//	public void saveManyAsiento(Integer total,Vuelo vuelo) {
//		
//		for(int i=0;i<total;i++) {
//			Asiento asiento=new Asiento();
//			asiento.setVuelos(vuelo);
//			this.saveAsiento(asiento);
//		}
//		
//	}
}
