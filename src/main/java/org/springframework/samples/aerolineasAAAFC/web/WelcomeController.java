package org.springframework.samples.aerolineasAAAFC.web;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {


	@GetMapping({"/","/welcome"})
	public String welcome(Map<String, Object> model) {	    

		model.put("title", "My super project");
		model.put("group", "G3-03");
		return "welcome";
	}

}
