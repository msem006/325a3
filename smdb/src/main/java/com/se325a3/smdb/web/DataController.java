package com.se325a3.smdb.web;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
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
	private static final String adminUser = "admin";
	private static final String adminPass = "admin";
	private static final String cookiedata = MD5(adminUser + adminPass + adminUser);
	
	// Encrypts String
	public static String MD5(String md5) {
	   try {
	        java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
	        byte[] array = md.digest(md5.getBytes());
	        StringBuffer sb = new StringBuffer();
	        for (int i = 0; i < array.length; ++i) {
	          sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
	       }
	        return sb.toString();
	    } catch (java.security.NoSuchAlgorithmException e) {
	    }
	    return null;
	}
	
	@Autowired
	public DataController(SmdbService smdbService) {
		_smdbService = smdbService;
	}
	
	@RequestMapping(value={"", "/index", "/search"})  
	public ModelAndView index(@ModelAttribute SearchQuery query, @CookieValue(value="SMDB-COOKIE", required = false) String cookie) {
	    ModelAndView modelAndView = new ModelAndView();  
	    modelAndView.setViewName("index");
	    
	    modelAndView.addObject("searchQuery", new SearchQuery());
	    
	    // Check cookies for login
	    if ((cookie != null) && (cookie.equals(cookiedata))) {
	    	modelAndView.addObject("user", adminUser);
	    }
         
	    return modelAndView;
	}
	
	@RequestMapping(value="/movie", method = RequestMethod.GET)  
	public ModelAndView movie(@ModelAttribute SearchQuery query, @RequestParam String title, @RequestParam String year, @CookieValue(value="SMDB-COOKIE", required = false) String cookie) {  
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
		    modelAndView.addObject("searchQuery", new SearchQuery());

	    }
	    // Check cookies for login
	    if ((cookie != null) && (cookie.equals(cookiedata))) {
	    	modelAndView.addObject("user", adminUser);
	    }
         
	    return modelAndView;  
	}
	
	@RequestMapping(value="/actor", method = RequestMethod.GET)  
	public ModelAndView movie(@ModelAttribute SearchQuery query, @RequestParam String id, @CookieValue(value="SMDB-COOKIE", required = false) String cookie) {  
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
		    modelAndView.addObject("searchQuery", new SearchQuery());

	    }
	    // Check cookies for login
	    if ((cookie != null) && (cookie.equals(cookiedata))) {
	    	modelAndView.addObject("user", adminUser);
	    }
	    return modelAndView;  
	}
	
	
}
