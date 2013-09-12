package com.se325a3.smdb.web;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.se325a3.smdb.model.Movie;
import com.se325a3.smdb.model.Person;
import com.se325a3.smdb.service.SmdbServce;


@Controller
@SessionAttributes
public class SearchQueryController {

	private SmdbServce _smdbServce;
	
	@Autowired
	public SearchQueryController(SmdbServce smdbService) {
		_smdbServce = smdbService;
	}
	
	@RequestMapping(value="/search")  
	public ModelAndView search() {  
	    return new ModelAndView("search", "command", new SearchQuery());  
	}  
	  
	@RequestMapping(value="/searchResults")  
	public ModelAndView searchResults(@ModelAttribute SearchQuery query) {  
	    ModelAndView modelAndView = new ModelAndView();  
	    modelAndView.setViewName("searchResults");

	    System.out.println("Query:" + query.getQuery());
	    
	    Collection<Movie> movieList = 
	    		_smdbServce.getMoviesByTitle(query.getQuery());
	    for (Movie movie : movieList) {
	    	System.out.println(movie.getTitle() + " " + movie.getProductionYear());
	    }
	    Collection<Person> personList = 
	    		_smdbServce.getActorsByName(query.getQuery());
	    for (Person person : personList) {
	    	System.out.println(person.getFirstName() + " " + person.getLastName());
	    }
	    System.out.println(movieList.size() + personList.size());


	    modelAndView.addObject("movieList", movieList);  
	    modelAndView.addObject("personList", personList);	      
	    return modelAndView;  
	}
}
