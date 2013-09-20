package com.se325a3.smdb.dao.jdbctemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.se325a3.smdb.dao.PersonSmdbDao;
import com.se325a3.smdb.model.Person;

@Repository
@Transactional
public class JdbcTemplatePersonSmdbDao implements PersonSmdbDao {

	private static final String SQL_SELECT_PERSON_BY_ID = "SELECT DISTINCT p.id, p.first_name, p.last_name, p.year_born "
			+ "FROM person p WHERE p.id = :id";

	private static final String SQL_SELECT_ACTOR_BY_ID = "SELECT DISTINCT p.id, p.first_name, p.last_name, p.year_born "
			+ "FROM person p, role r " + "WHERE p.id = r.id AND p.id LIKE :id";

	private static final String SQL_SELECT_ACTORS_BY_FIRST_NAME = "SELECT DISTINCT p.id, p.first_name, p.last_name, p.year_born "
			+ "FROM person p, role r "
			+ "WHERE p.id = r.id AND p.first_name LIKE :first_name";

	private static final String SQL_SELECT_ACTORS_BY_LAST_NAME = "SELECT DISTINCT p.id, p.first_name, p.last_name, p.year_born "
			+ "FROM person p, role r "
			+ "WHERE p.id = r.id AND p.last_name LIKE :last_name";

	private static final String SQL_SELECT_ACTORS_BY_FIRST_NAME_AND_LAST_NAME = "SELECT DISTINCT p.id, p.first_name, p.last_name, p.year_born "
			+ "FROM person p, role r "
			+ "WHERE p.id = r.id AND p.first_name LIKE :first_name AND p.last_name LIKE :last_name";

	private static final String SQL_SELECT_ACTORS_BY_FIRST_NAME_OR_LAST_NAME = "SELECT DISTINCT p.id, p.first_name, p.last_name, p.year_born "
			+ "FROM person p, role r "
			+ "WHERE p.id = r.id AND p.first_name LIKE :first_name OR p.last_name LIKE :last_name AND p.id = r.id";

	private static final String SQL_SELECT_ACTORS_BY_MOVIE_TITLE = "SELECT DISTINCT p.id, p.first_name, p.last_name, year_born "
			+ "FROM person p, role r "
			+ "WHERE p.id = r.id AND r.title LIKE :title";

	private static final String SQL_SELECT_ACTORS_BY_MOVIE_TITLE_AND_YEAR = "SELECT DISTINCT p.id, p.first_name, p.last_name, year_born "
			+ "FROM person p, role r "
			+ "WHERE p.id = r.id AND r.title LIKE :title AND r.production_year = :year";

	private static final String SQL_INSERT_ACTOR = "INSERT INTO PERSON (first_name, last_name, year_born) VALUES (:first_name, :last_name, :year_born)";

	private NamedParameterJdbcTemplate _namedParameterJdbcTemplatedbcTemplate;

	private JdbcTemplateRoleSmdbDao _roleSmdbDao;

	@Autowired
	public JdbcTemplatePersonSmdbDao(DataSource dataSource,
			JdbcTemplateRoleSmdbDao roleSmdbDao) {
		_namedParameterJdbcTemplatedbcTemplate = new NamedParameterJdbcTemplate(
				dataSource);
		_roleSmdbDao = roleSmdbDao;
	}

	@Override
	public Person getPersonById(int id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		try {
			Person person = _namedParameterJdbcTemplatedbcTemplate
					.queryForObject(SQL_SELECT_PERSON_BY_ID, params,
							new PersonRowMapper());
			return person;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public Person getActorById(int id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		try {
			Person person = _namedParameterJdbcTemplatedbcTemplate
					.queryForObject(SQL_SELECT_ACTOR_BY_ID, params,
							new PersonRowMapper());
			_roleSmdbDao.getRolesForActor(person);
			return person;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public List<Person> getActorsByFirstName(String name) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("first_name", name + "%");
		List<Person> persons = _namedParameterJdbcTemplatedbcTemplate.query(
				SQL_SELECT_ACTORS_BY_FIRST_NAME, params, new PersonRowMapper());
		_roleSmdbDao.getRolesForActors(persons);
		return persons;
	}

	@Override
	public List<Person> getActorsByLastName(String name) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("last_name", name + "%");
		List<Person> persons = _namedParameterJdbcTemplatedbcTemplate.query(
				SQL_SELECT_ACTORS_BY_LAST_NAME, params, new PersonRowMapper());
		_roleSmdbDao.getRolesForActors(persons);
		return persons;
	}

	@Override
	public List<Person> getActorsByFirstNameAndLastName(String firstname,
			String lastname) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("first_name", firstname + "%");
		params.put("last_name", lastname + "%");
		List<Person> persons = _namedParameterJdbcTemplatedbcTemplate.query(
				SQL_SELECT_ACTORS_BY_FIRST_NAME_AND_LAST_NAME, params,
				new PersonRowMapper());
		_roleSmdbDao.getRolesForActors(persons);
		return persons;
	}

	@Override
	public List<Person> getActorsByFirstNameOrLastName(String firstname,
			String lastname) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("first_name", firstname + "%");
		params.put("last_name", lastname + "%");
		List<Person> persons = _namedParameterJdbcTemplatedbcTemplate.query(
				SQL_SELECT_ACTORS_BY_FIRST_NAME_OR_LAST_NAME, params,
				new PersonRowMapper());
		_roleSmdbDao.getRolesForActors(persons);
		return persons;
	}

	@Override
	public List<Person> getActorsByMovieTitle(String title) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("title", "%" + title + "%");
		List<Person> persons = _namedParameterJdbcTemplatedbcTemplate
				.query(SQL_SELECT_ACTORS_BY_MOVIE_TITLE, params,
						new PersonRowMapper());
		_roleSmdbDao.getRolesForActors(persons);
		return persons;
	}

	@Override
	public List<Person> getActorsByMovieTitleAndYear(String title, String year) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("title", "%" + title + "%");
		params.put("year", year);
		List<Person> persons = _namedParameterJdbcTemplatedbcTemplate.query(
				SQL_SELECT_ACTORS_BY_MOVIE_TITLE_AND_YEAR, params,
				new PersonRowMapper());
		_roleSmdbDao.getRolesForActors(persons);
		return persons;
	}

	@Override
	public int insertPerson(Person person) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("first_name", person.getFirst_name());
		params.addValue("last_name", person.getLast_name());
		params.addValue("year_born", person.getYear_born());
		_namedParameterJdbcTemplatedbcTemplate.update(SQL_INSERT_ACTOR, params,
				keyHolder);
		return keyHolder.getKey().intValue();
	}

	private class PersonRowMapper implements RowMapper<Person> {

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

	}
}
