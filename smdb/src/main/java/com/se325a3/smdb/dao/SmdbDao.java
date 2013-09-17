package com.se325a3.smdb.dao;

import java.util.List;
import java.util.Map;

import com.se325a3.smdb.model.Movie;
import com.se325a3.smdb.model.Person;
import com.se325a3.smdb.model.Role;

public interface SmdbDao {
	Person getPersonById(int id);
	Person getActorById(int id);
	List<Person> getActorsByName(String name);
	List<Person> getActorsByMovieTitle(String title);
	List<Person> getActorsByMovieTitleAndYear(String title, String year);
	List<Movie> getMoviesByTitle(String title);
	List<Movie> getMoviesByActorName(String name);
	Movie getMovieByTitleAndYear(String title, String year);
	List<Movie> getMoviesByActorID(int id);
	int insertPerson(Person person);
	Map<String, Object> insertMovie(Movie movie);
	Map<String, Object> insertRole(Role role);
}
