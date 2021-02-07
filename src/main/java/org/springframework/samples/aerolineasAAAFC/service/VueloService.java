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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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

import lombok.extern.slf4j.Slf4j;

/**
 */

@Slf4j
@Service
public class VueloService {

	private VueloRepository vueloRepository;

	@Autowired
	private BilleteService billeteService;

	@Autowired
	private AsientoService asientoService;

	@Autowired
	public VueloService(VueloRepository vueloRepository){
		this.vueloRepository = vueloRepository;
	}

	@Transactional
	public void saveVuelo(Vuelo vuelo) throws DataAccessException, HorasImposiblesException, HorasMaximasVueloException, DisponibilidadAvionException{
		LocalDateTime salida = vuelo.getFechaSalida();
		LocalDateTime llegada = vuelo.getFechaLlegada();

		long horasVuelo = salida.until(llegada, ChronoUnit.HOURS);
		log.info("horas de Vuelo {}: {}", vuelo.getAeropuertoOrigen().getCodigoIATA()+"-"+
		vuelo.getAeropuertoDestino().getCodigoIATA(), horasVuelo);
		int horasAcum = vuelo.getAvion().getHorasAcumuladas();
		log.info("horas acumuladas del avión {}: {}", vuelo.getAvion().getId(), horasAcum);
		long horasTotal = horasVuelo + horasAcum;
		log.info("horas acumuladas del avión {} después del vuelo: {}", vuelo.getAvion().getId(), horasTotal);
		LocalDate añosSinRevisar = vuelo.getAvion().getFechaRevision().plusYears(2);
		log.info("año en el que se tiene que revisar el avión {}: {}", vuelo.getAvion().getId(), añosSinRevisar);

		if(salida.isAfter(llegada)) {
			log.error("Error, horas imposibles");
			throw new HorasImposiblesException("Horas de vuelo imposibles");
		}else if(horasVuelo > 14){
			log.error("Error, más de 14 horas de vuelo");
			throw new HorasMaximasVueloException("Ningún avión puede superar el límite de 14 horas seguidas en vuelo");
		}else if(horasTotal > 600 || horasTotal > 30000 || añosSinRevisar.isEqual(LocalDate.now()) || añosSinRevisar.isBefore(LocalDate.now())) {
			log.error("Error, más 600 horas acumuladas o muchas cosas");
			vuelo.getAvion().setDisponibilidad(false);
			throw new DisponibilidadAvionException("El avión no está disponible porque debe pasar una revisión");
		}else{
			vuelo.getAvion().setHorasAcumuladas((int) horasTotal);
			log.info("Horas acumuladas  del avión {} tras comprobar errores: {}", vuelo.getAvion().getTipoAvion(), horasTotal);
			log.info("Vuelo {}, con fecha de Salida {} y Llegada {}, con origen {} y destino {}.\n"
					+ "Asociado al avión {}, a los azafatos {}, oficinistas {} y controladores {}, con un coste base de {}.", 
					vuelo.getAeropuertoOrigen().getCodigoIATA()+"-"+vuelo.getAeropuertoDestino().getCodigoIATA(), vuelo.getFechaSalida(), vuelo.getFechaLlegada(), vuelo.getAeropuertoOrigen().getCodigoIATA(), vuelo.getAeropuertoDestino().getCodigoIATA(),
					vuelo.getAvion().getTipoAvion(), vuelo.getAzafatos().size(), vuelo.getPersonalOficina().size(), vuelo.getPersonalControl().size(), vuelo.getCoste());

			this.vueloRepository.save(vuelo);

			//Si es un vuelo nuevo, genera los asientos
			//Si viene de un update, los dejara tal cual
			if(vuelo.getAsientos().isEmpty()) { 
				asientoService.saveManyAsientos(vuelo);
			}
			if(vuelo.getId()==null)
				vuelo.setVersion(1);
			
			
		}
	}


	@Transactional
	public void eliminarVuelo(int id) throws DataAccessException {
		vueloRepository.deleteById(id);
	}

	@Transactional(readOnly = true)
	public Vuelo findVueloById(int id) throws DataAccessException{
		return vueloRepository.findById(id).get();
	}

	@Transactional(readOnly = true)
	public Vuelo findVueloByFechaLLegada(LocalDateTime fechaLLegada) throws DataAccessException{
		return vueloRepository.findVueloByfechaLlegada(fechaLLegada);
	}

