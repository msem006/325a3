package com.se325a3.smdb.dao;

import java.util.List;

import com.se325a3.smdb.model.Movie;

/**
 * Queries that return or insert movies from and to the Database
 *
 */
public interface MovieSmdbDao {
	
	/**
	 * Retrieve Movies with titles matching the given title
	 * 
	 * @param title
	 *            Title to match against
	 * @return A list of movies with titles starting with the title parameter
	 */
	List<Movie> getMoviesByTitle(String title);

	/**
	 * Retrieve Movies that have an actor (a Person with an entry in the ROLE
	 * table) that matches the given name
	 * 
	 * @param name
	 *            Name to match against
	 * @return A list of movies with an actor matching the given name parameter
	 */
	List<Movie> getMoviesByActorName(String name);

	/**
	 * Retrieve the Movie with the given title and production year (primary key)
	 * 
	 * @param title
	 *            Title of the Movie
	 * @param year
	 *            Production year of the Movie
	 * @return Movie with the given title and production year
	 */
	Movie getMovieByTitleAndYear(String title, String year);

	/**
	 * Retrieve Movies that have an actor (a Person with an entry in the ROLE
	 * table) with the given id (primary key on PERSON)
	 * 
	 * @param id
	 *            Id of actor to match against
	 * @return A list of movies with an actor matching the given id parameter
	 */
	List<Movie> getMoviesByActorID(int id);
	
	/**
	 * Insert a Movie into the database
	 * 
	 * @param movie
	 *            Movie object to be saved in database
	 * @return A map containing the title and production year fields of the
	 *         inserted Movie
	 */
	void insertMovie(Movie movie);
}
