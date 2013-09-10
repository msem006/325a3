package com.se325a3.smdb.dao.jdbctemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.se325a3.smdb.dao.SmdbDao;
import com.se325a3.smdb.model.Movie;
import com.se325a3.smdb.model.Person;

public class JdbcTemplateSmdbDao implements SmdbDao {

	private static final String SQL_SELECT_ACTORS_BY_NAME = 
			"SELECT id, first_name, last_name, year_born "
			+ "FROM person "
			+ "WHERE first_name LIKE '%?%'";
	
	private static final String SQL_SELECT_ACTORS_BY_MOVIE_TITLE = 
			"SELECT p.id, p.first_name, p.last_name, year_born "
			+ "FROM person p, role r "
			+ "WHERE p.id = r.id AND r.title LIKE '%?%'";
	
	private static final String SQL_SELECT_MOVIES_BY_TITLE = 
			"SELECT title, production_year, country, run_time, major_genre "
			+ "FROM movie "
			+ "WHERE title LIKE '%?%'";
	
	private static final String SQL_SELECT_MOVIES_BY_ACTOR_NAME = 
			"SELECT m.title, m.production_year, m.country, m.run_time, m.major_genre "
			+ "p.first_name, p.last_name"
			+ "FROM movie m, role r, person p "
			+ "WHERE m.title = r.title AND r.id = p.id "
			+ "AND p.first_name LIKE '%?%'";
	
	private JdbcTemplate _jdbcTemplate;
	
	@Override
	public List<Person> getPersonsByName(String name) {
		List<Person> persons = _jdbcTemplate.query(
				SQL_SELECT_ACTORS_BY_NAME, new RowMapper<Person>() {

					@Override
					public Person mapRow(ResultSet resultSet, int rowNum)
							throws SQLException {
						Person person = new Person();
						
						person.setId(resultSet.getString(1));
						person.setFirstName(resultSet.getString(2));
						person.setLastName(resultSet.getString(3));
						person.setYearBorn(resultSet.getInt(4));
						
						return person;
					}
					
				}, name);
		return persons;
	}

	@Override
	public List<Person> getPersonsByMovieTitle(String title) {
		List<Person> persons = _jdbcTemplate.query(
				SQL_SELECT_ACTORS_BY_MOVIE_TITLE, new RowMapper<Person>() {

					@Override
					public Person mapRow(ResultSet resultSet, int rowNum)
							throws SQLException {
						Person person = new Person();
						
						person.setId(resultSet.getString(1));
						person.setFirstName(resultSet.getString(2));
						person.setLastName(resultSet.getString(3));
						person.setYearBorn(resultSet.getInt(4));
						
						return person;
					}
					
				}, title);
		return persons;
	}
	
	@Override
	public List<Movie> getMoviesByTitle(String title) {
		List<Movie> movies = _jdbcTemplate.query(
				SQL_SELECT_MOVIES_BY_TITLE, new RowMapper<Movie>() {

					@Override
					public Movie mapRow(ResultSet resultSet, int rowNum)
							throws SQLException {
						Movie movie = new Movie();
						
						movie.setTitle(resultSet.getString(1));
						movie.setProductionYear(resultSet.getInt(2));
						movie.setTitle(resultSet.getString(3));
						movie.setRunTime(resultSet.getInt(4));
						movie.setTitle(resultSet.getString(5));
						
						return movie;
					}
					
				}, title);
		return movies;
	}

	
	@Override
	public List<Movie> getMoviesByActorName(String name) {
		List<Movie> movies = _jdbcTemplate.query(
				SQL_SELECT_MOVIES_BY_ACTOR_NAME, new RowMapper<Movie>() {

					@Override
					public Movie mapRow(ResultSet resultSet, int rowNum)
							throws SQLException {
						Movie movie = new Movie();
						
						movie.setTitle(resultSet.getString(1));
						movie.setProductionYear(resultSet.getInt(2));
						movie.setTitle(resultSet.getString(3));
						movie.setRunTime(resultSet.getInt(4));
						movie.setTitle(resultSet.getString(5));
						
						return movie;
					}
					
				}, name);
		return movies;
	}

}
