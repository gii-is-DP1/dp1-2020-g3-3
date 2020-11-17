package org.springframework.samples.aerolineasAAAFC.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.aerolineasAAAFC.model.Vuelo;
import org.springframework.samples.aerolineasAAAFC.repository.VueloRepository;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.repository.OwnerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 */

@Service
public class VueloService {
	 private VueloRepository vueloRepository;
/* 
	 @Autowired
		private UserService userService;
		
	@Autowired
		private AuthoritiesService authoritiesService;
*/
	 @Autowired
		public VueloService(VueloRepository vueloRepository) {
			this.vueloRepository = vueloRepository;
		}	
	 
	 @Transactional(readOnly = true)
		public Vuelo findVueloById(int id) throws DataAccessException {
			return vueloRepository.findById(id);
		}
	 
	 @Transactional(readOnly = false)
		public Vuelo modificarFechaVuelo(Date date) throws DataAccessException {
			return vueloRepository.modificarFecha(date);
		}
	 @Transactional(readOnly = false)
		public Vuelo modificarHoraSalidaVuelo(Date hora) throws DataAccessException {
			return vueloRepository.modificarHoraSalida(hora);
		}
	 @Transactional(readOnly = false)
		public Vuelo modificarHoraLlegadaVuelo(Date hora) throws DataAccessException {
			return vueloRepository.modificarHoraLlegada(hora);
		}
	 @Transactional(readOnly = false)
		public Vuelo modificarPrecioVuelo(Double precio) throws DataAccessException {
			return vueloRepository.modificarPrecio(precio);
		}
	 @Transactional(readOnly = false)
		public Vuelo modificarCodigoIATAOrigenVuelo(String codigo) throws DataAccessException {
			return vueloRepository.modificarCodigoIATAOrigen(codigo);
		}
	 @Transactional(readOnly = false)
		public Vuelo modificarCodigoIATADestinoVuelo(String codigo) throws DataAccessException {
			return vueloRepository.modificarCodigoIATADestino(codigo);
		}
	 
	 @Transactional(readOnly = false)
		public Vuelo eliminarVuelo(int id) throws DataAccessException {
			return vueloRepository.eliminarVuelo(id);
		}
	 
}






