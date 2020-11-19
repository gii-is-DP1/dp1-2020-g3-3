package org.springframework.samples.aerolineasAAAFC.utils;

public class menuUtils {
//necesita un throw exception para el caso de que no se introduzcan los menús que figuran
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
			//aqui iria la excepcion
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
			//aqui iria la excepcion
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
			//aqui iria la excepcion
		}

		return precio;
	}

}
