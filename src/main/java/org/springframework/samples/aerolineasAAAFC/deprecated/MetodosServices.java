package org.springframework.samples.aerolineasAAAFC.deprecated;

public class MetodosServices {
	//HU-9: Esto como tal no sirve, ya se saca esto a partir del controladorDetails	
//	public Collection<Vuelo> rutaVuelos(int id){ 
//		PersonalControl personal = pControlRepository.findById(id).get();
//		Set<Vuelo> vuelos = personal.getVuelos();		
//				
//		LocalDate date = LocalDate.now();
//		int mes = date.getMonthValue();
//		int año = date.getYear();
//				
//		Aeropuerto aeropuertoOrigen; //??
//		Aeropuerto aeropuertoDestino; //??
//						
//		List<Vuelo> res = new ArrayList<Vuelo>();
//				
//		for(Vuelo v: vuelos) { //Recoge los vuelos de este mes y el siguiente
//			if((v.getFechaSalida().getMonthValue() == mes && v.getFechaSalida().getYear() == año) || (v.getFechaSalida().getMonthValue() == (mes + 1)  && v.getFechaSalida().getYear() == año)) res.add(v);
//		}
//		return res;
//	}
}
