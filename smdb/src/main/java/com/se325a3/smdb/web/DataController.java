package com.se325a3.smdb.web;

import java.security.Principal;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.se325a3.smdb.model.Movie;
import com.se325a3.smdb.model.Person;
import com.se325a3.smdb.model.Role;
import com.se325a3.smdb.service.SmdbService;


@Controller
@SessionAttributes
public class DataController {

	private SmdbService _smdbService;
	private static final String adminUser = "admin";
	
	@Autowired
	public DataController(SmdbService smdbService) {
		_smdbService = smdbService;
	}
	
	// Simple pages
	@RequestMapping(value={"", "/index", "/search"})  
	public ModelAndView index(Principal principal) {
	    ModelAndView modelAndView = new ModelAndView();  
	    modelAndView.setViewName("index");
	    
	    modelAndView.addObject("searchQuery", new SearchQuery());
	    
	    // Check for login
	    if ((principal != null) && (principal.getName().equals(adminUser))) {
	    	modelAndView.addObject("user", adminUser);
	    }
         
	    return modelAndView;
	}
	
	// Movie page
	@RequestMapping(value="/movie", method = RequestMethod.GET)  
	public ModelAndView movie(@RequestParam String title, @RequestParam String year, Principal principal) {  
	    ModelAndView modelAndView = new ModelAndView();  
	    modelAndView.setViewName("movie");

	    // Get Movie Info
	    Movie movie = 
	    		_smdbService.getMovieByTitleAndYear(title, year);
	    
	    // If movie exists
	    if (movie != null) {
	    	
		    // Get actors for the movie
	    	Set<Role> roles = movie.getRoles();
		    
	    	modelAndView.addObject("movie", movie);
	    	modelAndView.addObject("roleList", roles);

	    }
	    // Check for login
	    if ((principal != null) && (principal.getName().equals(adminUser))) {
	    	modelAndView.addObject("user", adminUser);
	    }
	    modelAndView.addObject("searchQuery", new SearchQuery());         
	    return modelAndView;  
	}
	
	// Actor pages
	@RequestMapping(value="/actor", method = RequestMethod.GET)  
	public ModelAndView movie(@ModelAttribute SearchQuery query, @RequestParam int id, Principal principal) {  
	    ModelAndView modelAndView = new ModelAndView();  
	    modelAndView.setViewName("actor");

	    // Get Actor Info
	    Person actor = 
	    		_smdbService.getActorById(id);
	    // If actor exists
	    if (actor != null) {
	    	// Get actor roles
	    	Set<Role> roles = actor.getRoles();
	    	modelAndView.addObject("actor", actor);
	    	modelAndView.addObject("roleList", roles);

	    }
	    // Check for login
	    if ((principal != null) && (principal.getName().equals(adminUser))) {
	    	modelAndView.addObject("user", adminUser);
	    }
	    modelAndView.addObject("searchQuery", new SearchQuery());
	    return modelAndView;  
	}
	
	
}
