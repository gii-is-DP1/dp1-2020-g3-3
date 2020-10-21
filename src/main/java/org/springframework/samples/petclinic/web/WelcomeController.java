package org.springframework.samples.petclinic.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.samples.petclinic.model.Person;
import org.springframework.samples.petclinic.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class WelcomeController {
	
	
	  @GetMapping({"/","/welcome"})
	  public String welcome(Map<String, Object> model) {	    
		  List<Person> personas=new ArrayList<>();
		  Person per1=new Person();
		  per1.setFirstName("Antonio Javier");
		  per1.setLastName("Sánchez");
		  personas.add(per1);
		  
		  Person per2=new Person();
		  per2.setFirstName("Angel");
		  per2.setLastName("Torregrosa");
		  personas.add(per2);
		  
		  Person per3=new Person();
		  per3.setFirstName("Carolina");
		  per3.setLastName("Carrasco");
		  personas.add(per3);
		  
		  Person per4=new Person();
		  per4.setFirstName("Felipe");
		  per4.setLastName("Escalera");
		  personas.add(per4);
		  
		  Person per5=new Person();
		  per5.setFirstName("Antonio");
		  per5.setLastName("Viñuelas");
		  personas.add(per5);
		  
		  model.put("persons", personas);
		  model.put("title", "My super proyect");
		  model.put("group", "Teachers");
	    return "welcome";
	  }
}
