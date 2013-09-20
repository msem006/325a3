package com.se325a3.smdb.web;

import java.security.Principal;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.se325a3.smdb.model.Movie;
import com.se325a3.smdb.model.Person;
import com.se325a3.smdb.service.SmdbService;


@Controller
@SessionAttributes
public class SearchQueryController {

	private SmdbService _smdbService;
	private static final String adminUser = "admin";
	
	@Autowired
	public SearchQueryController(SmdbService smdbService) {
		_smdbService = smdbService;
	}

	// Search results page
	@RequestMapping(value="/searchResults")  
	public ModelAndView searchResults(@ModelAttribute SearchQuery query, Principal principal) {  
	    ModelAndView modelAndView = new ModelAndView();  
	    modelAndView.setViewName("searchResults");
	    // Get movies
	    Collection<Movie> movieList = 
	    		_smdbService.getMoviesByTitle(query.getQuery());
	    
	    // Check if search query has  space in it
	    String[] splited = query.getQuery().split("\\s+");
	    Collection<Person> personList;
	    
	    // If the word contains a space
	    if (splited.length > 1) {
		    // Anything after the 2nd word is ignored
	    	// Get actors with getActorsByFirstNameOrLastName
	    	personList = 
	    			_smdbService.getActorsByFirstNameOrLastName(splited[0], splited[1]);
	    } else {
	    	// Get actors by getActorsByFirstName and getActorsByLastName
	    	personList = 
	    			_smdbService.getActorsByFirstName(query.getQuery());
	    	personList.addAll(_smdbService.getActorsByLastName(query.getQuery()));
	    	
	    }
	    
	    // Returns objects to the view
	    modelAndView.addObject("query", query.getQuery());
	    modelAndView.addObject("movieList", movieList);  
	    modelAndView.addObject("personList", personList);
	    modelAndView.addObject("searchQuery", new SearchQuery());
	    // Check for login
	    if ((principal != null) && (principal.getName().equals(adminUser))) {
	    	modelAndView.addObject("user", adminUser);
	    }
         
	    return modelAndView;  
	}
}
