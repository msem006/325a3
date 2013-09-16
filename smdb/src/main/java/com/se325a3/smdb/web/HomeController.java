package com.se325a3.smdb.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.se325a3.smdb.service.SmdbService;

/**
 * Simple Controller class to process incoming HTTP GET requests of the form:
 * http://localhost:808/HelloSpringMVC/hello". The Controller simply generates a
 * greeting and names "output" as the logical view name to present the greeting.
 * 
 */
@Controller
public class HomeController {

	private SmdbService _smdbService;

	@Autowired
	public HomeController(SmdbService smdbService) {
		_smdbService = smdbService;
	}

	/*
	 * @RequestMapping(value = "/index", method = RequestMethod.GET) public
	 * String printHome(ModelMap model) { return "index"; }
	 */

	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	public String printWelcome(ModelMap model) {
		return "output";
	}
}
