package com.se325a3.smdb.dao.jdbctemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Parameter;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.condition.ParamsRequestCondition;

import com.se325a3.smdb.dao.SmdbDao;
import com.se325a3.smdb.model.Movie;
import com.se325a3.smdb.model.Person;
import com.se325a3.smdb.model.Role;

@Repository
public class JdbcTemplateSmdbDao implements SmdbDao {
	
	private static final String SQL_SELECT_PERSON_BY_ID = "SELECT DISTINCT p.id, p.first_name, p.last_name, p.year_born "
			+ "FROM person p WHERE p.id = :id";
	
	private static final String SQL_SELECT_ACTOR_BY_ID = "SELECT DISTINCT p.id, p.first_name, p.last_name, p.year_born "
			+ "FROM person p, role r " + "WHERE p.id = r.id AND p.id LIKE :id";

	private static final String SQL_SELECT_ACTORS_BY_NAME = "SELECT DISTINCT p.id, p.first_name, p.last_name, p.year_born "
			+ "FROM person p, role r "
			+ "WHERE p.id = r.id AND p.first_name LIKE :first_name";

	private static final String SQL_SELECT_ACTORS_BY_MOVIE_TITLE = "SELECT DISTINCT p.id, p.first_name, p.last_name, year_born "
			+ "FROM person p, role r "
			+ "WHERE p.id = r.id AND r.title LIKE :title";

	private static final String SQL_SELECT_ACTORS_BY_MOVIE_TITLE_AND_YEAR = "SELECT DISTINCT p.id, p.first_name, p.last_name, year_born "
			+ "FROM person p, role r "
			+ "WHERE p.id = r.id AND r.title LIKE :title AND r.production_year = :year";

	private static final String SQL_SELECT_MOVIES_BY_TITLE = "SELECT DISTINCT title, production_year, country, run_time, major_genre "
			+ "FROM movie " + "WHERE title LIKE :title";

	private static final String SQL_SELECT_MOVIES_BY_ACTOR_NAME = "SELECT DISTINCT m.title, m.production_year, m.country, m.run_time, m.major_genre "
			+ "FROM movie m, role r, person p "
			+ "WHERE m.title = r.title AND r.id = p.id "
			+ "AND p.first_name LIKE :first_name";

	private static final String SQL_SELECT_MOVIE_BY_TITLE_AND_YEAR = "SELECT DISTINCT title, production_year, country, run_time, major_genre "
			+ "FROM movie "
			+ "WHERE title = :title "
			+ "AND production_year = :year";

	private static final String SQL_SELECT_MOVIES_BY_ACTOR_ID = "SELECT DISTINCT m.title, m.production_year, m.country, m.run_time, m.major_genre "
			+ "FROM movie m, role r, person p "
			+ "WHERE m.title = r.title AND r.id = p.id " + "AND p.id LIKE :id";

	private static final String SQL_INSERT_ACTOR = "INSERT INTO PERSON (first_name, last_name, year_born) VALUES (:first_name, :last_name, :year_born)";

	private static final String SQL_INSERT_MOVIE = "INSERT INTO MOVIE VALUES (:title, :year, :country, :run_time, :major_genre)";
	
	private static final String SQL_INSERT_ROLE = "INSERT INTO ROLE VALUES (:id, :title, :year, :description, :credits)";

	private NamedParameterJdbcTemplate _namedParameterJdbcTemplatedbcTemplate;

	@Autowired
	public JdbcTemplateSmdbDao(DataSource dataSource) {
		_namedParameterJdbcTemplatedbcTemplate = new NamedParameterJdbcTemplate(
				dataSource);
	}

	@Override
	public Person getPersonById(int id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		Person person = _namedParameterJdbcTemplatedbcTemplate.queryForObject(
				SQL_SELECT_PERSON_BY_ID, params, new RowMapper<Person>() {

					@Override
					public Person mapRow(ResultSet resultSet, int rowNum)
							throws SQLException {
						Person person = new Person();
						person.setId(resultSet.getInt(1));
						person.setFirst_name(resultSet.getString(2));
						person.setLast_name(resultSet.getString(3));
						person.setYear_born(resultSet.getInt(4));
						return person;
					}

				});
		return person;
	}
	
