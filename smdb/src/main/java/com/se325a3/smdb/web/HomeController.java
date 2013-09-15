package com.se325a3.smdb.web;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.se325a3.smdb.model.Movie;
import com.se325a3.smdb.model.Person;
import com.se325a3.smdb.service.SmdbService;

/**
 * Simple Controller class to process incoming HTTP GET requests of the form:
 * http://localhost:808/HelloSpringMVC/hello". The Controller simply generates a
 * greeting and names "output" as the logical view name to present the greeting.
 * 
 */
@Controller
public class HomeController {

	private SmdbService _smdbService;

	@Autowired
	public HomeController(SmdbService smdbService) {
		_smdbService = smdbService;
	}

	/*
	 * @RequestMapping(value = "/index", method = RequestMethod.GET) public
	 * String printHome(ModelMap model) { return "index"; }
	 */

	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	public String printWelcome(ModelMap model) {
		Person actor = _smdbService.getActorById("00001001");
		model.addAttribute("message",
				"Actor's first name: " + actor.getFirstName());
		Collection<Movie> movies1 = _smdbService.getMoviesByTitle("Titanic");
		for (Movie movie : movies1) {
			System.out.println(movie.getTitle() + " "
					+ movie.getProductionYear());
		}
		Collection<Movie> movies2 = _smdbService.getMoviesByActorName("Christian");
		for (Movie movie : movies2) {
			System.out.println("Christian: " + movie.getTitle() + " "
					+ movie.getProductionYear());
		}
		Collection<Movie> movies3 = _smdbService.getMoviesByActorID("00000544");
		for (Movie movie : movies3) {
			System.out.println("Sigourney Weaver (00000544): " + movie.getTitle() + " "
					+ movie.getProductionYear());
		}
		Collection<Person> persons1 = _smdbService.getActorsByName("James");
		for (Person person : persons1) {
			System.out.println(person.getFirstName() + " "
					+ person.getLastName() + " " + person.getId() + " "
					+ person.getYearBorn());
		}
		Collection<Person> persons2 = _smdbService.getActorsByMovieTitle("Apollo");
		for (Person person : persons2) {
			System.out.println("Apollo: " + person.getFirstName() + " "
					+ person.getLastName() + " " + person.getId() + " "
					+ person.getYearBorn());
		}
		Collection<Person> persons3 = _smdbService.getActorsByMovieTitleAndYear("American Psycho", "2000");
		for (Person person : persons3) {
			System.out.println("American Psycho: " + person.getFirstName() + " "
					+ person.getLastName() + " " + person.getId() + " "
					+ person.getYearBorn());
		}
		Movie movie = _smdbService.getMovieByTitleAndYear("Titanic", "1997");
		System.out.println(movie.getTitle() + " " + movie.getProductionYear()
				+ " " + movie.getCountry());
		return "output";
	}
}
