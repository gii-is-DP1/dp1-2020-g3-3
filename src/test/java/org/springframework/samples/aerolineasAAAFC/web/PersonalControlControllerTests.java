package org.springframework.samples.aerolineasAAAFC.web;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
import org.springframework.samples.aerolineasAAAFC.model.Cliente;
import org.springframework.samples.aerolineasAAAFC.model.PersonalControl;
import org.springframework.samples.aerolineasAAAFC.model.PersonalOficina;
import org.springframework.samples.aerolineasAAAFC.model.Rol;
import org.springframework.samples.aerolineasAAAFC.model.User;
import org.springframework.samples.aerolineasAAAFC.service.AuthoritiesService;
import org.springframework.samples.aerolineasAAAFC.service.ClienteService;
import org.springframework.samples.aerolineasAAAFC.service.PersonalControlService;
import org.springframework.samples.aerolineasAAAFC.service.UserService;
import org.springframework.samples.aerolineasAAAFC.service.VueloService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers=PersonalControlController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
public class PersonalControlControllerTests {



	private static final int TEST_PERSONALCONTROL_ID = 2;

	@Autowired
	private PersonalControlController personalControlController;

	@MockBean
	private PersonalControlService personalControlService;

	@MockBean
	private VueloService vueloService;

	@MockBean
	private UserService userService;

	@MockBean
	private AuthoritiesService authoritiesService; 

	@Autowired
	private MockMvc mockMvc;

	private PersonalControl Juan;

	private User juanUser;

	@BeforeEach
	void setup() {

		Juan = new PersonalControl();
		Juan.setId(TEST_PERSONALCONTROL_ID);
		Juan.setNombre("Juan");
		Juan.setApellidos("Fernandez Romero");
		Juan.setNif("08493865B");
		Juan.setIban("ES 0159480518801639865810");
		Juan.setRol(Rol.COPILOTO);
		Juan.setSalario(2000.);
		Juan.setVersion(1);

		juanUser = new User();
		juanUser.setUsername("08493865B");
		juanUser.setPassword("juFerRo01");
		Juan.setUser(juanUser);

		given(this.personalControlService.findPersonalControlById(TEST_PERSONALCONTROL_ID)).willReturn(Juan);
	
		List<PersonalControl> lista = new ArrayList<PersonalControl>();
		lista.add(Juan);
		Page pagina = new PageImpl<PersonalControl>(lista);
		Pageable paging = PageRequest.of(0, 20);
		
		given(this.personalControlService.findPersonalControl(paging)).willReturn(pagina);
	}


