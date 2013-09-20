package com.se325a3.smdb.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.Set;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import com.se325a3.smdb.model.Movie;
import com.se325a3.smdb.model.Person;
import com.se325a3.smdb.model.Role;

public abstract class AbstractSmdbServiceTest {

	@Autowired
	protected SmdbService _smdbService;
	
	@Test 
	public void testGetActorByID() {
		Person actor = _smdbService.getActorById(1001);
		assertTrue(actor.getFirst_name().startsWith("James"));
	}
	
	@Test
	public void testGetActorsByFirstName() {
		Collection<Person> persons = _smdbService.getActorsByFirstName("James");
		assertEquals(2, persons.size());
	}
	
	@Test
	public void testGetActorsByLastName() {
		Collection<Person> persons = _smdbService.getActorsByLastName("Lee");
		assertEquals(1, persons.size());
	}
	
	@Test
	public void testGetActorsByFirstNameAndLastName() {
		Collection<Person> persons = _smdbService.getActorsByFirstNameAndLastName("T", "Mo");
		assertEquals(2, persons.size());
	}
	
	@Test
	public void testGetActorsByFirstNameOrLastName() {
		Collection<Person> persons = _smdbService.getActorsByFirstNameOrLastName("Bob", "Zhang");
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
	@Transactional
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
	@Transactional
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
	@Transactional
	public void testInsertMovie() {
		Movie movie = new Movie();
		movie.setTitle("The Bourne Identity");
		movie.setProduction_year(2002);
		movie.setCountry("USA");
		movie.setRun_time(119);
		movie.setMajor_genre("Action");
		_smdbService.insertMovie(movie);
	}

	@Test(expected = DataIntegrityViolationException.class)
	@Transactional
	public void testInsertExistingMovie() {
		Movie movie = new Movie();
		movie.setTitle("Alien 3");
		movie.setProduction_year(1992);
		movie.setCountry("USA");
		movie.setRun_time(115);
		movie.setMajor_genre("Action");
		_smdbService.insertMovie(movie);
	}
	
	@Test
	@Transactional
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
	
	@Test(expected = DataIntegrityViolationException.class)
	@Transactional
	public void testInsertExistingRole() {
		Role role = new Role();
		role.setId(544);
		role.setTitle("Alien 3");
		role.setProduction_year(1992);
		role.setDescription("Ellen Ripley");
		role.setCredits("Bob");
		_smdbService.insertRole(role);
	}
	
	@Test 
	public void testActorRoles() {
		Person actor = _smdbService.getActorById(544);
		Set<Role> roles = actor.getRoles();
		assertEquals(4, roles.size());
	}
	
	@Test
	public void testMovieRoles() {
		Movie movie = _smdbService.getMovieByTitleAndYear("Scream 3", "2000");
		Set<Role> actors = movie.getRoles();
		assertEquals(6, actors.size());
	}
	
	@Test
	public void testGetRolesAfterInsert() {
		Role role = new Role();
		role.setId(544);
		role.setTitle("Titanic");
		role.setProduction_year(1997);
		role.setDescription("Bob");
		role.setCredits("");
		_smdbService.insertRole(role);
		
		Person actor = _smdbService.getActorById(544);
		Set<Role> roles = actor.getRoles();
		assertEquals(5, roles.size());
		
		Movie movie = _smdbService.getMovieByTitleAndYear("Titanic", "1997");
		Set<Role> actors = movie.getRoles();
		assertEquals(5, actors.size());
	}

}