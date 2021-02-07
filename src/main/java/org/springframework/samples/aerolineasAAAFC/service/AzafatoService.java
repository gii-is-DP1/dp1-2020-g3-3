package org.springframework.samples.aerolineasAAAFC.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
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
import org.springframework.samples.aerolineasAAAFC.model.Azafato;
import org.springframework.samples.aerolineasAAAFC.model.Cliente;
import org.springframework.samples.aerolineasAAAFC.model.IdiomaType;
import org.springframework.samples.aerolineasAAAFC.model.PersonalControl;
import org.springframework.samples.aerolineasAAAFC.model.Vuelo;
import org.springframework.samples.aerolineasAAAFC.repository.AzafatoRepository;
import org.springframework.samples.aerolineasAAAFC.repository.VueloRepository;
import org.springframework.samples.aerolineasAAAFC.service.exceptions.IbanDuplicadoException;
import org.springframework.samples.aerolineasAAAFC.service.exceptions.IdiomasNoSuficientesException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AzafatoService {

	public AzafatoRepository azafatoRepository;
	
	@Autowired
	private VueloService vueloService;

	@Autowired
	private UserService userService;

	@Autowired
	private AuthoritiesService authoritiesService;


	@Autowired
	public AzafatoService(AzafatoRepository azafatoRepository) {
		this.azafatoRepository = azafatoRepository;
	}

	@Transactional(readOnly = true)
	public Azafato findAzafatoById(int id) {
		return azafatoRepository.findById(id).get();
	}

	public Azafato findAzafatoByNif(String nif) {
		return azafatoRepository.findByNif(nif);
	}

	public Azafato findAzafatoByIban(String iban) {
		return azafatoRepository.findByIban(iban);
	}

	@Transactional
	public void saveAzafato(Azafato azafato) throws  DataIntegrityViolationException, IdiomasNoSuficientesException{ 
		
		if(azafato.getIdiomas().size() < 2){
			throw new IdiomasNoSuficientesException("Parece que no ha introducido 2 o más idiomas para este empleado");
		}else {
			if(azafato.getId() == null) {
				azafatoRepository.save(azafato);		
				userService.saveUser(azafato.getUser());
				authoritiesService.saveAuthorities(azafato.getUser().getUsername(), "azafato");
			}else {
				azafatoRepository.save(azafato);	
			}
		}
	}
	
	@Transactional(readOnly = true)
	public Collection<Azafato> findAzafatosNoPageable() {
		return StreamSupport.stream(azafatoRepository.findAll().spliterator(), false)
			    .collect(Collectors.toList());
	}
	
	@Transactional(readOnly = true)
	public Page<Azafato> findAzafatos(Pageable pageable){
		return azafatoRepository.findAll(pageable);
	}
	
	@Transactional
	public void eliminarAzafato(int id) throws DataAccessException {
		azafatoRepository.deleteById(id);
	}
	
	
	public Collection<Vuelo> horario(int azafatoId, int mes, int año) {
		return this.vueloService.findVuelosAzafato(azafatoId, mes, año);
	}

	
	@Transactional(readOnly = true)
	public Collection<IdiomaType> findIdiomaTypes() throws DataAccessException {
		return azafatoRepository.findIdiomaTypes();
	}
	
	//  Historia de usuario 1	
	public Collection<Vuelo> horarioSemanalConFecha(int id, DayOfWeek diaSemana, int dia, int anyo){
		Azafato azafato = azafatoRepository.findById(id).get();
		Set<Vuelo> vuelos = azafato.getVuelos();
		
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
