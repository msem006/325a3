package com.se325a3.smdb.web;

import java.security.Principal;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
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
	
	// Login page
	@RequestMapping(value="/login", method = RequestMethod.GET)
	public String login(ModelMap model) {
		// Lets the search form work
		model.addAttribute("searchQuery", new SearchQuery());
		return "login";
	}
	
	// Login failed page
	@RequestMapping(value="/loginfailed", method = RequestMethod.GET)
	public String loginerror(ModelMap model) {
		// Returns the error
		model.addAttribute("error", "true");
		// Lets the search form work
		model.addAttribute("searchQuery", new SearchQuery());
		return "login";
 
	}
	
	// Logout page
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logout(ModelMap model) {
		model.addAttribute("searchQuery", new SearchQuery());
		// Redirects logout page to spring security logout
		return "redirect:/j_spring_security_logout";
	}
	
	// Succesfully logout
	@RequestMapping(value="/logoutsuccess", method = RequestMethod.GET)
	public String logoutsuccess(ModelMap model) {
		// Lets the search form work
		model.addAttribute("searchQuery", new SearchQuery());
		return "redirect:/";
	}
	
	// Admin page
	@RequestMapping(value="/admin", method = RequestMethod.GET)
	public String admin(ModelMap model, Principal principal ) {
		// If logged in
		if (principal!=null) {
			String name = principal.getName();
			model.addAttribute("user", name);
			// Lets the search form work
			model.addAttribute("searchQuery", new SearchQuery());
			return "admin";
		} else {
			return "redirect:login";
		}
 
	}
	
	// Add actor page
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
		    		// Check if movie exists
		    		if (movie != null) {
			    		// Add actor to database
			    		Person person = new Person();
			    		person.setFirst_name(actor.getFirst_name());
			    		person.setLast_name(actor.getLast_name());
			    		person.setYear_born(actor.getYear_born());
			    		int genId = _smdbService.insertPerson(person);
			    		actor.setId(genId);
			    		
			    		// Add the role to the database
			    		Role role = new Role();
			    		role.setId(actor.getId());
			    		role.setTitle(actor.getTitle());
			    		role.setProduction_year(actor.getProduction_year());
			    		role.setDescription(actor.getDescription());
			    		role.setCredits("");
			    		_smdbService.insertRole(role);
			    		
			    		// Add the actor to the view
			    		modelAndView.addObject("person", actor);
	
		    		} else {
		    			// Movie does not exist error
		    			modelAndView.addObject("error", "Movie does not exist");
		    		}
    			} else {
    				// Form validation
    				modelAndView.addAllObjects(result.getModel());
    				modelAndView.addObject("error", "Please fill out every field");
    			}
	    	}
	    	// Lets the add actor form work
		    modelAndView.addObject("addActor", new Actor());
		    
			// Lets the search form work
		    modelAndView.addObject("searchQuery", new SearchQuery());
		    // Gives the username to the view
		    modelAndView.addObject("user", adminUser);
	    	return modelAndView;
	    }
	}
	
	// Add Movie page
	@RequestMapping(value={"/addMovie"})  
	public ModelAndView addMovie(@ModelAttribute("addMovie") @Valid Movie movie, BindingResult result, Principal principal, HttpServletResponse response) {
		// Check for login
		if (!((principal != null) && (principal.getName().equals(adminUser)))) {
	    	return new ModelAndView("redirect:/login");
	    } else {
		    ModelAndView modelAndView = new ModelAndView();  
		    modelAndView.setViewName("addMovie");
		    // Check if form sent
		    if (movie.getTitle()!=null) {
		    	// Check for form errors with spring validation
		    	if (!result.hasErrors()) {
			    	// Add movie to database
			    	try {
			    		_smdbService.insertMovie(movie);
				    	modelAndView.addObject("movie", movie);
			    	} catch (DataIntegrityViolationException e) {
			    		// Movie already exists
			    		modelAndView.addObject("error", "Movie already exists");
			    	} 
		    	} else {
		    		// Form validation
		    		modelAndView.addAllObjects(result.getModel());
		    		modelAndView.addObject("error", "Please fill out every field");
		    	}
		    }
		    // Lets the add movie form work
		    modelAndView.addObject("addMovie", new Movie());
		    
			// Lets the search form work
		    modelAndView.addObject("searchQuery", new SearchQuery());
		    // Gives the username to the view
		    modelAndView.addObject("user", adminUser);
	    	return modelAndView;
	    }
	}
	
	// Add role page
	@RequestMapping(value={"/addRole"})  
	public ModelAndView addRole(@ModelAttribute SearchQuery query, @RequestParam int id, @ModelAttribute("addRole") @Valid Role role, BindingResult result, Principal principal, HttpServletResponse response) {
		// Check for login
		if (!((principal != null) && (principal.getName().equals(adminUser)))) {
	    	return new ModelAndView("redirect:/login");
	    } else {
		    ModelAndView modelAndView = new ModelAndView();  
		    modelAndView.setViewName("addRole");
		    
		    // Check if actor exists
		    if (_smdbService.getActorById(id)!=null) {
		    	modelAndView.addObject("actor", _smdbService.getActorById(id));
			    // Check if form has been entered
		    	if (role.getTitle()!=null) {
			    	// Form validation
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
		    // Lets the add role form work
		    modelAndView.addObject("addRole", new Role());
		    
			// Lets the search form work
		    modelAndView.addObject("searchQuery", new SearchQuery());
		    // Gives the username to the view
		    modelAndView.addObject("user", adminUser);
	    	return modelAndView;
	    }
	}
	

}
