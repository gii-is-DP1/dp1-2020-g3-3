package org.springframework.samples.aerolineasAAAFC.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
	public void shouldInsertPetIntoDatabaseAndGenerateId() {

		Vuelo vuelo = this.vueloService.findVueloById(1);

		int nBilletes=vuelo.getBilletes().size();

		Billete billete = new Billete();

		billete.setClase(Clase.ECONOMICA);
		billete.setAsiento("A44");
		billete.setCoste(12);
		try {
			billete.setFechaReserva(new SimpleDateFormat("yyyy/MM/dd").parse("2010/05/16"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		vuelo.getBilletes().add(billete);


		assertThat(vuelo.getBilletes().size()).isEqualTo(nBilletes + 1);


		this.billeteService.saveBillete(billete);

		this.vueloService.saveVuelo(vuelo);

		vuelo = this.vueloService.findVueloById(1);
		assertThat(vuelo.getBilletes().size()).isEqualTo(nBilletes + 1);
		// checks that id has been generated
		assertThat(billete.getId()).isNotNull();
	}

}
