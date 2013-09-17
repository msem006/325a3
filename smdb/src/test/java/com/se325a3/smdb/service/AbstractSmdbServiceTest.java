package com.se325a3.smdb.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.se325a3.smdb.model.Movie;
import com.se325a3.smdb.model.Person;
import com.se325a3.smdb.model.Role;

@Transactional
public abstract class AbstractSmdbServiceTest {

	@Autowired
	protected SmdbService _smdbService;
	
	@Test 
	public void testGetActorByID() {
		Person actor = _smdbService.getActorById(1001);
		assertTrue(actor.getFirst_name().startsWith("James"));
	}
	
	@Test
	public void testGetActorsByName() {
		Collection<Person> persons = _smdbService.getActorsByName("James");
		assertEquals(2, persons.size());
	}
	
	@Test
	public void testGetActorsByMovieTitle() {
		Collection<Person> persons = _smdbService.getActorsByMovieTitle("Apollo");
		assertEquals(3, persons.size());
	}
	
	@Test
	public void testGetActorsByMovieTitleAndYear() {
		Collection<Person> persons = _smdbService.getActorsByMovieTitleAndYear("American Psycho", "2000");
		assertEquals(4, persons.size());
	}
	
	@Test
	public void testGetMoviesByTitle() {
		Collection<Movie> movies = _smdbService.getMoviesByTitle("a");
		assertEquals(12, movies.size());
	}
	
	@Test
	public void testGetMoviesByActorName() {
		Collection<Movie> movies = _smdbService.getMoviesByActorName("Christian");
		assertEquals(2, movies.size());
	}
	
	@Test 
	public void testGetMovieByTitleAndYear() {
		Movie movie = _smdbService.getMovieByTitleAndYear("Titanic", "1997");
		assertTrue(movie.getTitle().startsWith("Titanic"));
		assertEquals(1997, movie.getProduction_year());
	}
	
	@Test
	public void testGetMoviesByActorID() {
		Collection<Movie> movies = _smdbService.getMoviesByActorID(544);
		assertEquals(4, movies.size());
	}
	
	@Test
	public void testInsertPersonSetId() {
		Person person = new Person();
		person.setFirst_name("Matt");
		person.setLast_name("Damon");
		person.setYear_born(1970);
		int genId = _smdbService.insertPerson(person);
		Person person1 = _smdbService.getPersonById(genId);
		assertEquals("Matt", person1.getFirst_name());
		assertEquals("Damon", person1.getLast_name());
	}
	
	@Test
	public void testInsertTwoPersonSetId() {
		Person person1 = new Person();
		person1.setFirst_name("Matt");
		person1.setLast_name("Damon");
		person1.setYear_born(1970);
		int genId1 = _smdbService.insertPerson(person1);
		Person rPerson1 = _smdbService.getPersonById(genId1);
		assertEquals("Matt", rPerson1.getFirst_name());
		assertEquals("Damon", rPerson1.getLast_name());
		
		Person person2 = new Person();
		person2.setFirst_name("Vin");
		person2.setLast_name("Diesel");
		person2.setYear_born(1967);
		int genId2 = _smdbService.insertPerson(person2);
		Person rPerson2 = _smdbService.getPersonById(genId2);
		assertEquals("Vin", rPerson2.getFirst_name());
		assertEquals("Diesel", rPerson2.getLast_name());
	}
	
	@Test
	public void testInsertMovie() {
		Movie movie = new Movie();
		movie.setTitle("The Bourne Identity");
		movie.setProduction_year(2002);
		movie.setCountry("USA");
		movie.setRun_time(119);
		movie.setMajor_genre("Action");
		Map<String, Object> returnedKey = _smdbService.insertMovie(movie);
		assertEquals(returnedKey.get("title"), "The Bourne Identity");
		assertEquals(returnedKey.get("production_year"), 2002);
	}

	@Test
	public void testInsertRole() {
		
		Person person = new Person();
		person.setFirst_name("Matt");
		person.setLast_name("Damon");
		person.setYear_born(1970);
		int personId = _smdbService.insertPerson(person);
		
		Movie movie = new Movie();
		movie.setTitle("The Bourne Identity");
		movie.setProduction_year(2002);
		movie.setCountry("USA");
		movie.setRun_time(119);
		movie.setMajor_genre("Action");
		_smdbService.insertMovie(movie);
	
		Role role = new Role();
		role.setId(personId);
		role.setPerson(person);
		role.setMovie(movie);
		role.setDescription("Jason Bourne");
		role.setCredits("");
		_smdbService.insertRole(role);
		
		Person actor = _smdbService.getActorById(personId);
		assertTrue(actor.getFirst_name().startsWith("Matt"));
		assertTrue(actor.getLast_name().startsWith("Damon"));
		assertEquals(1970, actor.getYear_born());
	}
	
	
}
