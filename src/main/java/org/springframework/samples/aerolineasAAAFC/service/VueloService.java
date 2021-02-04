package org.springframework.samples.aerolineasAAAFC.service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.aerolineasAAAFC.model.Cliente;
import org.springframework.samples.aerolineasAAAFC.model.Vuelo;
import org.springframework.samples.aerolineasAAAFC.model.menu.Menu;
import org.springframework.samples.aerolineasAAAFC.model.menu.PlatoBase;
import org.springframework.samples.aerolineasAAAFC.repository.VueloRepository;
import org.springframework.samples.aerolineasAAAFC.service.exceptions.DisponibilidadAvionException;
import org.springframework.samples.aerolineasAAAFC.service.exceptions.HorasImposiblesException;
import org.springframework.samples.aerolineasAAAFC.service.exceptions.HorasMaximasVueloException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 */

@Service
public class VueloService {

	private VueloRepository vueloRepository;
	
	@Autowired
	private BilleteService billeteService;
	

//	@Autowired
//	private AsientoService asientoService;
	 
	@Transactional
	public void saveVuelo(Vuelo vuelo) throws DataAccessException, HorasImposiblesException, HorasMaximasVueloException, DisponibilidadAvionException{
		LocalDateTime salida = vuelo.getFechaSalida();
		LocalDateTime llegada = vuelo.getFechaLlegada();
		
		long horasVuelo = salida.until(llegada, ChronoUnit.HOURS);
		int horasAcum = vuelo.getAvion().getHorasAcumuladas();
		long horasTotal = horasVuelo + horasAcum;
		LocalDate añosSinRevisar = vuelo.getAvion().getFechaRevision().plusYears(2);
		
		if(salida.isAfter(llegada)) {
			throw new HorasImposiblesException("Horas de vuelo imposibles");
		}else if(horasVuelo > 14){
			throw new HorasMaximasVueloException("Ningún avión puede superar el límite de 14 horas seguidas en vuelo");
		}else if(horasTotal > 600 || horasTotal > 30000 || añosSinRevisar.isEqual(LocalDate.now()) || añosSinRevisar.isAfter(LocalDate.now())) {
			vuelo.getAvion().setDisponibilidad(false);
			throw new DisponibilidadAvionException("El avión no está disponible porque debe pasar una revisión");
		}else{
			vuelo.getAvion().setHorasAcumuladas(horasAcum);
			vueloRepository.save(vuelo);
			
//			asientoService.saveManyAsiento(vuelo.getAvion().getCapacidadPasajero(),vuelo);

			
		}
	}
	 
	
	@Transactional(readOnly = true)
	public Vuelo findVueloById(int id) throws DataAccessException{
		return vueloRepository.findById(id).get();
	}
	
	@Transactional(readOnly = true)
	public Vuelo findVueloByFechaLLegada(LocalDateTime fechaLLegada) throws DataAccessException{
		return vueloRepository.findVueloByfechaLlegada(fechaLLegada);
	}
	 
	 
	@Autowired
	public VueloService(VueloRepository vueloRepository) {
		this.vueloRepository = vueloRepository;
	}
	 
	@Transactional(readOnly = true)
	public List<Vuelo> findVuelos(){
		return StreamSupport.stream(vueloRepository.findAll().spliterator(), false)
				.collect(Collectors.toList());
	}
	
	@Transactional(readOnly = true)
	public Collection<Vuelo> findVuelosOrdered(){
		return vueloRepository.findAllByOrderByFechaSalidaDesc();
	}
	
	@Transactional(readOnly = true)
	public List<Cliente> findClientesPorVuelo(Vuelo vuelo){
		Set<Cliente> res = this.billeteService.findClientesBilletesByVuelo(vuelo.getId());
		List<Cliente> l = res.stream().collect(Collectors.toList());
		return l;
	}
	
	@Transactional(readOnly = true)
	public Map<String, Long> findMenusPorVuelo(Vuelo vuelo){
		//Encuentra los platos + count de los mismos por vuelo
		List<PlatoBase> lPlatoBase = new ArrayList<PlatoBase>();
		
		List<Menu> aux = this.billeteService.findMenusByVuelo(vuelo.getId()).stream().collect(Collectors.toList());
		
		
		Map<String, Long> mapaMenuCountTotal = new HashMap<String,Long>();
		
		for(Menu m : aux) {		
			lPlatoBase.add(m.getPlato1().getPlatoBase());
			lPlatoBase.add(m.getPlato2().getPlatoBase());
			lPlatoBase.add(m.getPlato3().getPlatoBase());
		}
		
		mapaMenuCountTotal = lPlatoBase.stream()
				.collect(Collectors.groupingBy(PlatoBase::getName,Collectors.counting()));
		
		return mapaMenuCountTotal;
	}

	@Transactional(readOnly = true)
	public Collection<Vuelo> findVuelosByMes(int mes, int año){
		return vueloRepository.findVuelosByDate(mes, año);
	}
	
	@Transactional(readOnly = true)
	public Collection<Vuelo> findVuelosOfertadosByMes(int mes, int año){
		
		Collection<Vuelo> res=new ArrayList<>();
		if(LocalDate.of(año, mes, 1).isBefore(LocalDate.now()))
		{
			res=vueloRepository.findVuelosByDate(mes, año);
		}
		return res;
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






