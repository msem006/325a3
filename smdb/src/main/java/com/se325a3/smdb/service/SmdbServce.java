package com.se325a3.smdb.service;

import java.util.Collection;
import java.util.List;

import com.se325a3.smdb.model.Movie;
import com.se325a3.smdb.model.Person;

public interface SmdbServce {

	public Collection<Person> getActorsById(String id);
	public Collection<Person> getActorsByName(String name);
	public Collection<Person> getActorsByMovieTitle(String title); 
	public Collection<Person> getActorsByMovieTitleAndYear(String title, String year);
	public Collection<Movie> getMoviesByTitle(String title);
	public Collection<Movie> getMoviesByActorName(String name);
	public Collection<Movie> getMoviesByTitleAndYear(String title, String year);
	public Collection<Movie> getMoviesByActorID(String id);
}
