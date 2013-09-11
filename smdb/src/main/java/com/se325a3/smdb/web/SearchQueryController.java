package com.se325a3.smdb.web;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
	
	@RequestMapping(value = "/searchResults", method = RequestMethod.POST)
	public String addContact(@ModelAttribute("search")
							SearchQuery query, BindingResult result) {
		
		System.out.println("Query:" + query.getQuery() + 
					" Type:" + query.getType());
		
		if (query.getType().equals("Movie")) {
			Collection<Movie> list = 
					_smdbServce.getMoviesByTitle(query.getQuery());
			for (Movie movie : list) {
				System.out.println(movie.getTitle() + " " + movie.getProductionYear());
			}
			System.out.println(list.size());
		} else {
			Collection<Person> list = 
					_smdbServce.getActorsByName(query.getQuery());
			for (Person person : list) {
				System.out.println(person.getFirstName() + " " + person.getLastName());
			}
			System.out.println(list.size());
		}
		
		return "redirect:search.html";
	}
	
	@RequestMapping("/search")
	public ModelAndView search() {
		
		return new ModelAndView("search", "command", new SearchQuery());
	}
}
