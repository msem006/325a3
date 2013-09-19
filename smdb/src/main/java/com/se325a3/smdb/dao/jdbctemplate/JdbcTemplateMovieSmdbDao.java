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
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.se325a3.smdb.dao.MovieSmdbDao;
import com.se325a3.smdb.model.Movie;

@Repository
@Transactional
public class JdbcTemplateMovieSmdbDao implements MovieSmdbDao {

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

	private static final String SQL_INSERT_MOVIE = "INSERT INTO MOVIE VALUES (:title, :year, :country, :run_time, :major_genre)";

	private NamedParameterJdbcTemplate _namedParameterJdbcTemplatedbcTemplate;

	private JdbcTemplateRoleSmdbDao _roleSmdbDao;

	@Autowired
	public JdbcTemplateMovieSmdbDao(DataSource dataSource,
			JdbcTemplateRoleSmdbDao roleSmdbDao) {
		_namedParameterJdbcTemplatedbcTemplate = new NamedParameterJdbcTemplate(
				dataSource);
		_roleSmdbDao = roleSmdbDao;
	}

	@Override
	public List<Movie> getMoviesByTitle(String title) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("title", title + "%");
		List<Movie> movies = _namedParameterJdbcTemplatedbcTemplate.query(
				SQL_SELECT_MOVIES_BY_TITLE, params, new MovieRowMapper());
		_roleSmdbDao.getRolesForMovies(movies);
		return movies;
	}

	@Override
	public List<Movie> getMoviesByActorName(String name) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("first_name", name + "%");
		List<Movie> movies = _namedParameterJdbcTemplatedbcTemplate.query(
				SQL_SELECT_MOVIES_BY_ACTOR_NAME, params, new MovieRowMapper());
		_roleSmdbDao.getRolesForMovies(movies);
		return movies;
	}

	@Override
	public Movie getMovieByTitleAndYear(String title, String year) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("title", title);
		params.put("year", year);
		try {
			Movie movie = _namedParameterJdbcTemplatedbcTemplate
					.queryForObject(SQL_SELECT_MOVIE_BY_TITLE_AND_YEAR, params,
							new MovieRowMapper());
			_roleSmdbDao.getRolesForMovie(movie);
			return movie;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public List<Movie> getMoviesByActorID(int id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		List<Movie> movies = _namedParameterJdbcTemplatedbcTemplate.query(
				SQL_SELECT_MOVIES_BY_ACTOR_ID, params, new MovieRowMapper());
		_roleSmdbDao.getRolesForMovies(movies);
		return movies;
	}

	@Override
	public void insertMovie(Movie movie) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("title", movie.getTitle());
		params.addValue("year", movie.getProduction_year());
		params.addValue("country", movie.getCountry());
		params.addValue("run_time", movie.getRun_time());
		params.addValue("major_genre", movie.getMajor_genre());
		_namedParameterJdbcTemplatedbcTemplate.update(SQL_INSERT_MOVIE, params);
	}

	private class MovieRowMapper implements RowMapper<Movie> {

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

	}

}
