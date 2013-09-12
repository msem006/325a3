package com.se325a3.smdb.service;

import java.util.Collection;

import com.se325a3.smdb.model.Movie;
import com.se325a3.smdb.model.Person;

public interface SmdbServce {

	public Collection<Person> getActorsById(String id);
	public Collection<Person> getActorsByName(String name);
	public Collection<Person> getActorsByMovieTitle(String title); 
	public Collection<Movie> getMoviesByTitle(String title);
	public Collection<Movie> getMoviesByActorName(String name);
	public Collection<Movie> getMoviesByTitleAndYear(String title, String year);
}
