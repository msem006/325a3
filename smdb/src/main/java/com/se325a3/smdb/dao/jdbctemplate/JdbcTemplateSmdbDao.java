package com.se325a3.smdb.dao.jdbctemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.se325a3.smdb.dao.SmdbDao;
import com.se325a3.smdb.model.Movie;
import com.se325a3.smdb.model.Person;

@Repository
public class JdbcTemplateSmdbDao implements SmdbDao {

	private static final String SQL_SELECT_ACTORS_BY_ID = "SELECT p.id, p.first_name, p.last_name, p.year_born "
			+ "FROM person " + "WHERE p.id = r.id AND p.id LIKE :id";

	private static final String SQL_SELECT_ACTORS_BY_NAME = "SELECT p.id, p.first_name, p.last_name, p.year_born "
			+ "FROM person, role r " + "WHERE p.id = r.id AND p.first_name LIKE :first_name";

	private static final String SQL_SELECT_ACTORS_BY_MOVIE_TITLE = "SELECT p.id, p.first_name, p.last_name, year_born "
			+ "FROM person p, role r " + "WHERE p.id = r.id AND r.title LIKE :title";
	
	private static final String SQL_SELECT_ACTORS_BY_MOVIE_TITLE_AND_YEAR = "SELECT p.id, p.first_name, p.last_name, year_born "
			+ "FROM person p, role r " + "WHERE p.id = r.id AND r.title LIKE :title AND r.production_year = :year";

	private static final String SQL_SELECT_MOVIES_BY_TITLE = "SELECT title, production_year, country, run_time, major_genre "
			+ "FROM movie " + "WHERE title LIKE :title";

	private static final String SQL_SELECT_MOVIES_BY_ACTOR_NAME = "SELECT m.title, m.production_year, m.country, m.run_time, m.major_genre "
			+ "FROM movie m, role r, person p "
			+ "WHERE m.title = r.title AND r.id = p.id "
			+ "AND p.first_name LIKE :first_name";

	private static final String SQL_SELECT_MOVIES_BY_TITLE_AND_YEAR = "SELECT title, production_year, country, run_time, major_genre "
			+ "FROM movie " 
			+ "WHERE title = :title "
			+ "AND production_year = :year";
	
	private static final String SQL_SELECT_MOVIES_BY_ACTOR_ID = "SELECT m.title, m.production_year, m.country, m.run_time, m.major_genre "
			+ "FROM movie m, role r, person p "
			+ "WHERE m.title = r.title AND r.id = p.id "
			+ "AND p.id LIKE :id";


	private NamedParameterJdbcTemplate _namedParameterJdbcTemplatedbcTemplate;

	@Autowired
	public JdbcTemplateSmdbDao(DataSource dataSource) {
		_namedParameterJdbcTemplatedbcTemplate = new NamedParameterJdbcTemplate(
				dataSource);
	}

	@Override
	public Person getActorById(String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		Person person = _namedParameterJdbcTemplatedbcTemplate.queryForObject(
				SQL_SELECT_ACTORS_BY_ID, params, new RowMapper<Person>() {

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

				});
		return person;
	}

	@Override
	public List<Person> getActorsByName(String name) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("first_name", name + "%");
		List<Person> persons = _namedParameterJdbcTemplatedbcTemplate.query(
				SQL_SELECT_ACTORS_BY_NAME, params, new RowMapper<Person>() {

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

				});
		return persons;
	}

	@Override
	public List<Person> getActorsByMovieTitle(String title) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("title", "%" + title + "%");
		List<Person> persons = _namedParameterJdbcTemplatedbcTemplate.query(
				SQL_SELECT_ACTORS_BY_MOVIE_TITLE, params, new RowMapper<Person>() {

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

				});
		return persons;
	}
	
	@Override
	public List<Person> getActorsByMovieTitleAndYear(String title, String year) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("title", "%" + title + "%");
		params.put("year", year);
		List<Person> persons = _namedParameterJdbcTemplatedbcTemplate.query(
				SQL_SELECT_ACTORS_BY_MOVIE_TITLE_AND_YEAR, params, new RowMapper<Person>() {

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

				});
		return persons;
	}

	@Override
	public List<Movie> getMoviesByTitle(String title) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("title", title + "%");
		List<Movie> movies = _namedParameterJdbcTemplatedbcTemplate.query(
				SQL_SELECT_MOVIES_BY_TITLE, params, new RowMapper<Movie>() {

					@Override
					public Movie mapRow(ResultSet resultSet, int rowNum)
							throws SQLException {
						Movie movie = new Movie();

						movie.setTitle(resultSet.getString(1));
						movie.setProductionYear(resultSet.getInt(2));
						movie.setCountry(resultSet.getString(3));
						movie.setRunTime(resultSet.getInt(4));
						movie.setMajorGenre(resultSet.getString(5));

						return movie;
					}

				});
		return movies;
	}

	@Override
	public List<Movie> getMoviesByActorName(String name) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("first_name", name + "%");
		List<Movie> movies = _namedParameterJdbcTemplatedbcTemplate.query(
				SQL_SELECT_MOVIES_BY_ACTOR_NAME, params, new RowMapper<Movie>() {

					@Override
					public Movie mapRow(ResultSet resultSet, int rowNum)
							throws SQLException {
						Movie movie = new Movie();

						movie.setTitle(resultSet.getString(1));
						movie.setProductionYear(resultSet.getInt(2));
						movie.setCountry(resultSet.getString(3));
						movie.setRunTime(resultSet.getInt(4));
						movie.setMajorGenre(resultSet.getString(5));

						return movie;
					}

				});
		return movies;
	}



	@Override
	public Movie getMovieByTitleAndYear(String title, String year) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("title", title);
		params.put("year", year);
		Movie movie = _namedParameterJdbcTemplatedbcTemplate.queryForObject(
				SQL_SELECT_MOVIES_BY_TITLE_AND_YEAR, params, new RowMapper<Movie>() {

					@Override
					public Movie mapRow(ResultSet resultSet, int rowNum)
							throws SQLException {
						Movie movie = new Movie();

						movie.setTitle(resultSet.getString(1));
						movie.setProductionYear(resultSet.getInt(2));
						movie.setCountry(resultSet.getString(3));
						movie.setRunTime(resultSet.getInt(4));
						movie.setMajorGenre(resultSet.getString(5));

						return movie;
					}

				});
		return movie;
	}

	@Override
	public List<Movie> getMoviesByActorID(String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		List<Movie> movies = _namedParameterJdbcTemplatedbcTemplate.query(
				SQL_SELECT_MOVIES_BY_ACTOR_ID, params, new RowMapper<Movie>() {

					@Override
					public Movie mapRow(ResultSet resultSet, int rowNum)
							throws SQLException {
						Movie movie = new Movie();

						movie.setTitle(resultSet.getString(1));
						movie.setProductionYear(resultSet.getInt(2));
						movie.setCountry(resultSet.getString(3));
						movie.setRunTime(resultSet.getInt(4));
						movie.setMajorGenre(resultSet.getString(5));

						return movie;
					}

				});
		return movies;
	}

}
