package com.se325a3.smdb.service;

import java.util.Collection;

import com.se325a3.smdb.model.Movie;
import com.se325a3.smdb.model.Person;
import com.se325a3.smdb.model.Role;

public interface SmdbService {
	Person getPersonById(int id);
	public Person getActorById(int id);
	public Collection<Person> getActorsByFirstName(String name);
	public Collection<Person> getActorsByLastName(String name);
	public Collection<Person> getActorsByFirstNameAndLastName(String firstname, String lastname);
	public Collection<Person> getActorsByFirstNameOrLastName(String firstname, String lastname);
	public Collection<Person> getActorsByMovieTitle(String title); 
	public Collection<Person> getActorsByMovieTitleAndYear(String title, String year);
	public Collection<Movie> getMoviesByTitle(String title);
	public Collection<Movie> getMoviesByActorName(String name);
	public Movie getMovieByTitleAndYear(String title, String year);
	public Collection<Movie> getMoviesByActorID(int id);
	int insertPerson(Person person);
	void insertMovie(Movie movie);
	void insertRole(Role role);
}