	@Transactional(readOnly = true)
	public List<Vuelo> findVuelos(){
		return StreamSupport.stream(vueloRepository.findAll().spliterator(), false)
				.collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public Page<Vuelo> findVuelosOrdered(Pageable pageable){
		return vueloRepository.findAllByOrderByFechaSalidaDesc(pageable);
	}

	@Transactional(readOnly = true)
	public Page<Cliente> findClientesPorVuelo(Vuelo vuelo, Pageable pageable){
		Set<Cliente> res = this.billeteService.findClientesBilletesByVuelo(vuelo.getId());
		Page<Cliente> l = new PageImpl<Cliente>(res.stream().collect(Collectors.toList()));	
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
	public int countMenusInVuelo(Map<String, Long> platosPorVuelo){
		return (int) platosPorVuelo.values().stream().collect(Collectors.summingInt(x -> x.intValue()))/3;
	}

	@Transactional(readOnly = true)
	public Page<Vuelo> findVuelosByMes(int mes, int año,  Pageable pageable){
		return vueloRepository.findVuelosByDate(mes, año, pageable);
	}

	@Transactional(readOnly = true)
	public Page<Vuelo> findVuelosOfertadosByMes(int mes, int año, Pageable pageable){

		Page<Vuelo> res = new PageImpl<Vuelo>(new ArrayList<Vuelo>());
		if(LocalDate.of(año, mes, 1).isBefore(LocalDate.now()))
		{
			res = vueloRepository.findVuelosByDate(mes, año, pageable);
		}
		return res;
	}

	//Métodos para la vista HOME
	//1 atributo de filtro
	@Transactional(readOnly = true)
	public Page<Vuelo> findVuelosConFecha(LocalDateTime fecha, Pageable pageable){
		return vueloRepository.findVuelosConFecha(fecha,pageable);
	}

	//2 atributos de filtro
	@Transactional(readOnly = true)
	public Page<Vuelo> findVuelosConFechaYPrecio(LocalDateTime fecha, double precio, Pageable pageable){
		return vueloRepository.findVuelosConFechaYPrecio(fecha, precio, pageable);
	}

	@Transactional(readOnly = true)
	public Page<Vuelo> findVuelosConFechaYOrigen(LocalDateTime fecha, String iataOrigen, Pageable pageable){
		return vueloRepository.findVuelosConFechaYOrigen(fecha, iataOrigen, pageable);
	}

	@Transactional(readOnly = true)
	public Page<Vuelo> findVuelosConFechaYDestino(LocalDateTime fecha, String iataDestino, Pageable pageable){
		return vueloRepository.findVuelosConFechaYDestino(fecha, iataDestino, pageable);
	}

	//3 atributos de filtro
	@Transactional(readOnly = true)
	public Page<Vuelo> findVuelosConFechaPrecioYOrigen(LocalDateTime fecha, double precio, String iataOrigen, Pageable pageable){
		return vueloRepository.findVuelosConFechaPrecioYOrigen(fecha, precio, iataOrigen, pageable);
	}

	@Transactional(readOnly = true)
	public Page<Vuelo> findVuelosConFechaPrecioYDestino(LocalDateTime fecha, double precio, String iataDestino, Pageable pageable){
		return vueloRepository.findVuelosConFechaPrecioYDestino(fecha, precio, iataDestino, pageable);
	}

	@Transactional(readOnly = true)
	public Page<Vuelo> findVuelosConFechaOrigenYDestino(LocalDateTime fecha, String iataOrigen, String iataDestino, Pageable pageable){
		return vueloRepository.findVuelosConFechaOrigenYDestino(fecha, iataOrigen, iataDestino, pageable);
	}

	//4 atributos de filtro
	@Transactional(readOnly = true)
	public Page<Vuelo> findVuelosConTodo(LocalDateTime fecha, double precio, String iataOrigen, String iataDestino, Pageable pageable){
		return vueloRepository.findVuelosConTodo(fecha, precio, iataOrigen, iataDestino, pageable);
	}

	public Collection<Vuelo> findVuelosAzafato(int azafatoId, int mes, int año) {
		return vueloRepository.findVuelosAzafato(azafatoId, mes, año);
	}

	public Collection<Vuelo> findVuelosControl(int pControlId, int mes, int año) {
		return vueloRepository.findVuelosControl(pControlId, mes, año);
	}


}