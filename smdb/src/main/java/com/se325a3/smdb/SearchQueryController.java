package com.se325a3.smdb;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller
@SessionAttributes
public class SearchQueryController {

	@RequestMapping(value = "/searchResults", method = RequestMethod.POST)
	public String addContact(@ModelAttribute("search")
							SearchQuery query, BindingResult result) {
		
		System.out.println("Query:" + query.getQuery() + 
					" Type:" + query.getType());
		
		return "redirect:search.html";
	}
	
	@RequestMapping("/search")
	public ModelAndView search() {
		
		return new ModelAndView("search", "command", new SearchQuery());
	}
}
