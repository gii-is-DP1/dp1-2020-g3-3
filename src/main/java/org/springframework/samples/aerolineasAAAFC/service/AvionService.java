package org.springframework.samples.aerolineasAAAFC.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.aerolineasAAAFC.model.Avion;
import org.springframework.samples.aerolineasAAAFC.model.PersonalControl;
import org.springframework.samples.aerolineasAAAFC.model.Vuelo;
import org.springframework.samples.aerolineasAAAFC.repository.AvionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AvionService {
	
	private AvionRepository avionRepository;
	
	@Autowired
	public AvionService(AvionRepository avionRepository) {
		this.avionRepository = avionRepository;
	}
	
	@Transactional
	public void saveAvion(Avion avion) throws DataAccessException{
		avionRepository.save(avion);
	}
	
	@Transactional(readOnly = true)
	public Avion findAvionById(int id) throws DataAccessException{
		return avionRepository.findById(id).get();
	}
	
	@Transactional
	public Collection<Avion> findAviones(){
		return StreamSupport.stream(avionRepository.findAll().spliterator(), false)
				.collect(Collectors.toList());
	}
	
	@Transactional
	public void eliminarAvion(int id) throws DataAccessException{
		avionRepository.deleteById(id);
	}
	
	
	/* Una solución para la historia 4 
	 * 
	 * consultar las fechas de fabricación y las horas acumuladas de los aviones de la empresa,
	 * además de su estacionamiento y disponibilidad.
	 * 
	 */
	
		public Collection<Vuelo> estadoAviones(int id){ 
			Avion avion = avionRepository.findById(id).get();
			Set<Vuelo> vuelos = avion.getVuelos();		
			
			LocalDate date = LocalDate.now();
			int mes = date.getMonthValue();
			int año = date.getYear();
			
			Boolean disponibilidad; //??
			Integer horasAcumuladas; //??
			LocalDate fechaFabricacion; //??
			
			List<Vuelo> res = new ArrayList<Vuelo>();
			
			for(Vuelo v: vuelos) { //Recoge los vuelos de este mes y el siguiente
				if((v.getFechaSalida().getMonthValue() == mes && v.getFechaSalida().getYear() == año) || (v.getFechaSalida().getMonthValue() == (mes + 1)  && v.getFechaSalida().getYear() == año)) res.add(v);
			}
			
			return res;

		}
}
