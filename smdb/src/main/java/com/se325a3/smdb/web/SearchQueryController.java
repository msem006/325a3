package com.se325a3.smdb.web;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
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
	public SearchQueryController(SmdbService smdbService) {
		_smdbService = smdbService;
	}

	  
	@RequestMapping(value="/searchResults")  
	public ModelAndView searchResults(@ModelAttribute SearchQuery query, @CookieValue(value="SMDB-COOKIE", required = false) String cookie) {  
	    ModelAndView modelAndView = new ModelAndView();  
	    modelAndView.setViewName("searchResults");

	    System.out.println("Query:" + query.getQuery());
	    
	    Collection<Movie> movieList = 
	    		_smdbService.getMoviesByTitle(query.getQuery());
	    for (Movie movie : movieList) {
	    	System.out.println(movie.getTitle() + " " + movie.getProduction_year());
	    }
	    
	    String[] splited = query.getQuery().split("\\s+");
	    Collection<Person> personList;
	    // Anything after the 2nd word is ignored
	    if (splited.length > 1) {
	    	personList = 
	    			_smdbService.getActorsByFirstNameOrLastName(splited[0], splited[1]);
	    } else {
	    	personList = 
	    			_smdbService.getActorsByFirstName(query.getQuery());
	    	personList.addAll(_smdbService.getActorsByLastName(query.getQuery()));
	    	
	    }
	    

	    for (Person person : personList) {
	    	System.out.println(person.getFirst_name() + " " + person.getLast_name());
	    }
	    System.out.println(movieList.size() + personList.size());

	    modelAndView.addObject("query", query.getQuery());
	    modelAndView.addObject("movieList", movieList);  
	    modelAndView.addObject("personList", personList);
	    modelAndView.addObject("searchQuery", new SearchQuery());
	    // Check cookies for login
	    if ((cookie != null) && (cookie.equals(cookiedata))) {
	    	modelAndView.addObject("user", adminUser);
	    }
         
	    return modelAndView;  
	}
}
