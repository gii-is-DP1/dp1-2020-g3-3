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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.samples.aerolineasAAAFC.configuration.SecurityConfiguration;
import org.springframework.samples.aerolineasAAAFC.model.Aeropuerto;
import org.springframework.samples.aerolineasAAAFC.model.Avion;
import org.springframework.samples.aerolineasAAAFC.model.Azafato;
import org.springframework.samples.aerolineasAAAFC.model.PersonalControl;
import org.springframework.samples.aerolineasAAAFC.model.PersonalOficina;
import org.springframework.samples.aerolineasAAAFC.model.Rol;
import org.springframework.samples.aerolineasAAAFC.model.Vuelo;
import org.springframework.samples.aerolineasAAAFC.service.AeropuertoService;
import org.springframework.samples.aerolineasAAAFC.service.AsientoService;
import org.springframework.samples.aerolineasAAAFC.service.AvionService;
import org.springframework.samples.aerolineasAAAFC.service.AzafatoService;
import org.springframework.samples.aerolineasAAAFC.service.BilleteService;
import org.springframework.samples.aerolineasAAAFC.service.PersonalControlService;
import org.springframework.samples.aerolineasAAAFC.service.PersonalOficinaService;
import org.springframework.samples.aerolineasAAAFC.service.UserService;
import org.springframework.samples.aerolineasAAAFC.service.VueloService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers=VueloController.class,
includeFilters = {@ComponentScan.Filter(value = AzafatoFormatter.class, type = FilterType.ASSIGNABLE_TYPE),
@ComponentScan.Filter(value = AvionFormatter.class, type = FilterType.ASSIGNABLE_TYPE),
@ComponentScan.Filter(value = AeropuertoFormatter.class, type = FilterType.ASSIGNABLE_TYPE),
@ComponentScan.Filter(value = PersonalOficinaFormatter.class, type = FilterType.ASSIGNABLE_TYPE),
@ComponentScan.Filter(value = PersonalControlFormatter.class, type = FilterType.ASSIGNABLE_TYPE)},
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
public class VueloControllerTests {
	
	private static final int TEST_VUELO_ID = 1;
	
	@Autowired
	private VueloController vueloController;
	
	@MockBean
	private VueloService vueloService;
	
	@MockBean
	private UserService userService;
	
	@MockBean
	private AvionService avionService;
	
	@MockBean
	private AeropuertoService aeropuertoService;

	@MockBean
	private BilleteService billeteService;
	
	@MockBean
	private AsientoService asientoService;
	
	@MockBean
	private PersonalOficinaService personalOficinaService;
	
	@MockBean
	private PersonalControlService personalControlService;
	
	@MockBean
	private AzafatoService azafatoService;
	
	@Autowired
	private MockMvc mockMvc;
	
	private Vuelo vuelol;
	private PersonalOficina pOficina1;
	
	private Azafato azafato1;
	private Azafato azafato2;
	private Azafato azafato3;
	private Azafato azafato4;
	private Azafato azafato5;
	private Azafato azafato6;
	
	private PersonalControl pControl1;
	private PersonalControl pControl2;
	private PersonalControl pControl3;
	private PersonalControl pControl4;
	
	private Aeropuerto aeropuertoOrigen;
	private Aeropuerto aeropuertoDestino;
	
	private Avion avion;
	
