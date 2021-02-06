package org.springframework.samples.aerolineasAAAFC.deprecated;

public class MetodosControladores {

	
	// estadoAviones (H4)

	//	@RequestMapping(value = { "/controladores/{pControlId}/estadoAviones" }, method = RequestMethod.GET)
	//	public String showVuelosList(Map<String, Object> model, @PathVariable("pControlId") int pControlId,
	//			@RequestParam(name = "fecha", defaultValue = "") String fecha) {
	//
	//		if (fecha.isEmpty()) {
	//			model.put("vuelos", this.avionService.estadoAviones(pControlId));
	//		} else {
	//			fecha += "-01";
	//			LocalDate date = LocalDate.parse(fecha, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	//			int mes = date.getMonthValue();
	//			int año = date.getYear();
	//			Boolean disponibilidad; // ??
	//			Integer horasAcumuladas; // ??
	//			LocalDate fechaFabricacion; // ??
	//			model.put("vuelos", this.avionService.findAviones());
	//		}
	//		return "controladores/estadoAviones";
	//	}

//@GetMapping(value = "/controladores/rutaAviones")
//public String showAvionesListPersonal(Map<String, Object> model) {
//
//Aeropuerto aeropuerto = null; //Inicializa aeropuerto
//List<Avion> aviones = this.avionService.findAviones(); //Almacena todos los aviones de la compañía
//Collection<Vuelo> todosVuelos = this.vueloService.findVuelos(); //Almacena todos los vuelos de la compañía
//List<Aeropuerto> aeropuertosDestino = new ArrayList<Aeropuerto>(); //Inicializa aeropuertosDestino
//
////Recorre los vuelos y recoge su avión, si dicho avión es de la compañía establece como fechaFinal la fecha de llegada
//for (Vuelo v : todosVuelos) {
//LocalDateTime fechaFinal = LocalDateTime.of(1900, Month.JULY, 29, 19, 30, 40);
//Avion avion = v.getAvion();
//
//for (int i = 0; i <= aviones.size()-1; i++) {
//	if (avion.equals(aviones.get(i))) {
//
//		LocalDateTime fechaLlegada = v.getFechaLlegada();
//		if (fechaLlegada.isAfter(fechaFinal)) {
//			fechaFinal = fechaLlegada;
//		}
//	}
//}
//
////aeropuerto = this.vueloService.findVueloByFechaLLegada(fechaFinal).; //Coge el aeropuerto
//aeropuertosDestino.add(aeropuerto); //lo añade a la lista
//}
//
//model.put("aviones", aviones);
//model.put("aeropuertosDestino", aeropuertosDestino);
//
//return "controladores/rutaAviones";
//}
	
}
