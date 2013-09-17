package com.se325a3.smdb.web;

import java.util.Collection;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

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
public class AdminController {

	private SmdbService _smdbService;
	private static final String adminUser = "admin";
	private static final String adminPass = "admin";
	private static final String cookiedata = MD5(adminUser + adminPass + adminUser);
	
	
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
	
	@RequestMapping(value={"/login"})  
	public ModelAndView login(@ModelAttribute Login login, @CookieValue(value="SMDB-COOKIE", required = false) String cookie, HttpServletResponse response) {
	    ModelAndView modelAndView = new ModelAndView();  
	    modelAndView.setViewName("login");
	    if (cookie != null) {
		    if (cookie.equals(cookiedata)) {
		    	// Logged in
		    	return new ModelAndView("redirect:/admin");
		    }
	    }
	    if (login.getUsername() != null) {
		    if (login.getUsername().equals(adminUser)) {
		    	if (login.getPassword().equals(adminPass)) {
		    		// Correct login info
		    		response.addCookie(new Cookie("SMDB-COOKIE", cookiedata));
		    		return new ModelAndView("redirect:/admin");
		    	} else {
		    		// Incorrect password
		    		modelAndView.addObject("error", "Incorrect password");
		    	}
		    } else {
		    	// Incorrect username
		    	modelAndView.addObject("error", "Incorrect username: " + login.getUsername() + " does not exist.");
		    	
		    }
	    }
	    
	    modelAndView.addObject("login", new Login());
	    modelAndView.addObject("searchQuery", new SearchQuery());
	    // Check cookies for login
	    if ((cookie != null) && (cookie.equals(cookiedata))) {
	    	modelAndView.addObject("user", adminUser);
	    }
	    return modelAndView;
	}

	@RequestMapping(value="/logout")
	public ModelAndView logout(@CookieValue(value="SMDB-COOKIE", required = false) String cookie, HttpServletResponse response) {
		if (cookie != null) {
			Cookie logout = new Cookie("SMDB-COOKIE", "");
			logout.setMaxAge(0);
			response.addCookie(logout);
		}
		return new ModelAndView("redirect:/login");
	}
	
	
	
	@RequestMapping(value={"/admin"})  
	public ModelAndView admin(@ModelAttribute SearchQuery query, @CookieValue(value="SMDB-COOKIE", required = false) String cookie) {
	    ModelAndView modelAndView = new ModelAndView();  
	    modelAndView.setViewName("admin");
	    if ((cookie != null) && (cookie.equals(cookiedata))) {
		    	modelAndView.addObject("searchQuery", new SearchQuery());
			    modelAndView.addObject("addData", new Movie());
			    // Check cookies for login
			    if ((cookie != null) && (cookie.equals(cookiedata))) {
			    	modelAndView.addObject("user", adminUser);
			    }
		    	return modelAndView;
	    } else {
	    	return new ModelAndView("redirect:/login");	
	    }
	}


	@RequestMapping(value="/addData")  
	public ModelAndView newMovie(@CookieValue(value="SMDB-COOKIE", required = false) String cookie) {  
	    ModelAndView modelAndView = new ModelAndView();  
	    //modelAndView.setViewName("newMovie");
	    if ((cookie != null) && (cookie.equals(cookiedata))) {

	    	//insertMovie
	    	//insertPerson
	    	//insertRole
		    //modelAndView.addObject("searchQuery", new SearchQuery());
		    // Check cookies for login
		    if ((cookie != null) && (cookie.equals(cookiedata))) {
		    	modelAndView.addObject("user", adminUser);
		    }
		    return modelAndView;  
	    
	    } else {
	    	return new ModelAndView("redirect:/login");
	    }
	}
	

}