	@BeforeEach
	void setup() {
		
		//NUEVO VUELO
		vuelol = new Vuelo();
		vuelol.setId(TEST_VUELO_ID);
		vuelol.setFechaSalida(LocalDateTime.of(2020, 12, 10, 12, 23));
		vuelol.setFechaLlegada(LocalDateTime.of(2020, 12, 10, 19, 23));
		vuelol.setCoste(30000.0);
		vuelol.setVersion(1);
		
		//PERSONAL OFICINA
		Set<PersonalOficina> pOficina = new HashSet<PersonalOficina>();
		pOficina1 = new PersonalOficina();
		pOficina1.setId(1);
		pOficina.add(pOficina1);
		vuelol.setPersonalOficina(pOficina);
		given(this.personalOficinaService.findPersonalOficinaById(1)).willReturn(pOficina1);
		
		//AZAFATOS
		Set<Azafato> azafatos = new HashSet<Azafato>();
		azafato1 = new Azafato();
		azafato1.setId(1);
		azafatos.add(azafato1);
		given(this.azafatoService.findAzafatoById(1)).willReturn(azafato1);
		
		azafato2 = new Azafato();
		azafato2.setId(2);
		azafatos.add(azafato2);
		given(this.azafatoService.findAzafatoById(2)).willReturn(azafato2);
		
		azafato3 = new Azafato();
		azafato3.setId(3);
		azafatos.add(azafato3);
		given(this.azafatoService.findAzafatoById(3)).willReturn(azafato3);
		
		azafato4 = new Azafato();
		azafato4.setId(4);
		azafatos.add(azafato4);
		given(this.azafatoService.findAzafatoById(4)).willReturn(azafato4);
		
		azafato5 = new Azafato();
		azafato5.setId(5);
		azafatos.add(azafato5);
		given(this.azafatoService.findAzafatoById(5)).willReturn(azafato5);
		
		azafato6 = new Azafato();
		azafato6.setId(6);
		azafatos.add(azafato6);
		given(this.azafatoService.findAzafatoById(6)).willReturn(azafato6);
		
		vuelol.setAzafatos(azafatos);
		
		//CONTROLADORES
		List<PersonalControl> pControl = new ArrayList<PersonalControl>();
		pControl1 = new PersonalControl();
		pControl1.setRol(Rol.PILOTO);
		pControl1.setId(1);
		pControl.add(pControl1);
		given(this.personalControlService.findPersonalControlById(1)).willReturn(pControl1);
		
		pControl2 = new PersonalControl();
		pControl2.setRol(Rol.PILOTO);
		pControl2.setId(2);
		pControl.add(pControl2);
		given(this.personalControlService.findPersonalControlById(2)).willReturn(pControl2);
		
		pControl3 = new PersonalControl();
		pControl3.setRol(Rol.COPILOTO);
		pControl3.setId(3);
		pControl.add(pControl3);
		given(this.personalControlService.findPersonalControlById(3)).willReturn(pControl3);
		
		pControl4 = new PersonalControl();
		pControl4.setRol(Rol.INGENIERO_DE_VUELO);
		pControl4.setId(4);
		pControl.add(pControl4);
		given(this.personalControlService.findPersonalControlById(4)).willReturn(pControl4);
		
		vuelol.setPersonalControl(pControl.stream().collect(Collectors.toSet()));
		
		//AEROPUERTOS
		aeropuertoOrigen = new Aeropuerto();
		aeropuertoOrigen.setCodigoIATA("MAD");
		aeropuertoOrigen.setId(1);
		vuelol.setAeropuertoOrigen(aeropuertoOrigen);
		given(this.aeropuertoService.findAeropuertoById(1)).willReturn(aeropuertoOrigen);
		
		aeropuertoDestino = new Aeropuerto();
		aeropuertoDestino.setCodigoIATA("SVQ");
		aeropuertoDestino.setId(2);
		vuelol.setAeropuertoDestino(aeropuertoDestino);
		given(this.aeropuertoService.findAeropuertoById(2)).willReturn(aeropuertoDestino);
		
		//AVIÓN
		avion = new Avion();
		avion.setId(1);
		avion.setFechaFabricacion(LocalDate.of(2021, 01, 01));
		avion.setFechaRevision(LocalDate.of(2021, 01, 01));
		avion.setCapacidadPasajero(120);
		avion.setHorasAcumuladas(0);
		avion.setPlazasEconomica(60);
		avion.setPlazasEjecutiva(40);
		avion.setPlazasPrimera(20);
		given(this.avionService.findAvionById(1)).willReturn(avion);

		vuelol.setAvion(avion);
		
		given(this.vueloService.findVueloById(TEST_VUELO_ID)).willReturn(vuelol);
		
		
		given(this.azafatoService.findAzafatosNoPageable()).willReturn(azafatos);
		List<Vuelo> lista = new ArrayList<Vuelo>();
		lista.add(vuelol);
		Page<Vuelo> pagina = new PageImpl<Vuelo>(lista);
		Pageable paging = PageRequest.of(0, 20);
		
		Page<PersonalControl> pagina1 = new PageImpl<PersonalControl>(pControl);
		Pageable paging1 = PageRequest.of(0, 20);
		
		given(this.personalControlService.findPersonalControl(paging1)).willReturn(pagina1);
		
		LocalDateTime fechita = LocalDateTime.of(2020, 12, 10, 0, 0);
		int mes = fechita.getMonthValue();
		int anyo = fechita.getYear();
		given(this.vueloService.findVuelosByMes(mes,anyo,paging)).willReturn(pagina);
		given(this.vueloService.findVuelosConFecha(fechita, paging)).willReturn(pagina);
		
		LocalDate fechaii = LocalDate.now();
		int mesii = fechaii.getMonthValue();
		int anyoii = fechaii.getYear();
		given(this.vueloService.findVuelosByMes(mesii,anyoii,paging)).willReturn(pagina);
	}
	
	//TESTS DE INSERCION
	
