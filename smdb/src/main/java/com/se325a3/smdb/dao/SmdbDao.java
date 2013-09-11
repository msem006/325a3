package com.se325a3.smdb.dao;

import com.se325a3.smdb.model.*;

import java.util.List;

public interface SmdbDao {
	List<Person> getActorsByName(String name);
	List<Person> getActorsByMovieTitle(String title); 
	List<Movie> getMoviesByTitle(String title);
	List<Movie> getMoviesByActorName(String name);
}