	@Override
	public Person getActorById(int id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		Person person = _namedParameterJdbcTemplatedbcTemplate.queryForObject(
				SQL_SELECT_ACTOR_BY_ID, params, new RowMapper<Person>() {

					@Override
					public Person mapRow(ResultSet resultSet, int rowNum)
							throws SQLException {
						Person person = new Person();

						person.setId(resultSet.getInt(1));
						person.setFirst_name(resultSet.getString(2));
						person.setLast_name(resultSet.getString(3));
						person.setYear_born(resultSet.getInt(4));
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
						person.setId(resultSet.getInt(1));
						person.setFirst_name(resultSet.getString(2));
						person.setLast_name(resultSet.getString(3));
						person.setYear_born(resultSet.getInt(4));
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
				SQL_SELECT_ACTORS_BY_MOVIE_TITLE, params,
				new RowMapper<Person>() {

					@Override
					public Person mapRow(ResultSet resultSet, int rowNum)
							throws SQLException {
						Person person = new Person();
						person.setId(resultSet.getInt(1));
						person.setFirst_name(resultSet.getString(2));
						person.setLast_name(resultSet.getString(3));
						person.setYear_born(resultSet.getInt(4));
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
				SQL_SELECT_ACTORS_BY_MOVIE_TITLE_AND_YEAR, params,
				new RowMapper<Person>() {

					@Override
					public Person mapRow(ResultSet resultSet, int rowNum)
							throws SQLException {
						Person person = new Person();
						person.setId(resultSet.getInt(1));
						person.setFirst_name(resultSet.getString(2));
						person.setLast_name(resultSet.getString(3));
						person.setYear_born(resultSet.getInt(4));
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
						movie.setProduction_year(resultSet.getInt(2));
						movie.setCountry(resultSet.getString(3));
						movie.setRun_time(resultSet.getInt(4));
						movie.setMajor_genre(resultSet.getString(5));

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
				SQL_SELECT_MOVIES_BY_ACTOR_NAME, params,
				new RowMapper<Movie>() {

					@Override
					public Movie mapRow(ResultSet resultSet, int rowNum)
							throws SQLException {
						Movie movie = new Movie();

						movie.setTitle(resultSet.getString(1));
						movie.setProduction_year(resultSet.getInt(2));
						movie.setCountry(resultSet.getString(3));
						movie.setRun_time(resultSet.getInt(4));
						movie.setMajor_genre(resultSet.getString(5));

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
				SQL_SELECT_MOVIE_BY_TITLE_AND_YEAR, params,
				new RowMapper<Movie>() {

					@Override
					public Movie mapRow(ResultSet resultSet, int rowNum)
							throws SQLException {
						Movie movie = new Movie();

						movie.setTitle(resultSet.getString(1));
						movie.setProduction_year(resultSet.getInt(2));
						movie.setCountry(resultSet.getString(3));
						movie.setRun_time(resultSet.getInt(4));
						movie.setMajor_genre(resultSet.getString(5));

						return movie;
					}

				});
		return movie;
	}

	@Override
	public List<Movie> getMoviesByActorID(int id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		List<Movie> movies = _namedParameterJdbcTemplatedbcTemplate.query(
				SQL_SELECT_MOVIES_BY_ACTOR_ID, params, new RowMapper<Movie>() {

					@Override
					public Movie mapRow(ResultSet resultSet, int rowNum)
							throws SQLException {
						Movie movie = new Movie();

						movie.setTitle(resultSet.getString(1));
						movie.setProduction_year(resultSet.getInt(2));
						movie.setCountry(resultSet.getString(3));
						movie.setRun_time(resultSet.getInt(4));
						movie.setMajor_genre(resultSet.getString(5));

						return movie;
					}

				});
		return movies;
	}

	@Override
	public int insertPerson(Person person) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id", person.getId());
		params.addValue("first_name", person.getFirst_name());
		params.addValue("last_name", person.getLast_name());
		params.addValue("year_born", person.getYear_born());
		_namedParameterJdbcTemplatedbcTemplate.update(SQL_INSERT_ACTOR, params, keyHolder);
		return keyHolder.getKey().intValue();
	}

	@Override
	public Map<String, Object> insertMovie(Movie movie) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("title", movie.getTitle());
		params.addValue("year", movie.getProduction_year());
		params.addValue("country", movie.getCountry());
		params.addValue("run_time", movie.getRun_time());
		params.addValue("major_genre", movie.getMajor_genre());
		_namedParameterJdbcTemplatedbcTemplate.update(SQL_INSERT_MOVIE, params);
		
		Map<String, Object> key = new HashMap<String, Object>();
		key.put("title", movie.getTitle());
		key.put("production_year", movie.getProduction_year());
		return key; 
	}

	@Override
	public Map<String, Object> insertRole(Role role) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id", role.getId());
		params.addValue("title", role.getTitle());
		params.addValue("year", role.getProduction_year());
		params.addValue("description", role.getDescription());
		params.addValue("credits", role.getCredits());
		_namedParameterJdbcTemplatedbcTemplate.update(SQL_INSERT_ROLE, params);
		
		Map<String, Object> key = new HashMap<String, Object>();
		key.put("title", role.getTitle());
		key.put("production_year", role.getProduction_year());
		key.put("description", role.getDescription());
		return key; 
	}

}