	@WithMockUser(value = "spring")
	@Test
	void testInitCreationForm() throws Exception{
		mockMvc.perform(get("/vuelos/new"))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("vuelo"))
		.andExpect(model().attributeExists("aeropuertos"))
		.andExpect(model().attributeExists("aviones"))
		.andExpect(model().attributeExists("personalOficina"))
		.andExpect(model().attributeExists("personalControl"))
		.andExpect(model().attributeExists("azafatos"))
		.andExpect(view().name("vuelos/createOrUpdateVueloForm"));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testInitUpdateForm() throws Exception{
		mockMvc.perform(get("/vuelos/"+TEST_VUELO_ID+"/edit"))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("vuelo"))
		.andExpect(model().attributeExists("aeropuertos"))
		.andExpect(model().attributeExists("aviones"))
		.andExpect(model().attributeExists("personalOficina"))
		.andExpect(model().attributeExists("personalControl"))
		.andExpect(model().attributeExists("azafatos"))
		.andExpect(view().name("vuelos/createOrUpdateVueloForm"));
	}
	

	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormSuccess() throws Exception{
		mockMvc.perform(post("/vuelos/new")
				.with(csrf())
				.param("fechaSalida", LocalDateTime.of(2022,01,24,10,00).toString())
				.param("fechaLlegada", LocalDateTime.of(2022,01,24,15,00).toString())
				.param("coste", "350.")
				.param("personalOficina", "1")
				.param("azafatos", "1","2","3")
				.param("personalControl", "1","2")
				.param("aeropuertoOrigen", "1")
				.param("aeropuertoDestino", "2")
				.param("avion", "1"))
		.andExpect(view().name("redirect:/vuelos"));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormHasErrors() throws Exception{
		mockMvc.perform(post("/vuelos/new")
				.with(csrf())
				.param("fechaSalida", LocalDateTime.of(2021, 1,1,10,50).toString())
				.param("fechaLlegada", LocalDateTime.of(2021,1,1,12,50).toString())
				.param("coste", "350.")
				.param("personalOficina", "1")
				.param("azafatos", "1","2","3")
				.param("personalControl", "1","2")
				.param("aeropuertoOrigen", "1")
				.param("aeropuertoDestino", "1")
				.param("avion", "1"))
		.andExpect(status().is2xxSuccessful())
		.andExpect(view().name("vuelos/createOrUpdateVueloForm"));
	}
	
	
	//TEST DE ACTUALIZACION

	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdateVueloSuccess() throws Exception{
		mockMvc.perform(post("/vuelos/{vueloId}/edit", TEST_VUELO_ID)
				.with(csrf())
				.param("version", "1")
				.param("fechaSalida", LocalDateTime.of(2021, 1,1,10,50).toString())
				.param("fechaLlegada", LocalDateTime.of(2021, 1,1,11,50).toString())
				.param("coste", String.valueOf(100.0))
				.param("personalOficina", "1")
				.param("azafatos", "1","2","3")
				.param("personalControl", "1","2")
				.param("aeropuertoOrigen", "2")
				.param("aeropuertoDestino", "1")
				.param("avion", "1"))
		.andExpect(view().name("redirect:/vuelos/{vueloId}"));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdateVueloError() throws Exception{
		mockMvc.perform(post("/vuelos/{vueloId}/edit", TEST_VUELO_ID)
				.with(csrf())
				.param("version", String.valueOf(vuelol.getVersion()+1))
				.param("fechaSalida", LocalDateTime.of(2022,01,24,10,00).toString())
				.param("fechaLlegada", LocalDateTime.of(2021,01,24,15,00).toString())
				.param("coste", "350.")
				.param("personalOficina", "1")
				.param("azafatos", "1","2","3")
				.param("personalControl", "1","2")
				.param("aeropuertoOrigen", "1")
				.param("aeropuertoDestino", "2")
				.param("avion", "1"))
		.andExpect(status().is2xxSuccessful())
		.andExpect(view().name("vuelos/createOrUpdateVueloForm"));
	}
	
	
	@WithMockUser(value = "spring")
	@Test
	void testshowVuelosListInicial() throws Exception{
		mockMvc.perform(get("/vuelos"))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("vuelos"))
		.andExpect(view().name("vuelos/vuelosList"));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testshowVuelosList() throws Exception{
		mockMvc.perform(get("/vuelos?fecha=2020-12-10"))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("vuelos"))
		.andExpect(view().name("vuelos/vuelosList"));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testShowMenusByVuelo() throws Exception {
		mockMvc.perform(get("/vuelos/{vueloId}/showMenusByVuelo", TEST_VUELO_ID))
		.andExpect(status().isOk()).andExpect(model().attributeExists("vuelo"))
		.andExpect(view().name("vuelos/menusByVuelo"));
	}

}
