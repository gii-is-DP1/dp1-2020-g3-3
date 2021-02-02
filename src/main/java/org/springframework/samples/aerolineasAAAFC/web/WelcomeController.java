package org.springframework.samples.aerolineasAAAFC.web;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WelcomeController {


	@GetMapping({"/","/welcome"})
	public String welcome(Map<String, Object> model) {	    

		model.put("title", "AEROLINEAS AAAFC");
		model.put("group", "G3-03");
		return "welcome";
	}
	
    //Spring Security see this :
    @GetMapping(value = "/login")
    public ModelAndView login(
        @RequestParam(value = "error", required = false) String error) {

        ModelAndView model = new ModelAndView();
        if (error != null) {
            model.addObject("error", "Invalid username and password!");
        }

        model.setViewName("login");

        return model;

    }

}
