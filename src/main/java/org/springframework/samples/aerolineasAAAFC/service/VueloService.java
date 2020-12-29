package org.springframework.samples.aerolineasAAAFC.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.aerolineasAAAFC.model.Cliente;
import org.springframework.samples.aerolineasAAAFC.model.Vuelo;
import org.springframework.samples.aerolineasAAAFC.repository.VueloRepository;
import org.springframework.samples.aerolineasAAAFC.service.exceptions.HorasImposiblesException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 */

@Service
public class VueloService {
	@Autowired
	private VueloRepository vueloRepository;
	


	 
	@Transactional
	public void saveVuelo(Vuelo vuelo) throws DataAccessException, HorasImposiblesException{
		LocalDateTime salida = vuelo.getFechaSalida();
		LocalDateTime llegada = vuelo.getFechaLlegada();
		if(salida.isAfter(llegada)) {
			throw new HorasImposiblesException("Horas de vuelo imposibles");
		}else {
			vueloRepository.save(vuelo); 
		}
	}
	 
	
	@Transactional(readOnly = true)
	public Vuelo findVueloById(int id) throws DataAccessException{
		return vueloRepository.findById(id).get();
	}
	 
	 
	@Autowired
	public VueloService(VueloRepository vueloRepository) {
		this.vueloRepository = vueloRepository;
	}
	 
	@Transactional(readOnly = true)
	public Collection<Vuelo> findVuelos(){
		return StreamSupport.stream(vueloRepository.findAll().spliterator(), false)
				.collect(Collectors.toList());
	}
	
	@Transactional
	public List<Cliente> findClientesPorVuelo(Vuelo vuelo){
		List<Cliente> res=new ArrayList<>();
		 vuelo.getBilletes().forEach(x->res.add(x.getCliente()));
		return res;
	}

	@Transactional(readOnly = true)
	public Collection<Vuelo> findVuelosByMes(int mes, int año){
		return vueloRepository.findVuelosByDate(mes, año);
	}

	 /*
	 @Transactional(readOnly = true)
		public Optional<Vuelo> findVueloById(int id) throws DataAccessException {
			return vueloRepository.findById(id);
		}
	 
	 @Transactional(readOnly = false)
		public Vuelo modificarFechaVuelo(Date date,int id) throws DataAccessException {
			return vueloRepository.modificarFecha(date, id);
		}
	 @Transactional(readOnly = false)
		public Vuelo modificarHoraSalidaVuelo(Date hora,int id) throws DataAccessException {
			return vueloRepository.modificarHoraSalida(hora, id);
		}
	 @Transactional(readOnly = false)
		public Vuelo modificarHoraLlegadaVuelo(Date hora,int id) throws DataAccessException {
			return vueloRepository.modificarHoraLlegada(hora, id);
		}
	 @Transactional(readOnly = false)
		public Vuelo modificarPrecioVuelo(Double precio,int id) throws DataAccessException {
			return vueloRepository.modificarPrecio(precio, id);
		}

	 */
		
	public void eliminarVuelo(int id) throws DataAccessException {
		vueloRepository.deleteById(id);
	}
	
}






