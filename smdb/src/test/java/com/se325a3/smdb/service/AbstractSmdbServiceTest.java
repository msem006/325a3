package com.se325a3.smdb.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

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
		Person actor = _smdbService.getActorById("00001001");
		assertTrue(actor.getFirst_name().startsWith("James"));
	}
	
	@Test
	public void testGetActorsByName() {
		Collection<Person> persons = _smdbService.getActorsByName("James");
		for (Person person : persons) {
			System.out.println(person.getFirst_name() + " "
					+ person.getLast_name() + " " + person.getId() + " "
					+ person.getYear_born());
		}
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
		Collection<Movie> movies = _smdbService.getMoviesByActorID("00000544");
		assertEquals(4, movies.size());
	}
	
	@Test
	public void testInsertPerson() {
		Person person = new Person();
		person.setId("10000001");
		person.setFirst_name("Matt");
		person.setLast_name("Damon");
		person.setYear_born(1970);
		int rowsAffected = _smdbService.insertPerson(person);
		assertEquals(1, rowsAffected);
	}

	@Test
	public void testInsertMovie() {
		Movie movie = new Movie();
		movie.setTitle("The Bourne Identity");
		movie.setProduction_year(2002);
		movie.setCountry("USA");
		movie.setRun_time(119);
		movie.setMajor_genre("Action");
		int rowsAffected = _smdbService.insertMovie(movie);
		assertEquals(1, rowsAffected);
	}

	@Test
	public void testInsertRole() {
		
		Person person = new Person();
		person.setId("10000001");
		person.setFirst_name("Matt");
		person.setLast_name("Damon");
		person.setYear_born(1970);
		int pRowsAffected = _smdbService.insertPerson(person);
		assertEquals(1, pRowsAffected);
		
		Movie movie = new Movie();
		movie.setTitle("The Bourne Identity");
		movie.setProduction_year(2002);
		movie.setCountry("USA");
		movie.setRun_time(119);
		movie.setMajor_genre("Action");
		int mRowsAffected = _smdbService.insertMovie(movie);
		assertEquals(1, mRowsAffected);
		
		Role role = new Role();
		role.setId("10000001");
		role.setTitle("The Bourne Identity");
		role.setProduction_year(2002);
		role.setDescription("Jason Bourne");
		role.setCredits("");
		int rRowsAffected = _smdbService.insertRole(role);
		assertEquals(1, rRowsAffected);
		
		Person actor = _smdbService.getActorById("10000001");
		assertTrue(actor.getFirst_name().startsWith("Matt"));
		assertTrue(actor.getLast_name().startsWith("Damon"));
		assertEquals(1970, actor.getYear_born());
	}
	
	
}
