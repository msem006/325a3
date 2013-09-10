package com.se325a3.smdb.dao;

import com.se325a3.smdb.model.*;

import java.util.List;

public interface SmdbDao {
	List<Person> getPersonsByName(String name);
	List<Person> getPersonsByMovieTitle(String title); 
	List<Movie> getMoviesByTitle(String title);
	List<Movie> getMoviesByActorName(String name);
}
