package org.springframework.samples.aerolineasAAAFC.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.samples.aerolineasAAAFC.model.Aeropuerto;
import org.springframework.samples.aerolineasAAAFC.model.PersonalControl;
import org.springframework.samples.aerolineasAAAFC.model.PersonalOficina;
import org.springframework.samples.aerolineasAAAFC.model.Vuelo;
import org.springframework.samples.aerolineasAAAFC.repository.PersonalControlRepository;
import org.springframework.samples.aerolineasAAAFC.repository.VueloRepository;
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
	private VueloRepository vueloRepository;
	
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
	
	//Prueba
	@Transactional
	public void updatePersonalControl(PersonalControl personalControl) throws DataIntegrityViolationException{

		pControlRepository.save(personalControl);
		userService.saveUser(personalControl.getUser());
		authoritiesService.saveAuthorities(personalControl.getUser().getUsername(), "personalControl");

	}
	
	@Transactional(readOnly=true)
	public PersonalControl findPersonalControlById(int id) throws DataAccessException{
		return pControlRepository.findById(id).get();
	}
	

	@Transactional
	public List<PersonalControl> findPersonalControl(){
		return StreamSupport.stream(pControlRepository.findAll().spliterator(), false)
				.collect(Collectors.toList());
	}
	

	public void deletePersonalControlById(int id) throws DataAccessException {
		pControlRepository.deleteById(id);
	}
	
	
	public Collection<Vuelo> horario(int pControlId, int mes, int año) {
		
		return this.vueloRepository.findVuelosControl(pControlId, mes, año);
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


	/*
	 *  Historia de usuario 1
	 */
	public List<String> sacaFecha(){
		List<String> res = new ArrayList<String>();
		
		LocalDate fecha = LocalDate.now();
			
		DayOfWeek diaSemana = fecha.getDayOfWeek();
		String diaS = diaSemana.toString();
		res.add(diaS);										//0
		String diaM = String.valueOf(fecha.getDayOfMonth());
		res.add(diaM);										//1
		String mes = String.valueOf(fecha.getMonthValue());
		res.add(mes);										//2
		String anyo = String.valueOf(fecha.getYear());
		res.add(anyo);										//3
				
		return res;
	}
			
	public Collection<Vuelo> horarioSemanalSinFecha(int id){
		PersonalControl control = pControlRepository.findById(id).get();
		Set<Vuelo> vuelos = control.getVuelos();
		
		LocalDate fecha = LocalDate.now();
		DayOfWeek diaSemana = fecha.getDayOfWeek();
		int dia = fecha.getDayOfYear();
		int anyo = fecha.getYear();
			
		List<Vuelo> res = new ArrayList<Vuelo>();
		switch(diaSemana) {
		case MONDAY: 
			for(Vuelo v: vuelos) {
				if(((v.getFechaSalida().getYear() == anyo) && ((v.getFechaSalida().getDayOfYear() < dia+7) && (v.getFechaSalida().getDayOfYear() > dia-1))) 					//año actual
						|| ((v.getFechaSalida().getYear() == anyo+1) && ((v.getFechaSalida().getDayOfYear() < 7) && (v.getFechaSalida().getDayOfYear() > 0)))) 					//año siguiente
					res.add(v);
			};
			break;
		case TUESDAY:
			for(Vuelo v: vuelos) {
				if(((v.getFechaSalida().getYear() == anyo) && ((v.getFechaSalida().getDayOfYear() < dia+6) && (v.getFechaSalida().getDayOfYear() > dia-2)))						//año actual
						|| ((v.getFechaSalida().getYear() == anyo+1) && ((v.getFechaSalida().getDayOfYear() < 6) && (v.getFechaSalida().getDayOfYear() > 0)))					//año siguiente
						|| ((v.getFechaSalida().getYear() == anyo-1) && ((v.getFechaSalida().getDayOfYear() < 366) && (v.getFechaSalida().getDayOfYear() > 364)))) 				//año pasado
					res.add(v);
			};
			break;
		case WEDNESDAY:
			for(Vuelo v: vuelos) {
				if(((v.getFechaSalida().getYear() == anyo) && ((v.getFechaSalida().getDayOfYear() < dia+5) && (v.getFechaSalida().getDayOfYear() > dia-3)))
						|| ((v.getFechaSalida().getYear() == anyo+1) && ((v.getFechaSalida().getDayOfYear() < 5) && (v.getFechaSalida().getDayOfYear() > 0)))
						|| ((v.getFechaSalida().getYear() == anyo-1) && ((v.getFechaSalida().getDayOfYear() < 366) && (v.getFechaSalida().getDayOfYear() > 363))))
					res.add(v);
			};
			break;
		case THURSDAY: 
			for(Vuelo v: vuelos) {
				if(((v.getFechaSalida().getYear() == anyo) && ((v.getFechaSalida().getDayOfYear() < dia+4) && (v.getFechaSalida().getDayOfYear() > dia-4)))
						|| ((v.getFechaSalida().getYear() == anyo+1) && ((v.getFechaSalida().getDayOfYear() < 4) && (v.getFechaSalida().getDayOfYear() > 0)))
						|| ((v.getFechaSalida().getYear() == anyo-1) && ((v.getFechaSalida().getDayOfYear() < 366) && (v.getFechaSalida().getDayOfYear() > 362))))
					res.add(v);
			};
			break;
		case FRIDAY: 
			for(Vuelo v: vuelos) {
				if(((v.getFechaSalida().getYear() == anyo) && ((v.getFechaSalida().getDayOfYear() < dia+3) && (v.getFechaSalida().getDayOfYear() > dia-5)))
						|| ((v.getFechaSalida().getYear() == anyo+1) && ((v.getFechaSalida().getDayOfYear() < 3) && (v.getFechaSalida().getDayOfYear() > 0)))
						|| ((v.getFechaSalida().getYear() == anyo-1) && ((v.getFechaSalida().getDayOfYear() < 366) && (v.getFechaSalida().getDayOfYear() > 361))))
					res.add(v);
			};
			break;
		case SATURDAY: 
			for(Vuelo v: vuelos) {
				if(((v.getFechaSalida().getYear() == anyo) && ((v.getFechaSalida().getDayOfYear() < dia+2) && (v.getFechaSalida().getDayOfYear() > dia-6)))
						|| ((v.getFechaSalida().getYear() == anyo+1) && ((v.getFechaSalida().getDayOfYear() < 2) && (v.getFechaSalida().getDayOfYear() > 0)))
						|| ((v.getFechaSalida().getYear() == anyo-1) && ((v.getFechaSalida().getDayOfYear() < 366) && (v.getFechaSalida().getDayOfYear() > 360))))
					res.add(v);
			};
			break;
		case SUNDAY: 
			for(Vuelo v: vuelos) {
				if(((v.getFechaSalida().getYear() == anyo) && ((v.getFechaSalida().getDayOfYear() < dia+1) && (v.getFechaSalida().getDayOfYear() > dia-7)))
						|| ((v.getFechaSalida().getYear() == anyo-1) && ((v.getFechaSalida().getDayOfYear() < 366) && (v.getFechaSalida().getDayOfYear() > 359))))
					res.add(v);
			};
			break;
		}
		res.sort(Comparator.comparing(Vuelo::getFechaSalida).reversed());
		Set<Vuelo> resSet = new HashSet<Vuelo>(res);
		return resSet;
	}
			
	public Collection<Vuelo> horarioSemanalConFecha(int id, DayOfWeek diaSemana, int dia, int anyo){
		PersonalControl control = pControlRepository.findById(id).get();
		Set<Vuelo> vuelos = control.getVuelos();
		
		List<Vuelo> res = new ArrayList<Vuelo>();
		switch(diaSemana) {
		case MONDAY: 
			for(Vuelo v: vuelos) {
				if(((v.getFechaSalida().getYear() == anyo) && ((v.getFechaSalida().getDayOfYear() < dia+7) && (v.getFechaSalida().getDayOfYear() > dia-1))) 					//año actual
						|| ((v.getFechaSalida().getYear() == anyo+1) && ((v.getFechaSalida().getDayOfYear() < 7) && (v.getFechaSalida().getDayOfYear() > 0)))) 					//año siguiente
					res.add(v);
			};
			break;
		case TUESDAY:
			for(Vuelo v: vuelos) {
				if(((v.getFechaSalida().getYear() == anyo) && ((v.getFechaSalida().getDayOfYear() < dia+6) && (v.getFechaSalida().getDayOfYear() > dia-2)))						//año actual
						|| ((v.getFechaSalida().getYear() == anyo+1) && ((v.getFechaSalida().getDayOfYear() < 6) && (v.getFechaSalida().getDayOfYear() > 0)))					//año siguiente
						|| ((v.getFechaSalida().getYear() == anyo-1) && ((v.getFechaSalida().getDayOfYear() < 366) && (v.getFechaSalida().getDayOfYear() > 364)))) 				//año pasado
					res.add(v);
			};
			break;
		case WEDNESDAY:
			for(Vuelo v: vuelos) {
				if(((v.getFechaSalida().getYear() == anyo) && ((v.getFechaSalida().getDayOfYear() < dia+5) && (v.getFechaSalida().getDayOfYear() > dia-3)))
						|| ((v.getFechaSalida().getYear() == anyo+1) && ((v.getFechaSalida().getDayOfYear() < 5) && (v.getFechaSalida().getDayOfYear() > 0)))
						|| ((v.getFechaSalida().getYear() == anyo-1) && ((v.getFechaSalida().getDayOfYear() < 366) && (v.getFechaSalida().getDayOfYear() > 363))))
					res.add(v);
			};
			break;
		case THURSDAY: 
			for(Vuelo v: vuelos) {
				if(((v.getFechaSalida().getYear() == anyo) && ((v.getFechaSalida().getDayOfYear() < dia+4) && (v.getFechaSalida().getDayOfYear() > dia-4)))
						|| ((v.getFechaSalida().getYear() == anyo+1) && ((v.getFechaSalida().getDayOfYear() < 4) && (v.getFechaSalida().getDayOfYear() > 0)))
						|| ((v.getFechaSalida().getYear() == anyo-1) && ((v.getFechaSalida().getDayOfYear() < 366) && (v.getFechaSalida().getDayOfYear() > 362))))
					res.add(v);
			};
			break;
		case FRIDAY: 
			for(Vuelo v: vuelos) {
				if(((v.getFechaSalida().getYear() == anyo) && ((v.getFechaSalida().getDayOfYear() < dia+3) && (v.getFechaSalida().getDayOfYear() > dia-5)))
						|| ((v.getFechaSalida().getYear() == anyo+1) && ((v.getFechaSalida().getDayOfYear() < 3) && (v.getFechaSalida().getDayOfYear() > 0)))
						|| ((v.getFechaSalida().getYear() == anyo-1) && ((v.getFechaSalida().getDayOfYear() < 366) && (v.getFechaSalida().getDayOfYear() > 361))))
					res.add(v);
			};
			break;
		case SATURDAY: 
			for(Vuelo v: vuelos) {
				if(((v.getFechaSalida().getYear() == anyo) && ((v.getFechaSalida().getDayOfYear() < dia+2) && (v.getFechaSalida().getDayOfYear() > dia-6)))
						|| ((v.getFechaSalida().getYear() == anyo+1) && ((v.getFechaSalida().getDayOfYear() < 2) && (v.getFechaSalida().getDayOfYear() > 0)))
						|| ((v.getFechaSalida().getYear() == anyo-1) && ((v.getFechaSalida().getDayOfYear() < 366) && (v.getFechaSalida().getDayOfYear() > 360))))
					res.add(v);
			};
			break;
		case SUNDAY: 
			for(Vuelo v: vuelos) {
				if(((v.getFechaSalida().getYear() == anyo) && ((v.getFechaSalida().getDayOfYear() < dia+1) && (v.getFechaSalida().getDayOfYear() > dia-7)))
						|| ((v.getFechaSalida().getYear() == anyo-1) && ((v.getFechaSalida().getDayOfYear() < 366) && (v.getFechaSalida().getDayOfYear() > 359))))
					res.add(v);
			};
			break;
		}
		
		res.sort(Comparator.comparing(Vuelo::getFechaSalida).reversed());
		Set<Vuelo> resSet = new HashSet<Vuelo>(res);
		return resSet;
	}
	
}
