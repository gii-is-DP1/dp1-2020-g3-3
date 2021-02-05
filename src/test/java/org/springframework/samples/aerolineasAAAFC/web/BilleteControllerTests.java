package org.springframework.samples.aerolineasAAAFC.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.aerolineasAAAFC.configuration.SecurityConfiguration;
import org.springframework.samples.aerolineasAAAFC.model.Asiento;
import org.springframework.samples.aerolineasAAAFC.model.Billete;
import org.springframework.samples.aerolineasAAAFC.model.Clase;
import org.springframework.samples.aerolineasAAAFC.model.Vuelo;
import org.springframework.samples.aerolineasAAAFC.service.AeropuertoService;
import org.springframework.samples.aerolineasAAAFC.service.AsientoService;
import org.springframework.samples.aerolineasAAAFC.service.AuthoritiesService;
import org.springframework.samples.aerolineasAAAFC.service.AvionService;
import org.springframework.samples.aerolineasAAAFC.service.BilleteService;
import org.springframework.samples.aerolineasAAAFC.service.ClienteService;
import org.springframework.samples.aerolineasAAAFC.service.UserService;
import org.springframework.samples.aerolineasAAAFC.service.VueloService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(controllers=BilleteController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
public class BilleteControllerTests {

	private static final int TEST_BILLETE_ID = 2;

	@Autowired
	private BilleteController billeteController;

	@MockBean
	private BilleteService billeteService;
	
	@MockBean
	private AsientoService asientoService;

	@MockBean
	private UserService userService;
	
	@MockBean
	private ClienteService clienteService;
	
	@MockBean
	private VueloService vueloService;
	
	@MockBean
	private AvionService avionService;
	
	@MockBean
	private AeropuertoService aeropuertoService;

	@MockBean
	private AuthoritiesService authoritiesService; 

	@Autowired
	private MockMvc mockMvc;

	private Billete billetazo;
	
	private Vuelo vuelol;
	
	private Asiento asiento;

	@BeforeEach
	void setup() {

		billetazo = new Billete();
		billetazo.setId(TEST_BILLETE_ID);
		billetazo.setAsiento(new Asiento());
		//billetazo.setCliente(cliente);
		billetazo.setCoste(123.56);
		
		asiento = new Asiento();
		asiento.setLibre(true);
		asiento.setNombre("A1");
		
		vuelol = new Vuelo();
		vuelol.setId(0);
		vuelol.setFechaSalida(LocalDateTime.of(2021, Month.DECEMBER, 10, 12, 23));
		vuelol.setFechaLlegada(LocalDateTime.of(2021, Month.DECEMBER, 11, 12, 23));
		vuelol.setCoste(100.0);
		vuelol.setAeropuertoOrigen(aeropuertoService.findAeropuertoById(1));
		vuelol.setAeropuertoDestino(aeropuertoService.findAeropuertoById(2));
		vuelol.setAvion(avionService.findAvionById(2));
		
		asiento = new Asiento();
		asiento.setLibre(true);
		asiento.setNombre("A1");
		asiento.setVuelo(vuelol);
		asiento.setVuelo(vuelol);

		List<Asiento> l = new ArrayList<Asiento>();
		l.add(asiento);
		vuelol.setAsientos(l);
		
		billetazo.setFechaReserva(LocalDate.of(2020, 12, 03));

		given(this.billeteService.findBilleteById(TEST_BILLETE_ID)).willReturn(billetazo);
		
		/*
		 * Ángel, si pasas por aqui (y vas a retocar) aviso de que faltan hacer un par de given() y tal
		 * Asiento se formatea usando "{idVuelo},{nameAsiento}" -> Te tengo que preguntar como se encripta y desencripta un string random
		 */
		
	}

	@WithMockUser(value = "spring")
	@Test
	void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/billetes/10/new")).andExpect(status().isOk())
		.andExpect(model().attributeExists("billete"))
		.andExpect(view().name("billetes/createOrUpdateBilleteForm"));
	}


	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/billetes/{billeteId}/new", TEST_BILLETE_ID)
				.param("coste", "12.54")
				.param("asiento", "B72")
				.with(csrf())
				.param("fechaReserva", "1999/11/03"))
		.andExpect(status().is2xxSuccessful());
	}



	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdateBilleteSuccess() throws Exception {
		mockMvc.perform (post("/billetes/{billeteId}/edit", TEST_BILLETE_ID)
				.with(csrf())
				.param("coste", "5")
//				.param("asiento", "F99")
				.param("fechaReserva", "1999/11/03")
				.param("clase", "ECONOMICA"))
		//.param("equipajes", equipajes)
		//.param("menus",)
		//.param("cliente",)
		//.param("vuelos",))
		.andExpect(view().name("redirect:/billetes/"+ billetazo.getId()));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdateBilleteError() throws Exception {
		mockMvc.perform (post("/billetes/{billeteId}/edit", TEST_BILLETE_ID)
				.with(csrf())
				.param("coste", "DireStraits")
//				.param("asiento", "F99")
				.param("fechaReserva", "1999/11/03")
				.param("clase", "ECONOMICA"))
		//.param("cliente",)
		//.param("vuelos",))
		.andExpect(status().isOk())
		.andExpect(model().attributeHasErrors("billete"))
		//.andExpect(model().attributeHasFieldErrors("owner", "address"))
		.andExpect(model().attributeHasFieldErrors("billete", "coste"))
		.andExpect(view().name("billetes/createOrUpdateBilleteForm"));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdateBilleteErrorMenu() throws Exception {
		mockMvc.perform (post("/billetes/{billeteId}/new", TEST_BILLETE_ID)
				.with(csrf())
				.param("coste", "-5")
				.param("asiento", "F99")
				.param("fechaReserva", "1999/11/03"))
		//.param("cliente",)
		//.param("vuelos",))
		.andExpect(status().isOk())
		.andExpect(model().attributeHasErrors("billete"))
		//.andExpect(model().attributeHasFieldErrors("owner", "address"))
		.andExpect(model().attributeHasFieldErrors("billete", "coste"))
		.andExpect(view().name("billetes/createOrUpdateBilleteForm"));
	}


}
