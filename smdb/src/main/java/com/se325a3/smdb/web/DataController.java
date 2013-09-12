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
import com.se325a3.smdb.service.SmdbServce;


@Controller
@SessionAttributes
public class DataController {

	private SmdbServce _smdbServce;
	
	@Autowired
	public DataController(SmdbServce smdbService) {
		_smdbServce = smdbService;
	}
	
	@RequestMapping(value="/movie", method = RequestMethod.GET)  
	public ModelAndView movie(@ModelAttribute SearchQuery query, @RequestParam String title, @RequestParam String year) {  
	    ModelAndView modelAndView = new ModelAndView();  
	    modelAndView.setViewName("movie");

	    // Get Movie Info
	    Collection<Movie> movieList = 
	    		_smdbServce.getMoviesByTitleAndYear(title, year);

	    if (!movieList.isEmpty()) {
	    	Movie movie = movieList.iterator().next();
	    	
		    // Get actors from movie
		    Collection<Person> personList = 
		    		_smdbServce.getActorsByMovieTitleAndYear(title, year);
		    
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
	public ModelAndView movie(@ModelAttribute SearchQuery query, @RequestParam String id) {  
	    ModelAndView modelAndView = new ModelAndView();  
	    modelAndView.setViewName("actor");

	    // Get Movie Info
	    Collection<Person> actorList = 
	    		_smdbServce.getActorsById(id);

	    if (!actorList.isEmpty()) {
	    	Person actor = actorList.iterator().next();
	    	
		    // Get actors from movie
		    Collection<Movie> movieList = 
		    		_smdbServce.getMoviesByActorID(actor.getId());
		    
		    
	    	modelAndView.addObject("actor", actor);
	    	modelAndView.addObject("movieList", movieList);
		    modelAndView.addObject("command", new SearchQuery());

	    }
         
	    return modelAndView;  
	}
}