	//TESTS CREACIÓN
	@WithMockUser(value = "spring")
	@Test
	void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/controladores/new"))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("personalControl"))
		.andExpect(view().name("controladores/createOrUpdatePersonalControlForm"));
	}


	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/controladores/new")
				.param("nombre", "Daryl")
				.param("apellidos", "Brown")
				.with(csrf())
				.param("nif", "19083474Y")
				.param("iban", "ES 4700815264369772656796")
				.param("rol", Rol.INGENIERO_DE_VUELO.toString())
				.param("salario", "2500.")
				.param("user.username", "19083474Y")
				.param("user.password", "asdasd23"))
		.andExpect(status().is3xxRedirection());
	}


	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/controladores/new")
				.with(csrf())
				.param("nombre", "Alessandro")
				.param("apellidos", "Ferrara")
				.param("nif", "297635960K")
				.param("iban", "ES 4433300418403322567002")
				.param("rol",  Rol.COPILOTO.toString())
				.param("salario", "nosalario")
				.param("user.username", "297635960K")
				.param("user.password", "AAAAAAA"))
		.andExpect(status().isOk())
		.andExpect(model().attributeHasErrors("personalControl"))
		.andExpect(model().attributeHasFieldErrors("personalControl", "nif"))
		.andExpect(model().attributeHasFieldErrors("personalControl", "salario"))
		.andExpect(view().name("controladores/createOrUpdatePersonalControlForm"));
	}


	//TESTS ACTUALIZACIÓN
	@WithMockUser(value = "spring")
	@Test
	void testInitUpdateForm() throws Exception{
		mockMvc.perform(get("/controladores/{pControlId}/edit", TEST_PERSONALCONTROL_ID))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("personalControl"))
		.andExpect(model().attribute("personalControl", hasProperty("nombre", is("Juan"))))
		.andExpect(model().attribute("personalControl", hasProperty("apellidos", is("Fernandez Romero"))))
		.andExpect(model().attribute("personalControl", hasProperty("nif", is("08493865B"))))
		.andExpect(model().attribute("personalControl", hasProperty("iban", is("ES 0159480518801639865810"))))
		.andExpect(model().attribute("personalControl", hasProperty("rol", is(Rol.COPILOTO))))
		.andExpect(model().attribute("personalControl", hasProperty("salario", is(2000.))))
		.andExpect(view().name("controladores/createOrUpdatePersonalControlForm"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdatePersonalControlFormSuccess() throws Exception {
		mockMvc.perform(post("/controladores/{pControlId}/edit", TEST_PERSONALCONTROL_ID)
				.with(csrf())
				.param("nombre", "Juan")
				.param("apellidos", "Fernandez Romero")
				.param("nif", "64112428C")
				.param("iban", "ES 7200813221711748512485")
				.param("rol", Rol.PILOTO.toString())
				.param("salario", "4500.")
				.param("user.username", "64112428C")
				.param("user.password", "CCCCCCC"))
		.andExpect(view().name("redirect:/controladores/{pControlId}"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdatePersonalControlFormError() throws Exception {
		mockMvc.perform(post("/controladores/{pControlId}/edit", TEST_PERSONALCONTROL_ID)
				.with(csrf())
				.param("nombre", "Camile")
				.param("apellidos", "Bernard")
				.param("nif", "58963425")
				.param("iban", "yyyyyyy")
				.param("rol",  Rol.PILOTO.toString())
				.param("salario", "nosalario")
				.param("user.username", "08493865B")
				.param("user.password", "juFerRo01"))
		.andExpect(status().isOk())
		.andExpect(model().attributeHasErrors("personalControl"))
		.andExpect(model().attributeHasFieldErrors("personalControl", "nif"))
		.andExpect(model().attributeHasFieldErrors("personalControl", "iban"))
		.andExpect(model().attributeHasFieldErrors("personalControl", "salario"))
		.andExpect(view().name("controladores/createOrUpdatePersonalControlForm"));
	}

	
	//TEST ELIMINACIÓN
	@WithMockUser(value = "spring")
	@Test
	void testDeletePersonalControl() throws Exception{
		mockMvc.perform(get("/controladores/{pControlId}/delete", TEST_PERSONALCONTROL_ID))
		.andExpect(status().isFound())
		.andExpect(model().attributeDoesNotExist("personalControl"))
		.andExpect(view().name("redirect:/controladoresList"));
	}

	
	//TESTS BÚSQUEDA
	@WithMockUser(value = "spring")
	@Test
	void testProcessFindFormByNif() throws Exception {
		given(this.personalControlService.findPersonalControlByNif(Juan.getNif())).willReturn(Juan);
		Logger.getLogger(PersonalControlControllerTests.class.getName()).log(Level.INFO, "Juan: " + Juan);

		mockMvc.perform(get("/controladoresfind")
				.param("nif", "08493865B"))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/controladores/" + TEST_PERSONALCONTROL_ID));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessFindFormNoPersonalControlFound() throws Exception {
		mockMvc.perform(get("/controladoresfind")
				.param("nif", ""))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/controladores"));
	}

	
	//OTROS
	@WithMockUser(value = "spring")
	@Test
	void testShowPersonalControlList() throws Exception{
		mockMvc.perform(get("/controladores"))
		.andExpect(model().attributeExists("personalControl"))
		.andExpect(view().name("controladores/personalControlList"));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testShowPersonalControl() throws Exception {
		mockMvc.perform(get("/controladores/{pControlId}", TEST_PERSONALCONTROL_ID))
		.andExpect(model().attribute("personalControl", hasProperty("nombre", is("Juan"))))
		.andExpect(model().attribute("personalControl", hasProperty("apellidos", is("Fernandez Romero"))))
		.andExpect(model().attribute("personalControl", hasProperty("nif", is("08493865B"))))
		.andExpect(model().attribute("personalControl", hasProperty("iban", is("ES 0159480518801639865810"))))
		.andExpect(model().attribute("personalControl", hasProperty("rol", is(Rol.COPILOTO))))
		.andExpect(model().attribute("personalControl", hasProperty("salario", is(2000.))))
		.andExpect(view().name("controladores/controladorDetails"));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testshowHorario() throws Exception {
		mockMvc.perform(get("/controladores/{pControlId}/horario", 1)
				.param("fecha", LocalDate.of(2021, 01, 01).toString()))
		.andExpect(model().attributeExists("vuelos"))
		.andExpect(model().attribute("dias", 31))
		.andExpect(model().attribute("mes", Month.JANUARY))
		.andExpect(model().attribute("año", 2021))
		.andExpect(model().attributeExists("diasV"))
		.andExpect(view().name("controladores/horario"));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testshowHorarioSemanal() throws Exception {
		mockMvc.perform(get("/controladores/{pControlId}/semana", 1)
				.param("fecha", LocalDate.of(2021, 01, 01).toString()))
		.andExpect(model().attributeExists("vuelos"))
		.andExpect(model().attribute("diaS", DayOfWeek.FRIDAY.toString()))
		.andExpect(model().attribute("diaM", 1))
		.andExpect(model().attribute("mes", 1))
		.andExpect(model().attribute("anyo", 2021))
		.andExpect(view().name("controladores/horarioSemanal"));
	}

}
