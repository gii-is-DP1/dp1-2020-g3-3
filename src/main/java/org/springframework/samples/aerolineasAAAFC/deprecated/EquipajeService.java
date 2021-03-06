package org.springframework.samples.aerolineasAAAFC.deprecated;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.aerolineasAAAFC.model.equipaje.Equipaje;
import org.springframework.samples.aerolineasAAAFC.repository.EquipajeRepository;
import org.springframework.samples.aerolineasAAAFC.service.exceptions.EquipajePriceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EquipajeService {

	private EquipajeRepository equipajeRepository;

	@Autowired
	public EquipajeService(EquipajeRepository equipajeRepository) {
		this.equipajeRepository = equipajeRepository;
	}

	@Transactional
	public void saveEquipaje(Equipaje equipaje) throws DataAccessException, EquipajePriceException {

		if (!equipajeUtils.validaPrecio(equipaje)) {
			throw new EquipajePriceException("El precio recibido no se corresponde con el estipulado en web.");
		}

		else {
			equipajeRepository.save(equipaje);
		}

	}

	@Transactional
	public void deleteEquipaje(int id) throws DataAccessException {
		equipajeRepository.deleteById(id);

	}

	@Transactional(readOnly = true)
	public Equipaje findEquipajeById(int id) throws DataAccessException{
		
		Equipaje equipaje = equipajeRepository.findById(id).orElseGet(null);
		
		return equipaje;
	}

	public Collection<Equipaje> findEquipajes() {
		return StreamSupport.stream(equipajeRepository.findAll().spliterator(), false)
			    .collect(Collectors.toList());
	}

}
