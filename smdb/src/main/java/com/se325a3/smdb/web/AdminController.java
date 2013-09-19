package com.se325a3.smdb.web;

import java.security.Principal;
import java.sql.ResultSet;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.test.annotation.IfProfileValue;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
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
public class AdminController {

	private SmdbService _smdbService;
	private static final String adminUser = "admin";
	
	@Autowired
	public AdminController(SmdbService smdbService) {
		_smdbService = smdbService;
	}
	
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
	
	@RequestMapping(value="/login", method = RequestMethod.GET)
	public String login(ModelMap model) {
		model.addAttribute("searchQuery", new SearchQuery());
		return "login";
	}
	
	@RequestMapping(value="/loginfailed", method = RequestMethod.GET)
	public String loginerror(ModelMap model) {
 
		model.addAttribute("error", "true");
		model.addAttribute("searchQuery", new SearchQuery());
		return "login";
 
	}
 
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logout(ModelMap model) {
		model.addAttribute("searchQuery", new SearchQuery());
		return "redirect:/j_spring_security_logout";
	}
	
	@RequestMapping(value="/logoutsuccess", method = RequestMethod.GET)
	public String logoutsuccess(ModelMap model) {
		model.addAttribute("searchQuery", new SearchQuery());
		return "redirect:/";
	}
	
	@RequestMapping(value="/admin", method = RequestMethod.GET)
	public String admin(ModelMap model, Principal principal ) {
		if (principal!=null) {
			String name = principal.getName();
			model.addAttribute("user", name);
			model.addAttribute("searchQuery", new SearchQuery());
			//model.addAttribute("message", "Spring Security Custom Form example");
			return "admin";
		} else {
			return "redirect:login";
		}
 
	}
	
	
	@RequestMapping(value={"/addActor"})  
	public ModelAndView addActor(@ModelAttribute("addActor") @Valid Actor actor, BindingResult result, Principal principal, HttpServletResponse response) {
	    
		// Check for login
	    if (!((principal != null) && (principal.getName().equals(adminUser)))) {
	    	return new ModelAndView("redirect:/login");
	    } else {
		    ModelAndView modelAndView = new ModelAndView();  
		    modelAndView.setViewName("addActor");
	    	if (actor.getFirst_name() != null) {
    			if (!result.hasErrors()) {

		    		Movie movie = _smdbService.getMovieByTitleAndYear(actor.getTitle(), String.valueOf(actor.getProduction_year()));
		    		if (movie != null) {
			    		// Add actor to database
			    		Person person = new Person();
			    		//person.setId(actor.getId());
			    		person.setFirst_name(actor.getFirst_name());
			    		person.setLast_name(actor.getLast_name());
			    		person.setYear_born(actor.getYear_born());
			    		int genId = _smdbService.insertPerson(person);
			    		actor.setId(genId);
			    		
			    		Role role = new Role();
			    		role.setId(actor.getId());
			    		role.setTitle(actor.getTitle());
			    		role.setProduction_year(actor.getProduction_year());
			    		role.setDescription(actor.getDescription());
			    		role.setCredits("");
			    		
			    		_smdbService.insertRole(role);
			    		modelAndView.addObject("person", actor);
	
		    		} else {
		    			modelAndView.addObject("error", "Movie does not exist");
		    		}
    			} else {
    				modelAndView.addAllObjects(result.getModel());
    				modelAndView.addObject("error", "Please fill out every field");
    			}
	    	}
	    	
		    modelAndView.addObject("addActor", new Actor());
		    

		    modelAndView.addObject("searchQuery", new SearchQuery());
		    modelAndView.addObject("user", adminUser);
	    	return modelAndView;
	    }
	}
	
	@RequestMapping(value={"/addMovie"})  
	public ModelAndView addMovie(@ModelAttribute("addMovie") @Valid Movie movie, BindingResult result, Principal principal, HttpServletResponse response) {
		// Check for login
		if (!((principal != null) && (principal.getName().equals(adminUser)))) {
	    	return new ModelAndView("redirect:/login");
	    } else {
		    ModelAndView modelAndView = new ModelAndView();  
		    modelAndView.setViewName("addMovie");
		    if (movie.getTitle()!=null) {
		    	if (!result.hasErrors()) {
			    	// Add movie to database
			    	try {
			    		_smdbService.insertMovie(movie);
				    	//modelAndView.addObject("result", "The movie was successfully added.");
				    	modelAndView.addObject("movie", movie);
			    	} catch (DataIntegrityViolationException e) {
			    		modelAndView.addObject("error", "Movie already exists");
			    	} 
		    	} else {
		    		modelAndView.addAllObjects(result.getModel());
		    		modelAndView.addObject("error", "Please fill out every field");
		    	}
		    }
		    modelAndView.addObject("addMovie", new Movie());
		    
		    modelAndView.addObject("searchQuery", new SearchQuery());
		    modelAndView.addObject("user", adminUser);
	    	return modelAndView;
	    }
	}
	
	
	@RequestMapping(value={"/addRole"})  
	public ModelAndView addRole(@ModelAttribute SearchQuery query, @RequestParam int id, @ModelAttribute("addRole") @Valid Role role, BindingResult result, Principal principal, HttpServletResponse response) {
		// Check for login
		if (!((principal != null) && (principal.getName().equals(adminUser)))) {
	    	return new ModelAndView("redirect:/login");
	    } else {
		    ModelAndView modelAndView = new ModelAndView();  
		    modelAndView.setViewName("addRole");
		    
		    if (_smdbService.getActorById(id)!=null) {
		    	modelAndView.addObject("actor", _smdbService.getActorById(id));
				    if (role.getTitle()!=null) {
					    if (!result.hasErrors()) {
					    	Movie movie = _smdbService.getMovieByTitleAndYear(role.getTitle(), String.valueOf(role.getProduction_year()));
				    		if (movie!=null) {
						    	// Add movie to database
						    	role.setId(id);
						    	role.setCredits("");
						    	try {
						    		_smdbService.insertRole(role);
						    	}
						    	catch (DataIntegrityViolationException e) {
						    		modelAndView.addObject("error", "Actor already appears in this movie");
						    	}
						    	//modelAndView.addObject("result", "The movie was successfully added.");
						    	modelAndView.addObject("role", role);
				    		} else {
				    			modelAndView.addObject("error", "Movie does not exist");
				    		}
					    } else {
					    	modelAndView.addAllObjects(result.getModel());
					    	modelAndView.addObject("error", "Please fill out every field");
					    }
				    }
				    

		    } else {
		    	modelAndView.addObject("error", "Actor does not exist");
		    }

		    modelAndView.addObject("addRole", new Role());
		    
		    modelAndView.addObject("searchQuery", new SearchQuery());
		    modelAndView.addObject("user", adminUser);
	    	return modelAndView;
	    }
	}
	

}
