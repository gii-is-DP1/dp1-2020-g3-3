package org.springframework.samples.aerolineasAAAFC.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.aerolineasAAAFC.model.Billete;
import org.springframework.samples.aerolineasAAAFC.model.Clase;
import org.springframework.samples.aerolineasAAAFC.model.Vuelo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class BilleteServiceTests {


	@Autowired
	protected BilleteService billeteService;

	@Autowired
	protected VueloService vueloService;

	
	@Transactional
	@Test
	public void shouldInsertBilleteIntoDatabaseAndGenerateId() {

		Vuelo vuelo = this.vueloService.findVueloById(1);
		
		int nBilletes=vuelo.getBilletes().size();

		Billete billete = new Billete();

		billete.setCoste(12);
		billete.setAsiento("A44");
		billete.setFechaReserva(LocalDate.parse("2010-05-16", DateTimeFormatter.ISO_DATE));
		billete.setClase(Clase.ECONOMICA);
		
		vuelo.getBilletes().add(billete);
		assertThat(vuelo.getBilletes().size()).isEqualTo(nBilletes + 1);

		
		this.billeteService.saveBillete(billete);
		this.vueloService.saveVuelo(vuelo);

//		vuelo = this.vueloService.findVueloById(1);
//		assertThat(vuelo.getBilletes().size()).isEqualTo(nBilletes + 1);
		// checks that id has been generated
		assertThat(billete.getId()).isNotNull();
	}

}
