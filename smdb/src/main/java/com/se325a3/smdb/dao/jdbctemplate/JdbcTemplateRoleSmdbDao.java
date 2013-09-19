package com.se325a3.smdb.dao.jdbctemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.se325a3.smdb.dao.RoleSmdbDao;
import com.se325a3.smdb.model.Movie;
import com.se325a3.smdb.model.Person;
import com.se325a3.smdb.model.Role;

@Repository
@Transactional
public class JdbcTemplateRoleSmdbDao implements RoleSmdbDao {

	private static final String SQL_SELECT_ROLES_BY_ACTOR_ID = "SELECT DISTINCT r.id, r.title, r.production_year, r.description, r.credits, p.first_name, p.last_name, p.year_born, m.country, m.run_time, m.major_genre "
			+ "FROM role r, person p, movie m "
			+ "WHERE r.id = p.id AND r.title = m.title AND r.production_year = m.production_year "
			+ "AND p.id = :id";

	private static final String SQL_SELECT_ROLES_BY_MOVIE_TITLE_AND_YEAR = "SELECT DISTINCT r.id, r.title, r.production_year, r.description, r.credits, p.first_name, p.last_name, p.year_born, m.country, m.run_time, m.major_genre "
			+ "FROM role r, person p, movie m "
			+ "WHERE r.id = p.id AND r.title = m.title AND r.production_year = m.production_year "
			+ "AND r.title = :title AND r.production_year = :year";

	private static final String SQL_INSERT_ROLE = "INSERT INTO ROLE VALUES (:id, :title, :year, :description, :credits)";

	private NamedParameterJdbcTemplate _namedParameterJdbcTemplatedbcTemplate;

	@Autowired
	public JdbcTemplateRoleSmdbDao(DataSource dataSource) {
		_namedParameterJdbcTemplatedbcTemplate = new NamedParameterJdbcTemplate(
				dataSource);
	}

	@Override
	public void insertRole(Role role) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id", role.getId());
		params.addValue("title", role.getTitle());
		params.addValue("year", role.getProduction_year());
		params.addValue("description", role.getDescription());
		params.addValue("credits", role.getCredits());
		_namedParameterJdbcTemplatedbcTemplate.update(SQL_INSERT_ROLE, params);
	}

	void getRolesForActor(Person actor) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", actor.getId());
		actor.setRoles(new HashSet<Role>(_namedParameterJdbcTemplatedbcTemplate
				.query(SQL_SELECT_ROLES_BY_ACTOR_ID, params,
						new RoleRowMapper())));
	}

	void getRolesForActors(List<Person> actors) {
		for (Person actor : actors) {
			getRolesForActor(actor);
		}
	}

	void getRolesForMovie(Movie movie) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("title", movie.getTitle());
		params.put("year", movie.getProduction_year());
		movie.setRoles(new HashSet<Role>(
				_namedParameterJdbcTemplatedbcTemplate.query(
						SQL_SELECT_ROLES_BY_MOVIE_TITLE_AND_YEAR, params,
						new RoleRowMapper())));
	}

	void getRolesForMovies(List<Movie> movies) {
		for (Movie movie : movies) {
			getRolesForMovie(movie);
		}
	}

	private class RoleRowMapper implements RowMapper<Role> {

		@Override
		public Role mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			Role role = new Role();

			role.setId(resultSet.getInt(1));
			role.setTitle(resultSet.getString(2));
			role.setProduction_year(resultSet.getInt(3));
			role.setDescription(resultSet.getString(4));
			role.setCredits(resultSet.getString(5));
			role.getPerson().setId(role.getId());
			role.getPerson().setFirst_name(resultSet.getString(6));
			role.getPerson().setLast_name(resultSet.getString(7));
			role.getPerson().setYear_born(resultSet.getInt(8));
			role.getMovie().setTitle(role.getTitle());
			role.getMovie().setProduction_year(role.getProduction_year());
			role.getMovie().setCountry(resultSet.getString(9));
			role.getMovie().setRun_time(resultSet.getInt(10));
			role.getMovie().setMajor_genre(resultSet.getString(11));

			return role;
		}

	}
}
