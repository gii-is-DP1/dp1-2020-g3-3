package org.springframework.samples.aerolineasAAAFC.service;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.samples.aerolineasAAAFC.model.PersonalControl;
import org.springframework.samples.aerolineasAAAFC.model.Vuelo;
import org.springframework.samples.aerolineasAAAFC.repository.PersonalControlRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PersonalControlService {


	private PersonalControlRepository pControlRepository;
	
	@Autowired
	private VueloService vueloService;
	
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
	
	@Transactional(readOnly = true)
	public PersonalControl findPersonalControlByIban(String iban) {
		return pControlRepository.findByIban(iban);
	}
	
	@Transactional
	public void savePersonalControl(PersonalControl pControl) throws DataIntegrityViolationException{
		if(pControl.getId() == null) {
			pControl.setVersion(1);
			pControlRepository.save(pControl);
			log.info("Controlador {} creado.", pControl.getId());
			userService.saveUser(pControl.getUser());
			authoritiesService.saveAuthorities(pControl.getUser().getUsername(), "personalControl");
		}else {
			pControl.setVersion(pControl.getVersion()+1);
			pControlRepository.save(pControl);
			userService.saveUser(pControl.getUser());
			log.info("Controlador {} actualizado.", pControl.getId());
		}
	
	}
	
	@Transactional(readOnly = true)
	public PersonalControl findPersonalControlById(int id) throws DataAccessException{
		return pControlRepository.findById(id).get();
	}
	

	@Transactional(readOnly = true)
	public List<PersonalControl> findPersonalControlNoPageable(){
		return StreamSupport.stream(pControlRepository.findAll().spliterator(), false)
				.collect(Collectors.toList());
	}
	
	@Transactional(readOnly = true)
	public Page<PersonalControl> findPersonalControl(Pageable pageable){
		return pControlRepository.findAll(pageable);
	}

	@Transactional
	public void deletePersonalControlById(int id) throws DataAccessException {
		log.info("Controlador {} eliminado.", id);
		pControlRepository.deleteById(id);
	}
	
	
	public Collection<Vuelo> horario(int pControlId, int mes, int año) {
		return this.vueloService.findVuelosControl(pControlId, mes, año);
	}


	//  Historia de usuario 1	
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
