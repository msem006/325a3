package com.se325a3.smdb;

//import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Simple Controller class to process incoming HTTP GET requests of the form:
 * http://localhost:808/HelloSpringMVC/hello". The Controller simply generates
 * a greeting and names "output" as the logical view name to present the
 * greeting.
 *
 */
@Controller
public class HelloSpringController {

	/*// Initialise logger.
	private static final Logger logger = Logger
			.getLogger(HelloSpringController.class);*/

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String printWelcome(ModelMap model) {
		//logger.debug("The controller's in action!");
		model.addAttribute("message",
				"Hello! This is smdb MVC Web Controller.");
		return "output";
	}
}
