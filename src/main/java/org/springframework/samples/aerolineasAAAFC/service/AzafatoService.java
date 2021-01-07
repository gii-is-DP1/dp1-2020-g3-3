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
import org.springframework.samples.aerolineasAAAFC.model.Azafato;
import org.springframework.samples.aerolineasAAAFC.model.IdiomaType;
import org.springframework.samples.aerolineasAAAFC.model.Vuelo;
import org.springframework.samples.aerolineasAAAFC.repository.AzafatoRepository;
import org.springframework.samples.aerolineasAAAFC.service.exceptions.IbanDuplicadoException;
import org.springframework.samples.aerolineasAAAFC.service.exceptions.IdiomasNoSuficientesException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AzafatoService {

	public AzafatoRepository azafatoRepository;

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
	public void saveAzafato(Azafato azafato) throws DataAccessException, IbanDuplicadoException, 
	DataIntegrityViolationException, IdiomasNoSuficientesException{ 
		
		Azafato aIban = azafatoRepository.findByIban(azafato.getIban());
		
		if(aIban != null && ! aIban.getId().equals(azafato.getId())){
			throw new IbanDuplicadoException("");
		}else if(azafato.getIdiomas().size() < 2){
			throw new IdiomasNoSuficientesException("Parece que no ha introducido 2 o más idiomas para este empleado");
		}
		
		else {
			azafatoRepository.save(azafato);		
			userService.saveUser(azafato.getUser());
			authoritiesService.saveAuthorities(azafato.getUser().getUsername(), "azafato");
		}
	}
	
	public Collection<Azafato> findAzafatos() {
		return StreamSupport.stream(azafatoRepository.findAll().spliterator(), false)
			    .collect(Collectors.toList());
	}
	
	public void eliminarAzafato(int id) throws DataAccessException {
		azafatoRepository.deleteById(id);
	}
	
	public Collection<Vuelo> findVuelosByDate(int id, int mes, int año){ 
		Azafato azafato = azafatoRepository.findById(id).get();
		Set<Vuelo> vuelos = azafato.getVuelos();
		
		List<Vuelo> res = new ArrayList<Vuelo>();
		
		for(Vuelo v: vuelos) { //Recoge los vuelos de este mes y el siguiente
			if(v.getFechaSalida().getMonthValue() == mes && v.getFechaSalida().getYear() == año) res.add(v);
		}
		
		return res;
	}
	
	public Collection<Vuelo> horario(int id){ 
		Azafato azafato = azafatoRepository.findById(id).get();
		Set<Vuelo> vuelos = azafato.getVuelos();
		
		LocalDate date = LocalDate.now();
		int mes = date.getMonthValue();
		int año = date.getYear();
		
		List<Vuelo> res = new ArrayList<Vuelo>();
		
		for(Vuelo v: vuelos) { //Recoge los vuelos de este mes y el siguiente
			if((v.getFechaSalida().getMonthValue() == mes && v.getFechaSalida().getYear() == año) || (v.getFechaSalida().getMonthValue() == (mes + 1)  && v.getFechaSalida().getYear() == año)) res.add(v);
		}
		
		return res;
	}
	
	@Transactional(readOnly = true)
	public Collection<IdiomaType> findIdiomaTypes() throws DataAccessException {
		return azafatoRepository.findIdiomaTypes();
	}

}
