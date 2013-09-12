package com.se325a3.smdb.dao;

import com.se325a3.smdb.model.*;

import java.util.List;

public interface SmdbDao {
	Person getActorById(String id);
	List<Person> getActorsByName(String name);
	List<Person> getActorsByMovieTitle(String title);
	List<Person> getActorsByMovieTitleAndYear(String title, String year);
	List<Movie> getMoviesByTitle(String title);
	List<Movie> getMoviesByActorName(String name);
	Movie getMovieByTitleAndYear(String title, String year);
	List<Movie> getMoviesByActorID(String id);
}
