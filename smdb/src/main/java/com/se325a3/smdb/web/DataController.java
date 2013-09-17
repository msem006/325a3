package com.se325a3.smdb.web;

import java.util.Collection;

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
import com.se325a3.smdb.service.SmdbService;


@Controller
@SessionAttributes
public class DataController {

	private SmdbService _smdbService;
	
	@Autowired
	public DataController(SmdbService smdbService) {
		_smdbService = smdbService;
	}
	
	@RequestMapping(value="/movie", method = RequestMethod.GET)  
	public ModelAndView movie(@ModelAttribute SearchQuery query, @RequestParam String title, @RequestParam String year) {  
	    ModelAndView modelAndView = new ModelAndView();  
	    modelAndView.setViewName("movie");

	    // Get Movie Info
	    Movie movie = 
	    		_smdbService.getMovieByTitleAndYear(title, year);

	    if (movie != null) {
	    	
		    // Get actors from movie
		    Collection<Person> personList = 
		    		_smdbService.getActorsByMovieTitleAndYear(title, year);
		    
		    for (Person person : personList) {
		    	System.out.println(person.getFirstName() + " " + person.getLastName());
		    }
		    
	    	modelAndView.addObject("movie", movie);
	    	modelAndView.addObject("personList", personList);
		    modelAndView.addObject("command", new SearchQuery());

	    }
         
	    return modelAndView;  
	}
	
	@RequestMapping(value="/actor", method = RequestMethod.GET)  
	public ModelAndView movie(@ModelAttribute SearchQuery query, @RequestParam int id) {  
	    ModelAndView modelAndView = new ModelAndView();  
	    modelAndView.setViewName("actor");

	    // Get Movie Info
	    Person actor = 
	    		_smdbService.getActorById(id);

	    if (actor != null) {
	    	
		    // Get actors from movie
		    Collection<Movie> movieList = 
		    		_smdbService.getMoviesByActorID(actor.getId());
		    
		    
	    	modelAndView.addObject("actor", actor);
	    	modelAndView.addObject("movieList", movieList);
		    modelAndView.addObject("command", new SearchQuery());

	    }
         
	    return modelAndView;  
	}
}
