package org.springframework.samples.aerolineasAAAFC.utils;

import org.springframework.samples.aerolineasAAAFC.model.Equipaje;

public class equipajeUtils {
	
	public static Boolean validaPrecio(Equipaje equipaje) {
		
		String dimensiones = equipaje.getDimensiones();
		Double precio = equipaje.getPrecio();
		
		Boolean cond = (precio.equals(calculaPrecio(dimensiones, precio)));
			
		return cond;
	}
	
	public static Double calculaPrecio(String medidasValue, Double precio) {
		
		Double priceCalc = 0.;

		switch (medidasValue) {
		case "110x110x78":
			priceCalc = 30.;
			break;
		case "60x60x42":
			priceCalc = 22.;
			break;
		case "36x36x26":
			priceCalc = 15.;
			break;
		}
		
		
		return priceCalc;
	}

}
