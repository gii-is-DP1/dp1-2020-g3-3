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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.samples.aerolineasAAAFC.model.Aeropuerto;
import org.springframework.samples.aerolineasAAAFC.model.PersonalControl;
import org.springframework.samples.aerolineasAAAFC.model.Vuelo;
import org.springframework.samples.aerolineasAAAFC.repository.PersonalControlRepository;
import org.springframework.samples.aerolineasAAAFC.service.exceptions.IbanDuplicadoException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PersonalControlService {


	private PersonalControlRepository pControlRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthoritiesService authoritiesService;
	
	@Autowired
	public PersonalControlService(PersonalControlRepository pControlRepository) {
		this.pControlRepository = pControlRepository;
	}
	
	public PersonalControl findPersonalControlByNif(String nif) {
		return pControlRepository.findByNif(nif);
	}
	
	public PersonalControl findPersonalControlByIban(String iban) {
		return pControlRepository.findByIban(iban);
	}
	
	@Transactional
	public void savePersonalControl(PersonalControl pControl) throws DataAccessException, DataIntegrityViolationException, IbanDuplicadoException{
		PersonalControl pIban = pControlRepository.findByIban(pControl.getIban());
		
		if(pIban != null && !pIban.getId().equals(pControl.getId())){
			throw new IbanDuplicadoException("");
		}else {
			pControlRepository.save(pControl);
			userService.saveUser(pControl.getUser());
			authoritiesService.saveAuthorities(pControl.getUser().getUsername(), "personalControl");
		}
	}
	
	@Transactional(readOnly=true)
	public PersonalControl findPersonalControlById(int id) throws DataAccessException{
		return pControlRepository.findById(id).get();
	}
	

	@Transactional
	public Collection<PersonalControl> findPersonalControl(){
		return StreamSupport.stream(pControlRepository.findAll().spliterator(), false)
				.collect(Collectors.toList());
	}
	

	public void deletePersonalControlById(int id) throws DataAccessException {
		pControlRepository.deleteById(id);
	}

	//Una solución para la historia 14
	public Collection<Vuelo> horario(int id){ 
		PersonalControl personal = pControlRepository.findById(id).get();
		Set<Vuelo> vuelos = personal.getVuelos();		
		
		LocalDate date = LocalDate.now();
		int mes = date.getMonthValue();
		int año = date.getYear();
		
		List<Vuelo> res = new ArrayList<Vuelo>();
		
		for(Vuelo v: vuelos) { //Recoge los vuelos de este mes y el siguiente
			if((v.getFechaSalida().getMonthValue() == mes && v.getFechaSalida().getYear() == año) || (v.getFechaSalida().getMonthValue() == (mes + 1)  && v.getFechaSalida().getYear() == año)) res.add(v);
		}
		
		return res;
	}
	
	public Collection<Vuelo> findVuelosByDate(int id, int mes, int año){ 
		PersonalControl personal = pControlRepository.findById(id).get();
		Set<Vuelo> vuelos = personal.getVuelos();
		
		List<Vuelo> res = new ArrayList<Vuelo>();
		
		for(Vuelo v: vuelos) { //Recoge los vuelos de este mes y el siguiente
			if(v.getFechaSalida().getMonthValue() == mes && v.getFechaSalida().getYear() == año) res.add(v);
		}
		
		return res;
	}
	
	/* Una solución para la historia 4 
	 * 
	 * consultar las fechas de fabricación y las horas acumuladas de los aviones de la empresa,
	 * además de su estacionamiento y disponibilidad.
	 * 
	 */
	
		public Collection<Vuelo> estadoAviones(int id){ 
			PersonalControl personal = pControlRepository.findById(id).get();
			Set<Vuelo> vuelos = personal.getVuelos();		
			
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
		
		/* Una solución para la historia 9
		 * 
		 * Conocer los aeropuertos de salida y destino asociados a los vuelos,
		 * para conocer la ruta de vuelo con antelación.
		 *
		 */
		
			public Collection<Vuelo> rutaVuelos(int id){ 
				PersonalControl personal = pControlRepository.findById(id).get();
				Set<Vuelo> vuelos = personal.getVuelos();		
				
				LocalDate date = LocalDate.now();
				int mes = date.getMonthValue();
				int año = date.getYear();
				
				Aeropuerto aeropuertoOrigen; //??
				Aeropuerto aeropuertoDestino; //??
				
				
				List<Vuelo> res = new ArrayList<Vuelo>();
				
				for(Vuelo v: vuelos) { //Recoge los vuelos de este mes y el siguiente
					if((v.getFechaSalida().getMonthValue() == mes && v.getFechaSalida().getYear() == año) || (v.getFechaSalida().getMonthValue() == (mes + 1)  && v.getFechaSalida().getYear() == año)) res.add(v);
				}
				
				return res;

			}
	

}
