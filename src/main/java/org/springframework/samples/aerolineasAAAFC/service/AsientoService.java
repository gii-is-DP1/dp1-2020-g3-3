package org.springframework.samples.aerolineasAAAFC.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.aerolineasAAAFC.model.Asiento;
import org.springframework.samples.aerolineasAAAFC.model.Clase;
import org.springframework.samples.aerolineasAAAFC.model.Vuelo;
import org.springframework.samples.aerolineasAAAFC.repository.AsientoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AsientoService {

	private AsientoRepository asientoRepository;

	@Autowired
	private VueloService vueloService;

	@Autowired
	public AsientoService(AsientoRepository asientoRepository) {
		this.asientoRepository = asientoRepository;
	}

	@Transactional
	public void saveAsiento(Asiento asiento) throws DataAccessException {
		asientoRepository.save(asiento);
	}

	@Transactional(readOnly = true)
	public Asiento findAsientoById(int id) throws DataAccessException {
		return asientoRepository.findById(id).get();
	}

	@Transactional
	public List<Asiento> findAsientos() {
		return StreamSupport.stream(asientoRepository.findAll().spliterator(), false).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public List<Asiento> findAllAsientosByVuelo(Vuelo vuelo) {
		return this.vueloService.findVueloById(vuelo.getId()).getAsientos();
	}

	@Transactional(readOnly = true)
	public List<Asiento> findAsientosSinOcupar(Vuelo vuelo) {
		return vuelo.getAsientos().stream().filter(x -> x.isLibre()).collect(Collectors.toList());
	}

	@Transactional
	public Vuelo saveManyAsientos(Vuelo vuelo) {
		Integer totalPlazas = vuelo.getAvion().getCapacidadPasajero();
		Integer plazasEconomica = vuelo.getAvion().getPlazasEconomica();
		Integer plazasEjecutiva = vuelo.getAvion().getPlazasEjecutiva();
		Integer plazasPrimera = vuelo.getAvion().getPlazasPrimera();

		List<Asiento> asientos = new ArrayList<Asiento>();

		List<String> aux = new ArrayList<String>();
		aux.add("A");
		aux.add("B");
		aux.add("C");
		aux.add("D");

		Integer filasPrimera = (int) Math.ceil(plazasPrimera / 4.0);
		Integer filasEjecutiva = (int) Math.ceil(plazasEjecutiva / 4.0);
		Integer filasEconomica = (int) Math.ceil(plazasEconomica / 4.0);

		Integer numColumnasPrimera = 4;
		Integer numColumnasEjecutiva = 4;
		Integer numColumnasEconomica = 4;

		if (totalPlazas <= 200 && totalPlazas > 100) {
			aux.add("E");
			aux.add("F");
			filasEconomica = (int) Math.ceil(plazasEconomica / 6.0);
			numColumnasEconomica = 6;
		}

		else if (totalPlazas <= 300 && totalPlazas > 200) {
			aux.add("E");
			aux.add("F");
			aux.add("G");
			filasEconomica = (int) Math.ceil(plazasEconomica / 7.0);
			numColumnasEconomica = 7;
		}

		else if (totalPlazas > 300) {
			aux.add("E");
			aux.add("F");
			aux.add("G");
			aux.add("H");
			aux.add("I");
			numColumnasEconomica = 9;
			numColumnasEjecutiva = 7;
			filasEjecutiva = (int) Math.ceil(plazasEjecutiva / 6.0);
			filasEconomica = (int) Math.ceil(plazasEconomica / 9.0);
		}
		
		int cont = 1;

		Asiento asiento = null;
		for (int i = 0; i < filasPrimera; i++) {
			for (int j = 0; j < numColumnasPrimera; j++) {
				if (plazasPrimera > 0) {
					asiento = new Asiento();
					asiento.setLibre(true);
					asiento.setVuelo(vuelo);
					asiento.setClase(Clase.PRIMERACLASE);
					asiento.setNombre(aux.get(j) + cont);
					this.saveAsiento(asiento);
					asientos.add(asiento);
					plazasPrimera = plazasPrimera - 1;
				} else
					break;
			}
			cont = cont + 1;
		}

		for (int i = 0; i < filasEjecutiva; i++) {
			for (int j = 0; j < numColumnasEjecutiva; j++) {
				if (plazasEjecutiva > 0) {
					asiento = new Asiento();
					asiento.setLibre(true);
					asiento.setVuelo(vuelo);
					asiento.setClase(Clase.EJECUTIVA);
					asiento.setNombre(aux.get(j) + cont);
					this.saveAsiento(asiento);
					asientos.add(asiento);
					plazasEjecutiva = plazasEjecutiva - 1;
				} else
					break;

			}
			cont = cont + 1;
		}

		for (int i = 0; i < filasEconomica; i++) {
			for (int j = 0; j < numColumnasEconomica; j++) {
				if (plazasEconomica > 0) {
					asiento = new Asiento();
					asiento.setLibre(true);
					asiento.setVuelo(vuelo);
					asiento.setClase(Clase.ECONOMICA);
					asiento.setNombre(aux.get(j) + cont);
					this.saveAsiento(asiento);
					asientos.add(asiento);
					plazasEconomica = plazasEconomica - 1;
				} else
					break;
			}
			cont = cont + 1;
		}

		vuelo.setAsientos(asientos);

		return vuelo;

	}
}

