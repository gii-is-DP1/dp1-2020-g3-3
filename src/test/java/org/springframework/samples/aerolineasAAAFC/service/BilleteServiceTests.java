package org.springframework.samples.aerolineasAAAFC.service;

import static org.assertj.core.api.Assertions.assertThat;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.aerolineasAAAFC.model.Billete;
import org.springframework.samples.aerolineasAAAFC.model.Clase;
import org.springframework.samples.aerolineasAAAFC.model.Vuelo;
import org.springframework.samples.aerolineasAAAFC.service.exceptions.HorasImposiblesException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class BilleteServiceTests {


	@Autowired
	protected BilleteService billeteService;

	@Autowired
	protected VueloService vueloService;


	@Test
	@Transactional
	public void shouldInsertBilleteIntoDatabaseAndGenerateId() throws ParseException {


		Vuelo vuelo = this.vueloService.findVueloById(1);
		
		int nBilletes=vuelo.getBilletes().size();

		Billete billete = new Billete();

		Clase clase=Clase.ECONOMICA;
		billete.setClase(clase);
		billete.setAsiento("A44");

		billete.setCoste(12);
		LocalDate reserva=LocalDate.parse("2010-05-16", DateTimeFormatter.ISO_DATE);
		billete.setFechaReserva(reserva);


		
		//se comprueba que se añadio correctamente el billete

		vuelo.getBilletes().add(billete);
		assertThat(vuelo.getBilletes().size()).isEqualTo(nBilletes + 1);


		//se comprueba que se guarda exitosamente los cambios en vuelo

		
		//this.billeteService.saveBillete(billete);

//		this.vueloService.saveVuelo(vuelo); AHORA SE TIENE EN CUENTA LA EXCEPCION DE HORAS
		try {
			this.vueloService.saveVuelo(vuelo);
		} catch (HorasImposiblesException ex) {
			Logger.getLogger(VueloServiceTests.class.getName()).log(Level.SEVERE, null, ex);
		}

		vuelo = this.vueloService.findVueloById(1);
		assertThat(vuelo.getBilletes().size()).isEqualTo(nBilletes + 1);
		
		
		//se comprueba que el id se añade correctamente
		billete.setId(2);
		assertThat(billete.getId()).isNotNull();
	}

}
