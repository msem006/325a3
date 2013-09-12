package com.se325a3.smdb.dao.hibernate;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.se325a3.smdb.dao.SmdbDao;
import com.se325a3.smdb.model.Movie;
import com.se325a3.smdb.model.Person;

@Repository
public class HibernateSmdbDao implements SmdbDao {

	private static final String HQL_SELECT_ACTORS_BY_NAME = "SELECT id, first_name, last_name, year_born "
			+ "FROM person " + "WHERE first_name LIKE :first_name";

	private static final String HQL_SELECT_ACTORS_BY_MOVIE_TITLE = "SELECT p.id, p.first_name, p.last_name, year_born "
			+ "FROM person p, role r " + "WHERE p.id = r.id AND r.title LIKE :title";
	
	private static final String HQL_SELECT_ACTORS_BY_MOVIE_TITLE_AND_YEAR = "SELECT p.id, p.first_name, p.last_name, year_born "
			+ "FROM person p, role r " + "WHERE p.id = r.id AND r.title LIKE :title AND r.production_year = :year";

	private static final String HQL_SELECT_MOVIES_BY_TITLE = "SELECT title, production_year, country, run_time, major_genre "
			+ "FROM movie " + "WHERE title LIKE :title";

	private static final String HQL_SELECT_MOVIES_BY_ACTOR_NAME = "SELECT m.title, m.production_year, m.country, m.run_time, m.major_genre "
			+ "FROM movie m, role r, person p "
			+ "WHERE m.title = r.title AND r.id = p.id "
			+ "AND p.first_name LIKE :first_name";

	private static final String HQL_SELECT_MOVIES_BY_TITLE_AND_YEAR = "SELECT title, production_year, country, run_time, major_genre "
			+ "FROM movie " 
			+ "WHERE title = :title "
			+ "AND production_year = :year";
	
	private static final String HQL_SELECT_MOVIES_BY_ACTOR_ID = "SELECT m.title, m.production_year, m.country, m.run_time, m.major_genre "
			+ "FROM movie m, role r, person p "
			+ "WHERE m.title = r.title AND r.id = p.id "
			+ "AND p.id LIKE :id";
	
	private SessionFactory _sessionFactory;
	
	@Autowired
	public HibernateSmdbDao(SessionFactory sessionFactory) {
		_sessionFactory = sessionFactory;
	}

	@Override
	@Transactional
	public Person getActorById(String id) {
		return (Person) getCurrentSession().get(Person.class, id);
	}

	@Override
	@Transactional
	public List<Person> getActorsByName(String name) {
		return null;
	}

	@Override
	@Transactional
	public List<Person> getActorsByMovieTitle(String title) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	@Transactional
	public List<Person> getActorsByMovieTitleAndYear(String title, String year) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public List<Movie> getMoviesByTitle(String title) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public List<Movie> getMoviesByActorName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public Movie getMovieByTitleAndYear(String title, String year) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public List<Movie> getMoviesByActorID(String id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected final Session getCurrentSession() {
		return _sessionFactory.getCurrentSession();
	}

}
