package org.springframework.samples.aerolineasAAAFC.utils;

public class menuUtils {
//necesita un throw exception para el caso de que no se introduzcan los menús que figuran
	
	public static Boolean validaPrecio(Double precio, String primerPlato, String segundoPlato, String postre) {
		
		Boolean cond = (precio.equals(calculaPrecio(primerPlato, segundoPlato, postre)));
			
		return cond;
	}
	
	public static Double calculaPrecio(String primerPlato, String segundoPlato, String postre) {
		Double precio = 0.;

		switch (primerPlato) {

		case "SopaDeMiso":
			precio += 0.98;
			break;
		case "EnsaladaCésar":
			precio += 1.21;
			break;
		case "RevueltoDeSetasYGambas":
			precio += 2.;
			break;
		case "TortelliniDeTerneraSinGluten":
			precio += 2.;
			break;
		case "FideuaDeAlmejas":
			precio += 1.5;
			break;
		case "SalmonConCitricos":
			precio += 2.32;
			break;
		case "TomateConMozzarella":
			precio += 1.26;
			break;
		case "PanMantequilla":
			precio += 0.74;
			break;
		case "PanMantequillaSinGluten":
			precio += 0.91;
			break;
		case "CerealesMiniatura":
			precio += 0.56;
			break;
		// aqui iria la excepcion
		}

		switch (segundoPlato) {

		case "PolloTikkaMasala":
			precio += 4.78;
			break;
		case "ArrozTerneraCurry":
			precio += 3.54;
			break;
		case "ArrozPolloCurry":
			precio += 3.12;
			break;
		case "PatoPekinesa":
			precio += 5.54;
			break;
		case "GnocchiPatataSinGluten":
			precio += 3.21;
			break;
		case "TerneraConPatatas":
			precio += 4.76;
			break;
		case "MerluzaAlHorno":
			precio += 3.45;
			break;
		case "RisottoVegetal":
			precio += 2.87;
			break;
		case "TortillaConVerduras":
			precio += 2.65;
			break;
		case "BagelConJamon":
			precio += 1.97;
			break;
		case "CruasanJamonCocido":
			precio += 1.65;
			break;
		case "RolloDeCanela":
			precio += 2.01;
			break;
		case "MuffinChocolate":
			precio += 1.29;
			break;
		case "MuffinVainilla":
			precio += 1.29;
			break;
		// aqui iria la excepcion
		}

		switch (postre) {

		case "Manzana":
			precio += 0.23;
			break;
		case "Platano":
			precio += 0.29;
			break;
		case "Pera":
			precio += 0.19;
			break;
		case "Naranja":
			precio += 0.16;
			break;
		case "CremeBrulee":
			precio += 0.87;
			break;
		case "Tiramisu":
			precio += 1.21;
			break;
		case "Flan":
			precio += 0.65;
			break;
		case "PannaCotta":
			precio += 1.43;
			break;
		case "Macarons":
			precio += 1.74;
			break;
		}

		return precio;
	}

	public static boolean validarPrimerPlato(String menuField) {
		Boolean cond = false;

		switch (menuField) {

		case "SopaDeMiso":
			cond = true;
			break;
		case "EnsaladaCésar":
			cond = true;
			break;
		case "RevueltoDeSetasYGambas":
			cond = true;
			break;
		case "TortelliniDeTerneraSinGluten":
			cond = true;
			break;
		case "FideuaDeAlmejas":
			cond = true;
			break;
		case "SalmonConCitricos":
			cond = true;
			break;
		case "TomateConMozzarella":
			cond = true;
			break;
		case "PanMantequilla":
			cond = true;
			break;
		case "PanMantequillaSinGluten":
			cond = true;
			break;
		case "CerealesMiniatura":
			cond = true;
			break;
		}

		return cond;
	}

	public static boolean validarSegundoPlato(String menuField) {
		Boolean cond = false;

		switch (menuField) {
		case "PolloTikkaMasala":
			cond = true;
			break;
		case "ArrozTerneraCurry":
			cond = true;
			break;
		case "ArrozPolloCurry":
			cond = true;
			break;
		case "PatoPekinesa":
			cond = true;
			break;
		case "GnocchiPatataSinGluten":
			cond = true;
			break;
		case "TerneraConPatatas":
			cond = true;
			break;
		case "MerluzaAlHorno":
			cond = true;
			break;
		case "RisottoVegetal":
			cond = true;
			break;
		case "TortillaConVerduras":
			cond = true;
			break;
		case "BagelConJamon":
			cond = true;
			break;
		case "CruasanJamonCocido":
			cond = true;
			break;
		case "RolloDeCanela":
			cond = true;
			break;
		case "MuffinChocolate":
			cond = true;
			break;
		case "MuffinVainilla":
			cond = true;
			break;
		}

		return cond;
	}

	public static boolean validarPostres(String menuField) {
		Boolean cond = false;

		switch (menuField) {
		case "Manzana":
			cond = true;
			break;
		case "Platano":
			cond = true;
			break;
		case "Pera":
			cond = true;
			break;
		case "Naranja":
			cond = true;
			break;
		case "CremeBrulee":
			cond = true;
			break;
		case "Tiramisu":
			cond = true;
			break;
		case "Flan":
			cond = true;
			break;
		case "PannaCotta":
			cond = true;
			break;
		case "Macarons":
			cond = true;
			break;
		}
		return cond;
	}

}
